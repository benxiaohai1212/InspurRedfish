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

package com.inspur.redfish.discovery;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;

import com.inspur.redfish.common.context.BeanFactory;
import com.inspur.redfish.common.context.BeanNames;
import com.inspur.redfish.common.sychronization.TaskCoordinator;
import com.inspur.redfish.common.types.discovery.ServiceEndpoint;

//@Singleton
@Component
public class ScheduledDiscoveryManager {
    private Map<ServiceEndpoint, ScheduledFuture<?>> discoveryTasks = new HashMap<>();
    private Map<ServiceEndpoint, DiscoveryRunner> discoveryRunners = new HashMap<>();

//    @Inject
//    @Named(SYNCHRONIZED_TASK_EXECUTOR)
    @Autowired
    @Resource(name=BeanNames.SYNCHRONIZED_TASK_EXECUTOR)
    private ScheduledExecutorService discoveryTaskExecutor;

    @Autowired
    private TaskCoordinator taskCoordinator;

    @Autowired
    private BeanFactory beanFactory;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledDiscoveryManager.class);


    /**
     * LockType.WRITE used due to concurrent access to discovery tasks map that modifies it (put operation)
     */
//    @Lock(WRITE)
//    @Transactional(SUPPORTS)
//    @AccessTimeout(value = 5, unit = SECONDS)
//    @Transactional(propagation = Propagation.SUPPORTS, timeout = 5)
    public synchronized void scheduleDiscovery(ServiceEndpoint serviceEndPoint) {
        if (!discoveryTasks.containsKey(serviceEndPoint)) {
            ScheduledFuture<?> discoveryTask = scheduleDiscoveryTask(serviceEndPoint);
            discoveryTasks.put(serviceEndPoint, discoveryTask);
        } else {
            logger.warn("Discovery is already scheduled for service {}", serviceEndPoint);
        }
    }

    /**
     * LockType.WRITE used due to concurrent access to discovery tasks map that modifies it (remove operation)
     */
//    @Lock(WRITE)
//    @Transactional(SUPPORTS)
//    @AccessTimeout(value = 5, unit = SECONDS)
//    @Transactional(propagation = Propagation.SUPPORTS, timeout = 5)
    public synchronized void cancelDiscovery(ServiceEndpoint serviceEndPoint) {
    	//感觉这里逻辑有点问题, 如果110行的调度线程cancel时, 调度线程刚刚开始执行, 那么这时把discoveryRunners-
    	//-清除了, 调度线程会再次new 出来 DiscoveryRunner对象放到map里,详见getDiscoveryRunner方法
        discoveryRunners.remove(serviceEndPoint);
        ScheduledFuture discoveryTask = discoveryTasks.remove(serviceEndPoint);
        if (discoveryTask != null) {
            logger.info("Discovery cancelled for service {}", serviceEndPoint);
            //这是调用java.util.concurrent.future包的cancel, 调度器不再周期性执行
            discoveryTask.cancel(false);
        } else {
            logger.warn("Discovery was already cancelled for service {} or was not scheduled", serviceEndPoint);
        }
    }

    private ScheduledFuture<?> scheduleDiscoveryTask(ServiceEndpoint serviceEndPoint) {
//        DiscoveryConfig discoveryConfig = discoveryConfigHolder.get(DiscoveryConfig.class);
//        long discoveryIntervalSeconds = discoveryConfig.getDiscoveryIntervalSeconds();
    	//TODO discovery config需要单独的类来初始化
    	long discoveryIntervalSeconds = 600;
        long discoveryDelaySeconds = 0;

        return discoveryTaskExecutor.scheduleWithFixedDelay(
            () -> enqueueDiscovery(serviceEndPoint),
            discoveryDelaySeconds,
            discoveryIntervalSeconds,
            SECONDS
        );
    }

    private void enqueueDiscovery(ServiceEndpoint serviceEndPoint) {
        DiscoveryRunner discoveryRunner = getDiscoveryRunner(serviceEndPoint);
        taskCoordinator.registerAsync(serviceEndPoint, discoveryRunner);
    }

    private DiscoveryRunner getDiscoveryRunner(ServiceEndpoint serviceEndPoint) {
        DiscoveryRunner discoveryRunner = discoveryRunners.get(serviceEndPoint);
        if (discoveryRunner == null) {
            discoveryRunner = beanFactory.create(DiscoveryRunner.class);
            discoveryRunner.setServiceEndpoint(serviceEndPoint);
            discoveryRunners.put(serviceEndPoint, discoveryRunner);
        }

        return discoveryRunner;
    }
}
