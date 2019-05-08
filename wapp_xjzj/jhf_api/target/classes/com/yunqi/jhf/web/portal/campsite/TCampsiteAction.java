package com.yunqi.jhf.web.portal.campsite;

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
import com.yunqi.jhf.dao.domain.TCampsite;
import com.yunqi.jhf.service.front.TCampsiteService;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.StrUtil;

/**
 * 营地相关
 * 
 * @author llh
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/front/campsite")
public class TCampsiteAction {

	@Autowired
	private TCampsiteService tCampsiteService;


	/**
	 * 获取营地列表 
	 * @param 当前页 分类id 地址 附近（1）
	 */
	@RequestMapping(value = "/campsiteList")
	public ModelAndView campsiteList(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView("/campsite/tcampsite_list");
		/*PageList<TCampsite> campsiteList=new PageList<>(); 
		int page = StrUtil.getParamInt(req, "page", 1);
		int cateId = StrUtil.getParamInt(req, "cateId", -1);
		String address = StrUtil.getParamStr(req, "address");*/
		int near = StrUtil.getParamInt(req, "near");
		/*double x=115.884677;
		double y=28.66709;
		sess.setAttribute("latitude", x);
		sess.setAttribute("longitude", y);
		double latitude=(double) sess.getAttribute("latitude");
		double longitude=(double) sess.getAttribute("longitude");
		if(near==1){
			campsiteList = tCampsiteService.findNear(page);
		}else{
			campsiteList = tCampsiteService.getCampsiteList(page,latitude, longitude,cateId,address);
		}*/
		/*mav.addObject("campsiteList", campsiteList);
		mav.addObject("cateId", cateId);
		mav.addObject("address", address);*/
		mav.addObject("near", near);
		return mav;
	}
	
	/**
	 * 异步获取 根据营地列表
	 * @param req
	 * @param JsonInfo
	 * @return
	 */
	@RequestMapping(value = "/getCampsiteAllListsByCateIdOrAddress", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getCampsiteAllListsByCateIdOrAddress(HttpServletRequest req,@RequestBody ModelMap JsonInfo) {
		int near = (int) JsonInfo.get("near");
		double latitude = (double) JsonInfo.get("latitude");
		double longitude = (double) JsonInfo.get("longitude");
		int cateId = (int)JsonInfo.get("cateId");
		String address =  (String) JsonInfo.get("address");
		if(near==1){
			return tCampsiteService.findNearListByAll();
		}else{
			return tCampsiteService.getCampsiteAllListsByCateIdOrAddress(latitude,longitude,cateId,address);

		}
	}
	

	/**
	 * 获取营地详情
	 */
	@RequestMapping(value = "/campsiteDetailList")
	public ModelAndView orderDetailList(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView("/campsite/tcampsitedetail");
		JsonResult result = new JsonResult();
		int campsiteId = StrUtil.getParamInt(req, "campsiteId", -1);
		TCampsite campsite = tCampsiteService.getCampsiteByCampid(campsiteId);
		List<String> campFunctionList = null;
		List<String> imgSrcAllList = null;
		if (campsite != null) {
			String campFunction = campsite.getCampsiteFunction();
			String imageId = campsite.getImageId();
			campFunctionList = SplitStrUtil.splitStrToStr(campFunction);
//			imgSrcList = tImageService.getAllImg(imageId);
			imgSrcAllList = SplitStrUtil.splitStrToStr(imageId);
			if (imgSrcAllList.size()>=4) {
				imgSrcAllList = imgSrcAllList.subList(0, 4);
			}
		} else {
			result.error("获取营地详情失败");
		}
		mav.addObject("campsite", campsite);
		mav.addObject("campFunctionList", campFunctionList);
		mav.addObject("imgSrcList", imgSrcAllList);
		return mav;
	}
}
