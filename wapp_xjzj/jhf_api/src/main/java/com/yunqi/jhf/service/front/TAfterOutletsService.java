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
import com.yunqi.jhf.dao.domain.TAfterOutlets;
import com.yunqi.jhf.dao.persistence.TAfterOutletsDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.web.Distance;

@Service
public class TAfterOutletsService {
	protected Logger logger = Logger.getLogger(TShoppingCartService.class);

	@Resource
	private TAfterOutletsDao tAfterOutletsDao;

	/**
	 * 获取所有售后网点信息，进行分页展示
	 * 
	 * @author Seek
	 * @param currentPage
	 *            当前页码 默认是1
	 * @return pageList 分页的售后网点信息
	 * 
	 */
	public PageList<TAfterOutlets> findAllOutlets(Integer currentPage,double x1, double y1) {
		TAfterOutlets tAfterOutlets = new TAfterOutlets();
		tAfterOutlets.setIsDelete(ConstantTool.ISDELETE_YES);
		SqlSelect sqlSelect = new SqlSelect().where(TAfterOutlets.SQL_isDelete);
		PageList<TAfterOutlets> pageList = new PageList<>();
		pageList.setCurrentPage(currentPage);
		pageList.setPageSize(Const.FONT_PAGE_SIZE);
		try{
			List<TAfterOutlets> tafterList=tAfterOutletsDao.list(sqlSelect, tAfterOutlets);
			for (TAfterOutlets tafter : tafterList) {
				BigDecimal x=tafter.getLatitude();
				BigDecimal y=tafter.getLongitude();
				Float d=Distance.jingWeiDuDistance(x, y, x1, y1);
				tafter.setDistance(d);
				tafter.setId(tafter.getId());
				SqlUpdate sqlUpdate=new SqlUpdate().addColumn(TAfterOutlets.SQL_distance).where(TAfterOutlets.SQL_id);
				tAfterOutletsDao.update(sqlUpdate, tafter);
			}
			pageList = tAfterOutletsDao.pageList(pageList, true, sqlSelect, tAfterOutlets);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			
		}
		return pageList;

	}
	
	/**
	 * 获取所有售后网点信息，进行距离排序
	 * 
	 * @author Seek
	 * @param currentPage
	 *            当前页码 默认是1
	 * @return pageList 分页的售后网点信息
	 * 
	 */
	public PageList<TAfterOutlets> findNear(Integer currentPage) {
		TAfterOutlets tAfterOutlets = new TAfterOutlets();
		tAfterOutlets.setIsDelete(ConstantTool.ISDELETE_YES);
		SqlSelect sqlSelect = new SqlSelect().where(TAfterOutlets.SQL_isDelete).orderBy(" order by distance asc");
		PageList<TAfterOutlets> pageList = new PageList<>();
		pageList.setCurrentPage(currentPage);
		pageList.setPageSize(Const.FONT_PAGE_SIZE);
		try{
			pageList = tAfterOutletsDao.pageList(pageList, true, sqlSelect, tAfterOutlets);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			
		}
		return pageList;

	}
	
	/**
	 * 异步 获取 全部售后网点列表
	 * @return json
	 */
	public JsonResult findNearByAll() {
		JsonResult result = new JsonResult();
		List<TAfterOutlets> tafterList = new ArrayList<>();
		TAfterOutlets tAfterOutlets = new TAfterOutlets();
		tAfterOutlets.setIsDelete(ConstantTool.ISDELETE_YES);
		try {
			SqlSelect sqlSelect = new SqlSelect().where(TAfterOutlets.SQL_isDelete).orderBy(" order by distance asc");
			tafterList = tAfterOutletsDao.list(sqlSelect, tAfterOutlets);
			if(tafterList!=null){
				 result.setData(tafterList);
				 logger.info("获取全部售后门店列表成功");
				}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 异步 根据 地理位置 获取所有 售后网点列表
	 * @param x1
	 * @param y1
	 * @return json
	 */
	public JsonResult findAllStores( Double x1, Double y1) {
		JsonResult result = new JsonResult();
		List<TAfterOutlets> tafterList = new ArrayList<>();
		TAfterOutlets tAfterOutlets = new TAfterOutlets();
		tAfterOutlets.setIsDelete(ConstantTool.ISDELETE_YES);
		try {
			if(x1==null||y1==null){
				result.setData("未获取到用户位置");
				logger.info("未获取到用户位置");
			}else {
				SqlSelect sqlSelect = new SqlSelect().where(TAfterOutlets.SQL_isDelete);
				List<TAfterOutlets> list = tAfterOutletsDao.list(sqlSelect, tAfterOutlets);
				if (list != null) {
					for (TAfterOutlets afterOutlets : list) {
						BigDecimal x = afterOutlets.getLatitude();
						BigDecimal y = afterOutlets.getLongitude();
						Float d = Distance.jingWeiDuDistance(x, y, x1, y1);
						afterOutlets.setDistance(d);
						afterOutlets.setId(afterOutlets.getId());
						SqlUpdate sqlUpdate = new SqlUpdate().addColumn(TAfterOutlets.SQL_distance).where(TAfterOutlets.SQL_id);
						tAfterOutletsDao.update(sqlUpdate, afterOutlets);
					}
					tafterList = tAfterOutletsDao.list(sqlSelect, tAfterOutlets);
					result.setData(tafterList);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 根据afterId售后网点id进行查询
	 * 
	 * @param storeId
	 * @return storeList
	 */
	public TAfterOutlets findStoreById(int afterId) {
		TAfterOutlets tAfterOutlets = new TAfterOutlets();
		try {
			if (afterId != -1) {
				tAfterOutlets = tAfterOutletsDao.loadById(afterId);
			} else {
				logger.info("获取实体店接口失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return tAfterOutlets;
	}

}
