package com.yunqi.jhf.web.portal.route;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.domain.TMobileRoute;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.service.front.TMobileRouteService;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.StrUtil;

/**
 * 露营路线相关
 * 
 * @author llh
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/front/mobileroute")
public class TMobileRouteAction {

	@Autowired
	private TMobileRouteService tMobileRouteService;


	/**
	 * 获取露营路线列表
	 */
	@RequestMapping(value = "/mobileList")
	public ModelAndView mobileList(HttpServletRequest req, HttpSession sess, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView("/route/tmobileroute_list");
		int page = StrUtil.getParamInt(req, "page", 1);
		PageList<TMobileRoute> mobileList = tMobileRouteService.getMobileList(page);
		mav.addObject("mobileList", mobileList);
		return mav;
	}
	
	/**
	 * 异步 获取露营路线列表
	 * @param JsonInfo
	 * @return json
	 */
	@RequestMapping(value = "/getMobilePageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getMobilePageList(@RequestBody ModelMap JsonInfo) {
		return tMobileRouteService.getMobilePageList(JsonInfo);
	}

	
	/**
	 * 获取营地详情
	 */
	@RequestMapping(value = "/mobileDetailList")
	public ModelAndView mobileDetailList(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView("/route/tmobileroutedetail");
		JsonResult result = new JsonResult();
		int mobilerouteId = StrUtil.getParamInt(req, "mobilerouteId", -1);
		TMobileRoute mobileroute = tMobileRouteService.getMobileBymobileId(mobilerouteId);
		List<String> imgSrcAllList = null;
		if (mobileroute != null) {
			String imageId = mobileroute.getImageId();
//			imgSrcList = tImageService.getAllImg(imageId);
			imgSrcAllList = SplitStrUtil.splitStrToStr(imageId);
			if (imgSrcAllList.size()>=4) {
				imgSrcAllList = imgSrcAllList.subList(0, 4);
			}
		} else {
			result.error("获取营地详情失败");
		}
		mav.addObject("mobileroute", mobileroute);
		mav.addObject("imgSrcList", imgSrcAllList);
		return mav;
	}
}
