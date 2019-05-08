package com.yunqi.jhf.service.background;

import java.sql.Timestamp;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.EventDao;
import com.yunqi.jhf.dao.domain.TEvent;
import com.yunqi.jhf.dao.persistence.TEventDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.EventInfoBean;


@Service
public class EventService {

	protected static Logger log = Logger.getLogger(EventService.class);
	
	@Autowired
	private TEventDao tEventDao;
	
	@Autowired
	private EventDao eventDao;
	
	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入后台获取活动信息列表接口");
		JsonResult result = new JsonResult();
		PageList<EventInfoBean> pagelist = new PageList<>();
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
		}
		List<EventInfoBean> eventList = eventDao.getEventList(jsonInfo);
		if(eventList != null) {
			pagelist.setList(eventList);
			pagelist.setTotalSize(eventDao.getEventCount(jsonInfo));
			result.setData(pagelist);
			log.info("获取活动信息列表接口执行成功");
			result.success("获取成功");
		}else {
			log.info("获取活动信息列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}
	
	public JsonResult create(ModelMap jsonInfo) {
		log.info("进入后台新增活动信息接口");
		JsonResult result = new JsonResult();
		TEvent event = new TEvent();
		if(jsonInfo.get(TEvent.PROP_eventCateId) == null) {
			log.info("活动分类为空");
			return result.error("请选择活动分类");
		}else {
			event.setEventCateId((int) jsonInfo.get(TEvent.PROP_eventCateId));
		}
		if(jsonInfo.get(TEvent.PROP_title) == null || 
				"".equals(((String) jsonInfo.get(TEvent.PROP_title)).trim())) {
			log.info("活动名称为空");
			return result.error("活动名称不可为空");
		}else {
			event.setTitle((String) jsonInfo.get(TEvent.PROP_title));
		}
		if(jsonInfo.get(TEvent.PROP_cover) == null || 
				"".equals(((String) jsonInfo.get(TEvent.PROP_cover)).trim())) {
			log.info("活动封面为空");
			return result.error("活动封面不可为空");
		}else {
			event.setCover((String) jsonInfo.get(TEvent.PROP_cover));
		}
		if(jsonInfo.get(TEvent.PROP_content) == null || 
				"".equals(((String) jsonInfo.get(TEvent.PROP_content)).trim())) {
			log.info("活动图文详情为空");
			return result.error("活动图文详情不可为空");
		}else {
			event.setContent((String) jsonInfo.get(TEvent.PROP_content));
		}
		if(jsonInfo.get(TEvent.PROP_startTime) != null && jsonInfo.get(TEvent.PROP_endTime) != null) {
			event.setStartTime(Timestamp.valueOf((String) jsonInfo.get(TEvent.PROP_startTime)));
			event.setEndTime(Timestamp.valueOf((String) jsonInfo.get(TEvent.PROP_endTime)));
			if(checkTime(event.getStartTime(), event.getEndTime(), null)) {
				log.info("活动时间相冲突");
				return result.error("该时间段已有其他活动正在进行");
			}
		}else {
			log.info("活动时间为空");
			return result.error("活动时间不可为空");
		}
		event.setIsShow(ConstantTool.ISDELETE_YES);
		int eventId = tEventDao.insert(event);
		if(eventId > 0) {
			log.info("后台新增活动信息接口执行成功");
			result.success("新增成功");
			result.setData(eventDao.getEventById(eventId));
		}else {
			log.info("后台新增活动信息接口执行失败");
			result.error("新增失败");
		}
		return result;
	}
	
	public JsonResult update(ModelMap jsonInfo) {
		log.info("进入后台修改活动信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(jsonInfo.get(TEvent.PROP_id) == null) {
			log.info("传入活动Id为空");
			return result.error("传入活动Id不可为空");
		}else {
			sql.where(TEvent.SQL_id);
		}
		if(jsonInfo.get(TEvent.PROP_eventCateId) == null) {
			log.info("活动分类为空");
			return result.error("请选择活动分类");
		}else {
			sql.addColumn(TEvent.SQL_eventCateId);
		}
		if(jsonInfo.get(TEvent.PROP_title) == null || 
				"".equals(((String) jsonInfo.get(TEvent.PROP_title)).trim())) {
			log.info("活动名称为空");
			return result.error("活动名称不可为空");
		}else {
			sql.addColumn(TEvent.SQL_title);
		}
		if(jsonInfo.get(TEvent.PROP_cover) == null || 
				"".equals(((String) jsonInfo.get(TEvent.PROP_cover)).trim())) {
			log.info("活动封面为空");
			return result.error("活动封面不可为空");
		}else {
			sql.addColumn(TEvent.SQL_cover);
		}
		if(jsonInfo.get(TEvent.PROP_content) == null || 
				"".equals(((String) jsonInfo.get(TEvent.PROP_content)).trim())) {
			log.info("活动图文详情为空");
			return result.error("活动图文详情不可为空");
		}else {
			sql.addColumn(TEvent.SQL_content);
		}
		if(jsonInfo.get(TEvent.PROP_startTime) != null && jsonInfo.get(TEvent.PROP_endTime) != null) {
			if(checkTime(Timestamp.valueOf((String) jsonInfo.get(TEvent.PROP_startTime)), 
					Timestamp.valueOf((String) jsonInfo.get(TEvent.PROP_endTime)), 
					(int) jsonInfo.get(TEvent.PROP_id))) {
				log.info("活动时间相冲突");
				return result.error("该时间段已有其他活动正在进行");
			}else {
				sql.addColumn(TEvent.SQL_startTime);
				sql.addColumn(TEvent.SQL_endTime);
				jsonInfo.put(TEvent.PROP_startTime,Timestamp.valueOf((String) jsonInfo.get(TEvent.PROP_startTime)));
				jsonInfo.put(TEvent.PROP_endTime,Timestamp.valueOf((String) jsonInfo.get(TEvent.PROP_endTime)));
			}
		}else {
			log.info("活动时间为空");
			return result.error("活动时间不可为空");
		}
		int res = tEventDao.updateByMap(sql, jsonInfo);
		if(res > 0) {
			log.info("后台修改活动信息接口执行成功");
			result.success("修改成功");
			result.setData(eventDao.getEventById((int) jsonInfo.get(TEvent.PROP_id)));
		}else {
			log.info("后台修改活动信息接口执行失败");
			result.error("修改失败");
		}
		return result;
	}
	
	public JsonResult get(int eventId) {
		log.info("进入后台获取活动信息详情接口");
		JsonResult result = new JsonResult();
		EventInfoBean event = eventDao.getEventById(eventId);
		if(event != null) {
			log.info("后台获取活动信息详情接口执行成功");
			result.success("获取成功");
			result.setData(event);
		}else {
			log.info("后台获取活动信息详情接口执行失败");
			result.error("获取失败");
		}
		return result;
	}
	
	//判断该活动持续时间是否有其他活动在进行
	private boolean checkTime(Timestamp starttime, Timestamp endtime,Integer id) {
		ModelMap jsonInfo = new ModelMap();
		jsonInfo.put(TEvent.PROP_startTime, starttime);
		jsonInfo.put(TEvent.PROP_endTime, endtime);
		jsonInfo.put(TEvent.PROP_id, id);
		int res = eventDao.getEvenByTime(jsonInfo);
		return res > 0;
	}
	
}
