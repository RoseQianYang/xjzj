package com.yunqi.jhf.web.portal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yunqi.common.ServiceException;
import com.yunqi.common.util.HttpUtil;
import com.yunqi.common.util.Sha1Util;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.domain.TOrder;
import com.yunqi.jhf.dao.persistence.TOrderDao;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.service.wechat.WechatService;
import com.yunqi.jhf.web.StrUtil;

@CrossOrigin
@RestController
public class WeChatPaymentAction {

	private static Logger log = Logger.getLogger(WeChatPaymentAction.class);

	@Resource
	private TOrderDao tOrderDao;
	@Resource
	private WechatService wechatService;

	@RequestMapping("/test")
	public ModelAndView test(HttpServletRequest request) throws ServiceException, DigestException {
		WechatSessionUser sessUser = WechatSessionUser.getUser(request);

		JSONObject obj = new JSONObject();
		obj.putAll(sessUser.getProps());
		log.info("test is called and get weixin user info: " + obj.toJSONString());

		ModelAndView mav = new ModelAndView("test");
		mav.addObject("appId", Const.WECHAT_APP_ID);
		mav.addObject("templateId", request.getParameter("templateId"));

		mav.addObject("json", obj.toJSONString());
		mav.addObject("sessUser", sessUser);
		return mav;
	}

	@RequestMapping("/location")
	public ModelAndView location(HttpServletRequest request) throws ServiceException, DigestException {
		WechatSessionUser sessUser = WechatSessionUser.getUser(request);
		ModelAndView mav = new ModelAndView("location");
		mav.addObject("sessUser", sessUser);
		return mav;
	}

	@RequestMapping("/sendMsg")
	public ModelAndView sendMsg(HttpServletRequest request) throws ServiceException {
		String access_token = wechatService.getAccessToken();
		if (access_token == null) {
			ModelAndView mav = new ModelAndView("send_msg");
			mav.addObject("errorMsg", "access_token or jsticket is empty!");
			return mav;
		}
		WechatSessionUser sessUser = WechatSessionUser.getUser(request);

		String jsonBody = "{\r\n" + "           \"touser\":\"" + sessUser.getOpenId() + "\",\r\n"
				+ "           \"template_id\":\"Lixi23EtIsJTxccBxwByAkXexExzM3nk0Ar17JYknlc\",\r\n"
				+ "           \"url\":\"http://www.jhffcly.com/jhf/test.do?templateId=Lixi23EtIsJTxccBxwByAkXexExzM3nk0Ar17JYknlc\",  \r\n"
				+ "           \"data\":{\r\n" + "                   \"first\": {\r\n"
				+ "                       \"value\":\"季候风房车-" + sessUser.getNickname() + "，您有新的粉丝：甜甜心加入！\",\r\n"
				+ "                       \"color\":\"#173177\"\r\n" + "                   },\r\n"
				+ "                   \"keyword1\":{\r\n" + "                       \"value\":\"" + sessUser.getUserId()
				+ "\",\r\n" + "                       \"color\":\"#173177\"\r\n" + "                   },\r\n"
				+ "                   \"keyword2\": {\r\n" + "                       \"value\":\"2018年4月6日\",\r\n"
				+ "                       \"color\":\"#173177\"\r\n" + "                   },\r\n"
				+ "                   \"remark\":{\r\n"
				+ "                       \"value\":\"亲，干的漂亮，请继续努力！点击查看我的好友列表！\",\r\n"
				+ "                       \"color\":\"#173177\"\r\n" + "                   }\r\n" + "           }\r\n"
				+ "       }";

		log.info("=================");
		log.info(jsonBody);
		String returnBody = HttpUtil.doHttpPost(
				"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token, null, jsonBody,
				null, null);

		log.info(returnBody);
		log.info("====================");
		ModelAndView mav = new ModelAndView("send_msg");
		mav.addObject("returnBody", returnBody);
		return mav;
	}

