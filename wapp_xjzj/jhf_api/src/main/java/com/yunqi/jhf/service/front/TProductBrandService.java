package com.yunqi.jhf.service.front;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.domain.TProductBrand;
import com.yunqi.jhf.dao.persistence.TProductBrandDao;
import com.yunqi.jhf.dao.util.PageList;

@Service
public class TProductBrandService {

	private static final Logger log = Logger.getLogger(TOrderService.class);
	@Resource
	private TProductBrandDao tProductBrandDao;
	
	 /**
     * 获取产品品牌列表
     * @param TProductCate
     * @return list
     */
    public List<TProductBrand> prodBrandList(){
    	TProductBrand prodbrand = new TProductBrand();
    	List<TProductBrand> prodBrandList = tProductBrandDao.list(null, prodbrand);
        return prodBrandList;
    }
    
    /**
	 * 获取所有品牌
     * @param currentPage 当前页
     * @return 分页产品
	 */
	public PageList<TProductBrand> findProductBrand(Integer currentPage) throws SQLException {
		TProductBrand tProductBrand=new TProductBrand();
        PageList<TProductBrand> pageList = new PageList<>();
        pageList.setCurrentPage(currentPage);
		pageList.setPageSize(Const.FONT_PAGE_SIZE);
		pageList = tProductBrandDao.pageList(pageList, true, null, tProductBrand);
        return pageList;
    }
	
	   /**
		 * 获取所有品牌
	     * @param 
	     * @return 全部品牌不进行分页显示
		 */
		public List<TProductBrand> findProductBrandAll()  {
			TProductBrand tProductBrand=new TProductBrand();
	        List<TProductBrand> ProductBrandList = new ArrayList<>();
	        ProductBrandList = tProductBrandDao.list(null, tProductBrand);
	        if(ProductBrandList!=null){
	        	 return ProductBrandList;
	        }else{
	        	log.info("获取品牌列表为空");
	        	 return ProductBrandList;
	        }
	        
	    }
}
