package com.yunqi.jhf.dao;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import com.yunqi.jhf.vo.VehicleRentBean;

@MapperScan
@Repository("vehicleRentDao")
public interface VehicleRentDao {
	
	// 按 房车名称 搜索 展示列表
    List<VehicleRentBean> getVehicleRentList(ModelMap map);
    
    // 获取微信端 房车租赁获取详情
    VehicleRentBean getVehicleRentId(int id);
    
    // 微信端 获取房车租赁列表
   List<VehicleRentBean> getWeChatVehicleRentList(ModelMap map);
   
   //计算总条数
   Integer getWeChatVehicleRentCount(ModelMap map);
	 
}
