package com.inspur.redfish.detection;
/**
 * redfish服务的认证信息.
 * @author 86135
 *
 */
public class RedfishServiceAuthInfo {

	private String userName;
	
	private String passwd;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
