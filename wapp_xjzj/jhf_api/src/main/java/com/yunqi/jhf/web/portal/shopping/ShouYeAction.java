package com.yunqi.jhf.web.portal.shopping;

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

import com.yunqi.jhf.dao.domain.TProduct;
import com.yunqi.jhf.dao.domain.TProductBrand;
import com.yunqi.jhf.dao.domain.TProductCate;
import com.yunqi.jhf.service.front.TEventProductService;
import com.yunqi.jhf.service.front.TProductBrandService;
import com.yunqi.jhf.service.front.TProductCateService;
import com.yunqi.jhf.service.front.TProductService;
import com.yunqi.jhf.vo.EventProductBean;
import com.yunqi.jhf.web.portal.WechatSessionUser;

/**
 * 商城展示首页
 * @author Seek
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/shopping")
public class ShouYeAction {
	@Autowired
	private TProductService tProductService;
	@Autowired
	private TProductCateService tProductCateService;
	@Autowired
	private TProductBrandService tProductBrandService;

	@Autowired
	private TEventProductService tEventProductService;
	
	private static final Logger log = Logger.getLogger(ShouYeAction.class);
	/**
	 * 产品列表
	 * 
	 */
	@RequestMapping(value = "/shouYe")
	public ModelAndView shouYe(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/shopping/shouye");
	    WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
	    List<TProductCate> prodcatelist = tProductCateService.prodCateImageByNew();
	    String fristImg=null;
	    String secondImg=null;
	    List<TProduct> fristpro=new ArrayList<>();
	    List<TProduct> tpro=new ArrayList<>();
	    List<TProductBrand> prodBrandList=new ArrayList<>();
	    List<EventProductBean> eventprodList =new ArrayList<>();
	    try{
	    	if(prodcatelist!=null){
	    		fristImg=prodcatelist.get(0).getCover();
	    		int procId=prodcatelist.get(0).getId();
	    		List<TProduct> proList= tProductService.getProByCateId(procId);
	    		fristpro=new ArrayList<>();
	    	    for(int i=0;i<3;i++){
	    	    	fristpro.add(proList.get(i));
	    	    }
	    	    secondImg=prodcatelist.get(1).getCover();
	    	    int tprocId=prodcatelist.get(1).getId();
	    		List<TProduct> tproList= tProductService.getProByCateId(tprocId);
	    		tpro=new ArrayList<>();
	    	    for(int i=0;i<3;i++){
	    	    	tpro.add(tproList.get(i));
	    	    }
	    	    
	    	}else{
	    		log.error("获取数据失败");
	    	}
	    }catch(Exception e){
	    	log.error(e.getMessage(), e);
	    	log.info("商城首页获取产品分类失败");
			e.printStackTrace(); 
	    }
	    try{
	    	List<EventProductBean> eventprodAllList = tEventProductService.getEventProductShouYe();
	    	 for(int i=0;i<9;i++){
	    		 eventprodList.add(eventprodAllList.get(i));
	    	  }
	    	
	    }catch(Exception e){
	    	log.error(e.getMessage(), e);
	    	log.info("商城首页活动产品获取失败");
			e.printStackTrace(); 
	    }
	    try{
	    	List<TProductBrand> BrandList=tProductBrandService.prodBrandList();
	    	 for(int i=0;i<5;i++){
	    		 prodBrandList.add(BrandList.get(i));
	    	    }
	    }catch(Exception e){
	    	log.info("商城首页获取产品品牌失败");
	    	log.error(e.getMessage(), e);
			e.printStackTrace();
	    }
	  
	    mav.addObject("prodcatelist", prodcatelist);
	    //最新添加的第一个分类封面图及它下面展示三个产品
	    mav.addObject("fristImg",fristImg);
	    mav.addObject("fristpro",fristpro);
	    //最新添加的第二个分类封面图及它下面展示三个产品
	    mav.addObject("secondImg",secondImg);
	    mav.addObject("tpro",tpro);
	    //活动产品显示9个
	    mav.addObject("eventprodList",eventprodList);
	    //所有品牌列表
	    mav.addObject("prodBrandList",prodBrandList);
	    mav.addObject("userId",userId);
		return mav;
	}
}
