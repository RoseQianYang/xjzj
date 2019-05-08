package com.yunqi.jhf.service.background;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.VehicleDao;
import com.yunqi.jhf.dao.domain.TVehicle;
import com.yunqi.jhf.dao.domain.TVehicleRent;
import com.yunqi.jhf.dao.persistence.TVehicleDao;
import com.yunqi.jhf.dao.persistence.TVehicleRentDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlDelete;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.VehicleBean;

/**
 * 提供 房车相关服务
 * @author wangsong
 *
 */

@Service
public class VehicleService {

	protected Logger logger = Logger.getLogger(VehicleService.class);

	@Autowired
	private VehicleDao vehicleDao;

	@Autowired
	private TVehicleDao tvehicleDao;
	
	@Autowired
	private TVehicleRentDao tVehicleRentDao;

	/**
	 * 获取房车 分页列表
	 * 
	 * @return Json
	 */
	public JsonResult getVehicleList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		try {
			// 分页
			PageList<VehicleBean> pagelist = new PageList<>();
			if (jsonInfo.get("page") == null) {
				jsonInfo.put("page", 0);
			} else {
				pagelist.setCurrentPage((int) jsonInfo.get("page"));
				pagelist.setTotalSize(vehicleDao.getVehicleListTotalSize(jsonInfo));
				jsonInfo.put("page", pagelist.getFromPos());
			}
			List<VehicleBean> vehicle = vehicleDao.getVehickeLists(jsonInfo);
			if (vehicle != null) {
				pagelist.setList(vehicle);
				result.setData(pagelist);
				result.success("获取成功");
				logger.info("平台端获取房车列表接口执行成功");
			} else {
				result.error("平台端获取房车列表失败");
				logger.info("平台端获取房车列表数据为空");
			}

		} catch (Exception e) {
			result.error("获取失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取房车 不分页列表
	 * @return
	 */
	public JsonResult vehicleList() {
		JsonResult result = new JsonResult();
		ModelMap map = new ModelMap();
		try {
			List<VehicleBean> vehickeListsNoPage = vehicleDao.getVehickeListsNoPage(map);
			if (vehickeListsNoPage != null) {
				result.setData(vehickeListsNoPage);
				result.success("获取成功");
				logger.info("平台端获取房车列表接口执行成功");
			}else {
				result.error("平台端获取列表失败");
				logger.info("平台端获取房车列表数据为空");
			}
		} catch (Exception e) {
			result.error("获取失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 新增房车
	 * 
	 * @param vehicle
	 * @return Json
	 */
	public JsonResult create(TVehicle vehicle) {
		JsonResult result = new JsonResult();
		try {
            // 校验
			if (vehicle.getVehicleName() == null || "".equals(vehicle.getVehicleName().trim())) {
				return result.error("房车名称不可为空"); 
			}
			if (vehicle.getCover()  == null || "".equals(vehicle.getCover())) {
				return result.error("封面图片不可为空");
			} 
			if (vehicle.getVehicleBrandId() == 0) {
				return result.error("房车品牌不可为空");
			}
			if (vehicle.getImageId() == null || "".equals(vehicle.getImageId())) {
				return result.error("房车素材图片不可为空");
			}
			if (vehicle.getPrice() == 0) {
				return result.error("房车价格不可为空");
			}
			if (vehicle.getSpec() == null || "".equals(vehicle.getSpec())) {
				return result.error("房车规格型号不可为空");
			}
			if (vehicle.getGears() == null || "".equals(vehicle.getGears())) {
				return result.error("档位不可为空");
			}
			if (vehicle.getIsSencond() == null || "".equals(vehicle.getIsSencond())) {
				return result.error("请选择是否是二手");
			}
			if (vehicle.getIsSales() == null || "".equals(vehicle.getIsSales())) {
				return result.error("请选择是否是促销");
			}
			if (vehicle.getFunctionType() == 0) {
				return result.error("请选择房车类型");
			}
			// 新增
			int tvehicleId = tvehicleDao.insert(vehicle);
			if (tvehicleId > 0) {
				logger.info("平台端新增房车接口执行完成");
				result.success("新增成功");
				result.setData(tvehicleDao.loadById(tvehicleId));
			} 
		} catch (Exception e) {
			logger.info("平台端新增房车接口执行失败");
			result.error("新增失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 修改 房车
	 * 
	 * @param vehicle
	 * @return Json
	 */
	public JsonResult updateVehicle(TVehicle vehicle) {
		JsonResult result = new JsonResult();
		try {
			// 校验
			if (vehicle.getVehicleName() == null || "".equals(vehicle.getVehicleName())) {
				logger.info("传入房车名称为空");
				return result.error("房车名称不可为空");
			}
			if (vehicle.getCover() == null || "".equals(vehicle.getCover())) {
				logger.info("传入房车封面图片为空");
				return result.error("房车封面图片不可为空");
			}
			if (vehicle.getImageId() == null || "".equals(vehicle.getImageId())) {
				logger.info("传入房车素材图片id为空");
				return result.error("房车素材图片id不可为空");
			}
			if (vehicle.getIntroduction() == null || "".equals(vehicle.getIntroduction())) {
				logger.info("传入房车介绍为空");
				return result.error("房车介绍不可为空");
			}
			if (vehicle.getContent() == null || "".equals(vehicle.getContent()) ) {
				logger.info("传入图文详情不可为空");
				return result.error("图文详情为空");
			}
			if (vehicle.getPrice() == 0) {
				logger.info("传入预定价格为0");
				return result.error("预定价格不能为0");
			}
			if (vehicle.getSpec() == null || "".equals(vehicle.getSpec())) {
				logger.info("传入房车规格型号为空");
				return result.error("房车规格型号不能为空");
			}
			if (vehicle.getGears() == null || "".equals(vehicle.getGears())) {
				logger.info("传入档位为空");
				return result.error("档位不能为空");
			}
			if (vehicle.getIsSencond() == null || "".equals(vehicle.getIsSencond())) {
				logger.info("传入是否是2手未选择");
				return result.error("是否是2手请选择");
			}
			if (vehicle.getIsSales() == null ||"".equals(vehicle.getIsSales())) {
				logger.info("传入是否是促销未选择");
				return result.error("是否是促销请选择");
			}
			if (vehicle.getFunctionType() == 0) {
				logger.info("传入自行式1  拖挂式2   皮卡3未选择");
				return result.error("自行式1  拖挂式2   皮卡3请选择");
			}
			if (vehicle.getLicense() == null ||"".equals(vehicle.getLicense())) {
				logger.info("传入驾照级别为空");
				return result.error("档驾照级别不能为空");
			}
			if (vehicle.getBedNum() == 0) {
				logger.info("传入床位为空");
				return result.error("床位不能为空");
			}
			
			// 单表方式 根据 id主键 更新 修改 字段 和 记录更新时间
			SqlUpdate sql = new SqlUpdate()
					.addColumn(TVehicle.SQL_vehicleName)
					.addColumn(TVehicle.SQL_cover)
					.addColumn(TVehicle.SQL_imageId)
					.addColumn(TVehicle.SQL_introduction)
					.addColumn(TVehicle.SQL_content)
					.addColumn(TVehicle.SQL_price)
					.addColumn(TVehicle.SQL_spec)
					.addColumn(TVehicle.SQL_gears)
					.addColumn(TVehicle.SQL_isSencond)
					.addColumn(TVehicle.SQL_isSales)
					.addColumn(TVehicle.SQL_functionType)
					.addColumn(TVehicle.SQL_license)
					.addColumn(TVehicle.SQL_bedNum)
					.addColumn("update_time = NOW()")
					.where(TVehicle.SQL_id);
			int res = tvehicleDao.update(sql, vehicle);
			if (res == 1) {
				result.success("修改成功");
				result.setData(tvehicleDao.loadById(vehicle.getId()));
				logger.info("修改房车法执行成功");
			} 
		} catch (Exception e) {
			logger.info("修改房车方法执行失败");
			result.error("修改失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除 房车
	 * 房车删除了的话，房车租赁里面对应的房车也就删除了
	 * @param vehicle
	 * @return json
	 */
	public JsonResult delete(TVehicle vehicle) {
		JsonResult result = new JsonResult();
		try {
			// 根据 房车id 删除 房车租赁 对应的房车
			TVehicleRent tVehicleRent = new TVehicleRent();
			tVehicleRent.setVehicleId(vehicle.getId());
			SqlDelete delete = new SqlDelete().where(TVehicleRent.SQL_vehicleId);
			tVehicleRentDao.delete(delete, tVehicleRent);
			
			// 房车租赁 根据房车id 删除
			SqlDelete sqlDelete =new SqlDelete().where(TVehicle.SQL_id);
			int res = tvehicleDao.delete(sqlDelete, vehicle);
			if (res > 0) {
				logger.info("删除房车方法执行成功");
				result.success("删除成功");
			} else {
				logger.info("删除房车方法执行失败");
				result.error("删除失败");
			}
		} catch (Exception e) {
			logger.info("删除房车失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

}
