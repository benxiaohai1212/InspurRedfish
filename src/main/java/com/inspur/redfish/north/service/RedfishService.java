package com.inspur.redfish.north.service;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;

public interface RedfishService {
	Object testFetch(ServiceEndpoint service);
}
