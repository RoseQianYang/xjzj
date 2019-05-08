package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import com.yunqi.jhf.vo.OrderDetailBean;

@MapperScan
@Repository("orderDetailDao")
public interface OrderDetailDao {
	
	List<OrderDetailBean> getOrderDetailList(ModelMap map);

	//根据userId查找订单详情
	List<OrderDetailBean> getOrderDetailByUidList(ModelMap map);
	//根据userId查找订单详情 分页列表
	List<OrderDetailBean> getOrderDetailByUidLists(ModelMap map);
	//根据orderId查找订单详情
	List<OrderDetailBean> getOrderDetailByOidList(ModelMap map);
	
	int getOrderDetailByUidListsCount(ModelMap jsonInfo);
	
}
