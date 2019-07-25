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


import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import com.inspur.redfish.common.context.BeanNames;


/**
 * 
 * @ClassName: SerialExecutorRegistry
 * @Description: 此类被改写，去除了源码中对InstrumentedSerialExecutor这一层的封装。
 *
 * @author: liuchangbj
 * @date: 2018年12月26日 上午9:08:23
 */
@Component
public class SerialExecutorRegistry {
    private static final Logger log = LoggerFactory.getLogger(SerialExecutorRegistry.class);

    @Resource(name = BeanNames.SYNCHRONIZED_TASK_EXECUTOR)
    private ExecutorService executorService;

    private HashMap<Object, SerialExecutor> registry = new HashMap<>();

    /**
     * LockType.WRITE used due to concurrent access to registry map that modifies it (put operation).
     */
//    @Lock(WRITE)
//    @Transactional(SUPPORTS)
//    @AccessTimeout(value = 5, unit = SECONDS)
//    @Transactional(propagation = Propagation.SUPPORTS, timeout = 5)
    public synchronized SerialExecutor getExecutor(Object key) {
        if (!registry.containsKey(key)) {
            log.info("Creating serial executorService for {}", key);
            registry.put(key, createExecutor(key));
        }
        return registry.get(key);
    }

    /**
     * LockType.WRITE used due to concurrent access to registry map that modifies it (remove operation).
     */
//    @Lock(WRITE)
//    @Transactional(SUPPORTS)
//    @AccessTimeout(value = 5, unit = SECONDS)
//    @Transactional(propagation = Propagation.SUPPORTS, timeout = 5)
    public synchronized void unregisterExecutor(Object synchronizationKey) {
        registry.remove(synchronizationKey);
    }

    private SerialExecutor createExecutor(Object key) {
    	return new SerialExecutorImpl(key, executorService);
    }
}
