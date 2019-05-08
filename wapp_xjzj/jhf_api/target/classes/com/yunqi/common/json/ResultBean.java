package com.yunqi.common.json;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author wangsong
 *
 */
public class ResultBean {
	private Integer code;
	
	private List<?> value;
	
	private JSONObject jsonValue;
	
	private JSONObject jsonValue1;
	
	private JSONObject jsonValue2;
	
	private JSONArray jsonArrayValue;
	
	private String msg;
	
	
	public JSONObject getJsonValue1() {
		return jsonValue1;
	}
	public void setJsonValue1(JSONObject jsonValue1) {
		this.jsonValue1 = jsonValue1;
	}
	public JSONObject getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(JSONObject jsonValue) {
		this.jsonValue = jsonValue;
	}
	public JSONObject getJsonValue2() {
		return jsonValue2;
	}
	public void setJsonValue2(JSONObject jsonValue2) {
		this.jsonValue2 = jsonValue2;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	public List<?> getValue() {
		return value;
	}
	public void setValue(List<?> value) {
		this.value = value;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public JSONArray getJsonArrayValue() {
		return jsonArrayValue;
	}
	public void setJsonArrayValue(JSONArray jsonArrayValue) {
		this.jsonArrayValue = jsonArrayValue;
	}
	
}

