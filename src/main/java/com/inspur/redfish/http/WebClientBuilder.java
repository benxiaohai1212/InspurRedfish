/*
 * Copyright (c) 2016-2018 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inspur.redfish.http;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.UPPER_CAMEL_CASE;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inspur.redfish.common.types.ConnectionParameters;
import com.inspur.redfish.south.redfish.RedfishClient;
import com.inspur.redfish.south.redfish.SocketErrorAwareHttpClient;


public class WebClientBuilder {
    private ObjectMapper objMapper = initMapper();

    private PoolingHttpClientConnectionManager httpClientConnectionManager = getHttpClientConnectionManager();
    
    private  RequestConfig.Builder cfgBuilder = RequestConfig.custom();

    public Builder newInstance(URI baseUri) {
        return new Builder(baseUri);
    }
	//TODO 这里应该从配置文件里读取,现在简单设置一下
    private ConnectionParameters connectionParameters = new ConnectionParameters(5, 5, 200, 20);
    
    private BaseHttpClient getBaseClient() {
		  httpClientConnectionManager.setMaxTotal(connectionParameters.getConnectionPoolSize());
		  httpClientConnectionManager.setDefaultMaxPerRoute(connectionParameters.getMaxPooledPerRoute());
	      HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	      httpClientBuilder.setConnectionManager(httpClientConnectionManager)
	      .setConnectionManagerShared(true);
//	      CloseableHttpClient client = httpClientBuilder.build();
	      cfgBuilder.setConnectTimeout((int) (connectionParameters.getServiceConnectionTimeout()*1000));
	      cfgBuilder.setSocketTimeout((int) (connectionParameters.getServiceSocketTimeout()*1000));
	      RequestConfig requestConfig = cfgBuilder.build();
//	      return new BaseHttpClient(client, requestConfig, objMapper);
	      //台湾联调修改
	      return new BaseHttpClient(requestConfig, objMapper, httpClientBuilder);
	}

	  private ObjectMapper initMapper() {
        ObjectMapper mapper = new ObjectMapper()
	        .setPropertyNamingStrategy(UPPER_CAMEL_CASE)
	        .registerModule(new JavaTimeModule())
	        .registerModule(new SerializersProvider().getSerializersModule())
	        .disable(FAIL_ON_UNKNOWN_PROPERTIES)
	        .enable(FAIL_ON_NULL_FOR_PRIMITIVES);
        return mapper;
	  }
    public class Builder {
        private URI baseUri;
        private boolean retryable;
        private boolean cachable;

        Builder(URI baseUri) {
            this.baseUri = baseUri;
        }

        public Builder retryable() {
            retryable = true;
            return this;
        }

        public Builder cachable() {
            cachable = true;
            return this;
        }

        public WebClient build() {
        	BaseHttpClient client = getBaseClient();
            SimpleHttpClient httpClient = new SimpleHttpClient(client);
            SocketErrorAwareHttpClient socketErrorAwareHttpClient = new SocketErrorAwareHttpClient(httpClient);
            RedfishClient redfishClient = new RedfishClient(baseUri, socketErrorAwareHttpClient);
            WebClient webClient = new WebClientBasedOnRedfishClient(redfishClient);

            if (retryable) {
                webClient = new WebClientWithRetrying(webClient);
            }
            if (cachable) {
                webClient = new CachedWebClient(webClient);
            }
            return webClient;
        }
    }
    
    private PoolingHttpClientConnectionManager getHttpClientConnectionManager(){
        //SSLContext相关配置
        SSLContext context = null;
		try {
			context = SSLContext.getInstance("TLSv1.2");
			context.init(null, getTrustManagersWhichTrustAllIssuers(), null);
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Keystore has not been initialized", e);
		}
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE))
                .build();
        
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        return httpClientConnectionManager;
    }
    
    /**
	 * 忽略所有对server的认证.
	 * @return
	 */
    public TrustManager[] getTrustManagersWhichTrustAllIssuers() {
        return new TrustManager[]{
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };
    }
}
