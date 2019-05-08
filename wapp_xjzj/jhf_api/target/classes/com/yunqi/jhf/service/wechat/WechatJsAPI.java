package com.yunqi.jhf.service.wechat;

public class WechatJsAPI {

	
	public static final String WXJS_API_KEY = "wxjs_api";
	private String appId = null;
	private String timestamp = null;
	private String nonceStr = null;
	private String signature = null;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
