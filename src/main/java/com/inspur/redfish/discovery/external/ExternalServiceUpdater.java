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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;

//@Dependent
@Component
public class ExternalServiceUpdater {
//    @Inject
//    @ServiceLifecycle
//    private ServiceLifecycleLogger logger;
    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceUpdater.class);


//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateExternalService(ServiceEndpoint serviceEndpoint) {
    	//TODO 还没想好这个地方需要不需要
//        ExternalService service = repository.findOrNull(serviceEndpoint.getServiceUuid());
//        if (service == null) {
//            service = repository.create(serviceEndpoint);
////            logger.lifecycleInfo("New service {} discovered.", service);
//            logger.i("New service {} discovered.", service);   
//        }
//
//        if (!Objects.equals(service.getBaseUri(), serviceEndpoint.getEndpointUri())) {
////            logger.lifecycleInfo(
////                "Service's URI for {} was updated to {}",
////                service, serviceEndpoint.getEndpointUri()
////            );
//          logger.i(
//          "Service's URI for {} was updated to {}",
//          service, serviceEndpoint.getEndpointUri()
//      );
//        }
//
//        service.setEventingAvailable(isEventingAvailableForServiceType(serviceEndpoint.getServiceType()));
//        service.setComplementaryDataSource(INBAND_TYPES.contains(serviceEndpoint.getServiceType()));
//        service.setBaseUri(serviceEndpoint.getEndpointUri());
    }
}
