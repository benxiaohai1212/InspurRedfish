package com.inspur.redfish.discovery.restgraph;

public class RedfishDiscoveryResult {
	
	private boolean flag;
	private RestGraph restGraph;
	private String msg;
	
	public RedfishDiscoveryResult() {
		super();
	}
	public RedfishDiscoveryResult(boolean flag, RestGraph restGraph, String msg) {
		super();
		this.flag = flag;
		this.restGraph = restGraph;
		this.msg = msg;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public RestGraph getRestGraph() {
		return restGraph;
	}
	public void setRestGraph(RestGraph restGraph) {
		this.restGraph = restGraph;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
