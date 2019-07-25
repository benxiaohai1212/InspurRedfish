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

import static com.inspur.redfish.discovery.external.ExternalServiceMonitoringEvent.externalServiceMonitoringStartedEvent;
import static com.inspur.redfish.discovery.external.ExternalServiceMonitoringEvent.externalServiceMonitoringStoppedEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.context.AppContext;
import com.inspur.redfish.common.types.discovery.ServiceEndpoint;

//@Singleton
@Component
public class ExternalServiceMonitor {

    @Autowired
    private ScheduledDiscoveryManager scheduledDiscoveryManager;

//    @Autowired
//    private BeanManager beanManager;

    /**
     * LockType.WRITE used due to concurrent access to discovery manager and subscription monitor.
     */
//    @Lock(WRITE)
//    @Transactional(SUPPORTS)
//    @AccessTimeout(value = 5, unit = SECONDS)
//    @Transactional(propagation = Propagation.SUPPORTS, timeout = 5)
    public synchronized void monitorService(ServiceEndpoint serviceEndpoint) {
//        ExternalService service = externalServiceRepository.find(serviceUuid);
          scheduledDiscoveryManager.scheduleDiscovery(serviceEndpoint);

//        beanManager.fireEvent(externalServiceMonitoringStartedEvent(serviceUuid));
        ApplicationContext context = AppContext.context();
        context.publishEvent(externalServiceMonitoringStartedEvent(this, serviceEndpoint.getServiceUuid()));
    }

    /**
     * LockType.WRITE used due to concurrent access to discovery manager and subscription monitor.
     */
//    @Lock(WRITE)
//    @Transactional(SUPPORTS)
//    @AccessTimeout(value = 5, unit = SECONDS)
//    @Transactional(propagation = Propagation.SUPPORTS, timeout = 5)
    public synchronized void stopMonitoringOfService(ServiceEndpoint serviceEndpoint) {
//        eventSubscriptionMonitor.cancelMonitoring(serviceUuid);
        scheduledDiscoveryManager.cancelDiscovery(serviceEndpoint);
//        beanManager.fireEvent(externalServiceMonitoringStoppedEvent(serviceUuid));
        AppContext.context().publishEvent(externalServiceMonitoringStoppedEvent(this, serviceEndpoint.getServiceUuid()));
        
    }

    
}
