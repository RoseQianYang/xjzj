package com.yunqi.jhf.web.portal.integral;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.service.front.TIntegralConversionService;
import com.yunqi.jhf.service.front.TIntegralService;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

@CrossOrigin
@RestController
@RequestMapping(value = "/front/my")
public class TIntegralConversionAction {
	
	protected static Logger logger = Logger.getLogger(TIntegralConversionAction.class);
	
	@Autowired
	private TIntegralConversionService tIntegralConversionService;
	
	@Autowired
	private TIntegralService tIntegralService;
	
	/**
	 * 根据 用户id 和 兑换申请数  新增数据 提交给后台管理员审核
	 * @param req
	 * @param sess
	 * @return Json
	 */
	@RequestMapping(value = "/addIntegralConversion")
	public ModelAndView add(HttpServletRequest req, HttpSession sess) throws Exception{
		ModelAndView mav = new ModelAndView("my/my_riches/richesexchange");
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		// 根据 用户id userId  获取对于的积分总数
		int weChatUserIntegralSum = tIntegralService.getWeChatUserIntegralSum(userId);
		mav.addObject("weChatUserIntegralSum", weChatUserIntegralSum);
		return mav;
	}
	
	@RequestMapping(value = "/insert")
	public JsonResult insert(HttpServletRequest req, HttpSession sess) {
		JsonResult result = new JsonResult();
		try {
			WechatSessionUser sessUser = WechatSessionUser.getUser(req);
			int userId = sessUser.getUserId();
			int conversionNum = StrUtil.getParamInt(req, "conversionNum");
			String userMobile=StrUtil.getParamStr(req, "userMobile");
			if (userId !=-1) {
				result = tIntegralConversionService.create(userId,conversionNum,userMobile);
				logger.info("微信端根据 用户id发起兑换申请接口执行完成");
				result.success("发起积分兑换申请成功，请等待管理员待审核！");
			}
		} catch (Exception e) {
			logger.info("微信端根据 用户id发起兑换申请接口执行失败");
			result.error("发起积分兑换申请失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

}
