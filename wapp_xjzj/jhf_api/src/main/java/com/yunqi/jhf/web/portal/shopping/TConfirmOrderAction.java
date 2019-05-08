package com.yunqi.jhf.web.portal.shopping;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.ShoppingCartDetailDao;
import com.yunqi.jhf.dao.domain.TShoppingCartDetail;
import com.yunqi.jhf.dao.domain.TUserAddress;
import com.yunqi.jhf.dao.persistence.base.TShoppingCartDetailBaseDao;
import com.yunqi.jhf.service.front.TOrderService;
import com.yunqi.jhf.service.front.TUserAddressService;
import com.yunqi.jhf.vo.ShoppingCartDetailBean;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

/**
 * 
 * @author Seek 确定下单的页面
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/front/order")
public class TConfirmOrderAction {
	private static final Logger log = Logger.getLogger(TUserAddressService.class);

	@Autowired
	private TUserAddressService tUserAddressService;
	@Autowired
	private TOrderService tOrderService;
	@Autowired
	private ShoppingCartDetailDao ShoppingcartDetailDao;

	@Autowired
	private TShoppingCartDetailBaseDao tShoppingCartDetailBaseDao;
	/**
	 * 从购物车下订单的页面展示
	 * 
	 * @param(userId List<ShoppingcartDetailBean> productList)
	 */
	@RequestMapping(value = "/orderByCart")
	public ModelAndView orderByCart(HttpServletRequest req, HttpSession sess) throws Exception {
		ModelAndView mav = new ModelAndView("/shopping/confirmorder");
	    WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();	
		String scdIds = req.getParameter("scdIds");
	/*	int price = StrUtil.getParamInt(req, "price", 0);*/
		int noDate=0;
		if(scdIds==null){
			mav.addObject("noDate", noDate);
		}else{
			noDate=1;
			mav.addObject("noDate",noDate);
		}
		int price=0;
		scdIds = scdIds.replace("\"", "");
		scdIds = scdIds.replace("\"", "");
		scdIds = scdIds.replace("[", "");
		scdIds = scdIds.replace("]", "");
		scdIds = scdIds.replace(" ", "");
		String[] result = scdIds.split(",");
		int addressId = StrUtil.getParamInt(req, "addressId", -1);
		List<ShoppingCartDetailBean> tScdList = new ArrayList<>();
		// 没有传过来addressId，默认显示本用户的默认地址，没有的话显示最早添加的地址
		TUserAddress address = new TUserAddress();
		if (addressId == -1) {
			address=tUserAddressService.getUserAddressDefault(userId);
			if(address==null){
				List<TUserAddress> useraddList = tUserAddressService.getUserAddressList(userId);
				if(useraddList.size()!=0){
					address = useraddList.get(0);
				}else{
					address=null;
				}
			}
				
		} else {
			address = tUserAddressService.getAddressById(addressId);
		}
		if (result != null) {
			for (int i = 0; i < result.length; i++) {
				int scdId = Integer.parseInt(result[i]);
				ShoppingCartDetailBean ShoppingCartDetail = ShoppingcartDetailDao.getshopcartDetail(scdId);
				
				int totlePrice=ShoppingCartDetail.getProductNum()*ShoppingCartDetail.getProductPrice();
				price+=totlePrice;
				tScdList.add(ShoppingCartDetail);
			}
		}
		mav.addObject("address", address);
		mav.addObject("tScdList", tScdList);
		mav.addObject("price", price);
		return mav;
	}

	/**
	 * 立即购买下订单的页面展示
	 * 
	 * @param(userId List<ShoppingcartDetailBean> productList)
	 */
	@RequestMapping(value = "/quickByConfirmOrder")
	public ModelAndView quickByConfirmOrder(HttpServletRequest req, HttpSession sess) {
		ModelAndView mav = new ModelAndView("/shopping/confirmorder");
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		List<ShoppingCartDetailBean> tScdList = new ArrayList<>();
		int price=0;
		int productId = StrUtil.getParamInt(req, "productId");
		int productNum = StrUtil.getParamInt(req, "productNum", 1);
		String productColor = req.getParameter("productColor");
		String productSize = req.getParameter("productSize");
		int productPrice = StrUtil.getParamInt(req, "productPrice");
		//给购物车详情里面添加一条数据
		try{
			TShoppingCartDetail tsd=new TShoppingCartDetail();
			tsd.setProductColor(productColor);
			tsd.setProductId(productId);
			tsd.setProductNum(productNum);
			tsd.setProductPrice(productPrice);
			tsd.setProductSize(productSize);
			tsd.setShoppingCartId(0);
			int tsdId=tShoppingCartDetailBaseDao.insert(tsd);
			if(tsdId!=0){
				ShoppingCartDetailBean ShoppingCartDetail = ShoppingcartDetailDao.getshopcartDetail(tsdId);
				int totlePrice=ShoppingCartDetail.getProductNum()*ShoppingCartDetail.getProductPrice();
				price+=totlePrice;
				tScdList.add(ShoppingCartDetail);
			}
			int addressId = StrUtil.getParamInt(req, "addressId", -1);
			// 没有传过来addressId，默认显示本用户最早添加的地址
			TUserAddress address = new TUserAddress();
			if (addressId == -1) {
				address=tUserAddressService.getUserAddressDefault(userId);
				if(address==null){
					List<TUserAddress> useraddList = tUserAddressService.getUserAddressList(userId);
					if(useraddList.size()!=0){
						address = useraddList.get(0);
					}else{
						address=null;
					}
				}
					
			} else {
				address = tUserAddressService.getAddressById(addressId);
			}
			mav.addObject("address", address);
			mav.addObject("tScdList", tScdList);
			mav.addObject("price", price);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return mav;
	}

	
	/**
	 * 购物车下订单的页面展示
	 * 
	 * @param(userId )
	 */
	@RequestMapping(value = "/submitOrder")
	public JsonResult submitOrder(HttpServletRequest req, HttpSession sess) throws Exception {

		WechatSessionUser sessUser = WechatSessionUser.getUser(req);

		JsonResult result = new JsonResult();
		String scdIdstr = req.getParameter("scdIds");
		List<String> scdIds = SplitStrUtil.splitStrToStr(scdIdstr);
		if (scdIds == null || scdIds.size() == 0) {
			throw new RuntimeException("not choose shopping card id");
		}

		int userId = sessUser.getUserId();
		int addressId = StrUtil.getParamInt(req, "addressId");
		String remark = StrUtil.getParamStr(req, "remark");
		result = tOrderService.submitOrder(userId, addressId, remark, scdIds);

//		return new ModelAndView("redirect:/front/order/orderDetailList.do?orderId="
//				+ result.getData().toString());
		return result;

	}

}
