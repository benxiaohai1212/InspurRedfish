package com.inspur.redfish.south.cache;


import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.inspur.redfish.common.types.discovery.ServiceEndpoint;
import com.inspur.redfish.discovery.restgraph.RedfishDiscoveryResult;
import com.inspur.redfish.discovery.restgraph.RestGraph;
/**
 * 南向访问(如detect与discovery)redfish服务时用到的缓存.
 * @author 86135
 *
 */
public class SouthCache {
	/**
	 * 存放已知的redfish endpoints.
	 */
	private static final ConcurrentMap<URI, ServiceEndpoint> knownServiceEndpointsMap = new ConcurrentHashMap<>();
	
	/**
	 * 存放discovery的结果.
	 */
    private static final ConcurrentMap<URI, RedfishDiscoveryResult> discoveryResultMap = new ConcurrentHashMap<>();
    
    public static RedfishDiscoveryResult getRedfishResourceResult(URI uri) {
		return discoveryResultMap.get(uri);
    }
    
    public static void setRedfishResourceResult(URI key, RedfishDiscoveryResult value) {
    	discoveryResultMap.put(key, value);
    }
    
    public static Collection<ServiceEndpoint> getRedfishServiceCandicatesToDetect() {
    	return knownServiceEndpointsMap.values();
    }
}
