package com.yunqi.jhf.service.front;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.VehicleRentDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.vo.VehicleRentBean;

/**
 * 提供微信端 房车租赁相关服务
 * @author wangsong
 *
 */
@Service
public class TVehicleRentService {

	private static final Logger logger = Logger.getLogger(TVehicleBrandService.class);
	
	@Autowired
	private VehicleRentDao vehicleRentDao;
	
	
	
	/**
	 * 根据 当前页 获取 房车租赁 分页列表
	 * @param currentPage
	 * @return PageList
	 */
	public PageList<VehicleRentBean> getTvehicleRentList(Integer currentPage){
		PageList<VehicleRentBean> pageList = new PageList<>();
		ModelMap map = new ModelMap();
		try {
			pageList.setCurrentPage(currentPage);
			pageList.setPageSize(Const.FONT_PAGE_SIZE);
			map.put("page", pageList.getFromPos());
	     	map.put("size", Const.FONT_PAGE_SIZE); //传给后台 6条数据
			pageList.setTotalSize(vehicleRentDao.getWeChatVehicleRentCount(map)); // 多表查询数据总条数
			List<VehicleRentBean> list = vehicleRentDao.getWeChatVehicleRentList(map);
			if (list != null) {
				pageList.setList(list);
				logger.info("微信端获取房车租赁列表成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			logger.info("微信端获取房车租赁列表失败");
			e.printStackTrace();
		}
		return pageList;
	}
	
	
	/**
	 * 异步获取 房车租赁列表
	 * @param jsonInfo
	 * @return json
	 */
	public JsonResult getTvehicleRentPageList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<VehicleRentBean>  pageList = new PageList<VehicleRentBean>();
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			} 
			pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			jsonInfo.put("page", pageList.getFromPos());
			jsonInfo.put("size",Const.TEST_FONT_PAGE_SIZE);	
			List<VehicleRentBean> weChatVehicleRentList = vehicleRentDao.getWeChatVehicleRentList(jsonInfo);
			if (weChatVehicleRentList.size() != 0) {
				pageList.setList(weChatVehicleRentList);
				pageList.setTotalSize(vehicleRentDao.getWeChatVehicleRentCount(jsonInfo)); // 多表查询数据总条数
				if (pageList.getTotalPage() != 0) {
					result.success("微信端异步获取房车租赁列表获取成功");
					result.setData(pageList);
					logger.info("微信端异步获取房车租赁列表获取成功");
				} 
			}else {
				logger.info("微信端异步获取房车租赁列表获取失败");
				result.setData(pageList);
				return result.error("微信端异步获取房车租赁列表获取失败");
			}
		} catch (Exception e) {
			result.error("微信端异步获取房车租赁列表失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 根据 房车租赁Id 获取租赁详情
	 * @param vehicleRentId
	 * @return VehicleRentBean
	 */
	public VehicleRentBean getTvehicleRentById(Integer vehicleRentId) {
		VehicleRentBean vehicleRentBeanId = null;
		try {
			vehicleRentBeanId = vehicleRentDao.getVehicleRentId(vehicleRentId);
			if (vehicleRentBeanId != null) {
				logger.info("微信端获取房车租赁详情接口执行成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			logger.info("微信端获取房车租赁详情接口执行失败");
			e.printStackTrace();
		}
		return vehicleRentBeanId;
	}
	
}
