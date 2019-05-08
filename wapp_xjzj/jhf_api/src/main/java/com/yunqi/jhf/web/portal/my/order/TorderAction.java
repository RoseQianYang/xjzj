package com.yunqi.jhf.web.portal.my.order;

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
import com.yunqi.jhf.dao.domain.TOrder;
import com.yunqi.jhf.service.front.TOrderDetailService;
import com.yunqi.jhf.service.front.TOrderService;
import com.yunqi.jhf.vo.OrderDetailBean;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

/**
 * 个人中心订单产品相关
 * 
 * @author llh
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/front/order")
public class TorderAction {

	@Autowired
	private TOrderService tOrderService;

	@Autowired
	private TOrderDetailService tOrderdetailService;


	/**
	 * 获取订单列表
	 */
	@RequestMapping(value = "/orderList")
	public ModelAndView orderList(HttpServletRequest req, HttpSession sess, HttpServletResponse res) throws Exception {
		ModelAndView mav = new ModelAndView("/my/my_order/torder_list");
//		int page = StrUtil.getParamInt(req, "page", 1);
		WechatSessionUser user = WechatSessionUser.getUser(req);
		//String status = StrUtil.getParamStr(req, "status","-1");
		int status = StrUtil.getParamInt(req, "status",-1);
		//PageList<TOrder> orderList = tOrderService.getOrderList(page, user.getUserId(),status);
		
		List<TOrder> orderList = tOrderService.getOrderList( user.getUserId(),status);
		List<OrderDetailBean> orderdetailList = tOrderdetailService.getOrderDetailByUidList(user.getUserId());
		mav.addObject("ostatus", status);
		mav.addObject("orderList", orderList);
		mav.addObject("orderdetailList", orderdetailList);
		return mav;
	}
	
	/**
	 * 根据 userId 异步获取订单列表
	 * @param req
	 * @param JsonInfo
	 * @return json
	 */
	@RequestMapping(value = "/getorderLists", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getorderLists(HttpServletRequest req,@RequestBody ModelMap JsonInfo) {
		return tOrderService.getorderLists(req,JsonInfo);
	}

	/**
	 * 获取订单详情列表
	 */
	@RequestMapping(value = "/orderDetailList")
	public ModelAndView orderDetailList(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView("/my/my_order/torderdetail");
		int orderId = StrUtil.getParamInt(req, "orderId", -1);

		//TUserAddress useraddress = tUserAddressService.getUserAddressByOid(orderId);
		List<OrderDetailBean> orderdetailList = tOrderdetailService.getOrderDetailByOidList(orderId);
		TOrder order = tOrderService.getOrderByOid(orderId);

		//mav.addObject("useraddress", useraddress);
		mav.addObject("order", order);
		mav.addObject("orderdetailList", orderdetailList);
		return mav;
	}
	

	
	
	
	
	/**
	 * 改变订单的状态  确认收货
	 */
	@RequestMapping(value = "/sumitOrder")
	public JsonResult sumitOrder(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		JsonResult result = new JsonResult();
		WechatSessionUser user = WechatSessionUser.getUser(req);
		String orderStatus = StrUtil.getParamStr(req, "orderStatus");
		int orderId = StrUtil.getParamInt(req, "orderId", -1);
		result = tOrderService.sumitOrderStatus(orderStatus, orderId,user.getUserId());
		return result;
	}
	
	/**
	 * 改变订单的状态 取消订单
	 */
	@RequestMapping(value = "/cancelOrder")
	public JsonResult cancelOrder(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		JsonResult result = new JsonResult();
		WechatSessionUser user = WechatSessionUser.getUser(req);
		String orderStatus = StrUtil.getParamStr(req, "orderStatus");
		int orderId = StrUtil.getParamInt(req, "orderId", -1);
		result = tOrderService.cancelOrderStatus(orderStatus, orderId,user.getUserId());
		return result;
	}
	
	/**
	 * 订单状态为“支付取消”的状态 时可以进行删除订单
	 */
	@RequestMapping(value = "/delectOrder")
	public JsonResult delectOrder(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		JsonResult result = new JsonResult();
		WechatSessionUser user = WechatSessionUser.getUser(req);
		String orderStatus = StrUtil.getParamStr(req, "orderStatus");
		int orderId = StrUtil.getParamInt(req, "orderId", -1);
		result = tOrderService.delectOrder(orderStatus, orderId,user.getUserId());
		return result;
	}
	
}
