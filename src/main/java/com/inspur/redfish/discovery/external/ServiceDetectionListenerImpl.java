/*
 * Copyright (c) 2015-2018 Intel Corporation
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


import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.context.BeanFactory;
import com.inspur.redfish.common.sychronization.TaskCoordinator;
import com.inspur.redfish.common.types.discovery.ServiceEndpoint;

@Component
public class ServiceDetectionListenerImpl implements ServiceDetectionListener {
	private static final Logger logger = LoggerFactory.getLogger(ServiceDetectionListenerImpl.class);

//	@Autowired
//    private ServiceExplorer serviceExplorer;

//	@Autowired
//    private ExternalServiceUpdater externalServiceUpdater;

	@Autowired
    private TaskCoordinator taskCoordinator;

	@Autowired
	private BeanFactory beanFactory;
    @Override
    public void onServiceDetected(ServiceEndpoint serviceEndpoint) {
        //这里之所以再封一层，因为serviceExplorer的事务全是support(不知道为啥), 只好在外层给run方法加事务
        OnServiceDetectRunner runner = beanFactory.create(OnServiceDetectRunner.class);
        runner.setServiceEndpoint(serviceEndpoint);
        taskCoordinator.registerAsync(serviceEndpoint,runner);
    }

    @Override
    public void onServiceRemoved(ServiceEndpoint serviceEndpoint) {
    	//TODO 当节点被移除时的逻辑, 可以补充.
    }
}
