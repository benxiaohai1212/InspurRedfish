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

package com.inspur.redfish.common.types.discovery;

import static java.lang.String.format;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

import com.inspur.redfish.common.types.ServiceType;
import com.inspur.redfish.detection.RedfishServiceAuthInfo;

/**
 * Represents REST API entry point of external service
 */
public final class ServiceEndpoint {
	private RedfishServiceAuthInfo authInfo;
    private ServiceType serviceType;
    private UUID serviceUuid;
    private URI endpointUri;

    public ServiceEndpoint(ServiceType serviceType, UUID serviceUuid, URI endpointUri, RedfishServiceAuthInfo authInfo) {
        this.serviceType = serviceType;
        this.serviceUuid = serviceUuid;
        this.endpointUri = endpointUri;
        this.authInfo = authInfo;
    }

    public URI getEndpointUri() {
        return endpointUri;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public UUID getServiceUuid() {
        return serviceUuid;
    }


    public RedfishServiceAuthInfo getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(RedfishServiceAuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public void setServiceUuid(UUID serviceUuid) {
		this.serviceUuid = serviceUuid;
	}

	public void setEndpointUri(URI endpointUri) {
		this.endpointUri = endpointUri;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceEndpoint)) {
            return false;
        }
        ServiceEndpoint endpoint = (ServiceEndpoint) o;
        return Objects.equals(getServiceType(), endpoint.getServiceType())
                && Objects.equals(getServiceUuid(), endpoint.getServiceUuid())
                && Objects.equals(getEndpointUri(), endpoint.getEndpointUri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServiceType(), getServiceUuid());
    }

    @Override
    public String toString() {
        return format("{URI=%s, type=%s, UUID=%s}", endpointUri, serviceType, serviceUuid);
    }
}
