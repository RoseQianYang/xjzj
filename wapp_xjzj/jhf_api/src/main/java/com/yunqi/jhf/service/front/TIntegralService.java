package com.yunqi.jhf.service.front;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.IntegralDao;
import com.yunqi.jhf.dao.domain.TIntegral;
import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.dao.persistence.TIntegralDao;
import com.yunqi.jhf.dao.persistence.TUserDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.vo.IntegralBean;

/**
 * 微信端 提供 积分相关接口
 * @author wangsong
 *
 */
@Service
public class TIntegralService {
	
	protected static Logger logger = Logger.getLogger(TIntegralService.class);
	
	
	@Autowired
	private IntegralDao integralDao;
	
	@Autowired
	private TIntegralDao tIntegralDao;
	
	@Autowired
	private TUserDao tUserDao;
	
	/**
	 * 根据用户id  userId 和  当前页 currentPage 获取列表
	 * @param userId
	 * @param currentPage
	 * @return  PageList
	 */
	public PageList<IntegralBean> getWeChatIntegralListByUserId(Integer userId,Integer currentPage) {
		PageList<IntegralBean> pageList = new PageList<>();
		ModelMap map = new ModelMap();
		try {
			pageList.setCurrentPage(currentPage);
			pageList.setPageSize(Const.FONT_PAGE_SIZE);
			map.put("page", pageList.getFromPos());
	     	map.put("size", Const.FONT_PAGE_SIZE); //传给后台 6条数据
	     	map.put("userId", userId); // 当前用户 id
	     	pageList.setTotalSize(integralDao.getWeChatIntegralListByUserIdCount(map));
	     	List<IntegralBean> list = integralDao.getWeChatIntegralListByUserId(map);
	     	if (list != null) {
				pageList.setList(list);
				logger.info("微信端获取当前用户积分列表成功");
			}else {
				logger.info("微信端获取当前用户积分列表为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			logger.info("微信端获取当前用户积分列表失败");
			e.printStackTrace();
		}
		return pageList;
	}
	
	/**
	 * 根据用户id  userId 获取 所有列表
	 * @param userId
	 * @return list
	 */
	public List<TIntegral> getIntegralListByUserId(Integer userId){
		TIntegral tIntegral = new TIntegral();
		List<TIntegral> list = null;
		try {
			tIntegral.setUserId(userId);
			SqlSelect sql = new SqlSelect().where(TIntegral.PROP_userId);
			list = tIntegralDao.list(sql, tIntegral);
			// 如果列表数据 为空  则后台打印日志
		   if (list == null) {
		   logger.info("微信端获取积分  列表数据为空");
			}
		} catch (Exception e) {
			logger.info("微信端获取积分  列表接口执行成功");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * status 1 所有积分列表  2 可提财富列表  3 已提财富列表
	 * @param userId
	 * @param status
	 * @return list
	 */
	public List<TIntegral> getIntegralListByUserIdByStatus(Integer userId, Integer status){
		TIntegral tIntegral = new TIntegral();
		List<TIntegral> list = null;
		try {
			tIntegral.setUserId(userId);
			SqlSelect sql = new SqlSelect();
		if (status == 1) {
			sql.where(TIntegral.SQL_userId).orderBy("order by create_time desc");
			list = tIntegralDao.list(sql, tIntegral);
			// 如果列表数据 为空  则后台打印日志
		   if (list == null) {
		   logger.info("微信端获取积分  列表数据为空");
			}
		}else if (status == 2) {
			tIntegral.setIntegralType(Const.INTEGRAL_TYPE_INCREASE); // 可提（增长）财富
			sql.where(TIntegral.SQL_userId).and(TIntegral.SQL_integralType).orderBy("order by create_time desc");
			list = tIntegralDao.list(sql, tIntegral);
			 if (list == null) {
				   logger.info("微信端获取积分 可提财富 列表数据为空");
					}
		}else if (status == 3) {
			tIntegral.setIntegralType(Const.INTEGRAL_TYPE_DECREASE); // 已提财富
			sql.where(TIntegral.SQL_userId).and(TIntegral.SQL_integralType).orderBy("order by create_time desc");
			list = tIntegralDao.list(sql, tIntegral);
			 if (list == null) {
				   logger.info("微信端获取积分 可提财富 列表数据为空");
					}
		}
		} catch (Exception e) {
			logger.info("微信端获取积分列表接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	
	/**
	 * 异步获取  积分列表
	 * @param jsonInfo
	 * @param status
	 *         状态 1 总财富列表 2 可提财富列表 3 已提财富列表
	 * @return Json
	 */
	public JsonResult getWeChatIntegralPageListByUserId(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<TIntegral> pagelist = new PageList<>();
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				pagelist.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			}
			pagelist.setPageSize(Const.FONT_PAGE_SIZE); // 默认展示 10条数据
			SqlSelect sql = new SqlSelect();
			int status = 1; // 默认 为 总财富列表 
			if(jsonInfo.get(ConstantTool.STATUS) != null) {
				status = (int) jsonInfo.get(ConstantTool.STATUS);
			}
			if (status == 2) {
				jsonInfo.put(TIntegral.PROP_integralType, Const.INTEGRAL_TYPE_INCREASE); // 可提财富列表
				sql.where(TIntegral.SQL_integralType).orderBy("order by create_time desc");
				pagelist = tIntegralDao.pageListByMap(pagelist, true, sql, jsonInfo);
				if (pagelist != null) {
					result.success("微信端获取增长财富列表获取成功");
					logger.info("微信端获取增长财富列表执行成功");
					result.setData(pagelist);
				} else {
					logger.info("微信端获取增长财富列表执行失败");
					result.error("微信端获取增长财富列表执行失败");
				}
			}else if (status == 3) {
				jsonInfo.put(TIntegral.PROP_integralType, Const.INTEGRAL_TYPE_DECREASE); // 已提财富
				sql.where(TIntegral.SQL_integralType).orderBy("order by create_time desc");
				pagelist = tIntegralDao.pageListByMap(pagelist, true, sql, jsonInfo);
				if (pagelist != null) {
					result.success("微信端获取已提财富获取成功");
					logger.info("微信端获取已提财富执行成功");
					result.setData(pagelist);
				} else {
					logger.info("微信端获取已提财富执行失败");
					result.error("微信端获取已提财富执行失败");
				}
			}else {
				sql.where("1=1").orderBy("order by create_time desc");//按时间降序排列
				pagelist = tIntegralDao.pageListByMap(pagelist, true, sql, jsonInfo);
				if (pagelist != null) {
					result.success("微信端获取总财富列表获取成功");
					logger.info("微信端获取总财富列表获取成功");
					result.setData(pagelist);
				} else {
					logger.info("微信端获取总财富列表执行失败");
					result.error("微信端获取总财富列表执行失败");
				}
			}
		} catch (Exception e) {
			result.error("微信端积分 分页列表接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 根据 userId 获取 对应的积分总数
	 * @param userId
	 * @return
	 */
	public int getWeChatUserIntegralSum(Integer userId) {
		TUser tUserById = tUserDao.loadById(userId);
		int userIntegralSum = tUserById.getUserIntegralSum();
		if (userIntegralSum < 0) {
			logger.info("微信端获取当前用户对应的用户积分总数失败");
		}
		return userIntegralSum;
	}
	
	/**
	 * 根据 userId 自己的已提现财富 统计 自己总已提现金额
	 * @return
	 */
	public int getIntegralNumSum(Integer userId) {
		ModelMap map = new ModelMap();
		TIntegral tIntegral = new TIntegral();
		int a = 0;
		tIntegral.setUserId(userId);
		tIntegral.setIntegralType(Const.INTEGRAL_TYPE_DECREASE); //已提现财富
		SqlSelect sqlSelect = new SqlSelect().where(TIntegral.SQL_userId).and(TIntegral.SQL_integralType);
		List<TIntegral> list = tIntegralDao.list(sqlSelect, tIntegral);
		if (list.size() == 0) {
			logger.info("已提现财富为空");
		}else {
			map.put("userId", userId);
			map.put("integralType", Const.INTEGRAL_TYPE_DECREASE); // 已提现财富
			int integralNumSum = integralDao.getIntegralNumSum(map);
			String str = Integer.toString(integralNumSum);
			//Integer.parseInt(String.valueOf(userIntegralSum).substring(2));
			String str1=str.substring(1);
			a= Integer.parseInt(str1);
			if (a < 0) {
				logger.info("微信端获取当前用户对应的已提现金额总数失败");
			}
		}
		return  a;
	}
	
	
}
