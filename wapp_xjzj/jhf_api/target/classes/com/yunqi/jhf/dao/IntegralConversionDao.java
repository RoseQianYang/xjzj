package com.yunqi.jhf.dao;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.IntegralConversionBean;

public interface IntegralConversionDao {
	
	// 获取积分兑换列表
	List<IntegralConversionBean> getIntegralConversionList(ModelMap jsonInfo);
	
	// 获取 总条数
	int getIntegralConversionCount();
	
}
