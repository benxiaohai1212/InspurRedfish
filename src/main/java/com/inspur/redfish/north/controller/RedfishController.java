package com.inspur.redfish.north.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inspur.redfish.common.types.ServiceType;
import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.north.service.RedfishService;

@RestController
public class RedfishController {
	@Autowired
	private RedfishService redfishService;
	@RequestMapping(value="test", method = RequestMethod.GET)
	public Object fetchRedfishService() {
		URI uri = URI.create("http://192.168.243.11:9443/redfish/v1");
		ServiceEndpoint service = new ServiceEndpoint(ServiceType.SERVER, null, uri, null);
		return redfishService.testFetch(service);
	}
}
