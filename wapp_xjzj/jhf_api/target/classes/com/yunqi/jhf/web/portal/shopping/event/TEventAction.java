package com.yunqi.jhf.web.portal.shopping.event;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.jhf.dao.domain.TEvent;
import com.yunqi.jhf.service.front.TEventProductService;
import com.yunqi.jhf.service.front.TEventService;
import com.yunqi.jhf.vo.EventInfoBean;
import com.yunqi.jhf.vo.EventProductBean;

/**
 * 活动相关
 * @author llh
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/front/event")
public class TEventAction {
	
	@Autowired
	private TEventService tEventService;
	
	@Autowired
	private TEventProductService tEventProductService;
	
	/**
	 * 获取活动及活动产品列表
	 */
	@RequestMapping(value = "/eventList")
	public ModelAndView eventList(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/shopping/event/tevent_list");  
		List<TEvent> eventAllList = tEventService.getEventList();
		List<TEvent> eventList = new ArrayList<>();
		if (eventAllList != null) {
			for (int i = 0; i < 4; i++) {
				eventList.add(eventAllList.get(i));
			}
		}
		List<EventProductBean> eventprodList = 
				       tEventProductService.getEventProduct();
		mav.addObject("eventList", eventList);
		mav.addObject("eventAllList", eventAllList);
		mav.addObject("eventprodList", eventprodList);
		return mav;
	}
	
	/**
	 * 获取热卖专区活动及活动产品列表
	 */
	@RequestMapping(value = "/eventHotList")
	public ModelAndView eventHotList(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/shopping/event/teventhot_list");  
		List<EventInfoBean> eventAllList = tEventService.getEventListIsShow();
		List<EventInfoBean> eventList = new ArrayList<EventInfoBean>();
		if (eventAllList != null ) {
			for (int i = 0; i < 4; i++) {
				eventList.add(eventAllList.get(i));
			}
		} else {
			throw new RuntimeException("热卖活动列表获取失败");
		}
		List<EventProductBean> eventprodList = 
				       tEventProductService.getEventProduct();
		mav.addObject("eventList", eventList);
		mav.addObject("eventAllList", eventAllList);
		mav.addObject("eventprodList", eventprodList);
		return mav;
	}
}
