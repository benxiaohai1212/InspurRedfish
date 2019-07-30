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

//import static com.intel.podm.client.WebClientExceptionUtils.tryGetUnderlyingRedirectionException;
import static org.apache.commons.lang3.StringUtils.stripEnd;

import java.net.URI;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.types.ServiceType;
import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.http.WebClient;
import com.inspur.redfish.http.WebClientBuilder;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.redfish.response.UnexpectedRedirectionException;
import com.inspur.redfish.south.resources.redfish.ServiceRootResource;

/**
 * Service Detection implementation based on obtaining UUID of service
 * being detected at given URI using REST client.
 */
//@ApplicationScoped
//@SuppressWarnings({"checkstyle:ClassFanOutComplexity"})
@Component
public class ServiceDescriptor {
    private static final ServiceType UNDEFINED_SERVICE_TYPE = null;

    @Autowired
    private WebClientBuilder webClientBuilder;



    public ServiceEndpoint describe(ServiceEndpoint serviceEndPoint) throws UnrecognizedServiceTypeException {
    	URI serviceUri = serviceEndPoint.getEndpointUri();
    	try (WebClient webClient = webClientBuilder.newInstance(serviceUri).retryable().build()) {
            ServiceRootResource serviceRoot = fetchServiceRoot(webClient, serviceUri);
            ServiceType serviceType = determineServiceType(UNDEFINED_SERVICE_TYPE, serviceRoot);
            serviceEndPoint.setServiceType(serviceType);
            serviceEndPoint.setServiceUuid(serviceRoot.getUuid());
            return serviceEndPoint;
        } catch (WebClientRequestException e) {
            throw new UnrecognizedServiceTypeException("Unable to retrieve Rack Scale service root for: " + serviceUri, e);
        }
    }

    private ServiceRootResource fetchServiceRoot(WebClient webClient, URI serviceRootUri) throws WebClientRequestException {
        try {
            return getServiceRootResource(webClient, serviceRootUri);
        } catch (WebClientRequestException e) {
//            Optional<UnexpectedRedirectionException> redirectionException = tryGetUnderlyingRedirectionException(e);
//            if (redirectionException.isPresent() && isValidRedfishServiceRootRedirect(redirectionException.get())) {
//                URI redirectUri = redirectionException.get().getRedirectUri();
//                try (WebClient webClientForRedirect = webClientBuilder.newInstance(redirectUri).retryable().build()) {
//                    return getServiceRootResource(webClientForRedirect, redirectUri);
//                }
//            } else {
//                throw e;
//            }
        	//TODO 是否需要考虑重定向这种异常
        	throw e;
        }
    }

    private ServiceRootResource getServiceRootResource(WebClient webClient, URI serviceRootUri) throws WebClientRequestException {
        ServiceRootResource serviceRootResource = (ServiceRootResource) webClient.get(URI.create(serviceRootUri.getPath()));
        serviceRootResource.setUri(serviceRootUri);
        return serviceRootResource;
    }

//    private boolean isValidRedfishServiceRootRedirect(UnexpectedRedirectionException e) {
//        URI redirectUri = e.getRedirectUri();
//        if (redirectUri == null) {
//            return false;
//        }
//
//        return Objects.equals(
//            stripEnd(e.getExpectedUri().toString(), "/"),
//            stripEnd(redirectUri.toString(), "/")
//        );
//    }

    private ServiceType determineServiceType(ServiceType predefinedServiceType, ServiceRootResource redfishService) {
        ServiceType serviceType = predefinedServiceType;
        if (serviceType == UNDEFINED_SERVICE_TYPE) {
//            serviceType = serviceDetectionConfig.get(ServiceDetectionConfig.class).getServiceTypeMapping().getServiceTypeForName(redfishService.getName());
//            if (serviceType == null) {
//                serviceType = PSME;
//            }
        	//TODO 可以根据root目录下的name来判断是什么类型的redfish服务
        	serviceType = ServiceType.SERVER;
        }
        return serviceType;
    }
}
