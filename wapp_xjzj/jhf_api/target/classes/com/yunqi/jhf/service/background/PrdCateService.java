package com.yunqi.jhf.service.background;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.json.SuccessResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TProductCate;
import com.yunqi.jhf.dao.persistence.TProductCateDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;

@Service
public class PrdCateService {
	
	protected static Logger log = Logger.getLogger(PrdCateService.class);
	
	@Autowired
	private TProductCateDao tProductCateDao;
	
	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入后台获取产品分类列表接口");
		JsonResult result = new JsonResult();
		PageList<TProductCate> pagelist = new PageList<>();
		SqlSelect sql = new SqlSelect().where("1 = 1").orderBy("order by create_time DESC");
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
		}
		if(jsonInfo.get(TProductCate.PROP_title) != null) {
			jsonInfo.put(TProductCate.PROP_title, "%" + jsonInfo.get(TProductCate.PROP_title) +"%");
			sql.and(TProductCate.FLD_title + " like :title");
		}
		pagelist = tProductCateDao.pageListByMap(pagelist, true, sql, jsonInfo);
		if(pagelist != null) {
			log.info("后台获取商品分类列表接口执行成功");
			result.success("获取成功");
			result.setData(pagelist);
		}else {
			log.info("后台获取商品分类列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}
	
	public JsonResult getList() {
		return new SuccessResult().setData(tProductCateDao.list(null, null));
	}
	
	public JsonResult create(TProductCate productcate) {
		log.info("进入后台新增获取产品分类接口");
		JsonResult result = new JsonResult();
		if(productcate.getTitle() == null || "".equals(productcate.getTitle().trim())) {
			log.info("商品分类名称为空");
			return result.error("商品分类名称不可为空");
		}
		if(productcate.getCover() == null) {
			log.info("商品分类封面图片为空");
			return result.error("请选择商品分类封面图片");
		}
		int cateId = tProductCateDao.insert(productcate);
		if(cateId > 0) {
			log.info("后台新增产品分类接口执行成功");
			result.success("新增成功");
			result.setData(tProductCateDao.loadById(cateId));
		}else {
			log.info("后台新增产品分类接口执行失败");
			result.error("新增失败");
		}
		return result;
	}
	
	public JsonResult update(TProductCate productcate) {
		log.info("进入后台修改获取产品分类接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(productcate.getId() == 0) {
			log.info("传入产品分类Id为空");
			return result.error("传入产品分类Id不可为空");
		}else {
			sql.where(TProductCate.SQL_id );
		}
		if(productcate.getTitle() != null) {
			sql.addColumn(TProductCate.SQL_title);
		}
		if(productcate.getCover() != null) {
			sql.addColumn(TProductCate.SQL_cover);
		}
		int res = tProductCateDao.update(sql, productcate);
		if(res > 0) {
			log.info("后台修改产品分类接口执行成功");
			result.success("修改成功");
			result.setData(tProductCateDao.loadById(productcate.getId()));
		}else {
			log.info("后台修改产品分类接口执行失败");
			result.error("修改失败");
		}
		return result;
	}
}
