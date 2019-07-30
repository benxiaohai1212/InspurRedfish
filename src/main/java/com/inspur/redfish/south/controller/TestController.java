package com.inspur.redfish.south.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inspur.redfish.common.types.ServiceType;
import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.discovery.ServiceDetectionListener;

@RestController
public class TestController {
	@Autowired
	private ServiceDetectionListener listener;
	@RequestMapping(value="test2", method = RequestMethod.GET)
	public void test2() {
		URI uri = URI.create("http://192.168.243.11:9443/redfish/v1");
		ServiceEndpoint end = new ServiceEndpoint(ServiceType.SERVER, UUID.fromString("3420f2d0-aebc-11e9-9028-294cfa54c219"), uri, null);
		listener.onServiceDetected(end);
	} 
}
