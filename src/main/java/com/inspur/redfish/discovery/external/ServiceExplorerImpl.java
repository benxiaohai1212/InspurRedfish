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

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;

import com.inspur.redfish.common.context.BeanFactory;
import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.discovery.ServiceExplorer;

//@Stateless
@Component
public class ServiceExplorerImpl implements ServiceExplorer {
    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ExternalServiceMonitor externalServiceMonitor;

    @Autowired
    private ExternalServiceAvailabilityChecker availabilityChecker;

    @Override
//    @Transactional(SUPPORTS)
//    @Transactional(propagation = Propagation.SUPPORTS)    
    public void discover(ServiceEndpoint serviceEndpoint) {
        DiscoveryRunner discoveryRunner = beanFactory.create(DiscoveryRunner.class);
        discoveryRunner.setServiceEndpoint(serviceEndpoint);
        discoveryRunner.run();
    }

    @Override
//    @Transactional(propagation = Propagation.SUPPORTS) 
    public void startMonitoringOfService(ServiceEndpoint serviceEndpoint) {
        externalServiceMonitor.monitorService(serviceEndpoint);
    }

    @Override
//    @Transactional(propagation = Propagation.SUPPORTS) 
    public void enqueueVerification(ServiceEndpoint serviceEndpoint) {
        availabilityChecker.verifyServiceAvailabilityByUuid(serviceEndpoint);
    }

    @Override
//    @Transactional(propagation = Propagation.SUPPORTS) 
    public void forgetService(ServiceEndpoint serviceEndpoint) {
        externalServiceMonitor.stopMonitoringOfService(serviceEndpoint);
    }
}
