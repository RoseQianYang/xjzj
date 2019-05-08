package com.yunqi.jhf.service.background;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TAfterOutlets;
import com.yunqi.jhf.dao.persistence.TAfterOutletsDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;

@Service
public class AfterOutletsSerivce {

	@Autowired
	private TAfterOutletsDao tAfterOutletsDao;
	

	protected static Logger log = Logger.getLogger(AfterOutletsSerivce.class);

	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入获取售后网点列表接口");
		JsonResult result = new JsonResult();
		PageList<TAfterOutlets> pagelist = new PageList<>();
		SqlSelect sql = new SqlSelect()
				.where(TAfterOutlets.SQL_isDelete)
				.orderBy("order by create_time DESC");
		jsonInfo.put(TAfterOutlets.PROP_isDelete, ConstantTool.ISDELETE_YES);
		if (jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
		}
		if (jsonInfo.get(TAfterOutlets.PROP_title) != null) {
			sql.and("title like :title");
			jsonInfo.put(TAfterOutlets.PROP_title, "%" + jsonInfo.get(TAfterOutlets.PROP_title) + "%");
		}
		if (jsonInfo.get(TAfterOutlets.PROP_address) != null) {
			sql.and("address like :address");
			jsonInfo.put(TAfterOutlets.PROP_address, "%" + jsonInfo.get(TAfterOutlets.PROP_address) + "%");
		}
		pagelist = tAfterOutletsDao.pageListByMap(pagelist, true, sql, jsonInfo);
		if (pagelist != null) {
			log.info("获取售后网点列表接口执行成功");
			result.success("获取成功");
			result.setData(pagelist);
		} else {
			log.info("获取售后网点列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}

	public JsonResult create(TAfterOutlets afterOutlets) {
		log.info("进入新增售后网点信息接口");
		JsonResult result = new JsonResult();
		afterOutlets.setIsDelete(ConstantTool.ISDELETE_YES);
		if (afterOutlets.getTitle() == null) {
			log.info("售后网点名称为空");
			return result.error("售后网点名称不可为空");
		}
		if (afterOutlets.getAddress() == null) {
			log.info("售后网点地址为空");
			return result.error("售后网点地址不可为空");
		}
		if (afterOutlets.getLatitude() == null) {
			log.info("售后网点地址纬度为空");
			return result.error("售后网点地址纬度不可为空");
		}
		if (afterOutlets.getLongitude() == null) {
			log.info("售后网点地址经度为空");
			return result.error("售后网点地址经度不可为空");
		}
		if (afterOutlets.getCover() == null) {
			log.info("售后网点封面为空");
			return result.error("售后网点封面不可为空");
		}
		if (afterOutlets.getImageId() == null) {
			log.info("售后网点详情图片为空");
			return result.error("售后网点详情图片不可为空");
		}
		if (afterOutlets.getPhone() == null) {
			log.info("售后网点联系电话为空");
			return result.error("售后网点联系电话不可为空");
		}
		if (afterOutlets.getContent() == null) {
			log.info("售后网点图文信息为空");
			return result.error("售后网点图文信息不可为空");
		}
		int outletId = tAfterOutletsDao.insert(afterOutlets);
		if(outletId > 0) {
			log.info("新增售后网点信息接口执行成功");
			result.success("新增成功");
			result.setData(tAfterOutletsDao.loadById(outletId));
		} else {
			log.info("新增售后网点信息接口执行失败");
			result.error("新增失败");
		}
		return result;
	}

	public JsonResult update(TAfterOutlets afterOutlets) {
		log.info("进入修改售后网点信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(afterOutlets.getId() != 0) {
			sql.where(TAfterOutlets.SQL_id);
		}else {
			log.info("传入活动Id为空");
			return result.error("传入活动Id不可为空");
		}
		if (afterOutlets.getTitle() != null) {
			sql.addColumn(TAfterOutlets.SQL_title);
		} else {
			log.info("售后网点名称为空");
			return result.error("售后网点名称不可为空");
		}
		if (afterOutlets.getAddress() != null) {
			sql.addColumn(TAfterOutlets.SQL_address);
		} else {
			log.info("售后网点地址为空");
			return result.error("售后网点地址不可为空");
		}
		if (afterOutlets.getLatitude() != null) {
			sql.addColumn(TAfterOutlets.SQL_latitude);
		} else {
			log.info("售后网点地址纬度为空");
			return result.error("售后网点地址纬度不可为空");
		}
		if (afterOutlets.getLongitude() != null) {
			sql.addColumn(TAfterOutlets.SQL_longitude);
		} else {
			log.info("售后网点地址经度为空");
			return result.error("售后网点地址经度不可为空");
		}
		if (afterOutlets.getCover() != null) {
			sql.addColumn(TAfterOutlets.SQL_cover);
		} else {
			log.info("售后网点封面为空");
			return result.error("售后网点封面不可为空");
		}
		if (afterOutlets.getImageId() != null) {
			sql.addColumn(TAfterOutlets.SQL_imageId);
		} else {
			log.info("售后网点详情图片为空");
			return result.error("售后网点详情图片不可为空");
		}
		if (afterOutlets.getPhone() != null) {
			sql.addColumn(TAfterOutlets.SQL_phone);
		} else {
			log.info("售后网点联系电话为空");
			return result.error("售后网点联系电话不可为空");
		}
		if (afterOutlets.getContent() != null) {
			sql.addColumn(TAfterOutlets.SQL_content);
		} else {
			log.info("售后网点图文信息为空");
			return result.error("售后网点图文信息不可为空");
		}
		int res = tAfterOutletsDao.update(sql, afterOutlets);
		if(res > 0) {
			log.info("修改售后网点信息接口执行成功");
			result.success("修改成功");
			result.setData(tAfterOutletsDao.loadById(afterOutlets.getId()));
		} else {
			log.info("修改售后网点信息接口执行失败");
			result.error("修改失败");
		}
		return result;
	}

	public JsonResult delete(int outletId) {
		log.info("进入删除售后网点信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate()
				.where(TAfterOutlets.SQL_id)
				.addColumn("update_time = NOW()")
				.addColumn(TAfterOutlets.SQL_isDelete);
		ModelMap jsonInfo = new ModelMap();
		jsonInfo.put(TAfterOutlets.PROP_id, outletId);
		jsonInfo.put(TAfterOutlets.PROP_isDelete, ConstantTool.ISDELETE_NO);
		int res = tAfterOutletsDao.updateByMap(sql, jsonInfo);
		if(res > 0) {
			log.info("删除售后网点信息接口执行成功");
			result.success("删除成功");
		} else {
			log.info("删除售后网点信息接口执行失败");
			result.error("删除失败");
		}
		return result;
	}

}
