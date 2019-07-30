/*
 * Copyright (c) 2016-2018 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inspur.redfish.common.sychronization;


import java.time.Duration;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

//@SuppressWarnings({"checkstyle:ClassFanOutComplexity"})
class SerialExecutorImpl implements SerialExecutor {
	private static final Logger LOG = LoggerFactory.getLogger(SerialExecutorImpl.class);
    private final ExecutorService delegate;
    //异步队列用到的锁
    private final Object asyncExecutorSynchronizer = new Object();
    private final Object identity;
    //同步执行方法用到的锁
    private final Locker syncLocker = new Locker();
    private final InBox inBox;
    //running指向正在执行的异步任务
    private final CompletableFutureWrapper running = new CompletableFutureWrapper();

    SerialExecutorImpl(final Object identity, final ExecutorService executor) {
        this.identity = identity;
        this.delegate = executor;
        this.inBox = new InBox(identity);
    }

    @Override
    public Object getIdentity() {
        return identity;
    }

    //同步阻塞式的执行
    @Override
    public <E extends Exception, R> R executeSync(Duration timeToWaitFor, Executable<E, R> executable) throws TimeoutException, E {
        //获得同步锁，表示上个executeSync执行完成了
    	syncLocker.lockOrThrow(timeToWaitFor);
        try {
            return execute(timeToWaitFor, executable);
        } finally {
            syncLocker.unlockIfHeldByCurrentThread();
        }
    }
    //对于每个资源,同一时间只能执行一个任务,后续添加的任务在队列里.        
    /*
     * 属于异步执行的任务有：DeepDiscoveryTriggeringTask，DiscoveryRunner（唯一可以cancel的任务）
     * OnServiceDetectRunner，ExternalServiceAvailabilityCheckerTask
     * */
    @Override
    public void executeAsync(Runnable task) {
        LOG.trace("Registering new async task {} for {}", task, identity);
        synchronized (asyncExecutorSynchronizer) {
            inBox.add(task);
            //顺便看一下当前任务执行完了没，执行完了直接取下一个执行（或者说作用其实是为了触发异步任务继续执行）
            if (running.isDone()) {
                scheduleNext();
            }
        }
    }

    @SuppressWarnings({"checkstyle:IllegalCatch"})
    private <E extends Exception, R> R execute(Duration timeToWaitFor, Executable<E, R> task) throws TimeoutException, E {
        LOG.trace("Synchronous task({}) execution has been requested, pausing asynchronous task queue for {}", task, identity);
        //调用此方法后，即使有新的异步任务入队，也不会执行
        inBox.pause();
        try {
        	//尝试取消正在执行的异步任务（针对此资源的一个异步操作）为啥要取消掉而不是直接等待执行完成？注意这个cancel是自定义的
        	//因为在discovery过程中，RestGraphBuilderImpl的71行会一直判断任务有没有被取消，如果被取消，则discovery过程被终止。这样就防止了数据不一致的问题
            cancelRunnableIfApplicable(running.getCurrentRunnable());
            //等待当前异步任务完成
            waitForCurrentlyRunning(timeToWaitFor);
            LOG.debug("Coordinated Task (sync) {}/{} - started", task, identity);
            //阻塞式等待同步任务执行
            R execute = task.execute();
            LOG.debug("Coordinated Task (sync) {}/{} - ended", task, identity);
            return execute;
        } catch (Throwable throwable) {
            LOG.error(format("Task(%s) run for %s finished with exception.", task, identity), throwable);
            LOG.debug("Task coordinator queue dump: {}", inBox.print());
            throw throwable;
        } finally {
            inBox.resume();
            scheduleNext();
        }
    }

    private void cancelRunnableIfApplicable(Runnable runnable) {
        if (runnable instanceof CancelableRunnable) {
            ((CancelableRunnable) runnable).cancel();
        }
    }

    private void waitForCurrentlyRunning(Duration duration) throws TimeoutException {
        if (!running.isDone()) {
            LOG.trace("Previous async action for ({}) is still running, pausing sync task for a moment({})", identity, duration);
            try {
                running.get(duration.toMillis(), MILLISECONDS);
            } catch (ExecutionException | CancellationException e) {
                LOG.trace("Task has been interrupted or terminated unsuccessfully.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void scheduleNext() {
        synchronized (asyncExecutorSynchronizer) {
            if (running.isDone()) {
                Runnable runnable = inBox.poll();
                if (runnable != null) {
                    running.runAsyncAndThenRunAsync(runnable, delegate, this::scheduleNext, identity);
                }
            }
        }
    }

    private static final class CompletableFutureWrapper {
        private CompletableFuture<?> running = CompletableFuture.completedFuture(null);
        private Runnable runnable;

        public boolean isDone() {
            return running.isDone();
        }

        public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return running.get(timeout, unit);
        }

        public synchronized void runAsyncAndThenRunAsync(Runnable runnable, Executor executor, Runnable action, Object identity) {
            this.runnable = runnable;
            this.running = CompletableFuture.runAsync(logAndRun(runnable, identity), executor);
            this.running.thenRunAsync(action);
            this.running.exceptionally(throwable -> {
                LOG.error(format("Asynchronous operation (%s) execution finished with exception", runnable), throwable);
                return null;
            });
        }
        //包装了一下runnable，增加了日志
        private Runnable logAndRun(Runnable runnable, Object identity) {
            return () -> {
                LOG.debug("Coordinated Task (async) {}/{} - started", runnable, identity);
                runnable.run();
                LOG.debug("Coordinated Task (async) {}/{} - ended", runnable, identity);
            };
        }

        public synchronized Runnable getCurrentRunnable() {
            return runnable;
        }
    }
}
