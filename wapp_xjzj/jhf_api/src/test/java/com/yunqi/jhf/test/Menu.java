package com.yunqi.jhf.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunqi.common.util.HttpUtil;

public class Menu {

	// 更新菜单步骤
	// 1  获取access_token，需要配置白名单
	// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx4fb49455265a775f&secret=e75880e56ec6d234ecced549a5848bfd
	// 2 替换 createMenu() token = "xxx"
	// 3. excute main()
	
	public static void main(String[] args) throws Exception {
		createMenu();
		// deleteMenu();
	}

	public static void createMenu() throws Exception {
		String token = "9_naDMnPPoklrcbCBWj3wH7C2YE8xGHPt3yWU-xdWjxDdvhwKqQVmaVyF7Ctlg_P7w8E-TBfDOicxdulQmQrABgvaVYVmaBzsFQ7Hx1Piclf6cyBW8X3OEzBHkx9EAXZcCJAKHJ";
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create";

		JSONObject rootMenu = new JSONObject();

		JSONObject service = new JSONObject();
		service.put("name", "旅游服务");

		JSONObject shop = new JSONObject();
		shop.put("name", "商城服务");

		JSONObject personal = new JSONObject();
		personal.put("name", "个人中心");

		rootMenu.put("button", Arrays.asList(service, shop, personal));

		// 旅游服务菜单子菜单
		JSONObject home = new JSONObject();
		home.put("name", "首页");
		home.put("url", "http://www.jhffcly.com/jhf/wechatIndex.do");
		home.put("type", "view");

		JSONObject fangche = new JSONObject();
		fangche.put("name", "房车品鉴");
		fangche.put("url", "http://www.jhffcly.com/jhf/vehicle/enjoy/TVehicleHomeAction.do");
		fangche.put("type", "view");

		JSONObject yingdi = new JSONObject();
		yingdi.put("name", "营地展示");
		yingdi.put("url", "http://www.jhffcly.com/jhf/front/campsite/campsiteList.do");
		yingdi.put("type", "view");

		JSONObject xianlu = new JSONObject();
		xianlu.put("name", "露营路线");
		xianlu.put("url", "http://www.jhffcly.com/jhf/front/mobileroute/mobileList.do");
		xianlu.put("type", "view");

		JSONObject share = new JSONObject();
		share.put("name", "驴友共享");
		share.put("url", "http://www.jhffcly.com/jhf/share/shareList.do?suserName=null");
		share.put("type", "view");

		service.put("sub_button", Arrays.asList(home, fangche, yingdi, xianlu, share));

		/////////////////////////////////////////////////////
		JSONObject zhuangbei = new JSONObject();
		zhuangbei.put("name", "装备商城");
		zhuangbei.put("url", "http://www.jhffcly.com/jhf/shopping/shouYe.do");
		zhuangbei.put("type", "view");

		shop.put("sub_button", Arrays.asList(zhuangbei));

		///////////////////////////////////////////////////
		// 个人中心子菜单
		JSONObject grzx = new JSONObject();
		grzx.put("name", "个人中心");
		grzx.put("url", "http://www.jhffcly.com/jhf/usercenter/userCenterList.do");
		grzx.put("type", "view");

		JSONObject customer = new JSONObject();
		customer.put("name", "我的客服");
		customer.put("url", "https://mpkf.weixin.qq.com");
		customer.put("type", "view");

		JSONObject shwd = new JSONObject();
		shwd.put("name", "售后网点");
		shwd.put("url", "http://www.jhffcly.com/jhf/aftersales/afterOutletsList.do");
		shwd.put("type", "view");

		JSONObject lishi = new JSONObject();
		lishi.put("name", "历史文章");
		lishi.put("url", "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIyMzEwNDc0Mg==#wechat_redirect");
		lishi.put("type", "view");

		JSONObject test = new JSONObject();
		test.put("name", "测试3");
		test.put("url", "http://www.jhffcly.com/jhf/test.do");
		test.put("type", "view");

		personal.put("sub_button", Arrays.asList(grzx, customer, shwd, lishi, test));

		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", token);
		String res = HttpUtil.doHttpPost(url, param, JSON.toJSONString(rootMenu), null, null);
		System.out.println(res);
	
	}

}
