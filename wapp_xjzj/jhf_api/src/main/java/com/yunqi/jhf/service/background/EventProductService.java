package com.yunqi.jhf.service.background;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.EventProductDao;
import com.yunqi.jhf.dao.domain.TEventProduct;
import com.yunqi.jhf.dao.persistence.TEventProductDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.EventProductBean;

@Service
public class EventProductService {

	protected static Logger log = Logger.getLogger(EventProductService.class);
	
	@Autowired
	private EventProductDao eventProductDao;
	
	@Autowired
	private TEventProductDao tEventProductDao;
	
	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入后台获取活动商品列表接口");
		JsonResult result = new JsonResult();
		PageList<EventProductBean> pagelist = new PageList<>();
		PageList<EventProductBean> page = new PageList<>();
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
		}
		if(jsonInfo.get("prdPage") != null) {
			page.setCurrentPage((int) jsonInfo.get("prdPage"));
			jsonInfo.put("prdPage", page.getFromPos());
		}
		List<EventProductBean> list = eventProductDao.getEventProductList(jsonInfo);
		List<EventProductBean> not = eventProductDao.getProductListNotEventId(jsonInfo);
		if(list != null && not != null) {
			pagelist.setList(list);
			pagelist.setTotalSize(eventProductDao.getEventProductCount(jsonInfo));
			page.setList(not);
			page.setTotalSize(eventProductDao.getProductCountNotEventId(jsonInfo));
			ModelMap map = new ModelMap();
			map.put("list", pagelist);
			map.put("not", page);
			result.setData(map);
			log.info("获取活动商品列表接口执行成功");
			result.success("获取成功");
		}else {
			log.info("获取活动商品列表接口执行失败");
			result.error("获取失败");
		}
		return result;
		
	}
	
	public JsonResult create(TEventProduct eventProduct) {
		log.info("进入后台新增活动商品信息接口");
		JsonResult result = new JsonResult();
		if(eventProduct.getEventId() == 0) {
			log.info("活动ID为空");
			return result.error("活动ID不可为空");
		}
		if(eventProduct.getProductId() == 0) {
			log.info("所参与活动的商品为空");
			return result.error("所参与活动的商品不可为空");
		}
		if(eventProduct.getEventPrice() == 0) {
			log.info("活动商品价格为空");
			return result.error("活动商品价格不可为空");
		}
		int id = tEventProductDao.insert(eventProduct);
		if(id > 0) {
			log.info("后台新增活动商品信息接口执行成功");
			result.success("新增成功");
			result.setData(eventProductDao.getEventProductById(id));
		}else {
			log.info("后台新增活动商品信息接口执行失败");
			result.error("新增失败");
			
		}
		return result;
		
	}
	
	public JsonResult update(TEventProduct eventProduct) {
		log.info("进入后台修改活动商品信息接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(eventProduct.getId() == 0) {
			log.info("传入的活动商品Id为空");
			return result.error("传入的活动商品Id不可为空");
		}else {
			sql.where(TEventProduct.SQL_id);
		}
		if(eventProduct.getEventPrice() == 0) {
			log.info("活动商品价格为空");
			return result.error("活动商品价格不可为空");
		}else {
			sql.addColumn(TEventProduct.SQL_eventPrice);
		}
		int res = tEventProductDao.update(sql, eventProduct);
		if(res > 0) {
			log.info("后台修改活动商品信息执行成功");
			result.success("修改成功");
			result.setData(eventProductDao.getEventProductById(eventProduct.getId()));
		}else {
			log.info("后台修改活动商品信息执行失败");
			result.error("修改失败");
		}
		return result;
		
	}
	
	public JsonResult delete(int id) {
		log.info("进入后台删除活动商品信息接口");
		JsonResult result = new JsonResult();
		int res = tEventProductDao.deleteById(id);
		if(res > 0) {
			log.info("后台删除活动商品信息执行成功");
			result.success("删除成功");
		}else {
			log.info("后台删除活动商品信息执行失败");
			result.error("删除失败");
		}
		return result;
	}
}
