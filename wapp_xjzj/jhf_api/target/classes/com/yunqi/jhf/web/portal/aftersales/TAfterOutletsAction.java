package com.yunqi.jhf.web.portal.aftersales;

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
import com.yunqi.jhf.dao.domain.TAfterOutlets;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.service.front.TAfterOutletsService;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.StrUtil;


@CrossOrigin
@RestController
@RequestMapping(value = "/aftersales")
public class TAfterOutletsAction {

	@Autowired
	private TAfterOutletsService tAfterOutletsService;

	
	/**
	 * 获取全部的售后网点信息
     * @param currentPage 当前页T
     * @return 分页信息
	 */
	@RequestMapping(value = "/afterOutletsList")
	public ModelAndView afterOutletsList(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/after_sales/tafteroutlets_list"); 
	   /*PageList<TAfterOutlets> afteroutletsList=new PageList<>();
	    double x=115.884677;
	    double y=28.66709;
	    sess.setAttribute("latitude", x);
	    sess.setAttribute("longitude", y);
	    double latitude=(double) sess.getAttribute("latitude");
	    double longitude=(double) sess.getAttribute("longitude");
		int page = StrUtil.getParamInt(req, "page", 1);
		int near = StrUtil.getParamInt(req, "near");
		if(near==1){
			afteroutletsList = tAfterOutletsService.findNear(page);
		}else{
			afteroutletsList = tAfterOutletsService.findAllOutlets(page,latitude,longitude);

		}
		mav.addObject("afteroutletsList", afteroutletsList);*/
		int near = StrUtil.getParamInt(req, "near");
		mav.addObject("near", near);
		return mav;
	}
	
	/**
	 * 异步获取 售后网点列表 
	 * @param req
	 * @param JsonInfo
	 * @return
	 */
	@RequestMapping(value = "/getstoreLists", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getAfteroutletsLists(HttpServletRequest req,@RequestBody ModelMap JsonInfo) {
		int near = (int) JsonInfo.get("near");
		double latitude =(double) JsonInfo.get("latitude");
		double longitude = (double) JsonInfo.get("longitude");		
		if(near==1){
			return tAfterOutletsService.findNearByAll();
		}else{
			return tAfterOutletsService.findAllStores(latitude,longitude);

		}
	}
	
	
	/**
	 * 根据距离排序全部的售后网点信息
     * @param currentPage 当前页T
     * @return 分页信息
	 */
	@RequestMapping(value = "/afterOutletsNear")
	public ModelAndView afterOutletsNear(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/after_sales/tafteroutlets_list"); 
		int page = StrUtil.getParamInt(req, "page", 1);
		PageList<TAfterOutlets> afteroutletsList = tAfterOutletsService.findNear(page);
		mav.addObject("afteroutletsList", afteroutletsList);
		return mav;
	}

	/**
	 * 根据id获取售后网点信息
     * @param afterId 
     * @return TAfterOutlets对象
	 */
	@RequestMapping(value = "/afteroutletsDetail")
	public ModelAndView afterOutlets(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/after_sales/tafteroutletsdetail");  
	    int afterId = StrUtil.getParamInt(req, "afterId",-1);
		TAfterOutlets tAfterOutlets = tAfterOutletsService.findStoreById(afterId);
		String imageId=tAfterOutlets.getImageId();
		List<String> imgSrcAllList =  SplitStrUtil.splitStrToStr(imageId);
		if (imgSrcAllList.size()>=4) {
			imgSrcAllList = imgSrcAllList.subList(0, 4);
		}
		mav.addObject("tAfterOutlets", tAfterOutlets);
		mav.addObject("imgSrcList",imgSrcAllList);
		return mav;
	}
	
}
