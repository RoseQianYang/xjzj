package com.yunqi.common.json;

public class SuccessResult extends JsonResult {

	public SuccessResult() {
		this.setCode(JsonResult.CODE_SUCCESS);
		this.setInfo("操作成功!");
	}

}
