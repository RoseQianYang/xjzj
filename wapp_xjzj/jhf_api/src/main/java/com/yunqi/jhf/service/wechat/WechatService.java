package com.yunqi.jhf.service.wechat;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunqi.common.ServiceException;
import com.yunqi.common.util.HttpUtil;
import com.yunqi.jhf.config.Const;

@Service
public class WechatService {

	private String access_token = null;
	private long access_token_expire_time = 0;

	private String jsticket = null;
	private long jsticket_expire_time = 0;

	private static Logger log = Logger.getLogger(WechatService.class);

	public String getAccessToken() throws ServiceException {
		long now = System.currentTimeMillis();
		if (now >= access_token_expire_time) {
			refreshAccessToken();
		} else {
			log.info("重复利用 accessToken，距离过期还有 " + ((access_token_expire_time - now) / 1000) + "秒");
		}
		return access_token;
	}

	private void refreshAccessToken() throws ServiceException {
		log.info("更新 AccessToken:");
		long now = System.currentTimeMillis();
		// 获取access_token
		Map<String, String> p = new HashMap<String, String>();
		p.put("appid", Const.WECHAT_APP_ID);
		p.put("secret", Const.WECHAT_APP_SECRET);
		p.put("grant_type", "client_credential");
		String body = HttpUtil.doHttpGet("https://api.weixin.qq.com/cgi-bin/token", p);
		JSONObject jsonObj = JSON.parseObject(body);
		access_token = jsonObj.getString("access_token");
		access_token_expire_time = now + jsonObj.getLongValue("expires_in") * 1000;
	}

	public String getJsticket() throws ServiceException {
		long now = System.currentTimeMillis();
		if (now >= jsticket_expire_time) {
			refreshJsticket();
		} else {
			log.info("重复利用 jsticket，距离过期还有 " + ((jsticket_expire_time - now) / 1000) + "秒");
		}
		return jsticket;
	}

	private void refreshJsticket() throws ServiceException {
		log.info("更新 jsticket:");
		long now = System.currentTimeMillis();
		Map<String, String> p2 = new HashMap<String, String>();
		p2.put("access_token", getAccessToken());
		p2.put("type", "jsapi");
		String body2 = HttpUtil.doHttpGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket", p2);
		JSONObject jsonObj2 = JSON.parseObject(body2);
		String errcode = jsonObj2.getString("errcode");
		jsticket = jsonObj2.getString("ticket");
		jsticket_expire_time = now + jsonObj2.getLongValue("expires_in") * 1000;
	}

}
