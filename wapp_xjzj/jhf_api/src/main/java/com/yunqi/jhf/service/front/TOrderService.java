package com.yunqi.jhf.service.front;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.FriendDao;
import com.yunqi.jhf.dao.IntegralConversionDao;
import com.yunqi.jhf.dao.OrderDetailDao;
import com.yunqi.jhf.dao.domain.TIntegral;
import com.yunqi.jhf.dao.domain.TOrder;
import com.yunqi.jhf.dao.domain.TOrderDetail;
import com.yunqi.jhf.dao.domain.TProduct;
import com.yunqi.jhf.dao.domain.TProductAttribute;
import com.yunqi.jhf.dao.domain.TShoppingCartDetail;
import com.yunqi.jhf.dao.domain.TSysLog;
import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.dao.domain.TUserAddress;
import com.yunqi.jhf.dao.persistence.TIntegralDao;
import com.yunqi.jhf.dao.persistence.TOrderDao;
import com.yunqi.jhf.dao.persistence.TOrderDetailDao;
import com.yunqi.jhf.dao.persistence.TProductAttributeDao;
import com.yunqi.jhf.dao.persistence.TProductDao;
import com.yunqi.jhf.dao.persistence.TShoppingCartDetailDao;
import com.yunqi.jhf.dao.persistence.TSysLogDao;
import com.yunqi.jhf.dao.persistence.TUserAddressDao;
import com.yunqi.jhf.dao.persistence.TUserDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlDelete;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.service.SysConfigService;
import com.yunqi.jhf.vo.OrderDetailBean;
import com.yunqi.jhf.vo.OrderNewsBean;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

@Service
public class TOrderService {

	private static final Logger log = Logger.getLogger(TOrderService.class);

	@Resource
	private TOrderDao tOrderDao;

	@Resource
	private OrderDetailDao orderDetailDao;
	
	@Resource
	private TProductDao tProductDao;
	
	@Resource
	private TProductAttributeDao tProductAttributeDao;

	@Resource
	private TOrderDetailDao tOrderDetailDao;
	
	@Resource
	private TUserDao tUserDao;
	
	@Resource
	private TUserAddressDao tUserAddressDao;

	@Resource
	private TShoppingCartDetailDao tShoppingCartDetailDao;
	
	@Resource
	private FriendDao friendDao;

	@Resource
	private TIntegralDao tIntegralDao;

	@Resource
	private IntegralConversionDao integralConversionDao;

	@Resource
	private TSysLogDao tSysLogDao;

	@Resource
	private SysConfigService sysConfigService;

