package com.yunqi.jhf.web.portal.shopping.tproductcate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.jhf.dao.domain.TProductCate;
import com.yunqi.jhf.service.front.TProductCateService;

/**
 * 
 * @author lianlh 产品分类相关接口
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/front/productcate")
public class TProductCateAction {

	@Autowired
	private TProductCateService tProductCateService;
	
	protected static Logger logger = Logger.getLogger(TProductCateAction.class);

	/**
	 * 产品类别列表
	 */
	@RequestMapping(value = "/productCateList")  
	public ModelAndView productCateList() {  
	    ModelAndView mav = new ModelAndView("/shopping/tproductcate/tproductcate_list");  
	    List<TProductCate> prodcatelist = tProductCateService.prodCateList();
	    mav.addObject("prodcatelist", prodcatelist);  
	    return mav;  
	} 
	
	
}
