package com.yunqi.jhf.service.front;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.domain.TImage;
import com.yunqi.jhf.dao.domain.TProductCate;
import com.yunqi.jhf.dao.persistence.TImageDao;
import com.yunqi.jhf.dao.persistence.TProductCateDao;
import com.yunqi.jhf.dao.util.SqlSelect;

@Service
public class TProductCateService {
	private static final Logger log = Logger.getLogger(TProductCateService.class);
	
	@Resource
	private TProductCateDao tProductCateDao;

	@Resource
	private TImageDao tImgDao;
	
	 /**
     * 获取产品分类列表
     * @param TProductCate
     * @return list
     */
    public List<TProductCate> prodCateList(){
    	TProductCate prodcate = new TProductCate();
    	List<TProductCate> prodCateList = tProductCateDao.list(null, prodcate);
        return prodCateList;
    }
    
    /**
     * 根据分类名称  获取产品分类图片列表
     * @return TImage
     */
    public TImage prodCateImage(String title){
    	JsonResult result = new JsonResult();
    	SqlSelect sqlSelect = new SqlSelect();
    	TImage prodCateImage = new TImage();
    	try {
	    	if (title!=null) {
	    		prodCateImage.setTitle(title);
	        	sqlSelect.where(TImage.SQL_title);
			} else {
				result.error("获取title失败");
				log.error("获取title失败");
			}
	    	prodCateImage= tImgDao.load(sqlSelect, prodCateImage);
    	} catch (Exception e) {
			result.error("获取产品分类图片列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return prodCateImage;
    }
    
    /**
     * 获取产品分类图片列表，按时间先后排序，然后选前三个在首页展示
     * @return List
     */
    public List<TProductCate> prodCateImageByNew(){
    	TProductCate prodcate = new TProductCate();
    	SqlSelect sql = new SqlSelect();
    	sql.orderBy("order by create_time desc");
    	List<TProductCate> procatelist= tProductCateDao.list(sql, prodcate);
 
        return procatelist;
    }
    
    
}
