package com.yunqi.jhf.service.background;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ParamCheckUtil;
import com.yunqi.jhf.dao.domain.TImgCate;
import com.yunqi.jhf.dao.persistence.TImgCateDao;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.ImageCateBean;


@Service
public class ImageCateService {
	
	protected static Logger log = Logger.getLogger(ImageCateService.class);
	
	@Autowired
	private TImgCateDao tImgCateDao;
	
	public JsonResult getPageList() {
		log.info("进入获取图片分类列表接口");
		JsonResult result = new JsonResult();
		List<TImgCate> tic = tImgCateDao.list(null, null);
		if(tic != null && tic.size() > 0) {
			log.info("获取图片分类列表接口执行成功");
			result.success("获取成功");
			result.setData(treeImageCate(tic, 0));
		}else {
			log.info("获取图片分类列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}
	
	public JsonResult create(TImgCate imgCate) {
		log.info("进入新增图片分类信息接口");
		JsonResult result = new JsonResult();
		if(ParamCheckUtil.checkParamIsNull(imgCate.getTitle())) {
			int cateId = tImgCateDao.insert(imgCate);
			if(cateId > 0) {
				log.info("新增图片分类信息接口执行成功");
				result.success("新增成功");
				result.setData(tImgCateDao.loadById(cateId));
			}else {
				log.info("新增图片分类信息接口执行失败");
				result.error("新增失败");
			}
		}else {
			log.info("图片分类名称不可为空");
			result.error("图片名称为空");
		}
		
		return result;
	}
	
	public JsonResult update(TImgCate imgCate) {
		log.info("进入修改图片分类信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate update = new SqlUpdate().addColumn("update_time = NOW()");
		if(imgCate.getId() == 0) {
			log.info("传入图片分类Id为空");
			return result.error("传入图片分类Id不可为空");
		}else {
			update.where(TImgCate.SQL_id );
		}
		if(imgCate.getTitle() != null) {
			update.addColumn(TImgCate.SQL_title);
		}
		update.addColumn(TImgCate.SQL_parentId);
		int res = tImgCateDao.update(update, imgCate);
		if(res > 0) {
			log.info("修改图片分类信息接口执行成功");
			result.success("修改成功");
			result.setData(tImgCateDao.loadById(imgCate.getId()));
		}else {
			log.info("修改图片分类信息接口执行失败");
			result.success("修改失败");
		}
		return result;
	}

	private List<ImageCateBean> treeImageCate(List<TImgCate> tic, int fid){
		List<ImageCateBean> icb = new ArrayList<>();
		for(TImgCate t : tic) {
			if(t.getParentId() == fid) {
				ImageCateBean cate = new ImageCateBean();
				cate.setId(t.getId());
				cate.setCreateTime(t.getCreateTime());
				cate.setTitle(t.getTitle());
				cate.setUpdateTime(t.getUpdateTime());
				cate.setChildCate(treeImageCate(tic, t.getId()));
				icb.add(cate);
			}
		}
		return icb;
	}
	
}
