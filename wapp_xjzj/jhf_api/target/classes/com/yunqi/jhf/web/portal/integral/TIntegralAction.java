package com.yunqi.jhf.web.portal.integral;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.domain.TIntegral;
import com.yunqi.jhf.service.front.TIntegralService;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/front/my")
public class TIntegralAction {
	
	@Autowired
	private TIntegralService tIntegralService;
	
	@RequestMapping(value = "/integralList")
	public ModelAndView productList(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("my/my_riches/myriche"); 
	    WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		int status = StrUtil.getParamInt(req, "status", 1);
	    // 根据 用户id userId  获取所有不分页列表和对于的积分总数
	    List<TIntegral> integralListByUserId = tIntegralService.getIntegralListByUserIdByStatus(userId, status);
		int weChatUserIntegralSum = tIntegralService.getWeChatUserIntegralSum(userId); // 当前用户 总金额数
		int integralNumSum = tIntegralService.getIntegralNumSum(userId); // 已提现总金额数
		int sumIntegralNum = weChatUserIntegralSum + integralNumSum; // 根据 userId 获取 总财富数   用户当前财富 + 已经提现财富
		mav.addObject("weChatUserIntegralSum", weChatUserIntegralSum); 
		mav.addObject("integralNumSum", integralNumSum);
	    mav.addObject("sumIntegralNum", sumIntegralNum);
	    mav.addObject("integralListByUserId", integralListByUserId);
	   return mav;   
	}
	
	
	/**
	 * 异步获取 积分列表
	 * @param jsonInfo
	 * @return Json
	 */
	@ApiOperation(value = "积分列表", notes = "data{List TIntegral}", response = JsonResult.class)
	@RequestMapping(value = "/getWeChatIntegralPageListByUserId", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getWeChatIntegralPageListByUserId(@RequestBody ModelMap jsonInfo) {
		return tIntegralService.getWeChatIntegralPageListByUserId(jsonInfo); 
	}

}
