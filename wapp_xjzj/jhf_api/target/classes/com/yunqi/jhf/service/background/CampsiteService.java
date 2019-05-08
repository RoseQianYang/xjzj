package com.yunqi.jhf.service.background;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TCampsite;
import com.yunqi.jhf.dao.persistence.TCampsiteDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;

@Service
public class CampsiteService {
	
	protected static Logger log = Logger.getLogger(CampsiteService.class);
	
	@Autowired
	private TCampsiteDao tCampsiteDao;
	
	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入获取营地列表接口");
		JsonResult result = new JsonResult();
		PageList<TCampsite> pagelist = new PageList<>();
		SqlSelect sql = new SqlSelect()
				.where(TCampsite.SQL_isDelete)
				.orderBy("order by create_time DESC");
		jsonInfo.put(TCampsite.PROP_isDelete, ConstantTool.ISDELETE_YES);
		if (jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
		}
		if (jsonInfo.get(TCampsite.PROP_title) != null) {
			sql.and("title like :title");
			jsonInfo.put(TCampsite.PROP_title, "%" + jsonInfo.get(TCampsite.PROP_title) + "%");
		}
		if (jsonInfo.get(TCampsite.PROP_categoryId) != null) {
			sql.and(TCampsite.SQL_categoryId);
		}
		if (jsonInfo.get(TCampsite.PROP_address) != null) {
			sql.and("address like :address");
			jsonInfo.put(TCampsite.PROP_address, "%" + jsonInfo.get(TCampsite.PROP_address) + "%");
		}
		pagelist = tCampsiteDao.pageListByMap(pagelist, true, sql, jsonInfo);
		if (pagelist != null) {
			log.info("获取营地列表接口执行成功");
			result.success("获取成功");
			result.setData(pagelist);
		} else {
			log.info("获取营地列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}

	public JsonResult create(TCampsite campsite) {
		log.info("进入新增营地信息接口");
		JsonResult result = new JsonResult();
		campsite.setIsDelete(ConstantTool.ISDELETE_YES);
		if (campsite.getTitle() == null) {
			log.info("营地名称为空");
			return result.error("营地名称不可为空");
		}
		if (campsite.getAddress() == null) {
			log.info("营地地址为空");
			return result.error("营地地址不可为空");
		}
		if (campsite.getCategoryId() == 0) {
			log.info("营地分类为空");
			return result.error("营地分类不可为空");
		}
		if (campsite.getCampsiteFunction() == null) {
			log.info("营地基础功能为空");
			return result.error("营地基础功能不可为空");
		}
		if (campsite.getLatitude() == null) {
			log.info("营地地址纬度为空");
			return result.error("营地地址纬度不可为空");
		}
		if (campsite.getLongitude() == null) {
			log.info("营地地址经度为空");
			return result.error("营地地址经度不可为空");
		}
		if (campsite.getCover() == null) {
			log.info("营地封面为空");
			return result.error("营地封面不可为空");
		}
		if (campsite.getImageId() == null) {
			log.info("营地详情图片为空");
			return result.error("营地详情图片不可为空");
		}
		if (campsite.getPhone() == null) {
			log.info("营地联系电话为空");
			return result.error("营地联系电话不可为空");
		}
		if (campsite.getContent() == null) {
			log.info("营地图文信息为空");
			return result.error("营地图文信息不可为空");
		}
		int campsiteId = tCampsiteDao.insert(campsite);
		if(campsiteId > 0) {
			log.info("新增营地信息接口执行成功");
			result.success("新增成功");
			result.setData(tCampsiteDao.loadById(campsiteId));
		} else {
			log.info("新增营地信息接口执行失败");
			result.error("新增失败");
		}
		return result;
	}

	public JsonResult update(TCampsite campsite) {
		log.info("进入修改营地信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(campsite.getId() != 0) {
			sql.where(TCampsite.SQL_id);
		}else {
			log.info("传入活动Id为空");
			return result.error("传入活动Id不可为空");
		}
		if (campsite.getTitle() != null) {
			sql.addColumn(TCampsite.SQL_title);
		} else {
			log.info("营地名称为空");
			return result.error("营地名称不可为空");
		}
		if (campsite.getAddress() != null) {
			sql.addColumn(TCampsite.SQL_address);
		} else {
			log.info("营地地址为空");
			return result.error("营地地址不可为空");
		}
		if (campsite.getCategoryId() != 0) {
			sql.addColumn(TCampsite.SQL_categoryId);
		} else {
			log.info("营地分类为空");
			return result.error("营地分类不可为空");
		}
		if (campsite.getCampsiteFunction() != null) {
			sql.addColumn(TCampsite.SQL_campsiteFunction);	
		} else {
			log.info("营地基础功能为空");
			return result.error("营地基础功能不可为空");
		}
		if (campsite.getLatitude() != null) {
			sql.addColumn(TCampsite.SQL_latitude);
		} else {
			log.info("营地地址纬度为空");
			return result.error("营地地址纬度不可为空");
		}
		if (campsite.getLongitude() != null) {
			sql.addColumn(TCampsite.SQL_longitude);
		} else {
			log.info("营地地址经度为空");
			return result.error("营地地址经度不可为空");
		}
		if (campsite.getCover() != null) {
			sql.addColumn(TCampsite.SQL_cover);
		} else {
			log.info("营地封面为空");
			return result.error("营地封面不可为空");
		}
		if (campsite.getImageId() != null) {
			sql.addColumn(TCampsite.SQL_imageId);
		} else {
			log.info("营地详情图片为空");
			return result.error("营地详情图片不可为空");
		}
		if (campsite.getPhone() != null) {
			sql.addColumn(TCampsite.SQL_phone);
		} else {
			log.info("营地联系电话为空");
			return result.error("营地联系电话不可为空");
		}
		if (campsite.getContent() != null) {
			sql.addColumn(TCampsite.SQL_content);
		} else {
			log.info("营地图文信息为空");
			return result.error("营地图文信息不可为空");
		}
		int res = tCampsiteDao.update(sql, campsite);
		if(res > 0) {
			log.info("修改营地信息接口执行成功");
			result.success("修改成功");
			result.setData(tCampsiteDao.loadById(campsite.getId()));
		} else {
			log.info("修改营地信息接口执行失败");
			result.error("修改失败");
		}
		return result;
	}

	public JsonResult delete(int campsiteId) {
		log.info("进入删除营地信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate()
				.where(TCampsite.SQL_id)
				.addColumn("update_time = NOW()")
				.addColumn(TCampsite.SQL_isDelete);
		ModelMap jsonInfo = new ModelMap();
		jsonInfo.put(TCampsite.PROP_id, campsiteId);
		jsonInfo.put(TCampsite.PROP_isDelete, ConstantTool.ISDELETE_NO);
		int res = tCampsiteDao.updateByMap(sql, jsonInfo);
		if(res > 0) {
			log.info("删除营地信息接口执行成功");
			result.success("删除成功");
		} else {
			log.info("删除营地信息接口执行失败");
			result.error("删除失败");
		}
		return result;
	}

}
