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

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.south.reader.ExternalServiceReaderFactory;

@SuppressWarnings({"checkstyle:ClassFanOutComplexity"})
@Component
@Scope("prototype")
class ExternalServiceAvailabilityCheckerTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ExternalServiceAvailabilityCheckerTask.class);
    @Autowired
    private ExternalServiceReaderFactory readerFactory;


    private ServiceEndpoint serviceEndPoint;

    public ServiceEndpoint getServiceEndPoint() {
		return serviceEndPoint;
	}

	public void setServiceEndPoint(ServiceEndpoint serviceEndPoint) {
		this.serviceEndPoint = serviceEndPoint;
	}

	@Override
//    @Transactional(REQUIRES_NEW)
    @Transactional
    public void run() {
		//TODO 检查设备是否失联
//		requiresNonNull(serviceEndPoint.getServiceUuid(), "serviceUuid");
//        logger.info("Verifying service with UUID {}", serviceEndPoint.getServiceUuid());
//        //TODO 这里需要考虑是不是需要再从缓存里找一下相关资源
////        ExternalService service = externalServiceRepository.findOrNull(serviceUuid);
////        if (service == null) {
//////            logger.w("Requested service({}) is already removed", serviceUuid);
////        	logger.i("Requested service({}) is already removed", serviceUuid);
////            return;
////        }
//
//        try (ExternalServiceReader reader = readerFactory.createExternalServiceReaderWithRetries(service.getBaseUri())) {
//            ServiceRootResource serviceRoot = reader.getServiceRoot();
//            if (!Objects.equals(serviceRoot.getUuid(), serviceUuid)) {
////                logger.w("Service Root with UUID {} is no longer available at URI {}, UUID found at this URI: {}",
////                    serviceUuid, serviceRoot.getUri(), serviceRoot.getUuid());
//              logger.i("Service Root with UUID {} is no longer available at URI {}, UUID found at this URI: {}",
//              serviceUuid, serviceRoot.getUri(), serviceRoot.getUuid());
//                markAsUnreachable(service);
//            } else {
////                logger.d("Service {} still exists", service);
//            	logger.i("Service {} still exists", service);
//            }
//        } catch (WebClientRequestException e) {
//            markAsUnreachable(service);
//        }
    }

//    private void markAsUnreachable(ExternalService service) {
//        if (!service.isReachable()) {
////            logger.lifecycleInfo("Service {} is already marked as unreachable", service);
//        	logger.i("Service {} is already marked as unreachable", service);
//            return;
//        }
//
//        ServiceType serviceType = service.getServiceType();
//        if (!Objects.equals(serviceType, LUI)) {
//            markExternalServiceUnreachable(service);
//        }
//
//        if (Objects.equals(serviceType, RMM)) {
//            markRmmServiceUnreachable(service);
//        }
//    }
//
//    private void markRmmServiceUnreachable(ExternalService service) {
//        List<Chassis> rmmChassis = chassisDao.getChassis(RACK, service);
//        requiresNonNull(rmmChassis, "chassis list");
//        if (rmmChassis.size() == 1) {
//            markRackAsUnreachable(rmmChassis.get(0));
//        } else {
//            throw new IllegalStateException(format("RMM should contain exactly one chassis, but found: %d", rmmChassis.size()));
//        }
//    }
//
//    private void markExternalServiceUnreachable(ExternalService service) {
////        logger.lifecycleInfo(
////            "Service {} has been set to ABSENT state. Scheduling removal of service after {}",
////            service,
////            configHolder.get(ExternalServiceConfig.class).getServiceRemovalDelay()
////        );
//	      logger.i(
//	      "Service {} has been set to ABSENT state. Scheduling removal of service after {}",
//	      service,
//	      configHolder.get(ExternalServiceConfig.class).getServiceRemovalDelay()
//	  );
//        service.markAsNotReachable();
//        composableAssetsDiscoveryListener.updateRelatedComposedNodes(filterByType(service.getOwnedEntities(), ComposableAsset.class));
//    }
//
//    private void markRackAsUnreachable(Chassis chassis) {
//        chassis.setDescription(null);
//        chassis.setManufacturer(null);
//        chassis.setModel(null);
//        chassis.setSku(null);
//        chassis.setSerialNumber(null);
//        chassis.setPartNumber(null);
//        chassis.setAssetTag(null);
//        chassis.setIndicatorLed(null);
//        chassis.setStatus(new Status(ABSENT, null, null));
//        RackChassisAttributes attributes = chassis.getRackChassisAttributes();
//        attributes.setGeoTag(null);
//        attributes.setRackSupportsDisaggregatedPowerCooling(null);
//        chassis.setRackChassisAttributes(attributes);
//    }
//
//    public void setServiceUuid(UUID serviceUuid) {
//        this.serviceUuid = serviceUuid;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        ExternalServiceAvailabilityCheckerTask that = (ExternalServiceAvailabilityCheckerTask) o;
//        return Objects.equals(serviceUuid, that.serviceUuid);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(serviceUuid);
//    }
}
