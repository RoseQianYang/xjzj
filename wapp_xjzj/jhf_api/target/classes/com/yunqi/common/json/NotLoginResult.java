package com.yunqi.common.json;

public class NotLoginResult extends JsonResult {

	public NotLoginResult() {
		this.setCode(JsonResult.CODE_NOT_LOGIN);
		this.setInfo("会话过期或者未登录，请刷新页面重新登录后访问！");
	}

}
