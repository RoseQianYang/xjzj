package com.yunqi.jhf.web.portal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yunqi.jhf.config.Const;

public class WechatSessionUser {

	//  debugMode = true  改为本地模式
	private static boolean debugMode = true;
	
	private String openId = "";
	private String nickname = "";
	private String headimgurl = "";
	private int userId = 0;

	public static void saveUser(HttpServletRequest request, WechatSessionUser user) {
		request.getSession().setAttribute(Const.WECHAT_SESSION_USER_KEY, user);
	}

	public static WechatSessionUser getUser(HttpServletRequest request) {
		if (debugMode) {

			// debug mode ，请换成自己的 openId, userId进行调试
			WechatSessionUser user = new WechatSessionUser();
			user.setOpenId("o5qwcwCgEb-S9nvPjJX1zBPOmGO0");
			user.setNickname("雨末夏伤");
			user.setHeadimgurl("http://thirdwx.qlogo.cn/mmopen/vi_32/L3tvsFZf50ThQb8aau0mQ2NbIN7SejFZRnfic1UyiaWUrolYBdKW8Bo7EibgmDTGQqiaBRoiaJrYfCiceW3aoKcGZnQw/132");
			user.setUserId(10000);
			/*user.setOpenId("o5qwcwM4S7TqCCxKjvMQxLTtaTeE");
			user.setNickname("smile");
			user.setHeadimgurl("http://thirdwx.qlogo.cn/mmopen/vi_32/qTlfVhIBVibadibnQJKK0N9SNSg3HWKtf0iaLFtb4YFUdjuFMQicdqCcgDQUVOibEKmhAZKkPxbHQEp6yjbibtVILZHg/132");
			user.setUserId(10024);*/
			
			return user;
		}
		return (WechatSessionUser) request.getSession().getAttribute(Const.WECHAT_SESSION_USER_KEY);
	}

	public Map<String, String> getProps() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("openId", openId);
		map.put("nickname", nickname);
		map.put("headimgurl", headimgurl);
		return map;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
