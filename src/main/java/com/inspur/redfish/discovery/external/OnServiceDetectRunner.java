/**
 *<p> Copyright © 2018 Inspur Group Co.,Ltd.  版权所有 浪潮集团有限公司 </p>.
 */
package com.inspur.redfish.discovery.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
import com.inspur.redfish.discovery.ServiceExplorer;


/**
 * @ClassName: OnServiceDetectRunner
 * @Description: TODO
 *
 * @author: liuchangbj
 * @date: 2018年12月26日 上午10:52:26
 */
@Component
@Scope("prototype")
public class OnServiceDetectRunner implements Runnable {

private static final Logger logger = LoggerFactory.getLogger(OnServiceDetectRunner.class);

@Autowired
private ServiceExplorer serviceExplorer;
@Autowired
private ExternalServiceUpdater externalServiceUpdater;

private ServiceEndpoint serviceEndpoint;

public ServiceEndpoint getServiceEndpoint() {
	return serviceEndpoint;
}

public void setServiceEndpoint(ServiceEndpoint serviceEndpoint) {
	this.serviceEndpoint = serviceEndpoint;
}

	//因为 serviceExplorer.startMonitoringOfService没有事务会报错，所以在run上添加事务
//	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void run() {
        // Do not change form of this log, as this is being used in developer tools!
        logger.info("Service {} detected", serviceEndpoint);

        switch (serviceEndpoint.getServiceType()) {
            default:
            	/*
            	 * 根据UUID判断是否数据库里存在这个数据，如果没有则插入，有了的话，更新一下baseUrl
            	 * 同时，还会根据类型判断这个数据源会不会event，是不是ComplementaryDataSource
            	 * */
                externalServiceUpdater.updateExternalService(serviceEndpoint);
                serviceExplorer.startMonitoringOfService(serviceEndpoint);
                break;
        }
    }
}
