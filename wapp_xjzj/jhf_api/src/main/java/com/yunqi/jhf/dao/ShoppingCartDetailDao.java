package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.ShoppingCartDetailBean;


@MapperScan
@Repository("ShoppingcartDetailDao")
public interface ShoppingCartDetailDao {
	List<ShoppingCartDetailBean> getshopcartDetailList(ModelMap map);
	
	List<ShoppingCartDetailBean> getshopcartDetailpageList(ModelMap map);
	
	ShoppingCartDetailBean getshopcartDetail(int shopCartDetailId);
	
	int getshopcartDetailCount(ModelMap jsonInfo);
}
