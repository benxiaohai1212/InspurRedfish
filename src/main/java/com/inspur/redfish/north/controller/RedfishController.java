package com.inspur.redfish.north.controller;

import java.net.URI;

import com.inspur.redfish.common.types.ServiceType;
import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.north.service.RedfishService;
import com.inspur.redfish.north.service.RedfishServiceImpl;

public class RedfishController {
	private RedfishService redfishService = new RedfishServiceImpl();
	public Object fetchRedfishService() {
		URI uri = URI.create("http://192.168.243.11:9443/redfish/v1");
		ServiceEndpoint service = new ServiceEndpoint(ServiceType.SERVER, null, uri, null);
		return redfishService.testFetch(service);
	}
}
