package com.yunqi.jhf.service.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.EventDao;
import com.yunqi.jhf.dao.domain.TEvent;
import com.yunqi.jhf.dao.persistence.TEventDao;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.vo.EventInfoBean;

@Service
public class TEventService {
	
	private static final Logger log = Logger.getLogger(TEventService.class);
	
	@Resource
	private TEventDao teventDao;
	
	@Autowired
	private EventDao eventDao;
	

	/**
	 * 通过最新的活动创建时间获取活动列表8
	 * @return TEventList
	 */
	public List<TEvent> getEventList() {
		JsonResult result = new JsonResult();
		TEvent event = new TEvent();
		event.setIsShow(ConstantTool.ISDELETE_YES);
		List<TEvent> eventlist = new ArrayList<TEvent>();
		SqlSelect sqlSelect = new SqlSelect().orderBy(" order by create_time desc").where(TEvent.SQL_isShow);
		try {
			eventlist = teventDao.list(sqlSelect, event);
		} catch (Exception e) {
			result.error("获取活动列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return eventlist;
    }  
	
	//获取活动信息列表。过期的isShow字段为N,正在进行的活动的isShow字段为Y
	public List<EventInfoBean> getEventListIsShow(){
		return eventDao.getEventListIsShow();
	}
}
