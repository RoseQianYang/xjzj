package com.yunqi.common.json;

/**
 * 全局默认返回的数据结构
 * {
 *     code  :
 *     info  :
 *     data  :
 * }
 */
public class JsonResult {

	public String toJsonString() {
		return JsonUtil.toJson(this);
	}

	private int code = 0;
	private String info = "success";
	private Object data = null;

	/**
	 * 状态码约定
	 * 状态码：0-请求成功 1-请求失败 -1身份认证失败  -2未知错误
	 */
	public final static int CODE_SUCCESS = 0;
	public final static int CODE_SERVICE_ERROR = 1;
	public final static int CODE_NOT_LOGIN = -1;
	public final static String SUCCESS = "success";

	public Object getData() {
		return data;
	}

	public JsonResult setData(Object data) {
		this.data = data;
		return this;
	}

	public JsonResult() {
		this.code = CODE_SUCCESS;
	}

	public JsonResult(int code) {
		this.code = code;
	}

	public JsonResult success(String info) {
		this.code = CODE_SUCCESS;
		this.info = info;
		return this;
	}
	
	public JsonResult error(String info) {
		if (this.code != CODE_SERVICE_ERROR) {
			this.code = CODE_SERVICE_ERROR;
		}
		this.info = info;
		return this;
	}

	public int getCode() {
		return code;
	}

	public JsonResult setCode(int code) {
		this.code = code;
		return this;
	}

	public String getInfo() {
		return info;
	}

	public JsonResult setInfo(String info) {
		this.info = info;
		return this;
	}
}
