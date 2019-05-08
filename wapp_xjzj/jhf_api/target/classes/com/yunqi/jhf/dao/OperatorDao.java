package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.OperatorBean;

@MapperScan
@Repository("operDao")
public interface OperatorDao {
	
	List<OperatorBean> getOperatorList(ModelMap map);
	
	int getOperatorCount(ModelMap map);
	
	OperatorBean getOperatorById(int id);

}
