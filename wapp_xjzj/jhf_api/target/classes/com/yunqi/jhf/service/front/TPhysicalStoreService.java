package com.yunqi.jhf.service.front;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.domain.TPhysicalStore;
import com.yunqi.jhf.dao.persistence.TPhysicalStoreDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.web.Distance;

@Service
public class TPhysicalStoreService {
	protected Logger logger = Logger.getLogger(TShoppingCartService.class);

	@Resource
	private TPhysicalStoreDao tPhysicalStoreDao;

	/**
	 * 获取所有实体店信息，进行分页展示
	 * 
	 * @author Seek
	 * @param currentPage
	 *            当前页码 默认是1
	 * @return pageList 分页的实体店信息
	 * 
	 */
	public PageList<TPhysicalStore> findAllStore(Integer currentPage, double x1, double y1) {
		// 查询所有产品的list
		TPhysicalStore tstore = new TPhysicalStore();
		tstore.setIsDelete(ConstantTool.ISDELETE_YES);
		SqlSelect sqlSelect = new SqlSelect().where(TPhysicalStore.SQL_isDelete);
		PageList<TPhysicalStore> pageList = new PageList<>();
		pageList.setCurrentPage(currentPage);
		pageList.setPageSize(Const.FONT_PAGE_SIZE);
		try {
			List<TPhysicalStore> tStoreList = tPhysicalStoreDao.list(sqlSelect, tstore);
			for (TPhysicalStore store : tStoreList) {
				BigDecimal x = store.getLatitude();
				BigDecimal y = store.getLongitude();
				Float d = Distance.jingWeiDuDistance(x, y, x1, y1);
				store.setDistance(d);
				store.setId(store.getId());
				SqlUpdate sqlUpdate = new SqlUpdate().addColumn(TPhysicalStore.SQL_distance)
						.where(TPhysicalStore.SQL_id);
				tPhysicalStoreDao.update(sqlUpdate, store);
			}
			pageList = tPhysicalStoreDao.pageList(pageList, true, sqlSelect, tstore);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;

	}

	/**
	 * 获取所有实体店信息信息，进行距离排序
	 * 
	 * @author Seek
	 * @param currentPage
	 *            当前页码 默认是1
	 * @return pageList 分页的售后网点信息
	 * 
	 */
	public PageList<TPhysicalStore> findNear(Integer currentPage) {
		TPhysicalStore tPhysicalStore = new TPhysicalStore();
		tPhysicalStore.setIsDelete(ConstantTool.ISDELETE_YES);
		SqlSelect sqlSelect = new SqlSelect().where(TPhysicalStore.SQL_isDelete).orderBy(" order by distance asc");
		PageList<TPhysicalStore> pageList = new PageList<>();
		pageList.setCurrentPage(currentPage);
		pageList.setPageSize(Const.FONT_PAGE_SIZE);
		try {
			pageList = tPhysicalStoreDao.pageList(pageList, true, sqlSelect, tPhysicalStore);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;

	}

	/**
	 * @author wangsong
	 * 
	 * 全部门店列表
	 * @return json
	 */
	public JsonResult findNearByAll() {
		JsonResult result = new JsonResult();
		List<TPhysicalStore> storelist = new ArrayList<>();
		TPhysicalStore tPhysicalStore = new TPhysicalStore();
		tPhysicalStore.setIsDelete(ConstantTool.ISDELETE_YES);
		try {
			SqlSelect sqlSelect = new SqlSelect().where(TPhysicalStore.SQL_isDelete).orderBy(" order by distance asc");
			storelist=tPhysicalStoreDao.list(sqlSelect, tPhysicalStore);
			if(storelist!=null){
			 result.setData(storelist);
			 logger.info("微信端 异步获取 全部门店列表成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * @author wangsong
	 * 异步 根据 地理位置 获取所有 实体门店列表
	 * @param x1
	 * @param y1
	 * @return json
	 */
	public JsonResult findAllStores( Double x1, Double y1) {
		JsonResult result = new JsonResult();
		List<TPhysicalStore> storelist = new ArrayList<>();
		TPhysicalStore tPhysicalStore = new TPhysicalStore();
		tPhysicalStore.setIsDelete(ConstantTool.ISDELETE_YES);
		if(x1==null||y1==null){
			// result.setData("未获取到用户位置");
			float[] dataArray;//定义
			dataArray = new float[0];//空数组赋值  // 返回空数组
			result.setData(dataArray);
			 logger.info("未获取到用户位置");
		}else{
			try {
				SqlSelect sqlSelect = new SqlSelect().where(TPhysicalStore.SQL_isDelete);
				List<TPhysicalStore> sList=tPhysicalStoreDao.list(sqlSelect, tPhysicalStore);
				if(sList!=null){
					for (TPhysicalStore store : sList) {
						BigDecimal x = store.getLatitude();
						BigDecimal y = store.getLongitude();
						Float d = Distance.jingWeiDuDistance(x, y, x1, y1);
						store.setDistance(d);
						store.setId(store.getId());
						SqlUpdate sqlUpdate = new SqlUpdate().addColumn(TPhysicalStore.SQL_distance)
								.where(TPhysicalStore.SQL_id);
						tPhysicalStoreDao.update(sqlUpdate, store);
					}
					storelist=tPhysicalStoreDao.list(sqlSelect, tPhysicalStore);
				 result.setData(storelist);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return result;
	}

	
	
	
	/**
	 * 根据storeId 实体店id进行查询
	 * 
	 * @param storeId
	 * @return storeList
	 */
	public TPhysicalStore findStoreById(int storeId) {
		TPhysicalStore tPhysicalStore = new TPhysicalStore();
		try {
			if (storeId != -1) {
				tPhysicalStore = tPhysicalStoreDao.loadById(storeId);
			} else {
				logger.info("获取实体店接口失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return tPhysicalStore;
	}

}
