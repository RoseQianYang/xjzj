package com.yunqi.jhf.service.background;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.OrderDetailDao;
import com.yunqi.jhf.vo.OrderDetailBean;

/**
 * 提供订单详情相关服务
 * @author wangsong
 *
 */

@Service
public class OrderDetailService {

	protected Logger logger = Logger.getLogger(OrderDetailService.class);
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	// 获取订单详情列表接口
	public JsonResult getOrderDetailList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		try {
			List<OrderDetailBean> orderDetails = orderDetailDao.getOrderDetailList(jsonInfo);
			if (orderDetails != null) {
				result.setData(orderDetails);
				result.success("获取成功");
				logger.info("获取订单详情列表接口执行成功");
			} else {
				result.error("获取失败");
				logger.info("获取订单详情列表接口执行失败");
			}
		} catch (Exception e) {
			result.error("获取订单详情列表失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
}
