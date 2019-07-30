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

package com.inspur.redfish.detection;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.context.BeanNames;
import com.inspur.redfish.detection.config.RedfishDetectConfig;
import com.inspur.redfish.detection.task.ProvideEndpointsScheduledTask;

//@ApplicationScoped
@Component
public class RedfishServiceDetector {
    @Resource(name = BeanNames.MANAGED_EXECUTOR)
    private ScheduledExecutorService managedExecutorService;

	private static final Logger logger = LoggerFactory.getLogger(RedfishServiceDetector.class);

	@Autowired
	private ProvideEndpointsScheduledTask provideEndpointsScheduledTask;

    public void init() {
        logger.info("Initializing DHCP based service detector...");
        try {
            long checkInterval = RedfishDetectConfig.getFilesCheckIntervalInSeconds();
            //周期性的执行设备的detect
            managedExecutorService.scheduleWithFixedDelay(provideEndpointsScheduledTask, checkInterval, checkInterval, SECONDS);
        } catch (RejectedExecutionException e) {
            logger.error("Application failed to start properly. Service polling is disabled.", e);
            throw e;
        }
    }
}
