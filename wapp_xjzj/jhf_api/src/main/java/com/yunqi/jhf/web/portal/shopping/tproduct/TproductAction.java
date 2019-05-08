package com.yunqi.jhf.web.portal.shopping.tproduct;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
import com.yunqi.jhf.dao.domain.TProduct;
import com.yunqi.jhf.dao.domain.TProductAttribute;
import com.yunqi.jhf.dao.domain.TProductCate;
import com.yunqi.jhf.dao.persistence.TProductBrandDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.service.front.TProductCateService;
import com.yunqi.jhf.service.front.TProductService;
import com.yunqi.jhf.vo.EventProductBean;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;


/**
 * 前台展示产品
 * @author Seek
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/front/product")
public class TproductAction {
	
	private static final Logger logger = Logger.getLogger(TproductAction.class);

	@Autowired
	private TProductCateService tProductCateService;
	@Autowired
	private TProductService tProductService;
	@Autowired
	private TProductBrandDao tProductBrandDao;
	/**
	 * 产品列表
	 */
	@RequestMapping(value = "/productList")
	public ModelAndView productList(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/shopping/tproduct/tproduct_list"); 
		int page = StrUtil.getParamInt(req, "page", 1);
		int cateId = StrUtil.getParamInt(req, "cateId", -1);
		String title = StrUtil.getParamStr(req, "productName",null);
		int brandId = StrUtil.getParamInt(req, "brandId", -1);
		int trueorfalse = StrUtil.getParamInt(req, "trueorfalse", -1);
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		
		List<TProductCate> prodcatelist = tProductCateService.prodCateImageByNew();
		PageList<TProduct> productlist=tProductService.productPageList(page, cateId, title, trueorfalse,brandId);
		mav.addObject("userId",userId);
		mav.addObject("prodcatelist", prodcatelist);
		mav.addObject("productList", productlist);
		mav.addObject("productName", title);
		mav.addObject("cateId", cateId);
		mav.addObject("brandId", brandId);
		mav.addObject("trueorfalse", trueorfalse);
		return mav;
	}
	
	/**
	 * 异步获取 非活动产品列表
	 * @param req
	 * @param JsonInfo
	 * @return
	 */
	@RequestMapping(value = "/getProductPageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getProductPageList(@RequestBody ModelMap JsonInfo) {
		return tProductService.getProductPageList(JsonInfo);
	}
	
	/**
	 * 优化  异步获取  非活动产品列表
	 * @param JsonInfo
	 * @return json
	 */
	@RequestMapping(value = "/getProductPageLists", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getProductPageLists(@RequestBody ModelMap JsonInfo) {
		return tProductService.getProductPageLists(JsonInfo);
	}
	
	
	/**
	 * 活动产品列表
	 */
	@RequestMapping(value = "/productEventList")
	public ModelAndView productEventList(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/shopping/tproduct/teventproduct_list"); 
		int page = StrUtil.getParamInt(req, "page", 1);
		int eventId = StrUtil.getParamInt(req, "eventId", -1);
		int trueorfalse = StrUtil.getParamInt(req, "trueorfalse", -1);
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		PageList<EventProductBean> eventproductlist = tProductService.getEventProductByEid(page, eventId, trueorfalse);
		mav.addObject("userId",userId);
		mav.addObject("eventproductlist", eventproductlist);
		mav.addObject("eventId", eventId);
		mav.addObject("trueorfalse", trueorfalse);
		return mav;
	}
	

	/**
	 * 异步获取 活动产品列表  根据 eventid参数 
	 * @param JsonInfo
	 * @return json
	 */
	@RequestMapping(value = "/getEventProductPageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getEventProductPageList(@RequestBody ModelMap JsonInfo) {
		return tProductService.getEventProductPageList(JsonInfo);
	}
	
	
	/**
	 * 优化  异步获取 活动产品列表  根据 eventid参数 
	 * @param JsonInfo
	 * @return json
	 */
	@RequestMapping(value = "/getEventProductPageLists", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getEventProductPageLists(@RequestBody ModelMap JsonInfo) {
		return tProductService.getEventProductPageLists(JsonInfo);
	}
	
	
	
	/**
	 * 按品牌查询的产品列表
	 */
	@RequestMapping(value = "/productBrandList")
	public ModelAndView productBrandList(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/shopping/tproduct/tproductbybrand_list"); 
		int page = StrUtil.getParamInt(req, "page", 1);
		int brandId = StrUtil.getParamInt(req, "brandId", -1);
		int trueorfalse = StrUtil.getParamInt(req, "trueorfalse", -1);
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		PageList<TProduct> productlist = tProductService.getBrandProductByBid(page,brandId, trueorfalse);
		String brandTitle = tProductBrandDao.loadById(brandId).getTitle();
		mav.addObject("userId",userId);
		mav.addObject("productList", productlist);
		mav.addObject("brandTitle", brandTitle);
		mav.addObject("brandId", brandId);
		mav.addObject("trueorfalse", trueorfalse);
		return mav;
	}
	
	/**
	 * 异步获取 品牌产品列表
	 * @param req
	 * @param JsonInfo
	 * @return
	 */
	@RequestMapping(value = "/getBrandProductPageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getBrandProductPageList(@RequestBody ModelMap JsonInfo) {
		return tProductService.getBrandProductPageList(JsonInfo);
	}
	
	/**
	 * 根据  产品ID 获取 产品详情 
	 */
	@RequestMapping(value = "/getProductDetil")
	public ModelAndView getProductDetil(HttpServletRequest req,
			HttpSession sess) throws Exception {
		ModelAndView mav = new ModelAndView("/shopping/tproduct/tproduct_detail");
		int productId = StrUtil.getParamInt(req, "productId", -1);
		int eventprodId = StrUtil.getParamInt(req, "eventprodId", -1);
		 WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		TProduct product = tProductService.getProductByProId(productId, eventprodId);
		List<TProductAttribute> productAttList = tProductService.getproductAttByProId(productId);
		List<String> proColorList=new ArrayList<>();
		List<String> proSizeList=new ArrayList<>();
		for (TProductAttribute tProductAttribute : productAttList) {
			String color=tProductAttribute.getProductColor();
			String size=tProductAttribute.getProductSize();
			if(proColorList.contains(color)){
				logger.info("产品颜色相同进行合并");
			}else{
				proColorList.add(color);
			}
			if(proSizeList.contains(size)){
				logger.info("产品尺码相同进行合并");
			}else{
				proSizeList.add(size);
			}
		}
		List<String> proImgList =new ArrayList<String>();
		if(product != null){
			proImgList = SplitStrUtil.splitStrToStr(product.getImageId());
		}else{
			logger.info("获取产品详情失败");
		}
		mav.addObject("userId",userId);
		mav.addObject("proImgList", proImgList);
		mav.addObject("productAttList", productAttList);
		mav.addObject("proColorList", proColorList);
		mav.addObject("proSizeList", proSizeList);
		mav.addObject("product", product);
		return mav;
	}
	
	
	/**
	 * 根据  产品尺码 颜色 判断库存剩余 
	 */
	@RequestMapping(value = "/getProdstock")
	public JsonResult getProdstock(HttpServletRequest req){
		JsonResult result = new JsonResult();
		TProductAttribute prodAtt = new TProductAttribute();
		try {
			int productId = StrUtil.getParamInt(req, "productId",-1);
			String productColor = StrUtil.getParamStr(req, "productColor");
			String productSize = StrUtil.getParamStr(req, "productSize");
			prodAtt = tProductService.getProductStock(productId,productColor, productSize);
			result.setData(prodAtt.getStock());
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		return result;
	}
	
	
}
