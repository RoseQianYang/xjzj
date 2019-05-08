package com.yunqi.jhf.service.front;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunqi.jhf.dao.persistence.TEventCateDao;

@Service
public class TEventCateService {

//	private static final Logger log = Logger.getLogger(TEventCateService.class);
	
	@Resource
	private TEventCateDao teventCateDao;
	
	
}
