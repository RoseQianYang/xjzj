package com.yunqi.jhf.web.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yunqi.common.util.HttpUtil;
import com.yunqi.common.util.Sha1Util;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.service.wechat.WechatJsAPI;
import com.yunqi.jhf.service.wechat.WechatService;

/**
 * 只支持拦截get方式
 */
public class WechatJSTicketInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private WechatService wechatService;

	private static final Logger log = Logger.getLogger(WechatJSTicketInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String jsticket = wechatService.getJsticket();
		if (jsticket == null) {
			throw new RuntimeException("jsticket is empty");
		}
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String nonceStr = UUID.randomUUID().toString();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jsapi_ticket", jsticket);
		params.put("timestamp", timestamp);
		params.put("noncestr", nonceStr);
		params.put("url", HttpUtil.getRequestUrl(request));
		String sign = Sha1Util.SHA1(params);
		String str = "jsapi_ticket=" + jsticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url="
				+ HttpUtil.getRequestUrl(request);

		log.info("sha1 加密前字符串" + str);
		log.info("sha1 加密后字符串" + sign);

		WechatJsAPI wxjs = new WechatJsAPI();

		wxjs.setAppId(Const.WECHAT_APP_ID);
		wxjs.setNonceStr(nonceStr);
		wxjs.setSignature(sign);
		wxjs.setTimestamp(timestamp);

		request.getSession().setAttribute(WechatJsAPI.WXJS_API_KEY, wxjs);

		return true;
	}
}