	@RequestMapping("/pay")
	public ModelAndView pay(HttpServletRequest request) {
		WechatSessionUser sessUser = WechatSessionUser.getUser(request);

		long orderId = Long.parseLong(request.getParameter("orderId"));
		TOrder tOrder = tOrderDao.loadById((int) orderId);
		if (tOrder == null) {
			throw new RuntimeException("order is null");
		}
		Map<String, String> params = new HashMap<String, String>();
		try {
			String nonce = System.currentTimeMillis() + "";
			params.put("appid", Const.WECHAT_APP_ID);
			params.put("mch_id", Const.WECHAT_MCH_ID);
			params.put("device_info", "jhf_wxgzh");
			params.put("nonce_str", nonce);
			params.put("body", "支付测试");
			params.put("out_trade_no", "" + orderId);

			// params.put("total_fee", "" + tOrder.getOrderTotalPrice());
			params.put("total_fee", "1");// 测试阶段设置缴费金额为1分
			params.put("spbill_create_ip", "111.230.11.240");
			params.put("notify_url", "http://www.jhffcly.com/jhf/notify.do");
			params.put("trade_type", "JSAPI");
			params.put("openid", sessUser.getOpenId());

			String sign = sign(params);

			params.put("sign", sign);

			String unifiedOrderBody = HttpUtil.doHttpPostXML("https://api.mch.weixin.qq.com/pay/unifiedorder", params);

			log.info(unifiedOrderBody);

			Document document = DocumentHelper.parseText(unifiedOrderBody);
			Element root = document.getRootElement();

			String prepayId = root.element("prepay_id").getText();
			String paySign = root.element("sign").getText();
			String nonce_str = root.element("nonce_str").getText();
			String timestamp = System.currentTimeMillis() + "";

			log.info("Payment params prepayId = " + prepayId + "  paySign=" + paySign);

			Map<String, String> p2 = new HashMap<String, String>();
			p2.put("appId", Const.WECHAT_APP_ID);
			p2.put("nonceStr", nonce_str);
			p2.put("package", "prepay_id=" + prepayId);
			p2.put("signType", "MD5");
			p2.put("timeStamp", timestamp);
			String sign2 = sign(p2);

			ModelAndView mav = new ModelAndView("pay");
			mav.addObject("sessUser", sessUser);
			mav.addObject("prepayId", prepayId);
			mav.addObject("paySign", sign2);
			mav.addObject("nonce_str", nonce_str);
			mav.addObject("timestamp", timestamp);
			return mav;
		} catch (Exception e) {

		}
		return null;

	}

