package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.EventInfoBean;

@MapperScan
@Repository("eventDao")
public interface EventDao {

	//获取活动列表
	List<EventInfoBean> getEventList(ModelMap jsonInfo);
	
	//获取活动列表参数条数
	int	getEventCount(ModelMap jsonInfo);
	
	//获取活动信息详情
	EventInfoBean getEventById(int id);
	
	//查询该活动时间是否有冲突
	int getEvenByTime(ModelMap jsonInfo);
	
	//获取活动列表,过期活动加标示
	List<EventInfoBean> getEventListIsShow();
	
}
