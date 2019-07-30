package com.inspur.redfish;

import com.inspur.redfish.north.controller.RedfishController;

public class InspurRedfishApplication {

	public static void main(String[] args) {
		RedfishController c = new RedfishController();
		c.fetchRedfishService();
	}
	
	

}
