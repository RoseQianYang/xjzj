package com.yunqi.jhf.web.api.adm;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.google.code.kaptcha.Producer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunqi.common.json.ErrorResult;
import com.yunqi.common.json.JsonResult;
import com.yunqi.common.json.SuccessResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.config.Dict;
import com.yunqi.jhf.dao.domain.TOperator;
import com.yunqi.jhf.dao.domain.TPermissions;
import com.yunqi.jhf.dao.domain.TRole;
import com.yunqi.jhf.dao.domain.TSysLog;
import com.yunqi.jhf.dao.persistence.TOperatorDao;
import com.yunqi.jhf.dao.persistence.TRoleDao;
import com.yunqi.jhf.dao.persistence.TSysLogDao;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.vo.SessionUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author tuweiguo
 */

@CrossOrigin
@RestController
@Api(description = "Admin基础接口")
@RequestMapping(value = "/api")
public class AdmHomeAction {

	protected Logger logger = Logger.getLogger(AdmHomeAction.class);
	
	@Autowired
	private TOperatorDao operatorDao;
	
	@Autowired
	private Producer captchaProducer;
	
	@Autowired
	private TRoleDao tRoleDao;
	
	@Autowired
	private TSysLogDao tSysLogDao;
	
	@RequestMapping(value = "/getAdminCaptcha", method = RequestMethod.GET, produces = "application/json")
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
		logger.info("管理平台获取验证码 sessionId = " + session.getId() + " capText=" + capText);
		request.getSession().setAttribute(Const.SESSION_ADMIN_CAPTCHA_KEY, capText);

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

	@RequestMapping(value = "/getDict", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "获取数据字典", notes = "data{  }", response = JsonResult.class)
	public JsonResult getDict() {
		return new SuccessResult().setData(Dict.dictMap);
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
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "登陆", notes = "data:{menus:[code,name,submenu(code,name)],permission}，登陆过期时间为30分钟", response = JsonResult.class, responseReference = "response Ref")
	public JsonResult login(
			@ApiParam(value = "{accountName,password,verificationCode}", required = true) @RequestBody ModelMap JsonInfo,
			HttpServletRequest request) {

		if (JsonInfo == null || request == null) {
			return new ErrorResult("未获取到登陆请求数据，请联系管理员");
		}

		String receivedVeriCode = (String) JsonInfo.get("verificationCode");
		String expectedVeriCode = (String) request.getSession().getAttribute(Const.SESSION_ADMIN_CAPTCHA_KEY);

		if (receivedVeriCode == null || !receivedVeriCode.equals(expectedVeriCode)) {
			return new ErrorResult("验证码错误");
		}
		String receivedPassword = (String) JsonInfo.get(TOperator.PROP_password);

		JsonResult result = new JsonResult();
		SqlSelect sql = new SqlSelect()
				.where(TOperator.SQL_accountName)
				.and(TOperator.SQL_isDelete);
		JsonInfo.put(TOperator.PROP_isDelete, ConstantTool.ISDELETE_YES);
		TOperator operator = operatorDao.loadByMap(sql,JsonInfo);
		if(operator != null) {
			if(ConstantTool.ISDELETE_YES.equals(operator.getIsEnabled())) {
				logger.info("该用户已被冻结");
				return new ErrorResult("该用户已被冻结");
			}
			if(receivedPassword.equals(operator.getPassword())) {
				SessionUser su = getSeesionUserformOperator(operator);
				request.getSession().setAttribute(ConstantTool.SESSION_USER,su);
				result.success("登录成功");
				result.setData(su);
				// 登录成功 记录在系统日志表
				TSysLog sysLog = new TSysLog();
				sysLog.setLogType(ConstantTool.SYSLOG_LOGTYPE_ONE); // 日志类型为  后台来源类型
				sysLog.setLogAction(ConstantTool.SYSLOG_LOGACTION_ONE); // 操作类型为 登录操作
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar ca = Calendar.getInstance();
				sysLog.setLogContent(operator.getRealName()+"用户在" + sf.format(ca.getTime())+"时,登录成功" );
				tSysLogDao.insert(sysLog);	
			}else {
				logger.info("密码不正确");
				return new ErrorResult("密码不正确");
			}
			
		}else {
			logger.info("用户名不存在");
			return new ErrorResult("用户名不存在");
		}
		return result;
	}

	public SessionUser getSeesionUserformOperator(TOperator operator) {
		Gson gson = new Gson(); 
		SessionUser sess = new SessionUser();
		sess.setAccountName(operator.getAccountName());
		sess.setMobile(operator.getMobile());
		sess.setRealName(operator.getRealName());
		sess.setRemark(operator.getRemark());
		TRole role = tRoleDao.loadById(operator.getRoleId());
		if(role.getMenus() != null) {
			sess.setMenus(role.getMenus());
		}
		if(role.getPermissions() != null) {
			  List<TPermissions> tps =  gson.fromJson(role.getPermissions(),  
		                new TypeToken<List<TPermissions>>() {  
		                }.getType());  
			  sess.setPermissions(tps);
		}
		return sess;
	}
	
}
