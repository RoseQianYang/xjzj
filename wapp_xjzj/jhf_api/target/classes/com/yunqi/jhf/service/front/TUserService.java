package com.yunqi.jhf.service.front;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.dao.persistence.TUserDao;
import com.yunqi.jhf.dao.util.PageList;


@Service
public class TUserService {
	
	private static final Logger log = Logger.getLogger(TUserService.class);

	@Resource
	private TUserDao tUserDao;
	
	/**
	 * 通过用户id查找用户信息
	 * @param userid 用户id
	 * @return TUser
	 */
	
	public TUser getUserByUid(int userId) {
		JsonResult result = new JsonResult();
		TUser user = new TUser();
		try {
			if (userId != -1) {
				user= tUserDao.loadById(userId);
			} else {
				log.error("用户id获取失败");
			}
		} catch (Exception e) {
			result.error("获取用户信息失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 查找全部用户信息
	 * @param page 当前页
	 * @return PageList
	 */
	
	public PageList<TUser> getAllUser(int page) {
		JsonResult result = new JsonResult();
		TUser tUser = new TUser();
		PageList<TUser> userList = new PageList<TUser>();
		try {
			if (page!=-1) {
				userList.setCurrentPage(page);
				userList.setPageSize(Const.FONT_PAGE_SIZE);
				userList = tUserDao.pageList(userList, true, null, tUser);
			} else {
				log.error("获取当前页失败");
			}
		} catch (Exception e) {
			result.error("获取全部用户信息失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return userList;
	}

	
}
