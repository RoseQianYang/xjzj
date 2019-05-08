package com.yunqi.jhf.web.portal.physicalstore;


import java.security.DigestException;
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

import com.yunqi.common.ServiceException;
import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.domain.TPhysicalStore;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.service.front.TPhysicalStoreService;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

@CrossOrigin
@RestController
@RequestMapping(value = "/store")
public class TPhysicalStoreAction {

	@Autowired
	private TPhysicalStoreService tPhysicalStoreService;
	
	@RequestMapping("/storelocation")
	public ModelAndView location(HttpServletRequest request) throws ServiceException, DigestException {
		WechatSessionUser sessUser = WechatSessionUser.getUser(request);
		ModelAndView mav = new ModelAndView("/physical_store/tphysicalstore_list");
		mav.addObject("sessUser", sessUser);
		return mav;
	}
	
	

	/**
	 * 获取全部的实体门店信息
     * @param currentPage 当前页T
     * @return 分页信息
	 */
	@RequestMapping(value = "/storeList")
	public ModelAndView storeList(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView("/physical_store/tphysicalstore_list"); 
		int near = StrUtil.getParamInt(req, "near");
		/*PageList<TPhysicalStore> storeList=new PageList<>();
	    double x=115.884677;
	    double y=28.66709;
	    sess.setAttribute("latitude", x);
	    sess.setAttribute("longitude", y);
	    double latitude=(double) sess.getAttribute("latitude");
	    double longitude=(double) sess.getAttribute("longitude");
		int page = StrUtil.getParamInt(req, "page", 1);
		int near = StrUtil.getParamInt(req, "near");
		if(near==1){
			storeList = tPhysicalStoreService.findNear(page);
		}else{
			storeList = tPhysicalStoreService.findAllStore(page,latitude,longitude);
		}
		mav.addObject("storeList", storeList);
		mav.addObject("near", near);*/
		mav.addObject("near", near);
		return mav;
	}
	
	/**
	 * 异步获取实体门店信息列表
	 * @param req
	 * @param sess
	 * @param JsonInfo
	 * @return Json
	 */
	@RequestMapping(value = "/getstoreLists", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getstoreLists(HttpServletRequest req,@RequestBody ModelMap JsonInfo) {
		int near = (int) JsonInfo.get("near");
		double latitude =(double) JsonInfo.get("latitude");
		double longitude = (double) JsonInfo.get("longitude");		
		if(near==1){
			return tPhysicalStoreService.findNearByAll();
		}else{
			return tPhysicalStoreService.findAllStores(latitude,longitude);

		}
	}
	
	
	/**
	 * 根据距离排序全部的实体门店信息
     * @param currentPage 当前页T
     * @return 分页信息
	 */
	@RequestMapping(value = "/storeNear")
	public ModelAndView afterOutletsNear(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView("/physical_store/tphysicalstore_list"); 
		int page = StrUtil.getParamInt(req, "page", 1);
		PageList<TPhysicalStore> nearStoreList = tPhysicalStoreService.findNear(page);
		mav.addObject("nearStoreList", nearStoreList);
		return mav;
	}
	/**
	 * 根据id获取实体门店信息
     * @param storeId 
     * @return TPhysicalStore对象
	 */
	@RequestMapping(value = "/storeDetail")
	public ModelAndView storeDetail(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/physical_store/tphysicalstoredetail"); 
		int storeId = StrUtil.getParamInt(req, "storeId",-1);
		TPhysicalStore store = tPhysicalStoreService.findStoreById(storeId);
		String imageId=store.getImageId();
		List<String> imgSrcAllList =  SplitStrUtil.splitStrToStr(imageId);
		if (imgSrcAllList.size()>=4) {
			imgSrcAllList = imgSrcAllList.subList(0, 4);
		}
		mav.addObject("store", store);
		mav.addObject("imgSrcList",imgSrcAllList);
		return mav;
	}
	
}
