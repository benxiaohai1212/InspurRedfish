package com.inspur.redfish.detection;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.discovery.ServiceDescriptor;
import com.inspur.redfish.discovery.ServiceDetectionListener;
import com.inspur.redfish.discovery.UnrecognizedServiceTypeException;
import com.inspur.redfish.discovery.restgraph.RedfishDiscoveryResult;
import com.inspur.redfish.south.cache.SouthCache;



@Component
public class ServiceChecker {

	private static final Logger logger = LoggerFactory.getLogger(ServiceChecker.class);

	@Autowired
    private ServiceDetectionListener serviceDetectionListener;

	@Autowired
    private ServiceDescriptor serviceDescriptor;
	
//	@Transactional(propagation = Propagation.SUPPORTS, timeout = 5)
    public synchronized void triggerEndpointCandidatesCheck() {
        Collection<ServiceEndpoint> candidatesForPoll = SouthCache.getRedfishServiceCandicatesToDetect();
        candidatesForPoll.forEach(this::detectServiceUsingServiceEndpointCandidate);
    }
	/**
	 * 
	 * @param candidate
	 * @throws UnrecognizedServiceTypeException 
	 */
    private void detectServiceUsingServiceEndpointCandidate(ServiceEndpoint candidate) {
    	//这个方法去请去目标的redfish/v1目录判断是否为redfish服务，否则会抛出UnrecognizedServiceTypeException异常
        ServiceEndpoint serviceEndpoint;
		try {
			serviceEndpoint = serviceDescriptor.describe(candidate);
	        /*设备如果被识别,则触发周期性的异步discovery,将设备存入缓存*/
	        serviceDetectionListener.onServiceDetected(serviceEndpoint);
		} catch (UnrecognizedServiceTypeException e) {
			//设备无法被识别,不会开启discovery线程
			logger.error("service {} detect fail, resason is {}", candidate.getEndpointUri(), e.getMessage());
			//如果之前开启了对此资源的discovery,那么不再discovery这个设备
			serviceDetectionListener.onServiceRemoved(candidate);
		}
    }
}
