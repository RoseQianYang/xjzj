package com.yunqi.jhf.service.front;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunqi.jhf.dao.domain.TVehicleBrand;
import com.yunqi.jhf.dao.persistence.TVehicleBrandDao;
import com.yunqi.jhf.dao.util.SqlSelect;

/**
 * 微信端 提供 房车品牌相关服务
 * @author wangsong
 *
 */
@Service
public class TVehicleBrandService {
	
	private static final Logger logger = Logger.getLogger(TVehicleBrandService.class);
	@Autowired
	private TVehicleBrandDao tVehicleBrandDao;
	
	/**
	 * 获取房车品牌列表，按时间先后排序，然后选前五个 房车品牌 logo 在首页展示
	 * @return list
	 */
	public List<TVehicleBrand> getFiveVehicleBrandList(){
		TVehicleBrand vehicleBrand = new TVehicleBrand();
		List<TVehicleBrand> vehicleBrandList = null;
		try {
			SqlSelect sql = new SqlSelect().orderBy("order by create_time asc");
			vehicleBrandList = tVehicleBrandDao.list(sql, vehicleBrand);
			// 如果列表数据 为空  则后台打印日志
			if (vehicleBrandList == null) {
				logger.info("微信端获取房车品牌  列表数据为空");
			}
			// 如果列表数据 多于 5条 微信端  获取 前5个
			if (vehicleBrandList.size() >= 5) {
				vehicleBrandList = vehicleBrandList.subList(0, 5);
				logger.info("微信端获取房车品牌 选前五个列表接口执行成功");
			}
			
		} catch (Exception e) {
			logger.info("微信端获取房车品鉴 选前五个列表接口执行成功");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
        return vehicleBrandList;
    }
	
	
	/**
	 * 根据当前页   获取所有房车品牌 分页列表 
	 * @return pageList
	 */
	/*public PageList<TVehicleBrand> vehicleBrandPageList(Integer currentPage){
		TVehicleBrand vehicleBrand = new TVehicleBrand();
		PageList<TVehicleBrand> pageList = new PageList<>();
		try {
			pageList.setCurrentPage(currentPage);
			pageList.setPageSize(Const.FONT_PAGE_SIZE);	
			pageList = tVehicleBrandDao.pageList(pageList, false, null, vehicleBrand);
			logger.info("微信端获取所有房车品牌列表接口执行成功");
		} catch (Exception e) {
			logger.info("微信端获取所有房车品牌列表接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;
	}*/

	
	/**
	 * 获取所有房车品牌 不分页列表 
	 * @return pageList
	 */
	public List<TVehicleBrand> vehicleBrandList(){
		TVehicleBrand vehicleBrand = new TVehicleBrand();
		 List<TVehicleBrand> list = null ;
		try {
			list = tVehicleBrandDao.list(null, vehicleBrand);
			if (list == null) {
				logger.info("微信端获取所有房车品牌列表数据为空");
			}else {
				logger.info("微信端获取所有房车品牌列表接口执行成功");
			}
		} catch (Exception e) {
			logger.info("微信端获取所有房车品牌列表接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	
}
