package com.yunqi.jhf.dao;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import com.yunqi.jhf.vo.SysLogBean;

@MapperScan
@Repository("sysLogDao")
public interface SysLogDao {

	//系统日志数据 列表 
	List<SysLogBean> getSysLogList(ModelMap jsonInfo);
	
	//系统日志 数据条数
	int getSysLogListCount(ModelMap jsonInfo);
}
