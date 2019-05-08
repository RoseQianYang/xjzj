package com.yunqi.jhf.service.background;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.IntegralConversionDao;
import com.yunqi.jhf.dao.domain.TIntegral;
import com.yunqi.jhf.dao.domain.TIntegralConversion;
import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.dao.persistence.TIntegralConversionDao;
import com.yunqi.jhf.dao.persistence.TIntegralDao;
import com.yunqi.jhf.dao.persistence.TUserDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.service.SysConfigService;
import com.yunqi.jhf.vo.IntegralConversionBean;

@Service
public class IntegralConversionSerivce {
	
	protected static Logger log = Logger.getLogger(IntegralConversionSerivce.class);

	@Autowired
	private IntegralConversionDao integralConversionDao;
	
	@Autowired
	private TIntegralConversionDao tIntegralConversionDao;
	
	@Autowired
	private TIntegralDao tIntegralDao;
	
	@Autowired
	private TUserDao tUserDao;
	
	@Autowired
	private SysConfigService sysConfigService;
		
	public JsonResult getPageList(ModelMap jsonInfo) {
		log.info("进入获取返点申请列表接口");
		JsonResult result = new JsonResult();
		PageList<IntegralConversionBean> pagelist = new PageList<>();
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
		}
		List<IntegralConversionBean> list = integralConversionDao.getIntegralConversionList(jsonInfo);
		if(list != null) {
			pagelist.setList(list);
			pagelist.setTotalSize(integralConversionDao.getIntegralConversionCount());
			result.setData(pagelist);
			result.success("获取成功");
			log.info("获取返点申请列表接口执行成功");
		}else {
			result.error("获取失败");
			log.info("获取返点申请列表接口执行失败");
		}
		return result;
	}
	
	public JsonResult update(ModelMap jsonInfo) {
		log.info("进入审核通过返点申请接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate()
				.addColumn(TIntegralConversion.SQL_conversionStatus);
		if(jsonInfo.get(TIntegralConversion.PROP_id) != null) {
			sql.where(TIntegralConversion.SQL_id);
		} else {
			log.info("传入申请Id为空");
			return result.error("传入申请Id不可为空");
		}
		if(jsonInfo.get(TIntegralConversion.PROP_userId) == null) {
			log.info("传入用户Id为空");
			return result.error("传入用户Id不可为空");
		}
		if(jsonInfo.get(TIntegralConversion.PROP_conversionNum) == null) {
			log.info("传入用户积分为空");
			return result.error("传入用户积分不可为空");
		}
		//积分状态 为Y 已审核
		jsonInfo.put(TIntegralConversion.PROP_conversionStatus, ConstantTool.ISDELETE_YES);
		int res = tIntegralConversionDao.updateByMap(sql, jsonInfo);
		if(res > 0) {
			TIntegral ti = new TIntegral();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH"); 
			int num = (int) jsonInfo.get(TIntegralConversion.PROP_conversionNum);
			int userId =(int) jsonInfo.get(TIntegralConversion.PROP_userId);
			String userName = (String) jsonInfo.get(TUser.PROP_userName);
			ti.setUserId(userId);
			ti.setIntegralNum(-num); // 申请兑换 num 为 前台提现金额
			ti.setIntegralType(Const.INTEGRAL_TYPE_DECREASE); // 积分类型 为 已提财富
			ti.setIntroduction(userName + "用户在" + df.format(new Date()) +"时,已管理员审核,提现了" + num + "元"+"(扣除税费"+(int)Math.floor(num* (sysConfigService.getTaxes()))+"元)");
			ti.setIntegralType(ConstantTool.ISDELETE_NO);  //积分类型 提现财富
			res = tIntegralDao.insert(ti);
			// 给当前申请的用户 对应的积分总数 兑换（-1000） 积分
			TUser loadByuserId = tUserDao.loadById(userId);
			int userIntegralSum = loadByuserId.getUserIntegralSum();
			loadByuserId.setUserIntegralSum(userIntegralSum-num);
			SqlUpdate sqlUpdate=new SqlUpdate().addColumn(TUser.SQL_userIntegralSum).where(TUser.SQL_id);
			tUserDao.update(sqlUpdate, loadByuserId); // 通过管理员审核，立即对于用户积分总数-1000积分
			if(res > 0) {
				log.info("后台审核通过返点申请接口执行成功");
				result.success("执行成功");
			}else {
				log.info("后台返点申请扣除执行失败");
				result.error("执行失败");
			}
		}else {
			log.info("后台审核通过返点申请接口执行失败");
			result.error("执行失败");
		}
		return result;
	}
}
