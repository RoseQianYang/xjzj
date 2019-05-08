package com.yunqi.jhf.service.background;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.json.SuccessResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TImgCate;
import com.yunqi.jhf.dao.domain.TProductBrand;
import com.yunqi.jhf.dao.persistence.TProductBrandDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;

@Service
public class PrdBrandService {
	
	protected static Logger log = Logger.getLogger(PrdBrandService.class);

	@Autowired
	private TProductBrandDao tProductBrandDao;
	
	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入后台获取商品品牌列表接口");
		JsonResult result = new JsonResult();
		PageList<TProductBrand> pagelist = new PageList<>();
		SqlSelect sql = new SqlSelect().where("1 = 1").orderBy("order by create_time DESC");
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
		}
		if(jsonInfo.get(TProductBrand.PROP_title) != null) {
			jsonInfo.put(TProductBrand.PROP_title, "%" + jsonInfo.get(TProductBrand.PROP_title) +"%");
			sql.and(TProductBrand.FLD_title + " like :title");
		}
		pagelist = tProductBrandDao.pageListByMap(pagelist, true, sql, jsonInfo);
		if(pagelist != null) {
			log.info("后台获取商品品牌列表接口执行成功");
			result.success("获取成功");
			result.setData(pagelist);
		}else {
			log.info("后台获取商品品牌列表接口执行失败");
			result.error("获取失败");
		}
		return result;
	}
	
	public JsonResult getList() {
		return new SuccessResult().setData(tProductBrandDao.list(null, null));
	}
	
	public JsonResult create(TProductBrand productBrand) {
		log.info("进入后台新增获取产品品牌接口");
		JsonResult result = new JsonResult();
		if(productBrand.getTitle() == null || "".equals(productBrand.getTitle().trim())) {
			log.info("商品品牌名称为空");
			return result.error("商品品牌名称不可为空");
		}
		if(productBrand.getCover() == null) {
			log.info("商品品牌封面图片为空");
			return result.error("请选择商品品牌封面图片");
		}
		int cateId = tProductBrandDao.insert(productBrand);
		if(cateId > 0) {
			log.info("后台新增产品品牌接口执行成功");
			result.success("新增成功");
			result.setData(tProductBrandDao.loadById(cateId));
		}else {
			log.info("后台新增产品品牌接口执行失败");
			result.error("新增失败");
		}
		return result;
	}
	
	public JsonResult update(TProductBrand productBrand) {
		log.info("进入后台修改获取产品品牌接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(productBrand.getId() == 0) {
			log.info("传入产品品牌Id为空");
			return result.error("传入产品品牌Id不可为空");
		}else {
			sql.where(TImgCate.SQL_id );
		}
		if(productBrand.getTitle() != null) {
			sql.addColumn(TProductBrand.SQL_title);
		}
		if(productBrand.getCover() != null) {
			sql.addColumn(TProductBrand.SQL_cover);
		}
		int res = tProductBrandDao.update(sql, productBrand);
		if(res > 0) {
			log.info("后台修改产品品牌接口执行成功");
			result.success("修改成功");
			result.setData(tProductBrandDao.loadById(productBrand.getId()));
		}else {
			log.info("后台修改产品品牌接口执行失败");
			result.error("修改失败");
		}
		return result;
	}
}
