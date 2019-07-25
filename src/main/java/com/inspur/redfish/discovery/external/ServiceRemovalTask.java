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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inspur.redfish.discovery.ServiceExplorer;

//@Dependent
//@SuppressWarnings("checkstyle:IllegalCatch")
@Component
public class ServiceRemovalTask implements Runnable {

    @Autowired
    private Remover remover;
//
//    @Inject
//    @ServiceLifecycle
//    private ServiceLifecycleLogger logger;
    private static final Logger logger = LoggerFactory.getLogger(ServiceRemovalTask.class);
    @Override

    public void run() {
        try {
            remover.remove();
        } catch (Exception e) {
            logger.info("Exception({}) caught during performing ServiceRemovalTask: {}", e.getClass(), e.getMessage());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

//    @Dependent
    @Component
    static class Remover {

//        @Inject
//        @ServiceLifecycle
//        private ServiceLifecycleLogger logger;
    	private static final Logger logger = LoggerFactory.getLogger(Remover.class);

        @Autowired
        private ServiceExplorer serviceExplorer;

//        @Transactional(REQUIRES_NEW)
//        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public void remove() {
        	//TODO 如何删除那些已经过时的资源
//            logger.trace("checking whether unreachable services should be evicted");
//
//            Duration howLongCanBeRetained = config.get(ExternalServiceConfig.class).getServiceRemovalDelay();
//            List<ExternalService> unreachableServices = findUnreachableServices(howLongCanBeRetained);
//
//            unreachableServices.stream()
////                .peek(service -> logger.lifecycleInfo("{} is unreachable longer than {} - will be evicted.", service, howLongCanBeRetained))
//          .peek(service -> logger.i("{} is unreachable longer than {} - will be evicted.", service, howLongCanBeRetained))                
//            .forEach(this::removeService);
        }

//        private List<ExternalService> findUnreachableServices(Duration howLongCanBeRetained) {
//            return externalServiceDao.getAllUnreachableLongerThan(howLongCanBeRetained);
//        }
//
//        private void removeService(ExternalService service) {
//            serviceExplorer.forgetService(service.getUuid());
//            try {
//            	externalServiceDao.remove(service);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//        }
    }
}
