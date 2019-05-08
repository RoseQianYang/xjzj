package com.yunqi.jhf.service.front;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.EventProductDao;
import com.yunqi.jhf.dao.ProductAttributeDao;
import com.yunqi.jhf.dao.domain.TEvent;
import com.yunqi.jhf.dao.domain.TEventProduct;
import com.yunqi.jhf.dao.domain.TProduct;
import com.yunqi.jhf.dao.domain.TProductAttribute;
import com.yunqi.jhf.dao.persistence.TEventDao;
import com.yunqi.jhf.dao.persistence.TEventProductDao;
import com.yunqi.jhf.dao.persistence.TProductAttributeDao;
import com.yunqi.jhf.dao.persistence.TProductCateDao;
import com.yunqi.jhf.dao.persistence.TProductDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.vo.EventProductBean;

@Service
public class TProductService {

	private static final Logger log = Logger.getLogger(TProductService.class);

	@Resource
	private TProductDao tProductdao;
	@Resource
	private TProductCateDao tProductCateDao;
	@Resource
	private TEventProductDao tEventProductDao;
	@Resource
	private TEventDao tEventDao;
	@Resource
	private EventProductDao eventProductDao;
	@Resource
	private TProductAttributeDao tProductAttributeDao;
	@Resource
	private ProductAttributeDao productAttributeDao;
	/**
	 * 获取所有产品
     * @param currentPage 当前页
     * @return 分页产品
	 */
	public PageList<TProduct> findproductbypagenum(Integer currentPage) throws SQLException {
        //查询所有产品的list
    	TProduct tProduct=new TProduct();
        PageList<TProduct> pageList = new PageList<>();
        pageList.setCurrentPage(currentPage);
		pageList.setPageSize(Const.FONT_PAGE_SIZE);
		pageList = tProductdao.pageList(pageList, true, null, tProduct);
        return pageList;
    }
	/**
	 * 根据传入参数来进行查询产品列表
	 * @param   page     当前页
	 * @param   cateId   产品分类id
	 * @param   productName   微信名称
	 * @param   trueorfalse  1(true升序排列) or 0(false降序排列)
	 * @return  PageList
	 */
	public PageList<TProduct> productPageList(int page,int cateId, String productName, int trueorfalse, int brandId) throws Exception {
		PageList<TProduct> pageList = new PageList<TProduct>();
		TProduct cond = new TProduct();
		SqlSelect sql = new SqlSelect().where(TProduct.SQL_putaway);
		cond.setPutaway("Y");
		if (page!=-1) {
			pageList.setCurrentPage(page);
			pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			if (brandId != -1) {
				cond.setBrandId(brandId);
				sql.where(TProduct.SQL_brandId);
				if (trueorfalse==1) {
					sql.orderBy(" order by price asc");
				} else if (trueorfalse==0) {
					sql.orderBy(" order by price desc");
				}
				pageList = tProductdao.pageList(pageList, true, sql, cond);
			} else if (cateId != -1) {
				cond.setCateId(cateId);
				sql.where(TProduct.SQL_cateId);
				if (trueorfalse==1) {
					sql.orderBy(" order by price asc");
				} else if (trueorfalse==0) {
					sql.orderBy(" order by price desc");
				}
				pageList = tProductdao.pageList(pageList, true, sql, cond);
			} else if (!StringUtils.isEmpty(productName)) {
				sql.where("(" + TProduct.FLD_title + " like :title)");
				cond.setTitle("%" + productName + "%");
				if (trueorfalse==1) {
					sql.orderBy(" order by price asc");
				} else if (trueorfalse==0) {
					sql.orderBy(" order by price desc");
				}
				pageList = tProductdao.pageList(pageList, true, sql, cond);
			} else {
				pageList = tProductdao.pageList(pageList, true, null, cond);
			}
		}
		return pageList;
	}
	
	
	/**
	 * 异步获取  传入参数cateid  brandId  productName  trueorfalse来进行查询 产品 分页列表
	 * @param req
	 * @param jsonInfo
	 * @return Json
	 */
	public JsonResult getProductPageList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<TProduct> pageList = new PageList<TProduct>();
		TProduct cond = new TProduct();
		SqlSelect sql = new SqlSelect().where(TProduct.SQL_putaway);
		if ((int)jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) >= 2) {
			pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
		} 
		pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
		// 第一步：根据是否有 brandId 查询对于列表
		if (jsonInfo.get("brandId") != null) {
			cond.setBrandId((int)jsonInfo.get("brandId"));
			sql.where(TProduct.SQL_brandId).orderBy("order by create_time desc");
			pageList = tProductdao.pageList(pageList, false, sql, cond);
			if (pageList.getList().size() != 0) {
				result.success("根据 品牌 获取产品列表成功");
				result.setData(pageList);
				log.info("根据 品牌 获取产品列表成功");
			} else {
				log.info("根据 品牌 未获取到产品列表");
				result.setData(pageList);
				return result.error("根据 品牌 未获取到产品列表");
			}// 第二步: 1、根据是否有 cateId 查询对于列表
		}else {
			if (jsonInfo.get("cateId") != null) {
				cond.setCateId((int)jsonInfo.get("cateId"));
				sql.where(TProduct.SQL_cateId);
	            // 2、在根据 trueorfalse 排序 查询对应列表 和 全部列表
				if (jsonInfo.get("trueorfalse") != null) {
					if ((int)jsonInfo.get("trueorfalse") == 1) {
						sql.orderBy(" order by price asc");
					} else if ((int)jsonInfo.get("trueorfalse") == 0) {
						sql.orderBy(" order by price desc");
					}
					pageList = tProductdao.pageList(pageList, true, sql, cond);
				} else {
					pageList = tProductdao.pageList(pageList, true, sql, cond);
				}
				if (pageList.getList().size() != 0) {
					result.success("产品获取成功");
					result.setData(pageList);
					log.info("产品获取成功");
				} else {
					log.info("未获取到产品");
					result.setData(pageList);
					return result.error("未获取到产品");
				} // 第三步：1、根据 productName 搜索查询 列表
			} else if (!StringUtils.isEmpty((String)jsonInfo.get("productName"))) {
				sql.where("(" + TProduct.FLD_title + " like :title)");
				cond.setTitle("%" + (String)jsonInfo.get("productName") + "%");
				// 2、根据  trueorfalse 排序查询对于列表 和 全部列表
				if (jsonInfo.get("trueorfalse") != null) {
					if ((int)jsonInfo.get("trueorfalse") == 1) {
						sql.orderBy(" order by price asc");
					} else if ((int)jsonInfo.get("trueorfalse") == 0) {
						sql.orderBy(" order by price desc");
					}
					pageList = tProductdao.pageList(pageList, true, sql, cond);
				} else {
					pageList = tProductdao.pageList(pageList, true, sql, cond);
				}
				if (pageList.getList().size() != 0) {
					result.success("产品获取成功");
					result.setData(pageList);
					log.info("产品获取成功");
				} else {
					log.info("未获取到产品");
					result.setData(pageList);
					return result.error("未获取到产品");
				}
			} else {
				// 所有的产品列表 
				pageList = tProductdao.pageList(pageList, true, null, cond);
				if (pageList.getList().size() != 0) {
					result.success("产品获取成功");
					result.setData(pageList);
					log.info("产品获取成功");
				} else {
					log.info("未获取到产品");
					result.setData(pageList);
					return result.error("未获取到产品");
				}
			}
		}	
		return result;
	}
	
	
	/**
	 * @author wangsong
	 * 
	 * 异步获取产品列表  
	 *              根据 传入参数cateid 分类id   brandId 产品品牌id   productName 产品名称  
	 *              trueorfalse 传的1(true升序排列) or 0(false降序排列)
	 *              来进行查询 产品 分页列表
	 *              
	 * @param jsonInfo
	 * @return json
	 */
	public JsonResult getProductPageLists(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<TProduct> pageList = new PageList<>();
		SqlSelect sql = new SqlSelect().where(TProduct.SQL_putaway);
		if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
			pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
		}
		pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
		// 第一步：根据是否有 brandId 查询对于列表
		if (jsonInfo.get(TProduct.PROP_brandId)!= null) {
			sql.where(TProduct.SQL_brandId).orderBy("order by create_time desc");
			pageList = tProductdao.pageListByMap(pageList, true, sql, jsonInfo);
			if (pageList != null) {
				result.success("根据 品牌 获取产品列表成功");
				result.setData(pageList);
				log.info("根据 品牌 获取产品列表成功");
			} else {
				log.info("根据 品牌 未获取到产品列表");
				result.setData(pageList);
				return result.error("根据 品牌 未获取到产品列表");
			}// 第二步: 1、根据是否有 cateId 查询对于列表
		}else if (jsonInfo.get(TProduct.PROP_cateId)!= null) {
				sql.where(TProduct.SQL_cateId);
	            // 2、在根据 trueorfalse 排序 查询对应列表 和 全部列表
				if(jsonInfo.get(Const.TRUEORFALSE) != null) {
					// Const.IS_TRUE; //默认为 1 升序
					if ((int) jsonInfo.get(Const.TRUEORFALSE) == Const.IS_TRUE ) {
						sql.orderBy(" order by price asc");
					}else if ((int) jsonInfo.get(Const.TRUEORFALSE) == Const.IS_FALSE) {
						sql.orderBy(" order by price desc");
					}
					pageList = tProductdao.pageListByMap(pageList, true, sql, jsonInfo);
				}else {
					pageList = tProductdao.pageListByMap(pageList, true, sql, jsonInfo);
				}
				if (pageList.getList().size() != 0) {
					result.success("根据 产品分类 获取产品列表成功");
					result.setData(pageList);
					log.info("根据 产品分类 获取产品列表成功");
				} else {
					log.info("根据 产品分类 未获取产品列表");
					result.setData(pageList);
					return result.error("根据 分类 未获取产品列表");
				} 
				// 第三步：1、根据 productName 搜索查询 列表
		}else if (jsonInfo.get(TProduct.PROP_title)!= null) {
			sql.where("1 = 1").and("(" + TProduct.FLD_title + " like :title)");
			jsonInfo.put(TProduct.PROP_title, "%" + jsonInfo.get(TProduct.PROP_title) + "%");
			// 2、在根据 trueorfalse 排序 查询对应列表 和 全部列表
			if(jsonInfo.get(Const.TRUEORFALSE) != null) {
				// Const.IS_TRUE; //默认为 1 升序
				if ((int) jsonInfo.get(Const.TRUEORFALSE) == Const.IS_TRUE ) {
					sql.orderBy(" order by price asc");
				}else if ((int) jsonInfo.get(Const.TRUEORFALSE) == Const.IS_FALSE) {
					sql.orderBy(" order by price desc");
				}
				pageList = tProductdao.pageListByMap(pageList, true, sql, jsonInfo);
			}else {
				pageList = tProductdao.pageListByMap(pageList, true, sql, jsonInfo);
			}
			if (pageList.getList().size() != 0) {
				result.success("根据 商品名称 获取产品列表成功");
				result.setData(pageList);
				log.info("根据 商品名称 获取产品列表成功");
			} else {
				log.info("根据 商品名称 获取产品列表成功");
				result.setData(pageList);
				return result.error("根据 商品名称  未 获取产品列表");
			}
		}else {
			// 所有的产品列表 
			   sql.where("1=1").orderBy("order by create_time desc");
			   pageList = tProductdao.pageListByMap(pageList, true, sql, jsonInfo);
			   if (pageList.getList().size() != 0) {
				result.success("产品获取成功");
				result.setData(pageList);
				log.info("产品获取成功");
				} else {
				log.info("未获取到产品");
				result.setData(pageList);
				return result.error("未获取到产品");
			}
		}
		return result;
	}

	/**
	 * 根据传入的eventid参数来进行查询产品列表
	 * @param trueorfalse ! 
	 * 
	 * @param  eventid 活动id
	 * 
	 * @return pageList
	 * 
	 */
	public PageList<EventProductBean> getEventProductByEid(int page,int eventId,int trueorfalse) {
		JsonResult result = new JsonResult();
		ModelMap jsonInfo = new ModelMap();
		PageList<EventProductBean> eventproductList = new PageList<EventProductBean>();
		List<EventProductBean> eventprodList = null;
		try {
			if (page != -1) {
				eventproductList.setCurrentPage(page);
				eventproductList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
				jsonInfo.put("page",eventproductList.getFromPos());
				jsonInfo.put("size",Const.TEST_FONT_PAGE_SIZE);	
			}
			if (eventId != -1) {
				jsonInfo.put("eventId", eventId);
			}else {
				log.error("获取活动id失败");
			}
			if (trueorfalse != -1) {
				if (trueorfalse==1) {
					eventprodList = eventProductDao.getEventProdEventIdListAsc(jsonInfo);
				} else if (trueorfalse==0) {
					eventprodList = eventProductDao.getEventProdEventIdListDesc(jsonInfo);
				}
			} else {
				eventprodList = eventProductDao.getEventProdEventIdList(jsonInfo);
			}
			eventproductList.setList(eventprodList);
			eventproductList.setTotalSize(eventProductDao.getEventProductCount(jsonInfo));
		} catch (Exception e) {
			result.error("获取活动产品列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return eventproductList;
    }  
	
	/**
	 * 异步获取  传入的eventid参数来进行查询 产品 分页列表
	 * @param req
	 * @param jsonInfo
	 * @return Json
	 */
	public JsonResult getEventProductPageList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<EventProductBean> pageList = new PageList<EventProductBean>();
		List<EventProductBean>	eventprodList = null;
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			} 
			pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			jsonInfo.put("page", pageList.getFromPos());
			jsonInfo.put("size",Const.TEST_FONT_PAGE_SIZE);	
			if (jsonInfo.get("eventId") != null) {
				jsonInfo.put("eventId", jsonInfo.get("eventId"));
			}else {
				log.error("获取活动id失败");
			}
			if (jsonInfo.get("trueorfalse") != null) {
				if ((int)jsonInfo.get("trueorfalse") == 1) {
					eventprodList = eventProductDao.getEventProdEventIdListAsc(jsonInfo);
					if(eventprodList != null){
						pageList.setList(eventprodList);
						pageList.setTotalSize(eventProductDao.getEventProductCount(jsonInfo));
					    result.setData(pageList);
					    log.info("获取活动升序接口执行成功");
						result.success("获取活动升序成功");
					}else{
						log.info("获取活动升序接口执行失败");
						return result.error("获取活动升序信息失败");
					}
				} else if ((int)jsonInfo.get("trueorfalse") == 0) {
					eventprodList = eventProductDao.getEventProdEventIdListDesc(jsonInfo);
					if(eventprodList != null){
						pageList.setList(eventprodList);
						pageList.setTotalSize(eventProductDao.getEventProductCount(jsonInfo));
					    result.setData(pageList);
					    log.info("获取活动降序接口执行成功");
						result.success("获取活动降序成功");
					}else{
						log.info("获取活动降序接口执行失败");
						return result.error("获取活动降序信息失败");
					}
				}
			} else {
				eventprodList = eventProductDao.getEventProdEventIdList(jsonInfo);
				if(eventprodList != null){
					pageList.setList(eventprodList);
					pageList.setTotalSize(eventProductDao.getEventProductCount(jsonInfo));
				    result.setData(pageList);
				    log.info("获取活动综合排序接口执行成功");
					result.success("获取活动综合排序成功");
				}else{
					log.info("获取活动综合排序接口执行失败");
					return result.error("获取活动综合排序信息失败");
				}
			}
		} catch (Exception e) {
			result.error("获取活动产品列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * @author wangsong
	 * 
	 * 优化 异步获取  传入的eventid参数来进行查询 产品 分页列表
	 * @param jsonInfo
	 * @return Json
	 */
	public JsonResult getEventProductPageLists(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<EventProductBean> pageList = new PageList<EventProductBean>();
		List<EventProductBean> eventprodList = new ArrayList<>();
//		SqlSelect sql = new SqlSelect();
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			} 
			pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			jsonInfo.put("page", pageList.getFromPos());
			jsonInfo.put("size",Const.TEST_FONT_PAGE_SIZE);
			if (jsonInfo.get("eventId") != null){
			jsonInfo.put("eventId", jsonInfo.get("eventId"));
//			sql.where(TEvent.SQL_id).orderBy("order by create_time desc");
			 // 2、在根据 trueorfalse 排序 查询对应列表 和 全部列表
			if((int)jsonInfo.get(Const.TRUEORFALSE) != -1) {
				// Const.IS_TRUE; //默认为 1 升序
				if ((int) jsonInfo.get(Const.TRUEORFALSE) == Const.IS_TRUE ) {
					eventprodList = eventProductDao.getEventProdEventIdListAsc(jsonInfo);
					if(eventprodList != null){
						pageList.setList(eventprodList);
						pageList.setTotalSize(eventProductDao.getEventProductCount(jsonInfo));
					    result.setData(pageList);
					    log.info("微信端 根据 产品活动id 获取活动升序接口执行成功");
						result.success("获取活动升序成功");
					}else{
						log.info("微信端 根据 产品活动id 获取活动升序接口执行失败");
						return result.error("获取活动升序信息失败");
					}
				}else if ((int) jsonInfo.get(Const.TRUEORFALSE) == Const.IS_FALSE) {
					eventprodList = eventProductDao.getEventProdEventIdListDesc(jsonInfo);
					if(eventprodList != null){
						pageList.setList(eventprodList);
						pageList.setTotalSize(eventProductDao.getEventProductCount(jsonInfo));
					    result.setData(pageList);
					    log.info("微信端 根据 产品活动id 获取活动降序接口执行成功");
						result.success("获取活动降序成功");
					}else{
						log.info("微信端 根据 产品活动id 获取活动降序接口执行失败");
						return result.error("获取活动降序信息失败");
					}
				
				}
			}else {
				eventprodList = eventProductDao.getEventProdEventIdList(jsonInfo);
				if(eventprodList != null){
					pageList.setList(eventprodList);
					pageList.setTotalSize(eventProductDao.getEventProductCount(jsonInfo));
				    result.setData(pageList);
				    log.info("微信端 根据 产品活动id 获取活动综合排序接口执行成功");
					result.success("获取活动综合排序成功");
				}else{
					log.info("微信端 根据 产品活动id 获取活动综合排序接口执行失败");
					return result.error("获取活动综合排序信息失败");
				}
			}
			}else {
				log.error("获取活动id失败");
			}
		} catch (Exception e) {
			result.error("微信端 根据 产品活动id 获取活动产品列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 根据传入的brandid参数来进行查询品牌产品列表
	 * @param trueorfalse  
	 * @param  brandid 品牌id
	 * @return pageList
	 */
	public PageList<TProduct> getBrandProductByBid(int page, int brandId, int trueorfalse) {
		JsonResult result = new JsonResult();
		TProduct tProduct = new TProduct();
		PageList<TProduct> pageList = new PageList<TProduct>();
		SqlSelect sql = new SqlSelect().where(TProduct.SQL_brandId).and(TProduct.SQL_putaway);
		try {
			if (page!=-1) {
				pageList.setCurrentPage(page);
				pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
				if (brandId != -1) {
					tProduct.setBrandId(brandId);
					tProduct.setPutaway("Y");
					if (trueorfalse != -1) {
						if (trueorfalse==1) {
							sql.orderBy(" order by price asc");
						} else if (trueorfalse==0) {
							sql.orderBy(" order by price desc");
						}
						pageList = tProductdao.pageList(pageList, true, sql, tProduct);
					}else {
						pageList = tProductdao.pageList(pageList, true, sql, tProduct);
					}
				}
			}
		} catch (Exception e) {
			result.error("获取品牌产品列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return pageList;
    }  
	
	/**
	 * 异步获取  传入的brandid参数来进行查询 产品 分页列表
	 * @param req
	 * @param jsonInfo
	 * @return Json
	 */
	public JsonResult getBrandProductPageList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		TProduct tProduct = new TProduct();
		PageList<TProduct> pageList = new PageList<TProduct>();
		SqlSelect sql = new SqlSelect().where(TProduct.SQL_brandId).and(TProduct.SQL_putaway);
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			} 
			pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			if (jsonInfo.get("brandId") != null) {
//				jsonInfo.put("brandId", jsonInfo.get("brandId"));
				tProduct.setBrandId((int)jsonInfo.get("brandId"));
				tProduct.setPutaway("Y");
				if ((int)jsonInfo.get(Const.TRUEORFALSE) != -1) {
					if ((int)jsonInfo.get("trueorfalse") == Const.IS_TRUE) {
						sql.orderBy(" order by price asc");
						pageList = tProductdao.pageList(pageList, true, sql, tProduct);
						if(pageList != null){
						    result.setData(pageList);
						    log.info("获取品牌产品升序接口执行成功");
							result.success("获取品牌产品升序成功");
						}else{
							log.info("获取品牌产品升序接口执行失败");
							return result.error("获取品牌产品升序信息失败");
						}
					} else if ((int)jsonInfo.get("trueorfalse") == Const.IS_FALSE) {
						sql.orderBy(" order by price desc");
						pageList = tProductdao.pageList(pageList, true, sql, tProduct);
						if(pageList != null){
						    result.setData(pageList);
						    log.info("获取品牌产品升序接口执行成功");
							result.success("获取品牌产品升序成功");
						}else{
							log.info("获取品牌产品升序接口执行失败");
							return result.error("获取品牌产品升序信息失败");
						}
					}
				} else {
					pageList = tProductdao.pageList(pageList, true, sql, tProduct);
					if (pageList.getList().size() != 0) {
						result.success("产品获取成功");
						result.setData(pageList);
						log.info("产品获取成功");
					} else {
						log.info("产品获取失败");
						result.setData(pageList);
						return result.error("产品获取失败");
					}
				}
			}else {
				log.error("获取品牌id失败");
			}
		} catch (Exception e) {
			result.error("获取品牌产品列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return result;
    }  
	
	
	
	 /**
     * 根据 产品价格 从低到高 进行排序展示
     * @param trueorfalse 传的1(true升序排列) or 0(false降序排列)
     * @return PageList
     */
	public PageList<TProduct> byProdPriceSort(int page,int trueorfalse,PageList<TProduct> pageList) {
		TProduct cond = new TProduct();
		SqlSelect sql = new SqlSelect();
		if (page != -1) {
			pageList.setCurrentPage(page);
			pageList.setPageSize(Const.FONT_PAGE_SIZE);
		}
		if (trueorfalse!=-1) {
			if (trueorfalse==1) {
				sql.orderBy(" order by price asc");
			} else {
				sql.orderBy(" order by price desc");
			}
		}
		pageList = tProductdao.pageList(pageList, true, sql, cond);
		return pageList;
	}
	
	 /**
     * 根据 产品分类id 查找产品
     * @param cate_id 产品分类id
     * @return list
     */
	public List<TProduct> getProByCateId(int  cateid) {
		TProduct pro = new TProduct();
		List<TProduct> proList = new ArrayList<TProduct>();
		if (cateid!=-1) {
			pro.setCateId(cateid);
			SqlSelect sqlSelect = new SqlSelect().where(TProduct.SQL_cateId).orderBy(" order by create_time desc");
			proList = tProductdao.list(sqlSelect, pro);
		}
		return proList;
	}

	/**    
     * 根据 产品尺码颜色  获取 产品库存
     * @return TProductAttribute
     */
    public TProductAttribute getProductStock(int productId,String productColor,String productSize){
    	TProductAttribute prodAtt = new TProductAttribute();
    	
    	if(productId!=0){
    		if(StringUtils.isEmpty(productColor)&&StringUtils.isEmpty(productSize)){
        		TProductAttribute productAttribute = new TProductAttribute();
        		productAttribute.setProductId(productId);
        		SqlSelect sqlSelect = new SqlSelect().where(TProductAttribute.SQL_productId);
        			prodAtt = tProductAttributeDao.load(sqlSelect, productAttribute);
        		//return productatt;
        	}else if(StringUtils.isEmpty(productColor)&&StringUtils.isNotEmpty(productSize)){
        		
        		TProductAttribute productAttribute = new TProductAttribute();
        		productAttribute.setProductId(productId);
        		productAttribute.setProductSize(productSize);
        		SqlSelect sqlSelect = new SqlSelect().where(TProductAttribute.SQL_productId)
    					.and(TProductAttribute.SQL_productSize);
        		prodAtt = tProductAttributeDao.load(sqlSelect, productAttribute);
        	}else if(StringUtils.isNotEmpty(productColor)&&StringUtils.isEmpty(productSize)){
        		TProductAttribute productAttribute = new TProductAttribute();
        		productAttribute.setProductId(productId);
        		productAttribute.setProductColor(productColor);
        		
        		SqlSelect sqlSelect = new SqlSelect().where(TProductAttribute.SQL_productId)
    					.and(TProductAttribute.SQL_productColor);
        		prodAtt = tProductAttributeDao.load(sqlSelect, productAttribute);
        	}else if(StringUtils.isNotEmpty(productColor)&&StringUtils.isNotEmpty(productSize)){
        		TProductAttribute productAttribute = new TProductAttribute();
        		productAttribute.setProductId(productId);
        		productAttribute.setProductColor(productColor);
        		productAttribute.setProductSize(productSize);
        		
        		SqlSelect sqlSelect = new SqlSelect().where(TProductAttribute.SQL_productId)
    					.and(TProductAttribute.SQL_productColor)
    					.and(TProductAttribute.SQL_productSize);
        		prodAtt = tProductAttributeDao.load(sqlSelect, productAttribute);
        	}
    	}else{
    		log.error("获取商品ID失败");
    	}
    	
    	
    	return prodAtt;
    }
    
    /**
     * 根据  产品id 和 活动产品id 查找 产品
     * @return product
     */
    public TProduct getProductByProId(int productId,int eventprodId){
    	TProduct product = new TProduct();
    	TEventProduct eventprod = new TEventProduct();
    	if (eventprodId!=-1) {
    		eventprod.setId(eventprodId);
    		eventprod = tEventProductDao.loadById(eventprodId);
    		int eventprice = eventprod.getEventPrice();
    		product = tProductdao.loadById(productId);
    		product.setPrice(eventprice);
		}else {
	    	if (productId!=-1) {
	    		product = tProductdao.loadById(productId);
			} else {
				log.error("获取产品id失败");
			}
		}
        return product;
    }
    
    /**
     * 根据 活动id 查找活动信息
     * @param eventId 活动id
     * @return list
     */
	public List<TEvent> getEventByIdList(int  eventId) {
		List<TEvent> eventList = new ArrayList<TEvent>();
		TEvent event = new TEvent();
		if (eventId!=-1) {
			event.setId(eventId);
			eventList = tEventDao.list(new SqlSelect().where(TEvent.SQL_id), event);
		}
		return eventList;
	}
	
	/**
     * 根据 产品id 查找产品信息
     * @param productId 产品id
     * @return list
     */
	public List<TProduct> getProByproductIdList(int productId) {
		TProduct product = new TProduct();
		List<TProduct> productList = new ArrayList<TProduct>();

		if (productId!=-1) {
			product.setId(productId);
			productList = tProductdao.list(new SqlSelect().where(TProduct.SQL_id),product);
		}
		return productList;
	}
    
    /**
     * 根据  产品id 查找 产品属性
     * @return productAttList
     */
    public List<TProductAttribute> getproductAttByProId(int productId){
    	List<TProductAttribute> productAttList = new ArrayList<TProductAttribute>();
    	TProductAttribute productAtt = new TProductAttribute();
    	if (productId!=-1) {
    		productAtt.setProductId(productId);
    		SqlSelect sql = new SqlSelect().where(TProductAttribute.SQL_productId);
    		productAttList = tProductAttributeDao.list(sql, productAtt);
		} else {
			log.error("获取产品属性id失败");
		}
        return productAttList;
    }
    
    /*public List<ProductAttributeBean> getProductByProId(int productId,int eventprodId){
    	TEventProduct eventprod = new TEventProduct();
    	ModelMap map=new ModelMap();
    	map.put("pId", productId);
    	List<ProductAttributeBean> productDetailList=new ArrayList<>();
    	if (eventprodId!=-1) {
    		eventprod.setId(eventprodId);
    		eventprod = tEventProductDao.loadById(eventprodId);
    		int eventprice = eventprod.getEventPrice();
    		   productDetailList=productAttributeDao.getProductAttribute(map);
    		for (ProductAttributeBean productAttributeBean : productDetailList) {
    			productAttributeBean.setPrice(eventprice);
			}
		}else {
	    	if (productId!=-1) {
	    		productDetailList=productAttributeDao.getProductAttribute(map);
			} else {
				log.error("获取产品id失败");
			}
		}
        return productDetailList;
    }*/
}
