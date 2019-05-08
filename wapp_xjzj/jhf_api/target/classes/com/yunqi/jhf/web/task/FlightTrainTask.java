package com.yunqi.jhf.web.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.OrderDao;
import com.yunqi.jhf.dao.domain.TOrder;
import com.yunqi.jhf.dao.persistence.TOrderDao;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.service.front.TOrderService;
import com.yunqi.jhf.vo.OrderDetailBean;

@Component
public class FlightTrainTask {

	protected static Logger log = Logger.getLogger(FlightTrainTask.class);

	@Autowired
	private TOrderDao tOrderDao;
	
	@Autowired
	private TOrderService tOrderService;
	
	@Autowired
	private OrderDao ordersDao;

	 // 每天凌晨1点，把所有已经发货，但客户没有签收的，超过15天，改为超时签收状态
    @Scheduled(cron = "0 0 1 * * ?")
	public void taskCheckDeliver() {
		log.info("=======开始进行订单超时签收验证=======");
		SqlUpdate sql = new SqlUpdate().addColumn(TOrder.FLD_orderStatus + " = " + Const.ORDER_STATUS_EXCEED_SIGN)
				.addColumn("exceed_signed_time = now()")
				.where(TOrder.FLD_orderStatus + " = " + Const.ORDER_STATUS_ALREADY_SIPPING).and("exceed_time <= NOW()");
		int res = tOrderDao.update(sql, null);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
		log.info("=======截止到" + sdf.format(new Date()) + "为止,有" + res + "条订单超时签收=======");	
		// 状态为6  时 超时收货状态 要做对应的返现操作
		JsonResult threeResult = tOrderService.ThreeLevel(new TOrder());
		if (threeResult.getCode() == 0) {
			log.info("超时收货 返现成功");
		}
	}

	// 每天凌晨1点半,查询订单是否有超时,并做取消订单操作。
	@Scheduled(cron = "0 30 1 * * ?")
	@Transactional
	public void orderTimeout() {
		log.info("=======开始进行订单超时验证=======");
		SqlSelect sql = new SqlSelect().where("order_status = '1'").and("DATE_ADD(create_time,INTERVAL '00:30:00' DAY_SECOND) < NOW()");
		List<TOrder> orders =  tOrderDao.list(sql, null);
		List<OrderDetailBean> odb = ordersDao.getOrderDetailListByList(orders);
		try {
			ordersDao.updateOrderStatus(orders);
			ordersDao.updateOrderDetailStatus(odb);
			for(OrderDetailBean o : odb) {
				ordersDao.returnStock(o);
			}
		} catch (Exception e) {
			throw new RuntimeException("订单超时操作错误,事务回滚");//抛出unchecked异常，触发事物，回滚
		}
		
	}
	
	// 每天凌晨2点，获取微信缴费对账单，和本地对账。
	@Scheduled(cron = "0 0 2 * * ? ")
	// @Scheduled(cron = "0/3 * * * * ? ")
	public void taskReconciliation() {
		System.out.println("兽人永不为奴");
	}
}
