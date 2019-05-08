package com.yunqi.jhf.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import com.yunqi.jhf.dao.domain.TOrder;
import com.yunqi.jhf.vo.OrderBean;
import com.yunqi.jhf.vo.OrderDetailBean;

@MapperScan
@Repository("ordersDao")
public interface OrderDao { 
	
	 // 根据订单编号 查询订单列表
	 List<OrderBean> getOrderList(ModelMap map);
	 
	// 根据订单编号 查询订单列表 条数
	 int getOrderListTotalSize(ModelMap map);
	 
	 List<OrderDetailBean> getOrderDetailListByList(@Param("orders") List<TOrder> orders);
	 
	 int updateOrderStatus(@Param("orders") List<TOrder> orders);
	 
	 int updateOrderDetailStatus(@Param("details") List<OrderDetailBean> details);
	 
	 int returnStock(OrderDetailBean orderDetail);
	 
}
