package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.dao.domain.TImage;
import com.yunqi.jhf.vo.ImageInfoBean;

@MapperScan
@Repository("imageDao")
public interface ImageDao {
	
	List<ImageInfoBean> getImageList(ModelMap jsonInfo);
	
	int getImageCount(ModelMap jsonInfo);
	
	int addImageList(List<TImage> list);

}