	/**
	 * 获取订单列表
	 * 
	 * @param currentPage
	 *            当前页
	 * @return list
	 */
	public PageList<TOrder> getOrderList(int currentPage, int userId,int status) {

		TOrder order = new TOrder();
		PageList<TOrder> pageList = new PageList<>();
		SqlSelect sqlSelect = new SqlSelect();
		
		if (currentPage != -1) {
			pageList.setCurrentPage(currentPage);
			pageList.setPageSize(Const.FONT_PAGE_SIZE);
		}
		try {
			if (userId != -1) {
				if(status==-1){
					order.setUserId(userId);
					sqlSelect.where(TOrder.SQL_userId).and(" order_total_price>0")
							.orderBy(" order by id desc");
					pageList = tOrderDao.pageList(pageList, true, sqlSelect, order);
				}else if(status == 1){
					order.setUserId(userId);
					order.setOrderStatus(Const.ORDER_STATUS_WAITING_PAY); // 未支付 代付款Const. ORDER_STATUS_WAITING_PAY
					sqlSelect.where(TOrder.SQL_userId).and(TOrder.SQL_orderStatus).and(" order_total_price>0").orderBy(" order by id desc");
					pageList = tOrderDao.pageList(pageList, true, sqlSelect, order);
				}else if(status==3){
					order.setUserId(userId);
					order.setOrderStatus(Const.ORDER_STATUS_ALREADY_PAYED); // 代发货 Const.ORDER_STATUS_ALREADY_PAYED
					sqlSelect.where(TOrder.SQL_userId).and(TOrder.SQL_orderStatus).and(" order_total_price>0").orderBy(" order by id desc");
					pageList = tOrderDao.pageList(pageList, true, sqlSelect, order);
				}else if(status==4){
					order.setUserId(userId);
					order.setOrderStatus(Const.ORDER_STATUS_ALREADY_SIPPING); // 已发货，待收货 Const.ORDER_STATUS_ALREADY_SIPPING
					sqlSelect.where(TOrder.SQL_userId).and(TOrder.SQL_orderStatus).and(" order_total_price>0").orderBy(" order by id desc");
					pageList = tOrderDao.pageList(pageList, true, sqlSelect, order);
				}
				
			} else {
				log.error("获取用户id失败");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;
	}
	
	/**
	 * 判断订单是否超过30分钟
	 */
	public void getOrderTimeOver() {
		log.info("=======开始进行订单超时验证=======");
		try {
			SqlSelect sql = new SqlSelect().where("order_status = '1'").
										and("DATE_ADD(create_time,INTERVAL '00:30:00' DAY_SECOND) < NOW()");
			List<TOrder> orderList = tOrderDao.list(sql, null);
			if (orderList.size() > 0) {
				for (TOrder tOrder : orderList) {
					cancelOrderStatus(Const.ORDER_STATUS_WAITING_PAY, tOrder.getId(), tOrder.getUserId());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("订单超时操作错误");
		}
	}
	
	/**
	 * 全查获取订单列表
	 * 
	 * @param 全查订单
	 *     
	 * @return list
	 */
	public List<TOrder> getOrderList( int userId,int status) {
		TOrder order = new TOrder();
		List<TOrder> orderList = new ArrayList<>();
		SqlSelect sqlSelect = new SqlSelect();
		try {
			//首先进行订单超时判断
			getOrderTimeOver();
			if (userId != -1) {
				if(status==-1){
					order.setUserId(userId);
					sqlSelect.where(TOrder.SQL_userId).and(" order_total_price>0")
							.orderBy(" order by id desc");
					orderList = tOrderDao.list(sqlSelect, order);
				}else if(status == 1){
					order.setUserId(userId);
					order.setOrderStatus(Const. ORDER_STATUS_WAITING_PAY); // 未支付 代付款Const. ORDER_STATUS_WAITING_PAY
					sqlSelect.where(TOrder.SQL_userId).and(TOrder.SQL_orderStatus).and(" order_total_price>0").orderBy(" order by id desc");
					orderList = tOrderDao.list(sqlSelect, order);
				}else if(status==3){
					order.setUserId(userId);
					order.setOrderStatus(Const.ORDER_STATUS_ALREADY_PAYED); // 代发货 Const.ORDER_STATUS_ALREADY_PAYED
					sqlSelect.where(TOrder.SQL_userId).and(TOrder.SQL_orderStatus).and(" order_total_price>0").orderBy(" order by id desc");
					orderList = tOrderDao.list(sqlSelect, order);
				}else if(status==4){
					order.setUserId(userId);
					order.setOrderStatus(Const.ORDER_STATUS_ALREADY_SIPPING); // 已发货，待收货 Const.ORDER_STATUS_ALREADY_SIPPING
					sqlSelect.where(TOrder.SQL_userId).and(TOrder.SQL_orderStatus).and(" order_total_price>0").orderBy(" order by id desc");
					orderList = tOrderDao.list(sqlSelect, order);
				}
			} else {
				log.error("获取用户id失败");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return orderList;
	}
	

	
	/**
	 * 异步 通过userId 和  status 获取订单列表
	 * @param req
	 * @param jsonInfo
	 * @return json
	 */
	public JsonResult getorderLists(HttpServletRequest req,ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<TOrder> OrderDetailList = new PageList<TOrder>();
		List<OrderDetailBean> orderDetailPageList = new ArrayList<OrderDetailBean>();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		SqlSelect sql = new SqlSelect();
		int status = -1; // 默认 为 全部状态列表
		ModelMap map = new ModelMap();
		map.put("userId", userId);
		if(jsonInfo.get(ConstantTool.STATUS) != null) {
			status = (int) jsonInfo.get(ConstantTool.STATUS);
		}
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				OrderDetailList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			} 
			OrderDetailList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			if (userId != -1) {
				jsonInfo.put(TOrder.PROP_userId, userId);
				if (status == 1) {
					// 未支付 代付款
					jsonInfo.put(TOrder.PROP_orderStatus, Const.ORDER_STATUS_WAITING_PAY);
					sql.where(TOrder.SQL_userId).and(TOrder.SQL_orderStatus).and(" order_total_price>0").orderBy(" order by id desc");
					OrderDetailList = tOrderDao.pageListByMap(OrderDetailList, true, sql, jsonInfo);
					orderDetailPageList = orderDetailDao.getOrderDetailByUidList(map);
					if (OrderDetailList != null) {
						result.setData(OrderDetailList);
						result.setData(orderDetailPageList);
						result.success("微信端获取未支付 代付款 订单列表获取成功");
						log.info("微信端获取未支付 代付款 订单列表接口执行成功");
					} else {
						result.setData(OrderDetailList);
						result.setData(orderDetailPageList);
						log.info("微信端获取未支付 代付款 订单列表接口执行失败");
						result.error("微信端获取未支付 代付款 订单列表接口执行失败");
					}
				} else if (status == 3) {
					// 代发货
					jsonInfo.put(TOrder.PROP_orderStatus,Const. ORDER_STATUS_ALREADY_PAYED);
					sql.where(TOrder.SQL_userId).and(TOrder.SQL_orderStatus).and(" order_total_price>0").orderBy(" order by id desc");
					OrderDetailList = tOrderDao.pageListByMap(OrderDetailList, true, sql, jsonInfo);
					orderDetailPageList = orderDetailDao.getOrderDetailByUidList(map);
					if (OrderDetailList != null) {
						result.setData(OrderDetailList);
						result.setData(orderDetailPageList);
						result.success("微信端获取代发货 订单列表获取成功");
						log.info("微信端获取代发货 订单列表接口执行成功");
					} else {
						result.setData(OrderDetailList);
						result.setData(orderDetailPageList);
						log.info("微信端获取代发货 订单接口执行失败");
						result.error("微信端获取代发货 订单接口执行失败");
					}
				} else  if (status == 4){
					// 已发货，待收货
					jsonInfo.put(TOrder.PROP_orderStatus, Const. ORDER_STATUS_ALREADY_SIPPING);
					sql.where(TOrder.SQL_userId).and(TOrder.SQL_orderStatus).and(" order_total_price>0").orderBy(" order by id desc");
					OrderDetailList = tOrderDao.pageListByMap(OrderDetailList, true, sql, jsonInfo);
					orderDetailPageList = orderDetailDao.getOrderDetailByUidList(map);
					if (OrderDetailList != null) {
						result.setData(OrderDetailList);
						result.setData(orderDetailPageList);
						result.success("微信端获取 已发货，待收货 订单列表获取成功");
						log.info("微信端获取 已发货，待收货 订单列表接口执行成功");
					} else {
						log.info("微信端获取 已发货，待收货 订单接口执行失败");
						result.setData(OrderDetailList);
						result.setData(orderDetailPageList);
						result.error("微信端获取 已发货，待收货 订单接口执行失败");
					}
				}else {
					sql.where(TOrder.SQL_userId).and(" order_total_price>0").orderBy(" order by id desc");
					OrderDetailList = tOrderDao.pageListByMap(OrderDetailList, true, sql, jsonInfo);
					orderDetailPageList = orderDetailDao.getOrderDetailByUidList(map);
					if (OrderDetailList != null) {
						result.success("微信端获取 全部状态 订单列表获取成功");
						log.info("微信端获取 全部状态 订单列表接口执行成功");
						result.setData(OrderDetailList);
						result.setData(orderDetailPageList);
					} else {
						log.info("微信端获取 全部状态订单接口执行失败");
						result.setData(OrderDetailList);
						result.setData(orderDetailPageList);
						result.error("微信端获取 全部状态 订单接口执行失败");
					}
				}
			}else {
				log.error("获取用户id失败");
				return result.error("获取用户id失败");
			}
		} catch (Exception e) {
			result.error("获取订单列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	
	/**
	 * 获取最新下单的状况，在首页前台最新动态里面显示
	 * 
	 * @param 
	 * @return list
	 */
	public List<OrderNewsBean> getOrderNew() {
		TOrder order = new TOrder();
		List<OrderNewsBean> OrderNewsList=new ArrayList<>();
		try {
				SqlSelect sqlSelect = new SqlSelect().orderBy(" order by create_time desc");
				List<TOrder> orderList= tOrderDao.list(sqlSelect, order);
				List<TOrder> ordertwo=orderList.subList(0, 2);
				for (TOrder tOrder : ordertwo) {
					int userId=tOrder.getUserId();
					TUser user=tUserDao.loadById(userId);
					String userName=StrUtil.left(user.getUserName(), 6);
					TOrderDetail od=new TOrderDetail();
					od.setOrderId(tOrder.getId());
					SqlSelect sql = new SqlSelect().orderBy(" order by create_time asc").where(TOrderDetail.SQL_orderId);
					List<TOrderDetail> odlist= tOrderDetailDao.list(sql, od);
					if (odlist.size() != 0) {
						int pId=odlist.get(0).getProductId();
						String proName=StrUtil.left(tProductDao.loadById(pId).getTitle(), 8);
						
						String xiaoXi=userName+"刚才购买了"+proName;
						OrderNewsBean orn=new OrderNewsBean();
						orn.setOrderNews(xiaoXi);
						orn.setProductId(pId);
						OrderNewsList.add(orn);
					}
				}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return OrderNewsList;
	}
	

	/**
	 * 通过orderId获取用户地址列表
	 * 
	 * @param orderId
	 * @return TOrder
	 */
	public TOrder getOrderByOid(int orderId) {

		TOrder order = new TOrder();
		try {
			if (orderId != -1) {
				order = tOrderDao.loadById(orderId);
			} else {
				log.error("获取订单id失败");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return order;
	}

	/**
	 * 提交订单
	 * 
	 * @param userId,
	 *            useraddId, orderName, address,orderStatus,remark,productCount,
	 *            productPrice,productColor,productSize
	 * @return result
	 */
	public JsonResult submitOrder(int userId, int addressId, String remark, List<String> scdIds) {
		JsonResult result = new JsonResult();
		try {
			TOrder order = new TOrder();
			order.setUserId(userId);
			if (addressId != -1) {
				order.setUserAddressId(addressId);
				TUserAddress userAddress = tUserAddressDao.loadById(addressId);
				order.setAddress(userAddress.getAddress()+userAddress.getAddressDetail());
				order.setConsignee(userAddress.getConsignee());
				order.setMobile(userAddress.getMobile());
			} else {
				log.error("收货人地址不能为空");
				result.error("收货人地址不能为空");
			}
			order.setOrderNo(System.currentTimeMillis() + "");
			order.setOrderStatus(Const.ORDER_STATUS_WAITING_PAY);
			order.setPaydeTime(StrUtil.getCurrentTimestamp());
			if (remark != null || "".equals(remark)) {
				order.setRemark(remark);
			}
			for (String id : scdIds) {
				TShoppingCartDetail tsca = tShoppingCartDetailDao.loadById(Integer.parseInt(id));
				if (tsca == null) {
					throw new RuntimeException("shopping cart detail is not exist");
				}
				if(StringUtils.isEmpty(tsca.getProductColor())&&(StringUtils.isNotEmpty(tsca.getProductSize()))){
					tsca.getProductId();
					
					TProduct prod = tProductDao.loadById(tsca.getProductId());
					if (prod == null) {
						throw new RuntimeException("product is not exist");
					}else{
						TProductAttribute tProductAttribute=new TProductAttribute();
						tProductAttribute.setProductId(tsca.getProductId());
						//tProductAttribute.setProductColor(tsca.getProductColor());
						tProductAttribute.setProductSize(tsca.getProductSize());
						SqlSelect sqlSelect=new SqlSelect().where(TProductAttribute.SQL_productId).and(TProductAttribute.SQL_productSize);
						
						TProductAttribute tProducta= tProductAttributeDao.load(sqlSelect, tProductAttribute);
						if(tProducta!=null){
							//int attId=tProducta.getId();
							if(tsca.getProductNum()>tProducta.getStock()){
								result.error("购物车此商品库存不足请重新下单");
								log.error("购物车此商品库存不足请重新下单");
								return result;
							}
						}else{
							result.error("此商品暂无库存");
							log.error("所选商品暂无库存");
							return result;
						}	
							
					}
					
				}else if(StringUtils.isNotEmpty(tsca.getProductColor())&&(StringUtils.isEmpty(tsca.getProductSize()))){
					tsca.getProductId();
					
					TProduct prod = tProductDao.loadById(tsca.getProductId());
					if (prod == null) {
						throw new RuntimeException("product is not exist");
					}else{
						TProductAttribute tProductAttribute=new TProductAttribute();
						tProductAttribute.setProductId(tsca.getProductId());
						tProductAttribute.setProductColor(tsca.getProductColor());
						//tProductAttribute.setProductSize(tsca.getProductSize());
						SqlSelect sqlSelect=new SqlSelect().where(TProductAttribute.SQL_productId).and(TProductAttribute.SQL_productColor);
						
						TProductAttribute tProducta= tProductAttributeDao.load(sqlSelect, tProductAttribute);
						if(tProducta!=null){
							//int attId=tProducta.getId();
							if(tsca.getProductNum()>tProducta.getStock()){
								result.error("购物车此商品库存不足请重新下单");
								log.error("购物车此商品库存不足请重新下单");
								return result;
							}
						}else{
							result.error("此商品暂无库存");
							log.error("所选商品暂无库存");
							return result;
						}	
							
					}
					
					
				}else if(StringUtils.isEmpty(tsca.getProductColor())&&(StringUtils.isEmpty(tsca.getProductSize()))){
					tsca.getProductId();
					TProduct prod = tProductDao.loadById(tsca.getProductId());
					if (prod == null) {
						throw new RuntimeException("product is not exist");
					}else{
						TProductAttribute tProductAttribute=new TProductAttribute();
						tProductAttribute.setProductId(tsca.getProductId());
						//tProductAttribute.setProductColor(tsca.getProductColor());
						//tProductAttribute.setProductSize(tsca.getProductSize());
						SqlSelect sqlSelect=new SqlSelect().where(TProductAttribute.SQL_productId);
						
						TProductAttribute tProducta= tProductAttributeDao.load(sqlSelect, tProductAttribute);
						if(tProducta!=null){
							//int attId=tProducta.getId();
							if(tsca.getProductNum()>tProducta.getStock()){
								result.error("购物车此商品库存不足请重新下单");
								log.error("购物车此商品库存不足请重新下单");
								return result;
							}
						}else{
							result.error("此商品暂无库存");
							log.error("所选商品暂无库存");
							return result;
						}	
							
					}
				}else{
					tsca.getProductId();
					
					TProduct prod = tProductDao.loadById(tsca.getProductId());
					if (prod == null) {
						throw new RuntimeException("product is not exist");
					}else{
						TProductAttribute tProductAttribute=new TProductAttribute();
						tProductAttribute.setProductId(tsca.getProductId());
						tProductAttribute.setProductColor(tsca.getProductColor());
						tProductAttribute.setProductSize(tsca.getProductSize());
						SqlSelect sqlSelect=new SqlSelect().where(TProductAttribute.SQL_productId).and(TProductAttribute.SQL_productSize).and(TProductAttribute.SQL_productColor);
						
						TProductAttribute tProducta= tProductAttributeDao.load(sqlSelect, tProductAttribute);
						if(tProducta!=null){
							//int attId=tProducta.getId();
							if(tsca.getProductNum()>tProducta.getStock()){
								result.error("购物车此商品库存不足请重新下单");
								log.error("购物车此商品库存不足请重新下单");
								return result;
							}
						}else{
							result.error("此商品暂无库存");
							log.error("所选商品暂无库存");
							return result;
						}	
							
					}
				}
				
				
			}
			
			//所有条件满足才可以进行下单操作
			int orderId  = tOrderDao.insert(order);
			
			TOrder tOrder=tOrderDao.loadById(orderId);
			//生成订单后给订单详情添加商品
			if(orderId>0){
				result=submitOrderDetail(userId,tOrder, scdIds);
				return result;
			}else{
				result.error("添加订单失败");
				return result;
			}
			
		} catch (Exception e) {
			result.error("添加订单失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 通过订单添加订单详情
	 * 
	 * @param userId用户ID  order scdIds
	 * @return JsonResult
	 */	
	public JsonResult submitOrderDetail(int userId,TOrder order , List<String> scdIds) {
		
		JsonResult result = new JsonResult();
		int orderId=order.getId();
		int totalPrice = 0;
		
		try {
		
		for (String id : scdIds) {
			TShoppingCartDetail tsca = tShoppingCartDetailDao.loadById(Integer.parseInt(id));
			
			if(StringUtils.isEmpty(tsca.getProductColor())&&(StringUtils.isNotEmpty(tsca.getProductSize()))){
				
				tsca.getProductId();
				TProduct prod = tProductDao.loadById(tsca.getProductId());
					TProductAttribute tProductAttribute=new TProductAttribute();
					tProductAttribute.setProductId(tsca.getProductId());
					//tProductAttribute.setProductColor(tsca.getProductColor());
					tProductAttribute.setProductSize(tsca.getProductSize());
					SqlSelect sqlSelect=new SqlSelect().where(TProductAttribute.SQL_productId).and(TProductAttribute.SQL_productSize);
					
					TProductAttribute tProducta= tProductAttributeDao.load(sqlSelect, tProductAttribute);
					int attId=tProducta.getId();
						TOrderDetail tOrderDetail = new TOrderDetail();
						tOrderDetail.setOrderId(orderId);
						tOrderDetail.setProductId(tsca.getProductId());
						tOrderDetail.setProductCount(tsca.getProductNum());
						//tOrderDetail.setProductColor(tsca.getProductColor());
						tOrderDetail.setProductPrice(tsca.getProductPrice());
						tOrderDetail.setProductSize(tsca.getProductSize());
						int stock=tProducta.getStock()-tsca.getProductNum();
						
						TProductAttribute tPa=new TProductAttribute();
						tPa.setId(attId);
						tPa.setStock(stock);
						SqlUpdate sqlUpdate=new SqlUpdate().addColumn(TProductAttribute.SQL_stock).where(TProductAttribute.SQL_id);
						tProductAttributeDao.update(sqlUpdate, tPa);
						
						tOrderDetail.setProductBrandId(prod.getBrandId());
						tOrderDetail.setProductCateId(prod.getCateId());
						tOrderDetail.setUserId(userId);

						tOrderDetail.setOrderStatus(Const.ORDER_STATUS_WAITING_PAY);

						tOrderDetailDao.insert(tOrderDetail);
						tShoppingCartDetailDao.deleteById(Integer.parseInt(id));

						totalPrice = totalPrice + tsca.getProductPrice() * tsca.getProductNum();
					

			}else if(StringUtils.isNotEmpty(tsca.getProductColor())&&(StringUtils.isEmpty(tsca.getProductSize()))){
				

				tsca.getProductId();
			
				TProduct prod = tProductDao.loadById(tsca.getProductId());
				if (prod == null) {
					throw new RuntimeException("product is not exist");
				}else{
					TProductAttribute tProductAttribute=new TProductAttribute();
					tProductAttribute.setProductId(tsca.getProductId());
					tProductAttribute.setProductColor(tsca.getProductColor());
					//tProductAttribute.setProductSize(tsca.getProductSize());
					SqlSelect sqlSelect=new SqlSelect().where(TProductAttribute.SQL_productId).and(TProductAttribute.SQL_productColor);
					
					TProductAttribute tProducta= tProductAttributeDao.load(sqlSelect, tProductAttribute);
					if(tProducta!=null){
						int attId=tProducta.getId();
						if(tsca.getProductNum()>tProducta.getStock()){
							result.error("购物车此商品库存不足请重新下单");
							log.error("购物车此商品库存不足请重新下单");
							return result;
						}
						TOrderDetail tOrderDetail = new TOrderDetail();
						tOrderDetail.setOrderId(orderId);
						tOrderDetail.setProductId(tsca.getProductId());
						tOrderDetail.setProductCount(tsca.getProductNum());
						tOrderDetail.setProductColor(tsca.getProductColor());
						tOrderDetail.setProductPrice(tsca.getProductPrice());
						//tOrderDetail.setProductSize(tsca.getProductSize());
						int stock=tProducta.getStock()-tsca.getProductNum();
						
						TProductAttribute tPa=new TProductAttribute();
						tPa.setId(attId);
						tPa.setStock(stock);
						SqlUpdate sqlUpdate=new SqlUpdate().addColumn(TProductAttribute.SQL_stock).where(TProductAttribute.SQL_id);
						tProductAttributeDao.update(sqlUpdate, tPa);
						
						tOrderDetail.setProductBrandId(prod.getBrandId());
						tOrderDetail.setProductCateId(prod.getCateId());
						tOrderDetail.setUserId(userId);

						tOrderDetail.setOrderStatus(Const.ORDER_STATUS_WAITING_PAY);

						tOrderDetailDao.insert(tOrderDetail);
						tShoppingCartDetailDao.deleteById(Integer.parseInt(id));

						totalPrice = totalPrice + tsca.getProductPrice() * tsca.getProductNum();
					}else{
						result.error("购物车此商品暂无库存");
						log.error("所选商品暂无库存");
						return result;
					}
					
				}

			}else if(StringUtils.isEmpty(tsca.getProductColor())&&(StringUtils.isEmpty(tsca.getProductSize()))){

				tsca.getProductId();
			
				TProduct prod = tProductDao.loadById(tsca.getProductId());
				if (prod == null) {
					throw new RuntimeException("product is not exist");
				}else{
					TProductAttribute tProductAttribute=new TProductAttribute();
					tProductAttribute.setProductId(tsca.getProductId());
					//tProductAttribute.setProductColor(tsca.getProductColor());
					//tProductAttribute.setProductSize(tsca.getProductSize());
					SqlSelect sqlSelect=new SqlSelect().where(TProductAttribute.SQL_productId);
					
					TProductAttribute tProducta= tProductAttributeDao.load(sqlSelect, tProductAttribute);
					if(tProducta!=null){
						int attId=tProducta.getId();
						if(tsca.getProductNum()>tProducta.getStock()){
							result.error("购物车此商品库存不足请重新下单");
							log.error("购物车此商品库存不足请重新下单");
							return result;
						}
						TOrderDetail tOrderDetail = new TOrderDetail();
						tOrderDetail.setOrderId(orderId);
						tOrderDetail.setProductId(tsca.getProductId());
						tOrderDetail.setProductCount(tsca.getProductNum());
						//tOrderDetail.setProductColor(tsca.getProductColor());
						tOrderDetail.setProductPrice(tsca.getProductPrice());
						//tOrderDetail.setProductSize(tsca.getProductSize());
						int stock=tProducta.getStock()-tsca.getProductNum();
						
						TProductAttribute tPa=new TProductAttribute();
						tPa.setId(attId);
						tPa.setStock(stock);
						SqlUpdate sqlUpdate=new SqlUpdate().addColumn(TProductAttribute.SQL_stock).where(TProductAttribute.SQL_id);
						tProductAttributeDao.update(sqlUpdate, tPa);
						
						tOrderDetail.setProductBrandId(prod.getBrandId());
						tOrderDetail.setProductCateId(prod.getCateId());
						tOrderDetail.setUserId(userId);

						tOrderDetail.setOrderStatus(Const.ORDER_STATUS_WAITING_PAY);

						tOrderDetailDao.insert(tOrderDetail);
						tShoppingCartDetailDao.deleteById(Integer.parseInt(id));

						totalPrice = totalPrice + tsca.getProductPrice() * tsca.getProductNum();
					}else{
						result.error("购物车此商品暂无库存");
						log.error("所选商品暂无库存");
						return result;
					}
					
				}
			}else{
				tsca.getProductId();
				
				TProduct prod = tProductDao.loadById(tsca.getProductId());
				if (prod == null) {
					throw new RuntimeException("product is not exist");
				}else{
					TProductAttribute tProductAttribute=new TProductAttribute();
					tProductAttribute.setProductId(tsca.getProductId());
					tProductAttribute.setProductColor(tsca.getProductColor());
					tProductAttribute.setProductSize(tsca.getProductSize());
					SqlSelect sqlSelect=new SqlSelect().where(TProductAttribute.SQL_productId).and(TProductAttribute.SQL_productSize).and(TProductAttribute.SQL_productColor);
					
					TProductAttribute tProducta= tProductAttributeDao.load(sqlSelect, tProductAttribute);
					if(tProducta!=null){
						int attId=tProducta.getId();
						if(tsca.getProductNum()>tProducta.getStock()){
							result.error("购物车此商品库存不足请重新下单");
							log.error("购物车此商品库存不足请重新下单");
							return result;
						}
						TOrderDetail tOrderDetail = new TOrderDetail();
						tOrderDetail.setOrderId(orderId);
						tOrderDetail.setProductId(tsca.getProductId());
						tOrderDetail.setProductCount(tsca.getProductNum());
						tOrderDetail.setProductColor(tsca.getProductColor());
						tOrderDetail.setProductPrice(tsca.getProductPrice());
						tOrderDetail.setProductSize(tsca.getProductSize());
						int stock=tProducta.getStock()-tsca.getProductNum();
						
						TProductAttribute tPa=new TProductAttribute();
						tPa.setId(attId);
						tPa.setStock(stock);
						SqlUpdate sqlUpdate=new SqlUpdate().addColumn(TProductAttribute.SQL_stock).where(TProductAttribute.SQL_id);
						tProductAttributeDao.update(sqlUpdate, tPa);
						
						tOrderDetail.setProductBrandId(prod.getBrandId());
						tOrderDetail.setProductCateId(prod.getCateId());
						tOrderDetail.setUserId(userId);

						tOrderDetail.setOrderStatus(Const.ORDER_STATUS_WAITING_PAY);

						tOrderDetailDao.insert(tOrderDetail);
						tShoppingCartDetailDao.deleteById(Integer.parseInt(id));

						totalPrice = totalPrice + tsca.getProductPrice() * tsca.getProductNum();
					}else{
						result.error("购物车此商品暂无库存");
						log.error("所选商品暂无库存");
						return result;
						
					}
					
				}
			}
			
			
		}

		tOrderDao.update(new SqlUpdate().addColumn(TOrder.SQL_orderTotalPrice).where(TOrder.SQL_id),
				order.setId(orderId).setOrderTotalPrice(totalPrice));

		result.success("提交订单成功").setData(orderId);
		
		// 提交订单成功 记录系统日志
		TSysLog sysLog = new TSysLog();
		sysLog.setLogType(ConstantTool.SYSLOG_LOGTYPE_TWO); // 日志类型为  微信来源类型
		sysLog.setLogAction(ConstantTool.SYSLOG_LOGACTION_THREE); // 操作类型为 订单支付操作
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		TUser tUser = new TUser();
		tUser.setId(userId);
		SqlSelect select = new SqlSelect().where(TUser.SQL_id);
		TUser loadUser = tUserDao.load(select, tUser);
		/*sysLog.setLogContent(loadUser.getUserName()+"用户在" + sf.format(ca.getTime()) + "时,订单支付成功");
	    tSysLogDao.insert(sysLog);*/
		
		}catch (Exception e) {
			result.error("添加订单详情失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		

		
		
		return result;
		
		
		
		
	}
	/**
	 * 修改订单状态  取消支付  
	 * 
	 * @param orderStatus取消支付  
	 * @return JsonResult
	 */
	public JsonResult cancelOrderStatus(String orderStatus,int orderId,int userId) {
		JsonResult result = new JsonResult();
		TOrder order = new TOrder();
		TOrderDetail orderDetail = new TOrderDetail();
		SqlUpdate sqlorder = new SqlUpdate().addColumn("update_time = NOW()");
		SqlUpdate sqlorderDetail = new SqlUpdate();
		
		try {
			//查看是否有此用户
			if (tUserDao.loadById(userId) == null) {
				log.error("无此用户");
				return result.error("修改订单状态失败");
			}
			
			TOrder ord = tOrderDao.loadById(orderId);
			TOrderDetail tOrderDetail = new TOrderDetail();
			tOrderDetail.setOrderId(orderId);
			SqlSelect sql = new SqlSelect().where(TOrderDetail.SQL_orderId);
			List<TOrderDetail> orderdetailList = tOrderDetailDao.list(sql, tOrderDetail);
			
			//查看是否有此订单
			if (ord == null && orderdetailList == null) {
				log.error("无此订单");
				return result.error("修改订单状态失败");
			}
			for (TOrderDetail tOrderDetail2 : orderdetailList) {
				if (userId != ord.getUserId() && userId != tOrderDetail2.getUserId()) {
					log.error("此用户无此订单");
					return result.error("修改订单状态失败");
				}
				if (orderStatus==null || orderStatus.length() <= 0) {
					log.error("获取订单状态失败");
					return result.error("修改订单状态失败");
				}
				if (orderStatus.equals(ord.getOrderStatus()) == false && orderStatus.equals(tOrderDetail2.getOrderStatus()) == false) {
					log.error("订单状态不一致");
					return result.error("修改订单状态失败");
				}
				order.setId(orderId);
				order.setOrderStatus(Const.ORDER_STATUS_CANCELED);
				orderDetail.setOrderId(orderId);
				orderDetail.setOrderStatus(Const.ORDER_STATUS_CANCELED);
				sqlorder.addColumn("cancel_time = NOW()").where(TOrder.SQL_id).addColumn(TOrder.SQL_orderStatus);
				int resorder = tOrderDao.update(sqlorder, order);
				
				sqlorderDetail.where(TOrderDetail.SQL_orderId).addColumn(TOrderDetail.SQL_orderStatus);
				int res = tOrderDetailDao.update(sqlorderDetail, orderDetail);
				/*
				 * 在 取消订单  后要将  库存  修改为原来的  库存数量
				 */
                String productColor = 	tOrderDetail2.getProductColor();
                String productSize = tOrderDetail2.getProductSize();
                int productId = tOrderDetail2.getProductId();
                int productCount = tOrderDetail2.getProductCount();
                
                TProductAttribute tProductAttribute = new TProductAttribute();
                tProductAttribute.setProductColor(productColor);
                tProductAttribute.setProductId(productId);
                tProductAttribute.setProductSize(productSize);
                
                SqlSelect sqlSelect = new SqlSelect();
                SqlUpdate sqlUpdate = new SqlUpdate();
                //查出原来的库存
                if ("".equals(productColor)==false && "".equals(productSize)==false && productId > 0) {
                	sqlSelect.where(TProductAttribute.SQL_productId)
                    		.and(TProductAttribute.SQL_productColor).and(TProductAttribute.SQL_productSize);
                	//有颜色有尺码    修改为原来的库存
                	sqlUpdate.where(TProductAttribute.SQL_productId)
                    		.and(TProductAttribute.SQL_productColor).and(TProductAttribute.SQL_productSize)
                    		.addColumn(TProductAttribute.SQL_stock);
				} else if("".equals(productColor)==false && "".equals(productSize) && productId > 0) {
					sqlSelect.where(TProductAttribute.SQL_productId).and(TProductAttribute.SQL_productColor);
					//有颜色 无尺码  修改为原来的库存
                	sqlUpdate.where(TProductAttribute.SQL_productId)
                    		.and(TProductAttribute.SQL_productColor).addColumn(TProductAttribute.SQL_stock);
				} else if("".equals(productColor) && "".equals(productSize)==false && productId > 0) {
					sqlSelect.where(TProductAttribute.SQL_productId).and(TProductAttribute.SQL_productSize);
					//无颜色 有尺码 修改为原来的库存
                	sqlUpdate.where(TProductAttribute.SQL_productId)
                    		.and(TProductAttribute.SQL_productSize).addColumn(TProductAttribute.SQL_stock);
				} else if("".equals(productColor) && "".equals(productSize) && productId > 0) {
					sqlSelect.where(TProductAttribute.SQL_productId);
					//无尺码  无颜色  修改为原来的库存
                	sqlUpdate.where(TProductAttribute.SQL_productId).addColumn(TProductAttribute.SQL_stock);
				}
                int proStock = tProductAttributeDao.load(sqlSelect, tProductAttribute).getStock();
                //订单表的库存和原来的库存相加
                tProductAttribute.setStock(productCount+proStock);
                int resupdate = tProductAttributeDao.update(sqlUpdate, tProductAttribute);
				if ( res > 0 && resorder>0 && resupdate>0) {
					result.success("修改订单状态成功").setData(orderId);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 订单状态为“支付取消”的状态下可以进行删除订单  
	 * 
	 * @param orderStatus支付取消  orderId  userID  
	 * @return JsonResult
	 */
	public JsonResult delectOrder(String orderStatus,int orderId,int userId) {
		JsonResult result = new JsonResult();
	
		try {
			
			//查看是否有此用户
			if (tUserDao.loadById(userId) == null) {
				log.error("无此用户");
				return result.error("删除订单失败");
			}
			
			//查看此订单的状态是否为“支付取消”的状态
			if (orderStatus.equals(Const.ORDER_STATUS_CANCELED)==false) {
				log.error("此订单状态不为“支付取消”,不能删除");
				return result.error("删除订单失败");
			}
			
			
			TOrder ord = tOrderDao.loadById(orderId);
			TOrderDetail tOrderDetail = new TOrderDetail();
			tOrderDetail.setOrderId(orderId);
			SqlSelect sql = new SqlSelect().where(TOrderDetail.SQL_orderId);
			List<TOrderDetail> orderdetailList = tOrderDetailDao.list(sql, tOrderDetail);
			
			//查看是否有此订单
			if (ord == null && orderdetailList == null) {
				log.error("无此订单");
				return result.error("删除订单失败");
			}
			for (TOrderDetail tOrderDetail2 : orderdetailList) {
				if (userId != ord.getUserId() && userId != tOrderDetail2.getUserId()) {
					log.error("此用户无此订单");
					return result.error("删除订单失败");
				}
				if (orderStatus==null || orderStatus.length() <= 0) {
					log.error("获取订单状态失败");
					return result.error("删除订单失败");
				}
				if (orderStatus.equals(ord.getOrderStatus()) == false && orderStatus.equals(tOrderDetail2.getOrderStatus()) == false) {
					log.error("订单状态不一致");
					return result.error("删除订单失败");
				}
			}
			
			TOrderDetail orDetail=new TOrderDetail();
			int i=tOrderDetailDao.delete(new SqlDelete().where(TOrderDetail.SQL_orderId), orDetail.setOrderId(orderId));
			TOrder to=new TOrder();
			int r=tOrderDao.delete(new SqlDelete().where(TOrder.SQL_id), to.setId(orderId));
			if ( i > 0 && r>0) {
				result.success("修改订单状态成功").setData(orderId);
			}
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	

	/**
	 * 修改订单状态   确认收货
	 * 
	 * @param 确认收货orderStatus
	 * @return JsonResult
	 */
	public JsonResult sumitOrderStatus(String orderStatus,int orderId,int userId) {
		JsonResult result = new JsonResult();
		TOrder order = new TOrder();
		TOrderDetail orderDetail = new TOrderDetail();
		SqlUpdate sqlorder = new SqlUpdate().addColumn("update_time = NOW()");
		SqlUpdate sqlorderDetail = new SqlUpdate();
		try {
			//查看是否有此用户
			if (tUserDao.loadById(userId) == null) {
				log.error("无此用户");
				return result.error("无此用户");
			}
			
			TOrder ord = tOrderDao.loadById(orderId);
			TOrderDetail tOrderDetail = new TOrderDetail();
			tOrderDetail.setOrderId(orderId);
			SqlSelect sql = new SqlSelect().where(TOrderDetail.SQL_orderId);
			List<TOrderDetail> orderdetailList = tOrderDetailDao.list(sql, tOrderDetail);
			int res =0;
			
			//查看是否有此订单
			if ( ord == null && orderdetailList == null) {
				log.error("无此订单");
				return result.error("无此订单");
			}
			for (TOrderDetail tOrderDetail2 : orderdetailList) {
				//查看此用户是否有此订单
				if (userId != ord.getUserId() && userId != tOrderDetail2.getUserId()) {
					log.error("此用户无此订单");
					return result.error("修改订单状态失败");
				}
				//查看此订单状态是否一致
				if (orderStatus.length() <= 0 || orderStatus==null) {
					log.error("获取订单状态失败");
					return result.error("修改订单状态失败");
				}
				if (orderStatus.equals(ord.getOrderStatus()) == false && orderStatus.equals(tOrderDetail2.getOrderStatus()) == false) {
					log.error("订单状态不一致");
					return result.error("修改订单状态失败");
				}
				order.setId(orderId);
				order.setOrderStatus(Const.ORDER_STATUS_ALREADY_SIGNED);
				orderDetail.setOrderId(orderId);
				orderDetail.setOrderStatus(Const.ORDER_STATUS_ALREADY_SIGNED);
				sqlorder.addColumn("signed_time = NOW()").where(TOrder.SQL_id).addColumn(TOrder.SQL_orderStatus);
				tOrderDao.update(sqlorder, order);
				
				sqlorderDetail.where(TOrderDetail.SQL_orderId).addColumn(TOrderDetail.SQL_orderStatus);
				res = tOrderDetailDao.update(sqlorderDetail, orderDetail);
			}
			JsonResult threeResult = ThreeLevel(tOrderDao.loadById(orderId));
			if ( res > 0 && threeResult.getCode() == 0) {
				result.success("修改订单状态成功");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 根据订单状态 为“客户确认已收货” 状态或者 “超时，已收货” 为 4  三级分销
	 * @param order
	 * @return json
	 */
	public JsonResult ThreeLevel(TOrder order) {
		JsonResult result = new JsonResult();
		// 订单状态 为“客户确认已收货” 状态或者 “超时，已收货”
		if (order.getOrderStatus().equals(Const.ORDER_STATUS_ALREADY_SIGNED)||order.getOrderStatus().equals(Const.ORDER_STATUS_EXCEED_SIGN)) {
			order.getId();
			//int orderTotlePrice=Integer.parseInt(StrUtil.rmb(order.getOrderTotalPrice()));
			int orderTotlePrice=order.getOrderTotalPrice();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH"); 
			int userId=order.getUserId();
			TUser user=new TUser();
			if(userId>0){
				user=tUserDao.loadById(userId);
				//int ParentId=user.getParentId();
				
				//（一级返现）通过订单给本用户进行返积分返点0.10
				TIntegral tIntegral1 = new TIntegral();
				tIntegral1.setUserId(userId);
				tIntegral1.setIntegralSource(order.getOrderNo());
				int num1 =(int)Math.floor(orderTotlePrice* sysConfigService.getIntegral1()/100);
				tIntegral1.setIntegralNum(num1);
				tIntegral1.setIntegralType(Const.INTEGRAL_TYPE_INCREASE); // 积分类型 为 增长财富
				tIntegral1.setIntroduction(user.getUserName() + "用户在" + df.format(new Date())+ "时下单返现获得了" + num1 + "元");
				tIntegralDao.insert(tIntegral1);
				//获取订单本用户原来积分总数
				int oldInSum=user.getUserIntegralSum();
				//修改订单本用户好友积分总数
				SqlUpdate sqlUpdate1=new SqlUpdate().addColumn(TUser.SQL_userIntegralSum).where(TUser.SQL_id);
				tUserDao.update(sqlUpdate1, new TUser().setId(userId).setUserIntegralSum(oldInSum+num1));
				
				TUser parentUser=tUserDao.loadById(user.getParentId());
				if(parentUser!=null){
					
					
					//（二级返现）通过订单给本用户的上层好友返积分返点0.05
					TIntegral tIntegral = new TIntegral();
					tIntegral.setUserId(parentUser.getId());
					tIntegral.setIntegralSource(order.getOrderNo());
					int num =(int)Math.floor(orderTotlePrice* sysConfigService.getIntegral2()/100);
					tIntegral.setIntegralNum(num);
					tIntegral.setIntegralType(Const.INTEGRAL_TYPE_INCREASE); // 积分类型 为 增长财富
					tIntegral.setIntroduction(parentUser.getUserName() + "用户在" + df.format(new Date())+ "时,通过"+"(二级好友)"+user.getUserName()+"返现获得了" + num + "元");
					tIntegralDao.insert(tIntegral);
					//获取用户上层好友原来积分总数
					int oldIntegralSum=parentUser.getUserIntegralSum();
					//修改用户上层好友积分总数
					SqlUpdate sqlUpdate=new SqlUpdate().addColumn(TUser.SQL_userIntegralSum).where(TUser.SQL_id);
					tUserDao.update(sqlUpdate, new TUser().setId(parentUser.getId()).setUserIntegralSum(oldIntegralSum+num));
					
					
					//（三级返现）通过上层好友查找上上级好友进行返点0.02
					TUser pPUser=tUserDao.loadById(parentUser.getParentId());
					if(pPUser!=null){
						TIntegral pTIntegral = new TIntegral();
						pTIntegral.setUserId(pPUser.getId());
						pTIntegral.setIntegralSource(order.getOrderNo());
						int pNum =(int)Math.floor(orderTotlePrice* sysConfigService.getIntegral3()/100);
						pTIntegral.setIntegralNum(pNum);
						pTIntegral.setIntegralType(Const.INTEGRAL_TYPE_INCREASE); // 积分类型 为 增长财富
						pTIntegral.setIntroduction(pPUser.getUserName() + "用户在" +df.format(new Date()) + "时,通过"+"(三级好友)"+user.getUserName()+"返现获得了" + pNum + "元");
						tIntegralDao.insert(pTIntegral);
						//获取用户上上层好友原来积分总数
						int pOldIntegralSum=pPUser.getUserIntegralSum();
						//修改用户上层好友积分总数
						SqlUpdate update=new SqlUpdate().addColumn(TUser.SQL_userIntegralSum).where(TUser.SQL_id);
						tUserDao.update(update, new TUser().setId(pPUser.getId()).setUserIntegralSum(pOldIntegralSum+pNum));
						
						//result.success("上上级好友积分返点成功");
					}else{
						log.info("本用户没有上上层好友，不进行积分返点");
					}
				}else{
					log.info("本用户没有上层好友，不进行积分返点");
				}
				
			}
		}	
		return result;
		
	}
	
}
