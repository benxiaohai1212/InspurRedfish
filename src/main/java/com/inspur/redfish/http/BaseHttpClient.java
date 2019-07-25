/**
 *<p> Copyright © 2018 Inspur Group Co.,Ltd.  版权所有 浪潮集团有限公司 </p>.
 */
package com.inspur.redfish.http;

import java.net.URI;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspur.redfish.common.types.net.HttpMethod;

/**
 * @ClassName: BaseHttpClient
 * @Description: 这个类是对Apache HttpClient的最基础封装，作为所有http请求的最底层实现
 */
public class BaseHttpClient implements AutoCloseable{
	private static final Logger logger = LoggerFactory.getLogger(BaseHttpClient.class);
	private CloseableHttpClient httpClient;
	/**
	 * 请求的一些参数，如超时时间等
	 */
	private RequestConfig config;
	
	//start 台湾联调新增
	private HttpClientBuilder httpClientBuilder;
	
	//end 台湾联调新增
	public BaseHttpClient(RequestConfig config, ObjectMapper objMapper,
			HttpClientBuilder httpClientBuilder) {
		super();
		this.config = config;
		this.objMapper = objMapper;
		this.httpClientBuilder = httpClientBuilder;
	}
	/**
	 * 用来做jackson映射的mapper
	 */
	private ObjectMapper objMapper;
	
	public BaseHttpClient(CloseableHttpClient httpClient, RequestConfig config, ObjectMapper objMapper) {
		super();
		this.httpClient = httpClient;
		this.config = config;
		this.objMapper = objMapper;
	}
	public HttpResponse call(HttpMethod method, URI uri, Object requestEntity, Class responseEntityClass) throws Exception {
		HttpRequestBase request =  buildRequest(method, uri, requestEntity);
		request.setConfig(config);
		
		//台湾联调,现在访问时选哟提供用户名密码,暂时认为所有的密码都是统一的.
		InspurRedfishPasswdProvider.getThenSetDefaultPasswdHeader(request);
		/*
		 * 台湾联调,psme目前复用client会导致后面请求报{ "cc": 7, "error": "Invalid Authentication" },
		 * 因此这里每次都new一个
		 */
		this.httpClient= this.httpClientBuilder.build();
		try(CloseableHttpResponse response = this.httpClient.execute(request)) {
			
//		try(CloseableHttpResponse response = this.httpClient.execute(request)) {
			int statusCode = response.getStatusLine().getStatusCode();
			JavaType javaType = objMapper.constructType(responseEntityClass);
			if(response.getEntity() == null) {
				return new HttpResponse(statusCode, null, uri);
			}
			String entity = EntityUtils.toString(response.getEntity(), "UTF-8");
			if(entity.isEmpty()) {
				return new HttpResponse(statusCode, null, uri);
			}
			Object readEntity = objMapper.readValue(entity, javaType);
			HttpResponse httpResponse = new HttpResponse(statusCode, readEntity, uri);
			System.out.println("<<< url:" + uri + ", response: " + httpResponse + ">>>");
			return httpResponse;
		}
	}
	private HttpRequestBase buildRequest(HttpMethod method, URI uri, Object requestEntity){
		switch (method) {
		case GET:
			HttpGet get = new HttpGet(uri);
			return get;
		case POST:
			HttpPost post = new HttpPost(uri);
			String entity;
			try {
				entity = objMapper.writeValueAsString(requestEntity);
				post.setEntity(new StringEntity(entity, "utf-8"));
			} catch (JsonProcessingException e) {
				logger.error("Json Convert error on '{}' : " + e.getMessage(), requestEntity);
			}
			post.setHeader("Content-Type", "application/json");
			return post;
		case PATCH:
			HttpPatch patch = new HttpPatch(uri);
			String patchEntity;
			try {
				patchEntity = objMapper.writeValueAsString(requestEntity);
				patch.setEntity(new StringEntity(patchEntity, "utf-8"));
			} catch (JsonProcessingException e) {
				logger.error("Json Convert error on '{}' : " + e.getMessage(), requestEntity);
			}
			patch.setHeader("Content-Type", "application/json");
			return patch;
		case DELETE:
			HttpDelete delete = new HttpDelete(uri);
			return delete;
		default:
			break;
		}
		return null;
	}

	@Override
	public void close() throws Exception {
		httpClient.close();
	}
}

