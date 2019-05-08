package com.yunqi.jhf.service;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.SysLogDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.vo.SysLogBean;

/**
 * 提供系统日志相关接口
 * @author wangsong
 *
 */
@Service
public class SysLogService {

	protected Logger logger = Logger.getLogger(SysLogService.class);
	
	@Autowired
	private SysLogDao sysLogDao;
	
	/**
	 * 根据 创建时间 搜索 获取 系统日志列表
	 * @param jsonInfo
	 * @return Json
	 */
	public JsonResult getSysLogList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		try {
			PageList<SysLogBean> sysLogList = new PageList<>();
			if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
				sysLogList.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
				jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, sysLogList.getFromPos());
				jsonInfo.put(ConstantTool.PAGE_CURRENTSIZE, sysLogList.getPageSize());
			}
			List<SysLogBean> list = sysLogDao.getSysLogList(jsonInfo);
			if(list != null) {
				logger.info("系统日志列表获取执行成功");
				sysLogList.setList(list);
				sysLogList.setTotalSize(sysLogDao.getSysLogListCount(jsonInfo));
				result.success("获取成功");
				result.setData(sysLogList);
			}
		} catch (Exception e) {
			logger.info("系统日志列表获取执行失败");
			result.success("获取失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

}
