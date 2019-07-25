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


import static com.inspur.redfish.common.utils.Contracts.requiresNonNull;
import static com.inspur.redfish.http.WebClientExceptionUtils.isConnectionExceptionTheRootCause;
import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import com.inspur.redfish.common.context.AppContext;
import com.inspur.redfish.common.sychronization.TaskCanceledException;
import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.common.types.events.DiscoveryFinishedEvent;
import com.inspur.redfish.discovery.external.restgraph.RestGraph;
import com.inspur.redfish.discovery.external.restgraph.RestGraphBuilderFactory;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ExternalServiceReader;
import com.inspur.redfish.south.reader.ExternalServiceReaderFactory;

@SuppressWarnings({"checkstyle:ClassFanOutComplexity", "checkstyle:IllegalCatch"})
@Component
@Scope("prototype")
public class DiscoveryRunner extends com.inspur.redfish.common.sychronization.CancelableRunnable {
    private static final Logger logger = LoggerFactory.getLogger(DiscoveryRunner.class);

    @Autowired
    private ExternalServiceAvailabilityChecker availabilityChecker;

    @Autowired
    private ExternalServiceReaderFactory readerFactory;

    @Autowired
    private RestGraphBuilderFactory restGraphBuilderFactory;


    /**
     * Spring在注册bean的时候，需要调用hashcode方法，而本类的hashcode与uuid有关，因此需要
     * 赋一个初始值，防止空指针异常。此外，本类的equals与hashcode方法都重写了，调用spring的bean工厂的
     * registerBeanDefinition(beanName, beanDefinition)时，注意潜在问题。
     */
    private ServiceEndpoint serviceEndpoint;

//    @Autowired
//    private Event<DiscoveryFinishedEvent> discoveryFinishedEvent;

    @Override
//    @Transactional(REQUIRES_NEW)
//    @TimeMeasured(tag = "[DiscoveryTask]")
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void run() {
        try {
            requiresNonNull(serviceEndpoint.getServiceUuid(), "Service UUID cannot be null, discovery action has not been configured correctly");
//            findService(serviceUuid).ifPresent(this::discover);
            //TODO 这里是否直接触发discovery, 可以
            discover(serviceEndpoint);
        } finally {
            clearCancellationFlag();
        }
    }

//    private Optional<ExternalService> findService(UUID serviceUuid) {
//    	
//    }

    private void discover(ServiceEndpoint service) {
        logger.info("Polling data from {} started", service);
        try (ExternalServiceReader reader = readerFactory.createExternalServiceReaderWithCacheAndRetries(service.getEndpointUri())) {
        	//TODO 这里不再需要校验uuid了
        	//            UUID obtainedUuid = reader.getServiceRoot().getUuid();
//            if (!Objects.equals(obtainedUuid, serviceUuid)) {
//                logger.error("Service UUID change detected, terminating discovery! Expected UUID: {} obtained UUID: {}", serviceUuid, obtainedUuid);
//                availabilityChecker.verifyServiceAvailabilityByUuid(serviceUuid);
//                return;
//            }
            //这个graph放的数据都是json类型的resource而非entity，因此里面的link，set里面存的只是uri
            RestGraph graph = restGraphBuilderFactory.createWithCancelableChecker(this::throwIfEligibleForCancellation).build(reader);
//            mapper.map(graph);
            System.out.println(graph);
            logger.info("Polling data from {} finished", service);
        } catch (WebClientRequestException e) {
            triggerAvailabilityCheckOnConnectionException(service, e);
            logger.warn(format("Unable to process data from %s service", service), e);
        } catch (TaskCanceledException e) {
            logger.info("Discovery was canceled for {} due to: {}", service, e.getMessage());
        } catch (RuntimeException e) {
            logger.error("Error while polling data from " + service, e);
        }

//        discoveryFinishedEvent.fire(new DiscoveryFinishedEvent(serviceUuid));
        //接收者为DiscoveryFinishedObserver.java
        AppContext.context().publishEvent(new DiscoveryFinishedEvent(this, serviceEndpoint.getServiceUuid()));
    }

    private void triggerAvailabilityCheckOnConnectionException(ServiceEndpoint service, WebClientRequestException e) {
        if (isConnectionExceptionTheRootCause(e)) {
            logger.warn("Connection error while getting data from {} service - performing check on this service", service);
            availabilityChecker.verifyServiceAvailabilityByUuid(service);
        }
    }

    private void throwIfEligibleForCancellation() {
//        if (discoveryConfig.get(DiscoveryConfig.class).isDiscoveryCancelable()) {
//            throwWithMessageIfEligibleForCancellation("Discovery was canceled.");
//        }
    	//TODO discovery是否可以取消
    	throwWithMessageIfEligibleForCancellation("Discovery was canceled.");
    }

    

    public ServiceEndpoint getServiceEndpoint() {
		return serviceEndpoint;
	}

	public void setServiceEndpoint(ServiceEndpoint serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscoveryRunner that = (DiscoveryRunner) o;
        return serviceEndpoint.getServiceUuid().equals(that.serviceEndpoint.getServiceUuid());
    }

    @Override
    public int hashCode() {
    	if(serviceEndpoint.getServiceUuid() == null) {
    		return super.hashCode();
    	}
        return serviceEndpoint.getServiceUuid().hashCode();
    }

    @Override
    public String toString() {
        return format("DiscoveryRunner(%s)", serviceEndpoint.getServiceUuid());
    }
}
