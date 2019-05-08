package com.yunqi.jhf.service.front;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.domain.TMobileRoute;
import com.yunqi.jhf.dao.persistence.TMobileRouteDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;


@Service
public class TMobileRouteService {

	private static final Logger log = Logger.getLogger(TMobileRouteService.class);

	@Resource
	private TMobileRouteDao tMobileDao;
	
	/**
	 * 获取露营路线列表
	 * @param currentPage  
	 *            当前页               
	 * @return pageList 露营路线分页信息列表
	 */
	public PageList<TMobileRoute> getMobileList(int currentPage) {
		TMobileRoute mobileroute = new TMobileRoute();
		mobileroute.setIsDelete(ConstantTool.ISDELETE_YES);
		PageList<TMobileRoute> pageList = new PageList<>();
		SqlSelect sql = new SqlSelect();
		try {
			if (currentPage != -1) {
				pageList.setCurrentPage(currentPage);
				pageList.setPageSize(Const.FONT_PAGE_SIZE);
				sql.where(TMobileRoute.SQL_isDelete);
			}
			pageList = tMobileDao.pageList(pageList, true, sql, mobileroute);
		} catch (Exception e) {
			log.error("获取露营路线列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;
	}
	
	/**
	 * 异步获取  露营路线列表
	 * @param jsonInfo
	 * @return json
	 */
	public JsonResult getMobilePageList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<TMobileRoute> pageList = new PageList<>();
		SqlSelect sql = new SqlSelect();
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			} 
			pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			jsonInfo.put(TMobileRoute.PROP_isDelete, ConstantTool.ISDELETE_YES);
			sql.where(TMobileRoute.SQL_isDelete).orderBy("order by create_time desc"); //按时间降序排列
			pageList = tMobileDao.pageListByMap(pageList, true, sql, jsonInfo);
			if (pageList != null) {
				result.success("微信端异步获取所有露营路线列表获取成功");
				result.setData(pageList);
			} else {
				log.info("微信端异步获取所有露营路线列表获取失败");
				result.error("微信端异步获取所有露营路线列表获取失败");
			}
		} catch (Exception e) {
			log.error("微信端异步获取所有露营路线列表获取失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 通过mobilerouteId获取露营路线详情
	 * @param mobilerouteId 路线id
	 * @return TMobile
	 */
	public TMobileRoute getMobileBymobileId(int mobilerouteId) {
		TMobileRoute mobile = new TMobileRoute();
		try {
			if (mobilerouteId != -1) {
				mobile = tMobileDao.loadById(mobilerouteId);
			}else {
				log.error("获取路线id失败");
			}
		} catch (Exception e) {
			log.error("获取露营路线失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return mobile;
    }  
}
