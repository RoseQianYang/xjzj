package com.yunqi.common.json;

/**
 * 错误返回 JsonResult 类型
 */
public class ErrorResult extends JsonResult {

	public ErrorResult(String errorInfo) {
		this.setCode(JsonResult.CODE_SERVICE_ERROR);
		this.setInfo(errorInfo);

	}

}
