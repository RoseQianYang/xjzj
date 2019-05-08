package com.yunqi.jhf.web.portal.vehicle.enjoy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.yunqi.jhf.dao.domain.TVehicleBrand;
import com.yunqi.jhf.service.front.TVehicleBrandService;

@CrossOrigin
@RestController
@RequestMapping(value = "/vehicle/enjoy")
public class TVehicleBrandAction {

	@Autowired
	private TVehicleBrandService vehicleBrandService;
	
	protected static Logger logger = Logger.getLogger(TVehicleBrandAction.class);
	
	/**
	 * 获取 房车品牌 不分页列表 
	 * @return
	 */
	@RequestMapping(value = "/TVehicleBrandAction")  
	public ModelAndView productCateList(HttpServletRequest req, HttpSession sess, HttpServletResponse res) {  
	    ModelAndView mav = new ModelAndView("/vehicle/enjoy/vehiclebrandlist");    
	    try {
	    	List<TVehicleBrand> vehicleBrandList = vehicleBrandService.vehicleBrandList();
	    	mav.addObject("vehicleBrandList", vehicleBrandList);
	    	logger.info("微信端获取房车品牌列表成功");
		} catch (Exception e) {
			logger.info("微信端获取房车品牌列表失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	     return mav;  
	} 
}
