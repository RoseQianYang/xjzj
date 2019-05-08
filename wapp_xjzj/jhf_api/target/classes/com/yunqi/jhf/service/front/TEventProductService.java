package com.yunqi.jhf.service.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.EventProductDao;
import com.yunqi.jhf.dao.persistence.TEventProductDao;
import com.yunqi.jhf.vo.EventProductBean;

@Service
public class TEventProductService {

private static final Logger log = Logger.getLogger(TEventProductService.class);
	
	@Resource
	private TEventProductDao teventProductDao;
	
	@Resource
	private EventProductDao eventProductDao;
	/**
     * 根据  活动id 查找活动产品
     * @param eventId
     * @return list
     */
	/*
	public List<TEventProduct> getEventProdByEid(int eventId) {
		JsonResult result = new JsonResult();
		TEventProduct eventprod = new TEventProduct();
		List<TEventProduct> eventproList = new ArrayList<TEventProduct>();
		try {
			if (eventId>0) {
				eventprod.setEventId(eventId);
				SqlSelect sqlSelect = new SqlSelect().where(TEventProduct.SQL_eventId).orderBy(" order by create_time desc");
				eventproList = teventProductDao.list(sqlSelect, eventprod);
			} else {
				result.error("获取eventId失败");
				log.error("获取eventId失败");
			}
		} catch (Exception e) {
			result.error("获取活动产品列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return eventproList;
	}
*/
	/**
	 * 获取活动产品列表
	 * @return list
	 */
	public List<EventProductBean> getEventProduct() {
		JsonResult result = new JsonResult();
		List<EventProductBean> eventprodList = new ArrayList<EventProductBean>();
		try {
			eventprodList = eventProductDao.getEventProdList();
		} catch (Exception e) {
			result.error("获取活动产品列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return eventprodList;
    }  
	
	/**
	 * 获取首页活动产品列表
	 * @return list
	 */
	public List<EventProductBean> getEventProductShouYe() {
		JsonResult result = new JsonResult();
		List<EventProductBean> eventprodList = new ArrayList<EventProductBean>();
		try {
			eventprodList = eventProductDao.getEventProdListShouYe();
		} catch (Exception e) {
			result.error("获取活动产品列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return eventprodList;
    }  
	
}
