package com.inspur.redfish.north.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
@Service
public class RedfishServiceImpl implements RedfishService{
	@Autowired
	private RedfishProcessor processor;
	@Override
	public Object testFetch(ServiceEndpoint service) {
		processor.fetchRedfishResource(service);
		return null;
	}

}
