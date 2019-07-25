package com.inspur.redfish.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

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
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * HttpClient相关配置.
 * @author 86135
 *
 */
@Configuration
public class HttpClientConfig {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientConfig.class);
	    /**
	     * 首先实例化一个连接池管理器，设置最大连接数、并发连接数
	     * @return
	     */
	    @Bean(name = "httpClientConnectionManager")
	    public PoolingHttpClientConnectionManager getHttpClientConnectionManager(){
	        //SSLContext相关配置
	        SSLContext context = null;
			try {
				context = SSLContext.getInstance("TLSv1.2");
				context.init(null, getTrustManagersWhichTrustAllIssuers(), null);
			} catch (NoSuchAlgorithmException | KeyManagementException e) {
				logger.error("Keystore has not been initialized {}", e.getMessage());
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

	    /**
	     * Builder是RequestConfig的一个内部类
	     * 通过RequestConfig的custom方法来获取到一个Builder对象
	     * 设置builder的连接信息
	     * 这里还可以设置proxy，cookieSpec等属性。有需要的话可以在此设置
	     * @return
	     */
	    @Bean(name = "cfgBuilder")
	    public RequestConfig.Builder getBuilder(){
	        RequestConfig.Builder builder = RequestConfig.custom();
	        return builder;
	    }
}

