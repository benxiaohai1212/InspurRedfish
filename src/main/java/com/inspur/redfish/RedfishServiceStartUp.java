package com.inspur.redfish;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.inspur.redfish.south.typeidresolver.OdataTypeMatcher;
import com.inspur.redfish.south.typeidresolver.ResourceProvider;
import com.inspur.redfish.south.typeidresolver.ResourceResolver;



/**
 * 在Spring容器初始化时,进行一些bean的初始化.
 * @author 86135
 *
 */
@Component
public class RedfishServiceStartUp implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(RedfishServiceStartUp.class);
	@Autowired
	private ResourceProvider resourceProvider;
	@Override
	public void run(String... args) throws Exception {
		logger.info("starting redfish services...");
		resourceProvider.resourceProvider(); //初始化json2resource映射关系
		List<OdataTypeMatcher> matchers = ResourceResolver.MATCHERS;
		logger.info("The json2resource OData matchers size is " + matchers.size());
	}

}
