package com.yunqi.jhf.service.front;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.domain.TIntegralConversion;
import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.dao.persistence.TIntegralConversionDao;
import com.yunqi.jhf.dao.persistence.TUserDao;
import com.yunqi.jhf.service.SysConfigService;
import com.yunqi.jhf.web.StrUtil;

/**
 * 微信端 提供 积分兑换接口
 * @author wangsong
 *
 */
@Service
public class TIntegralConversionService {
	
	protected static Logger logger = Logger.getLogger(TIntegralConversionService.class);
	
	@Autowired
	private TIntegralConversionDao tIntegralConversionDao;
	
	@Autowired
	private TUserDao tUserDao;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 根据 userId 用户Id 和 需要提现金额  conversionNum （只能整百提现 最多提现 200元） 发起兑换申请 
	 * @param conversionNumSum
	 * @param userId
	 * @return Json
	 */
	public JsonResult create(Integer userId,Integer conversionNum,String userMobile) {
		JsonResult result = new JsonResult();
		TIntegralConversion integralConversion = new TIntegralConversion();
		try {
			if (userId != -1) {
				integralConversion.setUserId(userId);
				// 通过 userId 查询获得 当前用户积分总数
				TUser tUser = tUserDao.loadById(userId);
				int userIntegralSum = tUser.getUserIntegralSum();
				if (userIntegralSum <= 0 || userIntegralSum < conversionNum) {
					logger.info("兑换申请积分数不足,当前不能兑换积分,请继续努力增加积分");
					result.error("兑换申请积分数不足,当前不能兑换积分,请继续努力增加积分");
				}else {
					integralConversion.setConversionNum(conversionNum); // 前台传过来 兑换 提现金额
					integralConversion.setRealityConversionNum((int)Math.floor(conversionNum* (sysConfigService.getTaxes()))); // 扣税费的 金额
					// 申请兑换详情模版： XXX用户 在 数据库当前时间 发起提现  XXX元(扣除税费XXX元)
					integralConversion.setIntroduction(tUser.getUserName()+ "用户在" + StrUtil.getCurrentTimestamp() +"时,发起提现" + conversionNum + "元"+"(扣除税费"+(int)Math.floor(conversionNum* (sysConfigService.getTaxes()))+"元)");
					// 默认状态 为 NO 待审批
					integralConversion.setConversionStatus(ConstantTool.ISDELETE_NO);
					integralConversion.setUserMobile(userMobile);
					int integralConversionId = tIntegralConversionDao.insert(integralConversion);
					if (integralConversionId > 0) {
						result.success("微信端积分兑换申请接口执行完成");
						logger.info("微信端积分兑换申请接口执行完成");
						result.setData(tIntegralConversionDao.loadById(integralConversionId));
					}
				}
			} else {
				result.error("用户不存在");
				logger.error("用户不存在");
			}
		} catch (Exception e) {
			result.error("微信端积分兑换申请接口执行失败");
			logger.info("微信端积分兑换申请接口执行失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

}
