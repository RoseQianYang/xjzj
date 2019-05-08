package com.yunqi.jhf.web.portal.my;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.yunqi.jhf.web.portal.WechatSessionUser;

/**
 * 用户中心首页
 * 
 * @author tuweiguo
 */
@CrossOrigin
@RestController
@RequestMapping(value = "usercenter")
public class TUserInfoAction {
	
	/**
	 * 用户中心首页
	 */
	@RequestMapping(value = "/userCenterList")
	public ModelAndView userAddressList(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) throws Exception {
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		JSONObject obj = new JSONObject();
		obj.putAll(sessUser.getProps());
	    ModelAndView mav = new ModelAndView("/my/tusercenter");
	    mav.addObject("sessUser", sessUser);
		return mav;
	}
	
}