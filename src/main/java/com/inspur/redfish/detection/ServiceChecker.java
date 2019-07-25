package com.inspur.redfish.detection;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.discovery.external.ServiceDescriptor;
import com.inspur.redfish.discovery.external.ServiceDetectionListener;
import com.inspur.redfish.discovery.external.UnrecognizedServiceTypeException;



@Component
public class ServiceChecker {

	private static final Logger logger = LoggerFactory.getLogger(ServiceChecker.class);

	@Autowired
    private ServiceDetectionListener serviceDetectionListener;

	@Autowired
    private ServiceDescriptor serviceDescriptor;

	/**
	 * 
	 * @param candidate
	 */
    private void detectServiceUsingServiceEndpointCandidate(RedfishServiceCandidate candidate) {
        try {
        	//这个方法去请去目标uri，会抛出UnrecognizedServiceTypeException异常，否则这个设备被识别
            ServiceEndpoint serviceEndpoint = serviceDescriptor.describe(candidate.getEndpointUri(), candidate.getServiceType());
          //TODO
            /*这个地方需要考虑, 当资源已经发现是存在状态的时候, 那么应该怎么触发
             * discovery, discovery是异步周期执行的, 如果结果缓存里没有, 则应该阻塞, 如果
             * 缓存里已有资源的信息,则直接从缓存里取*/
            //            serviceDetectionListener.onServiceDetected(serviceEndpoint);
            
        } catch (UnrecognizedServiceTypeException e) {
        	//TODO 资源无法识别, 如何处理, 比如是突然失联的, 是否需要重试, 如果确定是失联, 需要放到failmap里
        }
    }
}
