package com.inspur.redfish.north.service;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
public class RedfishServiceImpl implements RedfishService{
	private RedfishProcessor processor = new RedfishProcessor();
	@Override
	public Object testFetch(ServiceEndpoint service) {
		processor.fetchRedfishResource(service);
		return null;
	}

}
