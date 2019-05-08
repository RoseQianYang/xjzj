package com.yunqi.jhf.web.portal.shopping.tproduct;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.jhf.dao.domain.TProductBrand;
import com.yunqi.jhf.service.front.TProductBrandService;
import com.yunqi.jhf.web.portal.shopping.tproductcate.TProductCateAction;

/**
 * 
 * @author seek 产品品牌相关接口
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/productbrand")


public class TProductBrandAction {

	@Autowired
	private TProductBrandService tProductBrandService;
	
	protected static Logger logger = Logger.getLogger(TProductCateAction.class);

	/**
	 * 产品品牌列表
	 */
	@RequestMapping(value = "/productBrandList")  
	public ModelAndView productBrandList(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) {
	    ModelAndView mav = new ModelAndView("/shopping/tproduct/tproductbrand_list");  

	    List<TProductBrand> probrandlist=new ArrayList<TProductBrand>();
	    try {
		     probrandlist = tProductBrandService.findProductBrandAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    mav.addObject("probrandlist", probrandlist);  
	    return mav;  
	} 
	
}
