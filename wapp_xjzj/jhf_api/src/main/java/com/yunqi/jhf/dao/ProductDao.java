package com.yunqi.jhf.dao;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.ProductBean;

public interface ProductDao {
	
	List<ProductBean> getProductList(ModelMap jsonInfo);
	
	int getProductCount(ModelMap jsonInfo);
	
	ProductBean getProductById(int id);

}
