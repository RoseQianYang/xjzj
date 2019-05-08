package com.yunqi.jhf.service.background;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TVehicleBrand;
import com.yunqi.jhf.dao.persistence.TVehicleBrandDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlDelete;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;

/**
 * 提供房车品牌相关服务
 * @author wangsong
 *
 */

@Service
public class VehicleBrandService {
	
	protected Logger logger = Logger.getLogger(VehicleBrandService.class);
	
	@Autowired
	private TVehicleBrandDao tvehicleBrandDao;
	
	/**
	 * 获取所有房车品牌列表(分页) 
	 * @return Json
	 */
	public JsonResult getVehicleBrandList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		try {
			// 单表 查询总记录翻页列表
			PageList<TVehicleBrand> pagelist = new PageList<>();
			SqlSelect sql = new SqlSelect().where("1 = 1").orderBy("order by create_time DESC");
			if (jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
				pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			}if (jsonInfo.get(TVehicleBrand.PROP_brandName) != null) {
				sql.and("brand_name like :brandName");
				jsonInfo.put(TVehicleBrand.PROP_brandName, "%" + jsonInfo.get(TVehicleBrand.PROP_brandName) + "%");
			}
			pagelist = tvehicleBrandDao.pageListByMap(pagelist, true, sql, jsonInfo);
			if (pagelist != null){
				result.setData(pagelist);
				result.success("获取成功");
				logger.info("获取所有房车品牌分页列表接口执行成功");	
			}
		} catch (Exception e) {
			logger.info("后台获取所有房车品牌分页列表接口执行失败");	
			result.error("获取失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取房车 品牌列表(不分页)
     * @param TVehicleBrand
     * @return list
	 */
	public JsonResult vehicleBrandList(){
	   JsonResult result = new JsonResult();
	   TVehicleBrand vehicleBrand = new TVehicleBrand();
	   try {
		   // 查询 所有列表
		   List<TVehicleBrand> vehicleBrands = tvehicleBrandDao.list(null, vehicleBrand);
		   if (vehicleBrands != null) {
			result.setData(vehicleBrands);
			result.success("获取成功");
			logger.info("后台获取房车品牌不分页列表接口执行成功");
		}
	} catch (Exception e) {
		logger.info("后台获取房车品牌列不分页表接口执行失败");
		result.error("获取失败");
		logger.error(e.getMessage(), e);
		e.printStackTrace();
	}
	  return result;
	   
	}
	
	/**
	 * 新增 房车品牌
	 * @param JsonInfo
	 * @return
	 */
	public JsonResult create(TVehicleBrand vehicleBrand) {
		JsonResult result = new JsonResult();
		try {
			// 校验
			if (vehicleBrand.getBrandName()== null || "".equals(vehicleBrand.getBrandName())) {
				return result.error("房车品牌名称不可为空");
			}
			if (vehicleBrand.getCover() == null || "".equals(vehicleBrand.getCover())) {
				return result.error("房车品牌图片未上传,请上传图片");
			}
			// 新增
			int vehicleBrandId = tvehicleBrandDao.insert(vehicleBrand);
			if (vehicleBrandId > 0) {
				logger.info("后台新增房车品牌图片接口执行完成");
				result.success("新增成功");
				result.setData(tvehicleBrandDao.loadById(vehicleBrand.getId()));
			}
		} catch (Exception e) {
			logger.info("后台新增房车品牌图片接口执行失败");
			result.success("新增失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 修改 房车品牌
	 * @param vehicleBrand
	 * @return
	 */
	public JsonResult updateVehicleBrand(TVehicleBrand vehicleBrand) {
		JsonResult result = new JsonResult();
		try {
			// 校验
			if (vehicleBrand.getBrandName() == null || "".equals(vehicleBrand.getBrandName())) {
				logger.info("传入房车品牌名称为空");
				return result.error("房车品牌名称不可为空");
			}
			if (vehicleBrand.getCover() == null || "".equals(vehicleBrand.getCover())) {
				logger.info("传入房车品牌logo图为空");
				return result.error("房车品牌logo图不可为空");
			}
			// 单表方式 更新 修改 字段 和 记录更新时间
			SqlUpdate sql = new SqlUpdate()
					.addColumn(TVehicleBrand.SQL_brandName)
					.addColumn(TVehicleBrand.SQL_cover)
					.addColumn("update_time = NOW()")
					.where(TVehicleBrand.SQL_id);
			int res = tvehicleBrandDao.update(sql, vehicleBrand);
			if (res == 1) {
				result.success("修改成功");
				result.setData(tvehicleBrandDao.loadById(vehicleBrand.getId()));
				logger.info("后台修改房车品牌方法执行接口成功");
			}
		} catch (Exception e) {
			logger.info("后台修改房车品牌方法执行接口失败");
			result.error("修改失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 物理删除
	 * 删除 房车品牌
	 * @param map
	 * @return
	 */
	public JsonResult delete(TVehicleBrand vehicleBrand) {
		JsonResult result = new JsonResult();
		try {
			SqlDelete sqlDelete = new SqlDelete().where(TVehicleBrand.SQL_id);
			int res = tvehicleBrandDao.delete(sqlDelete, vehicleBrand);
			if (res > 0) {
				logger.info("删除房车品牌方法执行成功");
				result.success("删除成功");
			} else {
				logger.info("删除房车品牌方法执行失败");
				result.error("删除失败");
			}
		} catch (Exception e) {
			logger.info("删除房车品牌失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	

}
