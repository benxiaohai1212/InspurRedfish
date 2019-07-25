package com.inspur.redfish.http;

import java.nio.charset.StandardCharsets;

import org.apache.http.Header;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.auth.BasicScheme;

/**
 * 目前与浪潮的BMC redfish通信都需要密码,如curl --insecure -u admin:admin -X GET https://192.168.1.151/redfish/v1.
 * @author 86135
 *
 */
public class InspurRedfishPasswdProvider {
	/**
	 * 目前默认都是admin, 后续可以增加方法,根据配置文件按照IP查找.
	 * @return
	 */
	public static void getThenSetDefaultPasswdHeader(HttpRequestBase request) {
		//目前默认都是admin, 后续可以视情况从其他地方获取
		String userName = "admin";
		String passwd = "admin";
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName, passwd);
        Header header;
		try {
			header = new BasicScheme(StandardCharsets.UTF_8).authenticate(creds, request, null);
			request.setHeader(header);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}
}