	@RequestMapping("/notify")
	public String notify(HttpServletRequest request) {

		try {
			BufferedReader br = request.getReader();
			String str, notifyBody = "";
			while ((str = br.readLine()) != null) {
				notifyBody += str;
			}
			log.info("payment got notify:" + notifyBody);

			Document document = DocumentHelper.parseText(notifyBody);
			Element root = document.getRootElement();
			String sign = null;
			Map<String, String> p = new HashMap<String, String>();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				if (!"sign".equals(elem.getName())) {
					p.put(elem.getName(), elem.getStringValue());
				} else {
					sign = elem.getStringValue();
				}
			}
			String mySign = sign(p);
			log.info("sign=" + sign + " mySign=" + mySign);

			String appid = p.get("appid");
			// String openid = p.get("openid");
			String out_trade_no = p.get("out_trade_no");
			String result_code = p.get("result_code");
			// String total_fee = p.get("total_fee");

			if (Const.WECHAT_APP_ID.equals(appid) && sign.equals(mySign)) {
				log.info("###### 支付通知验证通过 ######");
				if ("SUCCESS".equals(result_code)) {
					// 支付成功
					int orderId = Integer.parseInt(out_trade_no);
					TOrder tOrder = tOrderDao.loadById(orderId);
					if (tOrder == null) {
						return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[order does not exist!]]></return_msg></xml>";
					}
					tOrder.setOrderStatus(Const.ORDER_STATUS_ALREADY_PAYED);
					tOrderDao.update(new SqlUpdate().addColumn(TOrder.SQL_orderStatus).where(TOrder.SQL_id), tOrder);
					// TODO 三级分销返现，做成事务

				}
			}
			return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "<xml><return_code><![CDATA[FAILURE]]></return_code><return_msg><![CDATA[异常]]></return_msg></xml>";

	}

	@RequestMapping("/verifyToken")
	public String verifyToken(HttpServletRequest request, ModelAndView mav, HttpServletResponse response)
			throws IOException {
	//最终获得调用微信js接口验证需要的三个参数noncestr、timestamp、signature  用于 签名校验
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		log.info("=== get weixin notify ===");
		log.info("signature:" + signature);
		log.info("timestamp:" + timestamp);
		log.info("nonce:" + nonce);

		PrintWriter pw = response.getWriter();
		pw.append("true");
		pw.flush();
		return null;
	}

	/**
	 * 获取微信签名 
	 * @param params  请求参数集合
	 * @return 微信请求签名串
	 */
	public static String sign(Map<String, String> params) {
		Collection<String> keyset = params.keySet();
		List<String> list = new ArrayList<String>(keyset);
		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String name = list.get(i);
			String text = params.get(list.get(i));
			// 参数中sign、key不参与签名加密
			if (!StringUtils.isBlank(text)) {
				if (i > 0 && sb.length() > 0) {
					sb.append("&");
				}
				sb.append(name);
				sb.append("=");
				sb.append(text);
			}
		}
		String stringA = sb.toString();
		String stringSignTemp = stringA + "&key=" + Const.WECHAT_PAYMENT_SIGN_KEY;
		String sign = StrUtil.md5(stringSignTemp).toUpperCase();

		log.info("加密前：" + stringSignTemp);
		log.info("签名值：" + sign);
		return sign;
	}

	public static String signSh1(Map<String, String> params) {
		Collection<String> keyset = params.keySet();
		List<String> list = new ArrayList<String>(keyset);
		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String name = list.get(i);
			String text = params.get(list.get(i));
			if (!StringUtils.isBlank(text)) {
				if (i > 0 && sb.length() > 0) {
					sb.append("&");
				}
				sb.append(name);
				sb.append("=");
				sb.append(text);
			}
		}
		String stringA = sb.toString();
		// String stringSignTemp = stringA + "&key=" + Const.WECHAT_PAYMENT_SIGN_KEY;
		String sign = DigestUtils.shaHex(stringA).toUpperCase();

		log.info("加密前：" + stringA);
		log.info("签名值：" + sign);
		return sign;
	}

	/**
	 * @author：罗国辉 @date： 2015年12月17日 上午9:24:43 @description： SHA、SHA1加密 @parameter：
	 *             str：待加密字符串 @return： 加密串
	 **/
	public static String SHA1(String str) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1"); // 如果是SHA加密只需要将"SHA-1"改成"SHA"即可
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexStr = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexStr.append(0);
				}
				hexStr.append(shaHex);
			}
			return hexStr.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取精确到秒的时间戳
	 * 
	 * @return
	 */
	public static String getSecondTimestamp(Date date) {
		if (null == date) {
			return "";
		}
		String timestamp = String.valueOf(date.getTime());
		return timestamp.substring(0, timestamp.length() - 3);
	}

	public static void main(String[] args) throws Exception {

		try {
			// 获取access_token
			Map<String, String> p = new HashMap<String, String>();
			p.put("appid", Const.WECHAT_APP_ID);
			p.put("secret", Const.WECHAT_APP_SECRET);
			p.put("grant_type", "client_credential");
			String body = HttpUtil.doHttpGet("https://api.weixin.qq.com/cgi-bin/token", p);
			JSONObject jsonObj = JSON.parseObject(body);
			String accessToken = jsonObj.getString("access_token");

			// 获取ticket
			Map<String, String> p2 = new HashMap<String, String>();
			p2.put("access_token", accessToken);
			p2.put("type", "jsapi"); //交易类型
			String body2 = HttpUtil.doHttpGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket", p2);
			JSONObject jsonObj2 = JSON.parseObject(body2);
			String ticket = jsonObj2.getString("ticket");

			log.info("jsticket " + ticket);

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * 
		 * Map<String, String> params = new HashMap<String, String>(); try { String
		 * nonce = System.currentTimeMillis() + ""; String orderNo =
		 * System.currentTimeMillis() + ""; params.put("appid", Const.WECHAT_APP_ID);
		 * params.put("mch_id", "1295078201"); params.put("device_info", "jhf_wxgzh");
		 * params.put("nonce_str", nonce); params.put("body", "支付测试");
		 * params.put("out_trade_no", orderNo); params.put("total_fee", "1");
		 * params.put("spbill_create_ip", "111.230.11.240"); params.put("notify_url",
		 * "http://www.jhffcly.com/jhf/notify.do"); params.put("trade_type", "JSAPI");
		 * params.put("openid", "o5qwcwDoOupV29Ir5c8DdO_E38Sc");
		 * 
		 * String sign = sign(params);
		 * 
		 * // System.out.println(DigestUtils.shaHex(
		 * "jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW×tamp=1414587457&url=http://mp.weixin.qq.com?params=value"
		 * )); // 微信返回的加密串
		 * 
		 * params.put("sign", sign);
		 * 
		 * String body =
		 * HttpUtil.doHttpPostXML("https://api.mch.weixin.qq.com/pay/unifiedorder",
		 * params);
		 * 
		 * log.info("================ order return body ============="); log.info(body);
		 * 
		 * Document document = DocumentHelper.parseText(body); Element root =
		 * document.getRootElement();
		 * 
		 * String prepayId = root.element("prepay_id").getText(); String paySign =
		 * root.element("sign").getText();
		 * 
		 * log.info("Payment params prepayId = " + prepayId + "  paySign=" + paySign); }
		 * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
		 * }
		 */
		// pc.verifyUrl(null, null, null, null);
	}

}
