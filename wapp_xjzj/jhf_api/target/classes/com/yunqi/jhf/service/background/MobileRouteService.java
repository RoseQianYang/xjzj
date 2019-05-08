package com.yunqi.jhf.service.background;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TMobileRoute;
import com.yunqi.jhf.dao.persistence.TMobileRouteDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;

@Service
public class MobileRouteService {

	protected static Logger log = Logger.getLogger(MobileRouteService.class);
	
	@Autowired
	private TMobileRouteDao tMobileRouteDao;
	
	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入获取路线列表接口");
		JsonResult result = new JsonResult();
		PageList<TMobileRoute> pagelist = new PageList<>();
		SqlSelect sql = new SqlSelect()
				.where(TMobileRoute.SQL_isDelete)
				.orderBy("order by create_time DESC");;
		jsonInfo.put(TMobileRoute.PROP_isDelete, ConstantTool.ISDELETE_YES);
		if (jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
		}
		if (jsonInfo.get(TMobileRoute.PROP_title) != null) {
			sql.and("title like :title");
			jsonInfo.put(TMobileRoute.PROP_title, "%" + jsonInfo.get(TMobileRoute.PROP_title) + "%");
		}
		if (jsonInfo.get(TMobileRoute.PROP_address) != null) {
			sql.and("address like :address");
			jsonInfo.put(TMobileRoute.PROP_address, "%" + jsonInfo.get(TMobileRoute.PROP_address) + "%");
		}
		pagelist = tMobileRouteDao.pageListByMap(pagelist, true, sql, jsonInfo);
		if (pagelist != null) {
			log.info("获取路线列表接口执行成功");
			result.success("获取成功");
			result.setData(pagelist);
		} else {
			log.info("获取路线列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}

	public JsonResult create(TMobileRoute mobileRoute) {
		log.info("进入新增路线信息接口");
		JsonResult result = new JsonResult();
		mobileRoute.setIsDelete(ConstantTool.ISDELETE_YES);
		if (mobileRoute.getTitle() == null) {
			log.info("路线名称为空");
			return result.error("路线名称不可为空");
		}
		if (mobileRoute.getAddress() == null) {
			log.info("路线地址为空");
			return result.error("路线地址不可为空");
		}
		if (mobileRoute.getCover() == null) {
			log.info("路线封面为空");
			return result.error("路线封面不可为空");
		}
		if (mobileRoute.getImageId() == null) {
			log.info("路线详情图片为空");
			return result.error("路线详情图片不可为空");
		}
		if (mobileRoute.getPhone() == null) {
			log.info("路线联系电话为空");
			return result.error("路线联系电话不可为空");
		}
		if (mobileRoute.getContent() == null) {
			log.info("路线图文信息为空");
			return result.error("路线图文信息不可为空");
		}
		int mobileRouteId = tMobileRouteDao.insert(mobileRoute);
		if(mobileRouteId > 0) {
			log.info("新增路线信息接口执行成功");
			result.success("新增成功");
			result.setData(tMobileRouteDao.loadById(mobileRouteId));
		} else {
			log.info("新增路线信息接口执行失败");
			result.error("新增失败");
		}
		return result;
	}

	public JsonResult update(TMobileRoute mobileRoute) {
		log.info("进入修改路线信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(mobileRoute.getId() != 0) {
			sql.where(TMobileRoute.SQL_id);
		}else {
			log.info("传入路线Id为空");
			return result.error("传入路线Id不可为空");
		}
		if (mobileRoute.getTitle() != null) {
			sql.addColumn(TMobileRoute.SQL_title);
		} else {
			log.info("路线名称为空");
			return result.error("路线名称不可为空");
		}
		if (mobileRoute.getAddress() != null) {
			sql.addColumn(TMobileRoute.SQL_address);
		} else {
			log.info("路线地址为空");
			return result.error("路线地址不可为空");
		}
		if (mobileRoute.getCover() != null) {
			sql.addColumn(TMobileRoute.SQL_cover);
		} else {
			log.info("路线封面为空");
			return result.error("路线封面不可为空");
		}
		if (mobileRoute.getImageId() != null) {
			sql.addColumn(TMobileRoute.SQL_imageId);
		} else {
			log.info("路线详情图片为空");
			return result.error("路线详情图片不可为空");
		}
		if (mobileRoute.getPhone() != null) {
			sql.addColumn(TMobileRoute.SQL_phone);
		} else {
			log.info("路线联系电话为空");
			return result.error("路线联系电话不可为空");
		}
		if (mobileRoute.getContent() != null) {
			sql.addColumn(TMobileRoute.SQL_content);
		} else {
			log.info("路线图文信息为空");
			return result.error("路线图文信息不可为空");
		}
		int res = tMobileRouteDao.update(sql, mobileRoute);
		if(res > 0) {
			log.info("修改路线信息接口执行成功");
			result.success("修改成功");
			result.setData(tMobileRouteDao.loadById(mobileRoute.getId()));
		} else {
			log.info("修改路线信息接口执行失败");
			result.error("修改失败");
		}
		return result;
	}

	public JsonResult delete(int mobileRouteId) {
		log.info("进入删除路线信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate()
				.where(TMobileRoute.SQL_id)
				.addColumn("update_time = NOW()")
				.addColumn(TMobileRoute.SQL_isDelete);
		ModelMap jsonInfo = new ModelMap();
		jsonInfo.put(TMobileRoute.PROP_id, mobileRouteId);
		jsonInfo.put(TMobileRoute.PROP_isDelete, ConstantTool.ISDELETE_NO);
		int res = tMobileRouteDao.updateByMap(sql, jsonInfo);
		if(res > 0) {
			log.info("删除路线信息接口执行成功");
			result.success("删除成功");
		} else {
			log.info("删除路线信息接口执行失败");
			result.error("删除失败");
		}
		return result;
	}

}
