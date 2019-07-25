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

package com.inspur.redfish.discovery.external;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.context.BeanNames;

//@Startup
//@Singleton
//@DependsOn({"DiscoveryStartup"})
@Component
public class ServiceRemovalTaskScheduler {
    private static final Long TASK_DELAY_SECONDS = 10L;

//    @Inject
//    private Logger logger;
    private static final Logger logger = LoggerFactory.getLogger(ServiceRemovalTaskScheduler.class);

    @Autowired
    private ServiceRemovalTask task;

//    @Resource
//    private ManagedScheduledExecutorService managedExecutorService;
    @Resource(name = BeanNames.MANAGED_EXECUTOR)
    private ScheduledExecutorService managedExecutorService;

//    @PostConstruct
    public void schedule() {
        logger.debug("Scheduling Service Removal Task...");
        managedExecutorService.scheduleWithFixedDelay(
            task,
            TASK_DELAY_SECONDS,
            TASK_DELAY_SECONDS,
            SECONDS
        );
    }
}
