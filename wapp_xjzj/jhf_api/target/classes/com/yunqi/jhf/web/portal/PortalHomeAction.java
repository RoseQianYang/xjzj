package com.yunqi.jhf.web.portal;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;
import com.yunqi.common.json.ErrorResult;
import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author tuweiguo
 */

@CrossOrigin
@RestController
@Api(description = "前台微信基础接口")
@RequestMapping(value = "/api")
public class PortalHomeAction {

	private Producer captchaProducer;
	protected Logger logger = Logger.getLogger(PortalHomeAction.class);

	@Resource
	public void setCaptchaProducer(Producer captchaProducer) {
		this.captchaProducer = captchaProducer;
	}

	@RequestMapping(value = "/getPortalCaptcha", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "获取验证码", notes = "接口返回图片 img")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		HttpSession session = request.getSession();
		logger.info("前台平台获取验证码 sessionId = " + session.getId() + " capText=" + capText);
		request.getSession().setAttribute(Const.SESSION_PORTAL_CAPTCHA_KEY, capText);

		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 用户登陆
	 * 
	 * @param JsonInfo
	 *            收集验证码及密码信息
	 * @param request
	 *            HeepSession
	 * @return JsonResult
	 */
	@RequestMapping(value = "/portalLogin", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "登陆", notes = "data:{menus:[code,name,submenu(code,name)],permission}，登陆过期时间为30分钟", response = JsonResult.class, responseReference = "response Ref")
	public JsonResult logIn(
			@ApiParam(value = "{accountName,password，verificationCode}", required = true) @RequestBody ModelMap JsonInfo,
			HttpServletRequest request) {

		if (JsonInfo == null || request == null) {
			return new ErrorResult("未获取到登陆请求数据，请联系管理员");
		}

		String receivedVeriCode = (String) JsonInfo.get("verificationCode");
		String expectedVeriCode = (String) request.getSession().getAttribute(Const.SESSION_PORTAL_CAPTCHA_KEY);

		if (receivedVeriCode == null || receivedVeriCode.equals(expectedVeriCode)) {
			return new ErrorResult("验证码错误");
		}

		String accountName = (String) JsonInfo.get("accountName");
		String receivedPassword = (String) JsonInfo.get("password");
		logger.info("accountName is --------" + accountName + "-------password is -------------" + receivedPassword);

		JsonResult result = new JsonResult();

		return result;
	}

}
