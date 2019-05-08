package com.yunqi.jhf.web.interceptor;

import java.net.URLEncoder;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.yunqi.common.util.HttpUtil;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.dao.persistence.TUserDao;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.service.wechat.WechatService;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

/**
 * 只支持拦截get方式
 */
public class WechatOpenIdInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = Logger.getLogger(WechatOpenIdInterceptor.class);

	private static String APPLY_OPENID_TIMES = "applyOpenIdTimes";
	private static String STATE = "jhffcly2018";

	@Autowired
	private TUserDao tUserDao;
	
	@Resource
	private WechatService wechatService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		WechatSessionUser wechatSessionUser = WechatSessionUser.getUser(request);
		
		int shareUserId = 0;
		
		try {
		shareUserId = Integer.parseInt(request.getParameter("shareUserId"));
		} catch (Exception e) {
			shareUserId = 0;
		}
		
		if (wechatSessionUser == null) {
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			log.info("get openid code=" + code + " &state=" + state);

			// 获取用户url并删除code参数
			String clientUrl = HttpUtil.getRequestUrl(request);
			Set<String> urlParamToRemove = new HashSet<>();
			urlParamToRemove.add("code");
			clientUrl = HttpUtil.removeUrlParams(clientUrl, urlParamToRemove);

			// 申请openid的次数
			Object applyOpenidTimes = request.getSession().getAttribute(APPLY_OPENID_TIMES);

			if (StringUtils.isNotBlank(code)) {
				log.info("申请openid");

				Map<String, String> params = new LinkedHashMap<>();
				params.put("appid", Const.WECHAT_APP_ID);
				params.put("secret", Const.WECHAT_APP_SECRET);
				params.put("code", code);
				params.put("grant_type", "authorization_code");

				String content = HttpUtil.doHttpGet("https://api.weixin.qq.com/sns/oauth2/access_token", params);
				log.info("get openid body : " + content);
				JSONObject jsonObject = JSONObject.parseObject(content);

				String openid = jsonObject.getString("openid");
				String access_token = jsonObject.getString("access_token");

				if (StringUtils.isBlank(openid)) {
					int errcode = jsonObject.getIntValue("errcode");
					String errmsg = jsonObject.getString("errmsg");
					request.getSession().removeAttribute(APPLY_OPENID_TIMES);
					log.error("获取openid错误：errcode=" + errcode + ", errmsg=" + errmsg);
					response.addHeader("Content-Type", "text/html; charset=utf-8");
					response.getWriter().write("获取openid错误：errcode=" + errcode + ", errmsg=" + errmsg);
					return false;
				} else {
					log.info("获取openid成功，openid=" + openid);

					request.getSession().removeAttribute(APPLY_OPENID_TIMES);
					Map<String, String> p = new LinkedHashMap<>();
					p.put("access_token", access_token);
					p.put("openid", openid);
					p.put("lang", "zh_CN");

					String userBody = HttpUtil.doHttpGet("https://api.weixin.qq.com/sns/userinfo", p);
					log.info("获取用户信息成功: get user_info body: " + userBody);
					JSONObject obj = JSONObject.parseObject(userBody);

					String nickname = obj.getString("nickname");
					String headimgurl = obj.getString("headimgurl");
					if (StringUtils.isBlank(nickname) || StringUtils.isBlank(headimgurl)) {
						int errcode = jsonObject.getIntValue("errcode");
						String errmsg = jsonObject.getString("errmsg");
						request.getSession().removeAttribute(APPLY_OPENID_TIMES);
						log.error("获取userinfo错误：errcode=" + errcode + ", errmsg=" + errmsg);
						response.addHeader("Content-Type", "text/html; charset=utf-8");
						response.getWriter().write("获取userinfo错误：errcode=" + errcode + ", errmsg=" + errmsg);
						return false;
					} else {
						WechatSessionUser user = new WechatSessionUser();
						user.setOpenId(openid);
						user.setNickname(nickname);
						user.setHeadimgurl(headimgurl);

						TUser u = tUserDao.load(new SqlSelect().where(TUser.SQL_openId),
								new TUser().setOpenId(user.getOpenId()));

						
						// 如果用户不存在，那么使用openid注册用户
						if (u == null) {
							u = new TUser();
							u.setAvatar(user.getHeadimgurl());
							u.setOpenId(user.getOpenId());
							u.setIsEnabled("Y");
							u.setUserName(user.getNickname());
							if (shareUserId > 0) {
								TUser su = tUserDao.loadById(shareUserId);
								if (su !=null ) {
									u.setParentId(shareUserId);
								}
							}
							tUserDao.insert(u);
							
							//发送新会员的分享人发送推送消息
							if (u.getParentId() != 0) {
								TUser parentUser = tUserDao.loadById(u.getParentId());
								TUser currentUser = tUserDao.load(new SqlSelect().where(TUser.SQL_openId), new TUser().setOpenId(openid));
								String jsonBody = "{\r\n" + 
										"           \"touser\":\"" + parentUser.getOpenId() + "\",\r\n" + 
										"           \"template_id\":\"Lixi23EtIsJTxccBxwByAkXexExzM3nk0Ar17JYknlc\",\r\n" + 
										"           \"url\":\"http://www.jhffcly.com/jhf/friend/friendList.do\",  \r\n" + 
										"           \"data\":{\r\n" + 
										"                   \"first\": {\r\n" + 
										"                       \"value\":\"季候风房车-" + parentUser.getUserName() + "，您有新的粉丝：" + currentUser.getUserName()+"加入！\",\r\n" + 
										"                       \"color\":\"#173177\"\r\n" + 
										"                   },\r\n" + 
										"                   \"keyword1\":{\r\n" + 
										"                       \"value\":\""+ currentUser.getId() + "\",\r\n" + 
										"                       \"color\":\"#173177\"\r\n" + 
										"                   },\r\n" + 
										"                   \"keyword2\": {\r\n" + 
										"                       \"value\":\""+StrUtil.showTimeTo(currentUser.getCreateTime()) +"\",\r\n" + 
										"                       \"color\":\"#173177\"\r\n" + 
										"                   },\r\n" + 
										"                   \"remark\":{\r\n" + 
										"                       \"value\":\"亲，干的漂亮，请继续努力！点击查看我的好友列表！\",\r\n" + 
										"                       \"color\":\"#173177\"\r\n" + 
										"                   }\r\n" + 
										"           }\r\n" + 
										"       }";
								
								log.info("=================");
								log.info(jsonBody);
								String returnBody = HttpUtil.doHttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + wechatService.getAccessToken(),
										null, jsonBody, null, null);

								log.info(returnBody);
								log.info("====================");
							}
							
						} else {
							if (headimgurl.equals(u.getAvatar())==false || nickname.equals(u.getUserName())==false) {
								SqlUpdate sqlupdate = new SqlUpdate().where(TUser.SQL_id).addColumn(TUser.SQL_userName)
										.addColumn(TUser.SQL_avatar);
								TUser tuser = new TUser();
								tuser.setId(u.getId());
								tuser.setAvatar(headimgurl);
								tuser.setUserName(nickname);
								tUserDao.update(sqlupdate, tuser);
							}
						}
						
						u = tUserDao.load(new SqlSelect().where(TUser.SQL_openId),
								new TUser().setOpenId(user.getOpenId()));
						user.setUserId(u.getId());
						WechatSessionUser.saveUser(request, user);
						return true;
					}
				}
			} else {
				String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Const.WECHAT_APP_ID
						+ "&redirect_uri=" + URLEncoder.encode(clientUrl, "utf-8")
						+ "&response_type=code&scope=snsapi_userinfo&state=" + STATE + "#wechat_redirect";
				log.info("申请code, url：" + url);
				if (applyOpenidTimes != null) {
					Integer times = (Integer) applyOpenidTimes;
					request.getSession().setAttribute(APPLY_OPENID_TIMES, times + 1);
				} else {
					request.getSession().setAttribute(APPLY_OPENID_TIMES, Integer.valueOf(1));
				}
				response.sendRedirect(url);
				return false;
			}
		}

		return true;
	}
}
