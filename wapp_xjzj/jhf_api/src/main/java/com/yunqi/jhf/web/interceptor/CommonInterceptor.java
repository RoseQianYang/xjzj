package com.yunqi.jhf.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yunqi.jhf.web.StrUtil;

public class CommonInterceptor extends HandlerInterceptorAdapter {


	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();

		if (session.getAttribute("strUtil") == null) {
			session.setAttribute("strUtil", new  StrUtil());
		}
		String ctxPath = request.getContextPath();
		if (session.getAttribute("ctxPath") == null) {
			session.setAttribute("ctxPath", ctxPath);
		}
		String uri = request.getRequestURI();
		session.setAttribute("requestUri", uri);	
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}