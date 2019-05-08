package com.yunqi.jhf.web.interceptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yunqi.common.json.ErrorResult;
import com.yunqi.common.json.JsonUtil;
import com.yunqi.common.json.NotLoginResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TPermissions;
import com.yunqi.jhf.vo.SessionUser;


/**
 * @author ZhangQ
 */

public class ApiInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = Logger.getLogger(ApiInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		HttpSession session = request.getSession();
		String ctxPath = request.getContextPath();
		String uri = request.getRequestURI();
		uri = uri.substring(ctxPath.length());
		log.info("Req Uri--------------:" + uri);
		// 过滤条件
		if (uri.startsWith("/api/adm")) {
			System.out.println("interceptor:-------------");
			SessionUser data = (SessionUser) session.getAttribute(ConstantTool.SESSION_USER);
			if (data == null) {
				try {
					response.setContentType("text/html;charset=UTF-8");
					response.getWriter().write(JsonUtil.toJson(new NotLoginResult()));
					return false;
				} catch (IOException io) {
					log.error(io.getMessage());
				}
			} else {
				List<TPermissions> permissions = data.getPermissions();
				for(TPermissions p : permissions) {
					if("/api/adm/image/doUpload.do".equals(uri)) {
						log.info("=== " + p.getUrl());
					}
					if(uri.equals(p.getUrl() + ".do")) {
						return true;
					}
				}
				try {
					response.setContentType("application/json; charset=utf-8");
					response.getWriter().write(JsonUtil.toJson(new ErrorResult("您没有该功能的访问权限")));
					return false;
				} catch (IOException io) {
					log.error(io.getMessage());
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}