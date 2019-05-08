package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.ProductAttributeBean;

@MapperScan
@Repository("productAttributeDao")
public interface ProductAttributeDao {
	
	//获取商品规格列表
	List<ProductAttributeBean> getProductAttribute(ModelMap map);
	
	
}