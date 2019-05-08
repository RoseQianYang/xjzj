package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.EventProductBean;

@MapperScan
@Repository("eventProductDao")
public interface EventProductDao {
	
	List<EventProductBean> getEventProductList(ModelMap jsonInfo);
	
	int getEventProductCount(ModelMap jsonInfo);
	
	EventProductBean getEventProductById(int id);

	// 查询热卖活动产品列表4个
    List<EventProductBean> getEventProdList();
    
    // 查询首页九大爆款活动产品列表9个
    List<EventProductBean> getEventProdListShouYe();
    
    // 根据活动编号 查询活动产品列表
    List<EventProductBean> getProductListNotEventId(ModelMap jsonInfo);
    
    // 根据活动编号 查询活动产品列表
    int getProductCountNotEventId(ModelMap jsonInfo);
    
    //活动综合排序
    List<EventProductBean> getEventProdEventIdList(ModelMap jsonInfo);
    //活动降序
    List<EventProductBean> getEventProdEventIdListDesc(ModelMap jsonInfo);
    //活动升序
    List<EventProductBean> getEventProdEventIdListAsc(ModelMap jsonInfo);
}