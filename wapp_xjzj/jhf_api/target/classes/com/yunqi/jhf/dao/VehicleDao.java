package com.yunqi.jhf.dao;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import com.yunqi.jhf.vo.VehicleBean;

@MapperScan
@Repository("vehicleDao")
public interface VehicleDao {
	
	// 房车展示列表 map形式
	List<VehicleBean> getVehickeLists(ModelMap map);
	
	// 房车展示不分页列表
	List<VehicleBean> getVehickeListsNoPage(ModelMap map);
	
	// 房车总条数
	int getVehicleListTotalSize(ModelMap map);
	
	// 当季促销的房车展示列表
	List<VehicleBean> getVehicleListYesIsSales(ModelMap map);
	
	// 当季促销的房车总条数
	int getVehicleListYesIsSalesTotalSize(ModelMap map);
	
	// 二手置换的房车展示列表
	List<VehicleBean> getVehicleListYesIsSencond(ModelMap map);
	
	// 二手置换的房车总条数
	int getVehicleListYesIsSencondTotalSize(ModelMap map);
    
	// 微信端 根据 房车品牌id vehicleBrandId 获取对于的房车列表
	List<VehicleBean> getVehickeListByVehicleBrandId(ModelMap map);
	
	// 微信端 根据 房车品牌id vehicleBrandId 获取对于的房车列表 总条数
	int getVehickeListByVehicleBrandIdCount(ModelMap map);
}
