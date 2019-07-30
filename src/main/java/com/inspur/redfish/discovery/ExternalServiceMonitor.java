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

package com.inspur.redfish.discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;

@Component
public class ExternalServiceMonitor {

    @Autowired
    private ScheduledDiscoveryManager scheduledDiscoveryManager;

    /**
     * LockType.WRITE used due to concurrent access to discovery manager and subscription monitor.
     */
//    @Lock(WRITE)
//    @Transactional(SUPPORTS)
//    @AccessTimeout(value = 5, unit = SECONDS)
//    @Transactional(propagation = Propagation.SUPPORTS, timeout = 5)
    public synchronized void monitorService(ServiceEndpoint serviceEndpoint) {
          scheduledDiscoveryManager.scheduleDiscovery(serviceEndpoint);
    }

    /**
     * LockType.WRITE used due to concurrent access to discovery manager and subscription monitor.
     */
//    @Lock(WRITE)
//    @Transactional(SUPPORTS)
//    @AccessTimeout(value = 5, unit = SECONDS)
//    @Transactional(propagation = Propagation.SUPPORTS, timeout = 5)
    public synchronized void stopMonitoringOfService(ServiceEndpoint serviceEndpoint) {
        scheduledDiscoveryManager.cancelDiscovery(serviceEndpoint);
    }

    
}
