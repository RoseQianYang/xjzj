package com.yunqi.jhf.service.background;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.ShareDao;
import com.yunqi.jhf.dao.domain.TShare;
import com.yunqi.jhf.dao.persistence.TShareDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.ShareBean;

@Service
public class ShareService {

	protected static Logger log = Logger.getLogger(ShareService.class);
	
	@Autowired
	private ShareDao shareDao;
	
	@Autowired
	private TShareDao tShareDao;
	
	public JsonResult getShareList(ModelMap jsonInfo) {
		log.info("进入获取分享帖子列表信息接口");
		JsonResult result = new JsonResult();
		PageList<ShareBean> pagelist = new PageList<>();
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
			jsonInfo.put("size", pagelist.getPageSize());
		}
		List<ShareBean> list = shareDao.getShareByNameOrCate(jsonInfo);
		if(list != null) {
			log.info("获取分享帖子列表信息接口执行成功");
			result.success("获取成功");
			pagelist.setList(list);
			pagelist.setTotalSize(shareDao.getShareByCount(jsonInfo));
			result.setData(pagelist);
		}else {
			log.info("获取分享帖子列表信息接口执行失败");
			result.error("获取失败");
		}
		return result;
	}
	
	/**
	 *  帖子是否首页展示
	 * @param jsonInfo
	 * @return
	 */
	public JsonResult updateShareIsShow(ModelMap jsonInfo) {
		log.info("进入修改帖子是否首页展示接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		if(jsonInfo.get(TShare.PROP_id) != null) {
			sql.where(TShare.SQL_id);
		}else {
			log.info("传入帖子id为空");
			return result.error("帖子id不可为空");
		}
		if(jsonInfo.get(TShare.PROP_isShow) != null) {
			sql.addColumn(TShare.SQL_isShow);
		}else {
			log.info("传入帖子状态为空");
			return result.error("帖子状态不可为空");
		}
		int res = tShareDao.updateByMap(sql, jsonInfo);
		if(res > 0) {
			log.info("修改帖子是否首页展示接口执行成功");
			result.success("修改成功");
			result.setData(tShareDao.loadById((int) jsonInfo.get(TShare.PROP_id)));
		}else {
			log.info("修改帖子是否首页展示接口执行失败");
			result.error("修改失败");
		}
		return result;
	}
	
	/**
	 * 用户是否挂起状态 
	 * @param jsonInfo
	 * @return
	 */
	public JsonResult updateShareIsEnabled(ModelMap jsonInfo) {
		log.info("用户挂起状态接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().addColumn("update_time = NOW()");
		// 传入用户挂起状态默认状态为 N 不挂起
		if(jsonInfo.get(TShare.PROP_id) != null) {
			sql.where(TShare.SQL_id);
		}else {
			log.info("传入帖子id为空");
			return result.error("帖子id不可为空");
		}
		if(jsonInfo.get(TShare.PROP_isEnabled) != null) {
			sql.addColumn(TShare.SQL_isEnabled);
		}else {
			log.info("传入用户挂起状态为空");
			return result.error("传入用户挂起状态不可为空");
		}
		int res = tShareDao.updateByMap(sql, jsonInfo);
		if(res > 0) {
			log.info("修改用户是否挂起状态 接口执行成功");
			result.success("修改成功");
			result.setData(tShareDao.loadById((int) jsonInfo.get(TShare.PROP_id)));
		}else {
			log.info("修改用户是否挂起状态 接口执行失败");
			result.error("修改失败");
		}
		return result;
	}
	
}
