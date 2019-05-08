package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.IntegralBean;

@MapperScan
@Repository("integralDao")
public interface IntegralDao {
	
	//  微信端 根据 userId获取列表
	List<IntegralBean> getWeChatIntegralListByUserId(ModelMap map);
	
	// 统计 微信端  总条数
	int getWeChatIntegralListByUserIdCount(ModelMap map);
	
	//统计 已提现金额总数
	int getIntegralNumSum(ModelMap map);
}
