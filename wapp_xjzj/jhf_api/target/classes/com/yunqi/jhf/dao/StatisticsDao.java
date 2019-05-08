package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.IntegralStatsBean;
import com.yunqi.jhf.vo.OrderDetailBean;
import com.yunqi.jhf.vo.SalesVolumeStatsBean;
import com.yunqi.jhf.vo.UserStatisticsBean;

@MapperScan
@Repository("statisticsDao")
public interface StatisticsDao {
	
	//用户月统计详情
	List<UserStatisticsBean> getUserStatsByMonth(ModelMap jsonInfo);
	
	//用户年统计详情
	List<UserStatisticsBean> getUserStatsByYear(ModelMap jsonInfo);
	
	//用户积分统计流水 
	List<IntegralStatsBean> getIntegralStats(ModelMap jsonInfo);
	
	//用户积分统计流水数据条数
	int getIntegralStatsCount(ModelMap jsonInfo);
	
	//销量统计列表
	List<SalesVolumeStatsBean> getSalesVolumeStatsList(ModelMap jsonInfo);
	
	//销量统计列表参数条数
	int getSalesVolumeStatsCount(ModelMap jsonInfo);
	
	//单品月销量统计
	List<SalesVolumeStatsBean> getSalesVolumeStatsByMonth(ModelMap jsonInfo);
	
	//单品年销量统计
	List<SalesVolumeStatsBean> getSalesVolumeStatsByYear(ModelMap jsonInfo);

	//销售明细列表
	List<OrderDetailBean> getOrderDetailList(ModelMap jsonInfo);
	
	//销量明细数据条数
	int getOrderDetailCount(ModelMap jsonInfo);
}
