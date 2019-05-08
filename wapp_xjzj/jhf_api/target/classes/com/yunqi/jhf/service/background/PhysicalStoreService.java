package com.yunqi.jhf.service.background;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TPhysicalStore;
import com.yunqi.jhf.dao.persistence.TPhysicalStoreDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;

@Service
public class PhysicalStoreService {

	@Autowired
	private TPhysicalStoreDao tPhysicalStoreDao;
	
	protected static Logger log = Logger.getLogger(PhysicalStoreService.class);

	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入获取实体门店列表接口");
		JsonResult result = new JsonResult();
		PageList<TPhysicalStore> pagelist = new PageList<>();
		SqlSelect sql = new SqlSelect()
				.where(TPhysicalStore.SQL_isDelete)
				.orderBy("order by create_time DESC");
		jsonInfo.put(TPhysicalStore.PROP_isDelete, ConstantTool.ISDELETE_YES);
		if (jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
		}
		if (jsonInfo.get(TPhysicalStore.PROP_title) != null) {
			sql.and("title like :title");
			jsonInfo.put(TPhysicalStore.PROP_title, "%" + jsonInfo.get(TPhysicalStore.PROP_title) + "%");
		}
		if (jsonInfo.get(TPhysicalStore.PROP_address) != null) {
			sql.and("address like :address");
			jsonInfo.put(TPhysicalStore.PROP_address, "%" + jsonInfo.get(TPhysicalStore.PROP_address) + "%");
		}
		pagelist = tPhysicalStoreDao.pageListByMap(pagelist, true, sql, jsonInfo);
		if (pagelist != null) {
			log.info("获取实体门店列表接口执行成功");
			result.success("获取成功");
			result.setData(pagelist);
		} else {
			log.info("获取实体门店列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}


	public JsonResult create(TPhysicalStore physicalStore) {
		log.info("进入新增实体门店信息接口");
		JsonResult result = new JsonResult();
		physicalStore.setIsDelete(ConstantTool.ISDELETE_YES);
		if (physicalStore.getTitle() == null) {
			log.info("门店名称为空");
			return result.error("门店名称不可为空");
		}
		if (physicalStore.getAddress() == null) {
			log.info("门店地址为空");
			return result.error("门店地址不可为空");
		}
		if (physicalStore.getLatitude() == null) {
			log.info("门店地址纬度为空");
			return result.error("门店地址纬度不可为空");
		}
		if (physicalStore.getLongitude() == null) {
			log.info("门店地址经度为空");
			return result.error("门店地址经度不可为空");
		}
		if (physicalStore.getCover() == null) {
			log.info("门店封面为空");
			return result.error("门店封面不可为空");
		}
		if (physicalStore.getImageId() == null) {
			log.info("门店详情图片为空");
			return result.error("门店详情图片不可为空");
		}
		if (physicalStore.getPhone() == null) {
			log.info("门店联系电话为空");
			return result.error("门店联系电话不可为空");
		}
		if (physicalStore.getContent() == null) {
			log.info("门店图文信息为空");
			return result.error("门店图文信息不可为空");
		}
		int storeId = tPhysicalStoreDao.insert(physicalStore);
		if(storeId > 0) {
			log.info("新增实体门店信息接口执行成功");
			result.success("新增成功");
			result.setData(tPhysicalStoreDao.loadById(storeId));
		} else {
			log.info("新增实体门店信息接口执行失败");
			result.error("新增失败");
		}
		return result;
	}

	public JsonResult update(TPhysicalStore physicalStore) {
		log.info("进入修改实体门店信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(physicalStore.getId() != 0) {
			sql.where(TPhysicalStore.SQL_id);
		}else {
			log.info("传入活动Id为空");
			return result.error("传入活动Id不可为空");
		}
		if (physicalStore.getTitle() != null) {
			sql.addColumn(TPhysicalStore.SQL_title);
		} else {
			log.info("门店名称为空");
			return result.error("门店名称不可为空");
		}
		if (physicalStore.getAddress() != null) {
			sql.addColumn(TPhysicalStore.SQL_address);
		} else {
			log.info("门店地址为空");
			return result.error("门店地址不可为空");
		}
		if (physicalStore.getLatitude() != null) {
			sql.addColumn(TPhysicalStore.SQL_latitude);
		} else {
			log.info("门店地址纬度为空");
			return result.error("门店地址纬度不可为空");
		}
		if (physicalStore.getLongitude() != null) {
			sql.addColumn(TPhysicalStore.SQL_longitude);
		} else {
			log.info("门店地址经度为空");
			return result.error("门店地址经度不可为空");
		}
		if (physicalStore.getCover() != null) {
			sql.addColumn(TPhysicalStore.SQL_cover);
		} else {
			log.info("门店封面为空");
			return result.error("门店封面不可为空");
		}
		if (physicalStore.getImageId() != null) {
			sql.addColumn(TPhysicalStore.SQL_imageId);
		} else {
			log.info("门店详情图片为空");
			return result.error("门店详情图片不可为空");
		}
		if (physicalStore.getPhone() != null) {
			sql.addColumn(TPhysicalStore.SQL_phone);
		} else {
			log.info("门店联系电话为空");
			return result.error("门店联系电话不可为空");
		}
		if (physicalStore.getContent() != null) {
			sql.addColumn(TPhysicalStore.SQL_content);
		} else {
			log.info("门店图文信息为空");
			return result.error("门店图文信息不可为空");
		}
		int res = tPhysicalStoreDao.update(sql, physicalStore);
		if(res > 0) {
			log.info("修改实体门店信息接口执行成功");
			result.success("修改成功");
			result.setData(tPhysicalStoreDao.loadById(physicalStore.getId()));
		} else {
			log.info("修改实体门店信息接口执行失败");
			result.error("修改失败");
		}
		return result;
	}

	public JsonResult delete(int storeId) {
		log.info("进入删除实体门店信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate()
				.where(TPhysicalStore.SQL_id)
				.addColumn("update_time = NOW()")
				.addColumn(TPhysicalStore.SQL_isDelete);
		ModelMap jsonInfo = new ModelMap();
		jsonInfo.put(TPhysicalStore.PROP_id, storeId);
		jsonInfo.put(TPhysicalStore.PROP_isDelete, ConstantTool.ISDELETE_NO);
		int res = tPhysicalStoreDao.updateByMap(sql, jsonInfo);
		if(res > 0) {
			log.info("删除实体门店信息接口执行成功");
			result.success("删除成功");
		} else {
			log.info("删除实体门店信息接口执行失败");
			result.error("删除失败");
		}
		return result;
	}

}
