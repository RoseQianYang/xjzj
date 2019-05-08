package com.yunqi.jhf.service.background;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.VehicleRentDao;
import com.yunqi.jhf.dao.domain.TVehicleRent;
import com.yunqi.jhf.dao.persistence.TVehicleRentDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlDelete;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.VehicleRentBean;

/**
 * 提供 房车租赁 相关服务
 * 
 * @author wangsong
 *
 */

@Service
public class VehicleRentService {

	protected Logger logger = Logger.getLogger(VehicleRentService.class);

	@Autowired
	private VehicleRentDao vehicleRentDao;

	@Autowired
	private TVehicleRentDao tVehicleRentDao;

	/**
	 * 获取 房车租赁 分页列表
	 * 
	 * @param jsonInfo
	 * @return Json
	 */
	public JsonResult getVehicleRentList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		try {
			// 分页
			PageList<VehicleRentBean> pagelist = new PageList<>();
			if (jsonInfo.get("page") == null) {
				jsonInfo.put("page", 0);
			} else {
				pagelist.setCurrentPage((int) jsonInfo.get("page"));
				pagelist.setTotalSize(vehicleRentDao.getWeChatVehicleRentCount(jsonInfo)); // 多表查询数据总条数
				jsonInfo.put("page", pagelist.getFromPos());
			}
			// 查询 列表
			List<VehicleRentBean> vehicleRentBeans = vehicleRentDao.getVehicleRentList(jsonInfo);
			if (vehicleRentBeans != null) {
				pagelist.setList(vehicleRentBeans);
				result.setData(pagelist);
				result.success("获取成功");
				logger.info("后台房车租赁列表接口执行成功");
			}
		} catch (Exception e) {
			result.error("后台房车租赁列表执行接口失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 新增 房车租赁
	 * 
	 * @param vehicleRent
	 * @return Json
	 */
	public JsonResult create(TVehicleRent vehicleRent) {
		JsonResult result = new JsonResult();
		try {
			// 字段校验
			if (vehicleRent.getVehicleId() == 0) {
				return result.error("房车id不可为空");
			}
			if (vehicleRent.getRentPrice() == 0) {
				return result.error("房车租赁价格不可为空");
			}
			if (vehicleRent.getAddress() == null || "".equals(vehicleRent.getAddress())) {
				return result.error("联系地址不可为空");
			}
			if (vehicleRent.getPhone() == null || "".equals(vehicleRent.getPhone())) {
				return result.error("联系电话不可为空");
			}
			// 新增
			int tvehicleRentId = tVehicleRentDao.insert(vehicleRent);
			if (tvehicleRentId > 0) {
				logger.info("后台新增房车租赁接口执行完成");
				result.success("新增成功");
				result.setData(tVehicleRentDao.loadById(vehicleRent.getId()));
			} else {
				logger.info("后台新增房车租赁接口执行失败");
				result.error("新增失败");
			}
		} catch (Exception e) {
			logger.info("后台新增房车租赁失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 修改 房车租赁
	 * 
	 * @param vehicleRent
	 * @return Json
	 */
	public JsonResult update(TVehicleRent vehicleRent) {
		JsonResult result = new JsonResult();
		try {
			// 校验
			if (vehicleRent.getRentPrice() == 0) {
				return result.error("房车租赁价格不可为空");
			}
			if (vehicleRent.getAddress() == null || "".equals(vehicleRent.getAddress())) {
				return result.error("联系地址不可为空");
			}
			if (vehicleRent.getPhone() == null || "".equals(vehicleRent.getPhone())) {
				return result.error("联系电话不可为空");
			}

			// 单表方式 更新 修改 字段 和 记录更新时间
			SqlUpdate sql = new SqlUpdate()
					.addColumn(TVehicleRent.SQL_rentPrice)
					.addColumn(TVehicleRent.SQL_address)
					.addColumn(TVehicleRent.SQL_phone)
					.addColumn("update_time = NOW()")
					.where(TVehicleRent.SQL_id);
			int row = tVehicleRentDao.update(sql, vehicleRent);
			if (row == 1) {
				logger.info("后台修改房车租赁接口执行完成");
				result.success("后台修改房车租赁成功");
				result.setData(tVehicleRentDao.loadById(vehicleRent.getId()));
			}
		} catch (Exception e) {
			logger.info("后台修改房车租赁接口执行失败");
			logger.error(e.getMessage(), e);
			result.error("后台修改房车租赁失败");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除 房车租赁
	 * 逻辑删除
	 * @param vehicleRent
	 * @return
	 */
	public JsonResult delete(TVehicleRent vehicleRent) {
		JsonResult result = new JsonResult();
		try {
			SqlDelete sqlDelete = new SqlDelete().where(TVehicleRent.SQL_id);
			int res = tVehicleRentDao.delete(sqlDelete, vehicleRent);
			if (res > 0) {
				logger.info("删除房车租赁方法执行成功");
				result.success("删除成功");
			} else {
				logger.info("删除房车租赁方法执行失败");
				result.error("删除失败");
			}
		} catch (Exception e) {
			logger.info("删除房车租赁失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
}
