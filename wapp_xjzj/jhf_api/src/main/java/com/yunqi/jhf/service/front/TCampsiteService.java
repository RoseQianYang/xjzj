package com.yunqi.jhf.service.front;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.domain.TCampsite;
import com.yunqi.jhf.dao.persistence.TCampsiteDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.web.Distance;

@Service
public class TCampsiteService {

	private static final Logger log = Logger.getLogger(TCampsiteService.class);

	@Resource
	private TCampsiteDao tCampsiteDao;

	/**
	 * 获取营地列表
	 * 
	 * @param currentPage
	 *            cateId 当前页 营地分类id(1 自由营地 2 合作营地)
	 * @return pageList 营地分页信息列表
	 */
	/*
	 * public PageList<TCampsite> getCampsiteList(Integer currentPage,int
	 * cateId,String address) { TCampsite campsite = new TCampsite();
	 * PageList<TCampsite> pageList = new PageList<>();
	 * pageList.setCurrentPage(currentPage);
	 * pageList.setPageSize(Const.FONT_PAGE_SIZE); SqlSelect sql = new SqlSelect();
	 * try { if (cateId != -1) { campsite.setCategoryId(cateId);
	 * sql.where(TCampsite.SQL_categoryId); pageList =
	 * tCampsiteDao.pageList(pageList, true, sql, campsite); } else if
	 * (!StringUtils.isEmpty(address)) {
	 * 
	 * int i = address.indexOf("-"); String str2 =
	 * String.valueOf(address.charAt(i)); address = address.replaceFirst(str2,"省");
	 * 
	 * sql.where("(" + TCampsite.FLD_address + " like :address)");
	 * campsite.setAddress("%" + address + "%"); pageList =
	 * tCampsiteDao.pageList(pageList, true, sql, campsite); } else { pageList =
	 * tCampsiteDao.pageList(pageList, true, null, campsite); } } catch (Exception
	 * e) { log.error("获取营地列表失败"); log.error(e.getMessage(), e);
	 * e.printStackTrace(); } return pageList; }
	 */
	public PageList<TCampsite> getCampsiteList(int currentPage, double x1, double y1, int cateId, String address) {
		TCampsite campsite = new TCampsite();
		PageList<TCampsite> pageList = new PageList<>();
		campsite.setIsDelete(ConstantTool.ISDELETE_YES);
		pageList.setCurrentPage(currentPage);
		pageList.setPageSize(Const.FONT_PAGE_SIZE);
		SqlSelect sql = new SqlSelect().where(TCampsite.SQL_isDelete);
		try {
			List<TCampsite> campsiteList = tCampsiteDao.list(sql, campsite);
			for (TCampsite tcampsite : campsiteList) {
				BigDecimal x = tcampsite.getLatitude();
				BigDecimal y = tcampsite.getLongitude();
				Float d = Distance.jingWeiDuDistance(x, y, x1, y1);
				tcampsite.setDistance(d);
				tcampsite.setId(tcampsite.getId());
				SqlUpdate sqlUpdate = new SqlUpdate().addColumn(TCampsite.SQL_distance).where(TCampsite.SQL_id);
				tCampsiteDao.update(sqlUpdate, tcampsite);
			}
			if (cateId != -1) {
				campsite.setCategoryId(cateId);
				sql.and(TCampsite.SQL_categoryId);
				pageList = tCampsiteDao.pageList(pageList, true, sql, campsite);
			} else if (!StringUtils.isEmpty(address)) {
				// 根据所选市区进行模糊查询
				address = address.substring(address.indexOf("-") + 1);
				sql.and("(" + TCampsite.FLD_address + " like :address)");
				campsite.setAddress("%" + address + "%");
				pageList = tCampsiteDao.pageList(pageList, true, sql, campsite);
			} else {
				pageList = tCampsiteDao.pageList(pageList, true, sql, campsite);
			}
		} catch (Exception e) {
			log.error("获取营地列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;
	}

	/**
	 * 异步获取 不分页 列表 
	 *    根据 地理位置 获取所有营地列表 、 根据 传入参数cateid 分类id 或 参数 address 地址 查询 获取所有营地列表
	 * 
	 * @param x1
	 * @param y1
	 * @return json
	 */
	public JsonResult getCampsiteAllListsByCateIdOrAddress(Double x1, Double y1,int cateId, String address) {
			JsonResult result = new JsonResult();
		    TCampsite campsite = new TCampsite();
			List<TCampsite> CampsiteList = new ArrayList<>();
			campsite.setIsDelete(ConstantTool.ISDELETE_YES);
			SqlSelect sql = new SqlSelect().where(TCampsite.SQL_isDelete);
			try {
				if(x1==null||y1==null){
					result.setData("未获取到用户位置");
					log.info("未获取到用户位置");
				}else {
					List<TCampsite> campsiteList = tCampsiteDao.list(sql, campsite);
					if (campsiteList !=null) {
						for (TCampsite tcampsite : campsiteList) {
							BigDecimal x = tcampsite.getLatitude();
							BigDecimal y = tcampsite.getLongitude();
							Float d = Distance.jingWeiDuDistance(x, y, x1, y1);
							tcampsite.setDistance(d);
							tcampsite.setId(tcampsite.getId());
							SqlUpdate sqlUpdate = new SqlUpdate().addColumn(TCampsite.SQL_distance).where(TCampsite.SQL_id);
							tCampsiteDao.update(sqlUpdate, tcampsite);
						}
						CampsiteList = tCampsiteDao.list(sql, campsite);
						result.setData(CampsiteList);
					}
					if (cateId != 0) {
						campsite.setCategoryId(cateId);
						sql.and(TCampsite.SQL_categoryId);
						CampsiteList = tCampsiteDao.list(sql, campsite);
						if (CampsiteList !=null)  {
							result.success("微信端 异步 根据 营地分类 获取营地列表成功");
							result.setData(CampsiteList);
							log.info("微信端 异步 根据 营地分类 获取营地列表成功");
						} else {
							log.info("微信端 异步 根据 营地分类 未获取营地列表");
							result.setData(CampsiteList);
							return result.error("微信端 异步 根据 营地分类 未获取营地列表");
						}
					} 
					if (!StringUtils.isEmpty(address)) {
						// 根据所选市区进行模糊查询
						address = address.substring(address.indexOf("-") + 1);
						campsite.setAddress("%" + address + "%");
						sql.and("(" + TCampsite.FLD_address + " like :address)");
						CampsiteList = tCampsiteDao.list(sql, campsite);
						if (campsiteList != null)  {
							result.success("微信端 异步 根据 营地地址 获取营地列表成功");
							result.setData(CampsiteList);
							log.info("微信端 异步 根据 营地地址 获取营地列表成功");
						} else {
							log.info("微信端 异步 根据 营地地址 未获取营地列表");
							result.setData(CampsiteList);
							return result.error("微信端 异步 根据 营地地址 未获取营地列表");
						}
					}
				}
			} catch (Exception e) {
				log.error("获取营地列表失败");
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
			return result;
		}
	
	/**
	 *  异步获取 分页 列表 
	 *    根据 地理位置 获取所有营地列表 、 根据 传入参数cateid 分类id 或 参数 address 地址 查询 获取所有营地列表
	 * @param jsonInfo
	 * @return PageList
	 */
	public JsonResult findAllCampsite(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<TCampsite> pageList = new PageList<>();
		TCampsite tCampsite = new TCampsite();
		SqlSelect sql = new SqlSelect();
		if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
			pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
		}
		pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);       
		if(jsonInfo.get(Const.LATITUDE)== null || jsonInfo.get(Const.LONGITUDE) == null ){
			float[] dataArray;// 定义
			dataArray = new float[0];// 空数组赋值 // 返回空数组
			result.setData(dataArray);
			log.info("未获取到用户位置");
		}else {
			try {
				double latitude = (double) jsonInfo.get(Const.LATITUDE);
				double longitude = (double) jsonInfo.get(Const.LONGITUDE);
				jsonInfo.put(TCampsite.PROP_isDelete, ConstantTool.ISDELETE_YES);
				tCampsite.setIsDelete(ConstantTool.ISDELETE_YES);
				sql.where(TCampsite.SQL_isDelete).orderBy("order by create_time desc");
				List<TCampsite> campsiteList = tCampsiteDao.list(sql, tCampsite);
				if (campsiteList != null) {
					for (TCampsite tcampsite : campsiteList) {
						BigDecimal x = tcampsite.getLatitude();
						BigDecimal y = tcampsite.getLongitude();
						Float d = Distance.jingWeiDuDistance(x, y, latitude, longitude);
						tcampsite.setDistance(d);
						tcampsite.setId(tcampsite.getId());
						SqlUpdate sqlUpdate = new SqlUpdate().addColumn(TCampsite.SQL_distance).where(TCampsite.SQL_id);
						tCampsiteDao.update(sqlUpdate, tcampsite);
					}
				}
				if (jsonInfo.get(TCampsite.PROP_categoryId)!= null && (int)jsonInfo.get(TCampsite.PROP_categoryId) != 0) {
					sql.and(TCampsite.SQL_categoryId).orderBy("order by create_time desc");
					pageList = tCampsiteDao.pageListByMap(pageList, true, sql, jsonInfo);
					if (pageList.getList().size() != 0) {
						result.success("微信端 异步 根据 营地分类 获取营地列表成功");
						result.setData(pageList);
						log.info("微信端 异步 根据 营地分类 获取营地列表成功");
					} else {
						log.info("微信端 异步 根据 营地分类 未获取营地列表");
						result.setData(pageList);
						return result.error("微信端 异步 根据 营地分类 未获取营地列表");
					}
				}else if (jsonInfo.get(TCampsite.PROP_address)!= null && (String)jsonInfo.get(TCampsite.PROP_address) != "0") {
					// 根据所选市区进行模糊查询
					String address =(String)jsonInfo.get(TCampsite.PROP_address);
					address = address.substring(address.indexOf("-") + 1);
					sql.where("1 = 1").and("(" + TCampsite.FLD_address + " like :address)");
					jsonInfo.put(TCampsite.PROP_address, "%" + jsonInfo.get(TCampsite.PROP_address) + "%");
					pageList = tCampsiteDao.pageListByMap(pageList, true, sql, jsonInfo);
					if (pageList.getList().size() != 0) {
						result.success("微信端 异步 根据 营地地址 获取营地列表成功");
						result.setData(pageList);
						log.info("微信端 异步 根据 营地地址 获取营地列表成功");
					} else {
						log.info("微信端 异步 根据 营地地址 未获取营地列表");
						result.setData(pageList);
						return result.error("微信端 异步 根据 营地地址 未获取营地列表");
					}
				}else {
				  pageList = tCampsiteDao.pageListByMap(pageList, true, sql, jsonInfo);
				}
			
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取所有营地信息信息，进行距离排序
	 * 
	 * @author Seek
	 * @param currentPage
	 *            当前页码 默认是1
	 * @return pageList 分页的售后网点信息
	 * 
	 */
	public PageList<TCampsite> findNear(Integer currentPage) {
		TCampsite tCampsite = new TCampsite();
		tCampsite.setIsDelete(ConstantTool.ISDELETE_YES);
		SqlSelect sqlSelect = new SqlSelect().where(TCampsite.SQL_isDelete).orderBy(" order by distance asc");
		PageList<TCampsite> pageList = new PageList<>();
		pageList.setCurrentPage(currentPage);
		pageList.setPageSize(Const.FONT_PAGE_SIZE);
		try {
			pageList = tCampsiteDao.pageList(pageList, true, sqlSelect, tCampsite);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;

	}

	/**
	 * 异步获取分页列表   所有营地信息信息，进行距离排序
	 * 
	 * @return json
	 */
	public JsonResult findNearByAll(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<TCampsite> campsiteList = new PageList<>();
		SqlSelect sqlSelect = new SqlSelect();
		if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
			campsiteList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
		}
		campsiteList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
		try {
			jsonInfo.put(TCampsite.PROP_isDelete, ConstantTool.ISDELETE_YES);
			sqlSelect.where(TCampsite.SQL_isDelete).orderBy(" order by distance asc");
			campsiteList = tCampsiteDao.pageListByMap(campsiteList, true, sqlSelect, jsonInfo);
			if (campsiteList != null) {
				result.setData(campsiteList);
				result.success("异步获取  所有营地信息成功");
				log.info("微信端 异步获取  所有营地信息成功");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 异步获取不分页列表   所有营地信息信息，进行距离排序
	 * @return
	 */
	public JsonResult findNearListByAll() {
		JsonResult result = new JsonResult();
		List<TCampsite> campsiteList  = new ArrayList<>();
		TCampsite tCampsite = new TCampsite();
		tCampsite.setIsDelete(ConstantTool.ISDELETE_YES);
		try {
			SqlSelect sqlSelect = new SqlSelect().where(TCampsite.SQL_isDelete).orderBy(" order by distance asc");
			campsiteList = tCampsiteDao.list(sqlSelect, tCampsite);
			if (campsiteList != null) {
				result.setData(campsiteList);
				result.success("异步获取  所有营地信息成功");
				log.info("微信端 异步获取  所有营地信息成功");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	

	/**
	 * 通过campsiteId获取营地详情
	 * 
	 * @param campsiteId
	 *            营地id
	 * @return TCampsite
	 */
	public TCampsite getCampsiteByCampid(int campsiteId) {
		TCampsite campsite = new TCampsite();
		try {
			if (campsiteId != -1) {
				campsite = tCampsiteDao.loadById(campsiteId);
			} else {
				log.error("获取营地id失败");
			}
		} catch (Exception e) {
			log.error("获取营地失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return campsite;
	}
}
