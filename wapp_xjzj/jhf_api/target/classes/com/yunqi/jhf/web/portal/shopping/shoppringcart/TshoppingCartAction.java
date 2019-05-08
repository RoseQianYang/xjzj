package com.yunqi.jhf.web.portal.shopping.shoppringcart;

import javax.servlet.http.HttpServletRequest;
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
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.service.front.TShoppingCartService;
import com.yunqi.jhf.vo.ShoppingCartDetailBean;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

import io.swagger.annotations.ApiOperation;

/**
 * 购物车相关
 * @author seek
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/front/shoppingcart")
public class TshoppingCartAction {

	
	@Autowired
	private TShoppingCartService tShoppingCartService;

	
	/**
	 * 根据用户Id查询购物车详情
	 */
	@RequestMapping(value = "/cartDetail")
	public ModelAndView cartDetail(
			HttpServletRequest req, HttpSession sess) throws Exception {
	    ModelAndView mav = new ModelAndView("/shopping/shoppingcart/tshoppingcart");  
		int page = StrUtil.getParamInt(req, "page", 1);
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		PageList<ShoppingCartDetailBean> cartDetail=tShoppingCartService.findShopcartProduct(page, userId);
		mav.addObject("cartDetail",cartDetail);
		return mav ;
	}

	/**
	 * 异步获取 购物车详情列表
	 * @param req
	 * @param sess
	 * @param JsonInfo
	 * @return Json
	 */
	@ApiOperation(value = "查询房车列表", notes = "data{List ShoppingCartDetailBean}", response = JsonResult.class)
	@RequestMapping(value = "/cartDetails", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getcartDetails(HttpServletRequest req,@RequestBody ModelMap JsonInfo) {
		return tShoppingCartService.getcartDetails(req,JsonInfo);
	}
	
	
	/**
	 * 加入购物车
	 */
	@RequestMapping(value = "/insert")
	public JsonResult insert(
			HttpServletRequest req, HttpSession sess) throws Exception {
		JsonResult result = new JsonResult();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		int productId = StrUtil.getParamInt(req, "productId");
		int productNum = StrUtil.getParamInt(req, "productNum", 1);
		String productColor = req.getParameter("productColor");
		String productSize = req.getParameter("productSize");
		int productPrice = StrUtil.getParamInt(req, "productPrice");
		//result.
		result=tShoppingCartService.insertShopCart(userId, productId, productNum, productColor, productSize,productPrice);
		return result;
	}

	/**
	 * 删除购物车产品
	 */
	@RequestMapping(value = "/deleteShoppingCart")
	public JsonResult delete(HttpServletRequest req, HttpSession sess)
			throws Exception {
		JsonResult result = new JsonResult();
		int shoppingCartDetailId = StrUtil.getParamInt(req, "shoppingCartDetailId", -1);
		try {
			result = tShoppingCartService.delete(shoppingCartDetailId);
			result.success("购物车此产品删除成功！");
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据购物车产品Id进行数量的添加和减少
	 */
	@RequestMapping(value = "/updateNum")
	public JsonResult updateNum(HttpServletRequest req, HttpSession sess)
			throws Exception {
		JsonResult result = new JsonResult();
		int shoppingCartDetailId = StrUtil.getParamInt(req, "shoppingCartDetailId", -1);
		int jiaJian = StrUtil.getParamInt(req, "jiaJian", -1);
		try {
			result = tShoppingCartService.update(shoppingCartDetailId,jiaJian);
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		return result;
	}
	
	
}
