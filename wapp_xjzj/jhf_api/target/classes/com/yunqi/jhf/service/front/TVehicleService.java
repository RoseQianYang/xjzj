package com.yunqi.jhf.service.front;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.VehicleDao;
import com.yunqi.jhf.dao.domain.TVehicle;
import com.yunqi.jhf.dao.persistence.TVehicleDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.vo.VehicleBean;

/**
 * 微信端 提供 房车相关服务
 * 
 * Created by WangSong on 2018/2/7.
 */

@Service
public class TVehicleService {

	@Autowired
	private TVehicleDao tVehicleDao;

	@Autowired
	private VehicleDao vehicleDao;

	protected static Logger logger = Logger.getLogger(TVehicleService.class);

	/**
	 * 获取房车品鉴列表，按时间先后排序，然后选前二个在首页展示
	 * 
	 * @return List
	 */
	public List<TVehicle> getTwoVehicleLists() {
		TVehicle vehicle = new TVehicle();
		List<TVehicle> tVehicleLists = null;
		try {
			SqlSelect sql = new SqlSelect().orderBy("order by create_time desc");
			tVehicleLists = tVehicleDao.list(sql, vehicle);
			// 如果列表数据 为空 则后台打印日志
			if (tVehicleLists == null) {
				logger.info("微信端获取房车品鉴  列表数据为空");
			}
			// 如果列表数据 多于 2条 微信端 获取 前二个
			if (tVehicleLists.size() >= 2) {
				tVehicleLists = tVehicleLists.subList(1, 3);
				logger.info("微信端获取房车品鉴 选前二个列表接口执行成功");
			}
		} catch (Exception e) {
			logger.info("微信端获取房车品鉴 选前二个列表接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return tVehicleLists;
	}

	/**
	 * 获取房 当季促销的房车，按时间先后排序，然后选前二个在首页展示
	 * 
	 * @return List
	 */
	public List<TVehicle> getTwoVehicleListsYesIsSales() {
		TVehicle vehicle = new TVehicle();
		List<TVehicle> tVehicleListsYesIsSales = null;
		try {
			// 当季促销isSales为Y
			vehicle.setIsSales(ConstantTool.ISSALES_YES);
			SqlSelect sql = new SqlSelect().where(TVehicle.SQL_isSales).orderBy("order by create_time desc");
			tVehicleListsYesIsSales = tVehicleDao.list(sql, vehicle);
			// 如果列表数据 为空 则后台打印日志
			if (tVehicleListsYesIsSales == null) {
				logger.info("微信端获取当季促销  列表数据为空");
			}
			// 如果列表数据 多于 2条 微信端 获取 前二个
			if (tVehicleListsYesIsSales.size() >= 2) {
				tVehicleListsYesIsSales = tVehicleListsYesIsSales.subList(0, 2);
				logger.info("微信端获取当季促销的房车 选前二个列表接口执行成功");
			}
		} catch (Exception e) {
			logger.info("微信端获取当季促销的房车 选前二个列表接口执行成功");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return tVehicleListsYesIsSales;
	}

	/**
	 * 获取房 二手置换的房车列表 ，按时间先后排序，然后选前二个在首页展示
	 * 
	 * @return List
	 * 
	 */
	public List<TVehicle> getTwoVehicleListsYesIsSencond() {
		TVehicle vehicle = new TVehicle();
		List<TVehicle> tVehicleListsYesIsSencond = null;
		try {
			// 二手置换isSencond为Y
			vehicle.setIsSencond(ConstantTool.ISSENCOND_YES);
			SqlSelect sql = new SqlSelect().where(TVehicle.SQL_isSencond).orderBy("order by create_time desc");
			tVehicleListsYesIsSencond = tVehicleDao.list(sql, vehicle);
			// 如果列表数据 为空 则后台打印日志
			if (tVehicleListsYesIsSencond == null) {
				logger.info("微信端获取二手置换  列表数据为空");
			}
			// 如果列表数据 多于 2条 微信端 获取 前二个
			if (tVehicleListsYesIsSencond.size() >= 2) {
				tVehicleListsYesIsSencond = tVehicleListsYesIsSencond.subList(0, 2);
				logger.info("微信端获取二手置换的房车 选前二个列表接口执行成功");
			}

		} catch (Exception e) {
			logger.info("微信端获取二手置换 选前二个列表接口执行成功");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return tVehicleListsYesIsSencond;
	}

	/**
	 * 
	 * 根据当前页 获取所有房车品鉴 分页列表
	 * 
	 * @param currentPage
	 *            当前页 status状态 1 房车所有列表 2 当季促销列表 3 二手置换 vehicleBrandId 获取房车列表
	 * @return PageList
	 */
	public PageList<TVehicle> getVehicleLists(Integer currentPage, Integer status, Integer vehicleBrandId) {
		TVehicle vehicleBen = new TVehicle();
		PageList<TVehicle> pageList = new PageList<>();
		try {
			// 分页
			pageList.setCurrentPage(currentPage);
			pageList.setPageSize(Const.FONT_PAGE_SIZE);
			if (status == 1) {
				pageList = tVehicleDao.pageList(pageList, true, null, vehicleBen);
				logger.info("微信端获取所有房车列表接口执行成功");
			}
			if (status == 2) {
				// 当季促销isSales为Y
				vehicleBen.setIsSales(Const.ISSALES_YES);
				SqlSelect sql = new SqlSelect().where(TVehicle.SQL_isSales).orderBy("order by create_time desc");
				pageList = tVehicleDao.pageList(pageList, true, sql, vehicleBen);
				logger.info("微信端获取当季促销的房车列表接口执行成功");
			}
			if (status == 3) {
				// 二手置换isSencond为Y
				vehicleBen.setIsSencond(Const.ISSENCOND_YES);
				SqlSelect sql = new SqlSelect().where(TVehicle.SQL_isSencond).orderBy("order by create_time desc");
				pageList = tVehicleDao.pageList(pageList, true, sql, vehicleBen);
				logger.info("微信端获取当二手置换的房车列表接口执行成功");
			}
			if (vehicleBrandId != -1) {
				vehicleBen.setVehicleBrandId(vehicleBrandId);
				SqlSelect sql = new SqlSelect().where(TVehicle.SQL_vehicleBrandId).orderBy("order by create_time desc");
				tVehicleDao.pageList(pageList, true, sql, vehicleBen);
			}
		} catch (Exception e) {
			logger.info("微信端获取房车品鉴列表接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;
	}

	/**
	 * 异步 获取所有房车品鉴 分页列表
	 * 
	 * @param jsonInfo
	 * @param status
	 *            状态 1 房车所有列表 2 当季促销列表 3 二手置换列表
	 * @param vehicleBrandId
	 *            根据房车品牌Id 获取房车列表
	 * @return Json
	 */
	public JsonResult getVehiclePageLists(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<TVehicle> pagelist = new PageList<>();
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				pagelist.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			}
			pagelist.setPageSize(Const.FONT_PAGE_SIZE); // 默认展示 10条数据
			SqlSelect sql = new SqlSelect();
			int status = 1; // 默认 为 房车所有列表
			if(jsonInfo.get(ConstantTool.STATUS) != null) {
				status = (int) jsonInfo.get(ConstantTool.STATUS);
			}
			// 如果品牌ID不为空的话,就以品牌ID来进行查询
			if (jsonInfo.get(TVehicle.PROP_vehicleBrandId) != null) {
				sql.where(TVehicle.SQL_vehicleBrandId).orderBy("order by create_time desc");
				pagelist.setPageSize(Const.FONT_PAGE_SIZE); // 默认一次展示 10条数据
				pagelist = tVehicleDao.pageListByMap(pagelist, true, sql, jsonInfo);
				if (pagelist != null) {
					result.success("微信端根据房车品牌Id 房车品鉴列表获取成功");
					result.setData(pagelist);
					logger.info("微信端根据房车品牌Id 房车品鉴列表获取成功");
				} else {
					logger.info("微信端获取房车品鉴列表接口执行失败");
					result.error("微信端获取房车品鉴列表接口执行失败");
				}
			} else {
				if (status == 2) {
					// 当季促销isSales为Y
					jsonInfo.put(TVehicle.PROP_isSales, ConstantTool.ISSALES_YES);
					sql.where(TVehicle.SQL_isSales).orderBy("order by create_time desc");
					pagelist.setPageSize(Const.FONT_PAGE_SIZE); // 默认展示 2条数据
					pagelist = tVehicleDao.pageListByMap(pagelist, true, sql, jsonInfo);
					if (pagelist != null) {
						result.success("微信端获取当季促销的房车列表获取成功");
						logger.info("微信端获取当季促销的房车列表接口执行成功");
						result.setData(pagelist);
					} else {
						logger.info("微信端获取房车品鉴列表接口执行失败");
						result.error("微信端获取房车品鉴列表接口执行失败");
					}
				} else if (status == 3) {
					// 二手置换isSencond为Y
					jsonInfo.put(TVehicle.PROP_isSencond, Const.ISSENCOND_YES);
					sql.where(TVehicle.SQL_isSencond).orderBy("order by create_time desc");
					pagelist = tVehicleDao.pageListByMap(pagelist, true, sql, jsonInfo);
					if (pagelist != null) {
						logger.info("微信端获取二手置换的房车列表接口执行成功");
						result.success("微信端获取二手置换的房车列表获取成功");
						result.setData(pagelist);
					} else {
						logger.info("微信端获取房车品鉴列表接口执行失败");
						result.error("微信端获取房车品鉴列表接口执行失败");
					}
				} else {
					sql.where("1=1").orderBy("order by create_time desc"); //按时间降序排列
					pagelist = tVehicleDao.pageListByMap(pagelist, true, sql, jsonInfo);
					if (pagelist != null) {
						result.success("微信端获取所有房车列表获取成功");
						result.setData(pagelist);
					} else {
						logger.info("微信端获取房车品鉴列表接口执行失败");
						result.error("微信端获取房车品鉴列表接口执行失败");
					}
				}
			}
		} catch (Exception e) {
			result.error("微信端房车品鉴 分页列表接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据 vehicleId 获取 房车详情
	 * 
	 * @param vehicleId
	 *            房车Id
	 * @return vehicle 实体类对象
	 */
	public TVehicle getVehicleById(Integer vehicleId) {
		TVehicle vehicle = new TVehicle();
		try {
			if (vehicleId != -1) {
				vehicle = tVehicleDao.loadById(vehicleId);
				if (vehicle == null) {
					logger.info("微信端获取房车详情数据为空");
				}
				logger.info("微信端获取房车详情接口执行成功");
			} else {
				logger.error("微信端 获取房车id失败");
			}
		} catch (Exception e) {
			logger.info("微信端获取房车详情接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return vehicle;
	}

	/**
	 * 根据当前页 currentPage 和 房车品牌id vehicleBrandId 获取房车列表
	 * 
	 * @param currentPage
	 *            当前页
	 * @param vehicleBrandId
	 *            房车品牌Id
	 * @return pageList
	 */
	public PageList<VehicleBean> getVehickeListByVehicleBrandId(Integer currentPage, Integer vehicleBrandId) {
		PageList<VehicleBean> pageList = new PageList<>();
		ModelMap map = new ModelMap();
		try {
			pageList.setCurrentPage(currentPage);
			pageList.setPageSize(Const.FONT_PAGE_SIZE);
			map.put("vehicleBrandId", vehicleBrandId);
			pageList.setTotalSize(vehicleDao.getVehickeListByVehicleBrandIdCount(map));
			map.put("page", pageList.getFromPos());
			map.put("size", Const.FONT_PAGE_SIZE); // 传给后台 3条数据
			List<VehicleBean> list = vehicleDao.getVehickeListByVehicleBrandId(map);
			if (list == null) {
				logger.info("微信端 房车租赁列表数据 为空");
			} else {
				pageList.setList(list);
				logger.info("微信端根据 房车品牌id 获取房车租赁列表成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("微信端根据 房车品牌id 获取房车租赁列表失败");
			e.printStackTrace();
		}
		return pageList;
	}
}
