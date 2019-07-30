package com.inspur.redfish.north.service;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.discovery.ServiceDescriptor;
import com.inspur.redfish.discovery.UnrecognizedServiceTypeException;
import com.inspur.redfish.discovery.restgraph.RedfishDiscoveryResult;
import com.inspur.redfish.discovery.restgraph.RestGraph;
import com.inspur.redfish.discovery.restgraph.RestGraphBuilderFactory;
import com.inspur.redfish.http.WebClientRequestException;
import com.inspur.redfish.south.reader.ExternalServiceReader;
import com.inspur.redfish.south.reader.ExternalServiceReaderFactory;
/**
 * 对redfish资源进行遍历的执行器.
 * @author 86135
 *
 */
public class RedfishProcessor {
    private ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
	RestGraphBuilderFactory restGraphBuilderFactory = new RestGraphBuilderFactory();
	private ExternalServiceReaderFactory readerFactory = new ExternalServiceReaderFactory();
	
	private static final Logger logger = LoggerFactory.getLogger(RedfishProcessor.class);
	
	public RedfishDiscoveryResult fetchRedfishResource(ServiceEndpoint candidate) {
		//首先请求一下redfish/v1根目录,识别一下设备.
		try {
			candidate = serviceDescriptor.describe(candidate);
		} catch (UnrecognizedServiceTypeException e) {
			logger.error("service {} detect fail, resason is {}", candidate.getEndpointUri(), e.getMessage());
			return new RedfishDiscoveryResult(false, null, e.getMessage());
		}
		//然后对资源进行遍历.
		 logger.info("Polling data from {} started", candidate);
		 RestGraph graph = null;
	        try (ExternalServiceReader reader = readerFactory.createExternalServiceReaderWithCacheAndRetries(candidate.getEndpointUri())) {
	            //这个graph放的数据都是json类型的resource而非entity，因此里面的link，set里面存的只是uri
	            graph = restGraphBuilderFactory.createWithCancelableChecker(this::throwIfEligibleForCancellation).build(reader);
	            logger.info("Polling data from {} finished", candidate);
	        } catch (WebClientRequestException e) {
	            logger.warn(format("Unable to process data from %s service", candidate), e);
	        } catch (RuntimeException e) {
	            logger.error("Error while polling data from " + candidate, e);
	        }
		return null;
	}
	
	//暂时啥也不干,永远也不会抛出异常
    private void throwIfEligibleForCancellation() {
//      if (discoveryConfig.get(DiscoveryConfig.class).isDiscoveryCancelable()) {
//          throwWithMessageIfEligibleForCancellation("Discovery was canceled.");
//      }
  }
}
