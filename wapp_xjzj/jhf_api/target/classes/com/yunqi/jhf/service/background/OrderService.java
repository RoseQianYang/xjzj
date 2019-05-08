package com.yunqi.jhf.service.background;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.OrderDao;
import com.yunqi.jhf.dao.domain.TOrder;
import com.yunqi.jhf.dao.domain.TOrderDetail;
import com.yunqi.jhf.dao.persistence.TOrderDao;
import com.yunqi.jhf.dao.persistence.TOrderDetailDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.OrderBean;

/**
 * 提供订单相关服务
 * 
 * @author wangsong
 */

@Service
public class OrderService {

	protected Logger logger = Logger.getLogger(OrderService.class);

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private TOrderDao torderDao;
	
	@Autowired
	private TOrderDetailDao tOrderDetailDao;

	// 获取订单列表接口 分页
	public JsonResult getOrderList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		try {
			// 分页
			PageList<OrderBean> pagelist = new PageList<>();
			if (jsonInfo.get("page") == null) {
				jsonInfo.put("page", 0);
			} else {
				pagelist.setCurrentPage((int) jsonInfo.get("page"));
				pagelist.setTotalSize(orderDao.getOrderListTotalSize(jsonInfo)); 
				jsonInfo.put("page", pagelist.getFromPos());
			}
			// 查询 列表
			List<OrderBean> ordersBeans = orderDao.getOrderList(jsonInfo);
			if (ordersBeans != null) {
				pagelist.setList(ordersBeans);
				result.setData(pagelist);
				result.success("获取成功");
				logger.info("后台订单列表接口执行成功");

			}
		} catch (Exception e) {
			result.error("后台订单列表接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 订单只有处于“已支付，代发货” 3 ，才允许点击物流发货按钮
	 * 物流发货 
	 * 
	 * @param order
	 * @return Json
	 */
	public JsonResult updateOrder(TOrder order) {
		JsonResult result = new JsonResult();
		try {
			if (order.getLogisticsName() == null || "".equals(order.getLogisticsName())) {
				return result.error("物流名称不可为空");
			}
			if (order.getLogisticsNo() == null || "".equals(order.getLogisticsNo())) {
				return result.error("物流编号不可为空");
			}
			// 判断 订单状态 是否为  已发货状态
			if ( !order.getOrderStatus().equals(Const. ORDER_STATUS_ALREADY_SIPPING)) {
				return result.error("订单状态不为已发货状态");
			} 
			// 定时任务根据 超时收货时间+15 来做判断
			 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 Calendar ca = Calendar.getInstance();
			 System.out.println("获取当前时间"+sf.format(ca.getTime()));
			 order.setExceedTime(addData(sf.format(ca.getTime()),Const.DAYS)); //当前时间+15天 为 超时，已收货 时间
		  	// 单表方式 更新 修改 字段 和 记录订单发货时间  
			 SqlUpdate sql = new SqlUpdate()
					    .addColumn(TOrder.SQL_logisticsNo)
					    .addColumn(TOrder.SQL_logisticsName)
						.addColumn(TOrder.SQL_orderStatus)
						.addColumn("sipping_time = NOW()")
						.addColumn(TOrder.SQL_exceedTime).where(TOrder.SQL_id);
			// 订单       
			int row = torderDao.update(sql, order);
			// 根据 该订单id 获取 该条所有记录信息
			TOrder o=torderDao.loadById(order.getId());
			// 订单处的状态修改了 同时需要在订单详情里面 更改同步订单状态
			TOrderDetail tOrderDetail=new TOrderDetail();
			tOrderDetail.setOrderStatus(Const. ORDER_STATUS_ALREADY_SIPPING);
			tOrderDetail.setOrderId(o.getId());
			SqlUpdate sqlUpdate=new SqlUpdate().addColumn(TOrderDetail.SQL_orderStatus).where(TOrderDetail.SQL_orderId);
			int rs=tOrderDetailDao.update(sqlUpdate, tOrderDetail);
			if (row == 1&&rs==1) {
				result.success("发货成功");
				result.setData(torderDao.loadById(order.getId()));
				logger.info("后台修改订单方法接口执行成功");
			}
		} catch (Exception e) {
			result.error("发货失败");
			logger.info("后台修改订单方法接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 指定日期时间加上想要的天数 后的时间日期
	 * @param sqlSippingtime 指定日期时间
	 * @param day 想要的天数
	 * @return Timestamp类型 时间日期
	 */
	public static Timestamp addData(String sqlSippingtime , int day)  throws ParseException{
		   SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //24小时制
		   Date date =null;
		   try {
			date = sf.parse(sqlSippingtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		   if (date == null)   
	            return null; 
		   System.out.println("订单发货当前日期:" + sf.format(date)); //显示 输入的 订单发货当前日期
		   Calendar ca = Calendar.getInstance();
		   ca.setTime(date);
		   ca.add(Calendar.DATE, day);
		   date = ca.getTime();
		   System.out.println("超时，已收货:" + sf.format(date));  //显示超时，已收货 后的日期 
		   Timestamp ts = Timestamp.valueOf(sf.format(date)); //String类型转 Timestamp类型
		return ts;
	}
 
}	

