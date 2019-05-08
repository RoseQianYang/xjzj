package com.yunqi.jhf.service.background;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.yunqi.common.json.JsonResult;
import com.yunqi.common.json.SuccessResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TEventCate;
import com.yunqi.jhf.dao.persistence.TEventCateDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;

@Service
public class EventCateService {
	
	protected static Logger log = Logger.getLogger(EventCateService.class);
	
	@Autowired
	private TEventCateDao tEventCateDao;
	
	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入后台获取活动分类列表接口");
		JsonResult result = new JsonResult();
		PageList<TEventCate> pagelist = new PageList<>();
		SqlSelect sql = new SqlSelect().where("1 = 1")
				.orderBy("order by create_time DESC");
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
		}
		if(jsonInfo.get(TEventCate.PROP_title) != null) {
			jsonInfo.put(TEventCate.PROP_title, "%" + jsonInfo.get(TEventCate.PROP_title) +"%");
			sql.and(TEventCate.FLD_title + " like :title");
		}
		pagelist = tEventCateDao.pageListByMap(pagelist, true, sql, jsonInfo);
		if(pagelist != null) {
			log.info("后台获取活动分类列表接口执行成功");
			result.success("获取成功");
			result.setData(pagelist);
		}else {
			log.info("后台获取活动分类列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}
	
	public JsonResult getList() {
		return new SuccessResult().setData(tEventCateDao.list(null, null));
	}
	
	public JsonResult create(TEventCate eventCate) {
		log.info("进入后台新增活动分类接口");
		JsonResult result = new JsonResult();
		if(eventCate.getTitle() == null || "".equals(eventCate.getTitle().trim())) {
			log.info("活动分类名称为空");
			return result.error("活动分类名称不可为空");
		}
		int cateId = tEventCateDao.insert(eventCate);
		if(cateId > 0) {
			log.info("后台新增活动分类接口执行成功");
			result.success("新增成功");
			result.setData(tEventCateDao.loadById(cateId));
		}else {
			log.info("后台新增活动分类接口执行失败");
			result.error("新增失败");
		}
		return result;
	}
	
	public JsonResult update(TEventCate eventCate) {
		log.info("进入后台修改活动分类接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(eventCate.getId() == 0) {
			log.info("传入活动分类Id为空");
			return result.error("传入活动分类Id不可为空");
		}else {
			sql.where(TEventCate.SQL_id );
		}
		if(eventCate.getTitle() != null) {
			sql.addColumn(TEventCate.SQL_title);
		}
		int res = tEventCateDao.update(sql, eventCate);
		if(res > 0) {
			log.info("后台修改活动分类接口执行成功");
			result.success("修改成功");
			result.setData(tEventCateDao.loadById(eventCate.getId()));
		}else {
			log.info("后台修改活动分类接口执行失败");
			result.error("修改失败");
		}
		return result;
	}
}
