package com.yunqi.jhf.service.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.FriendDao;
import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.dao.persistence.TFriendDao;
import com.yunqi.jhf.dao.persistence.TUserDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.vo.FriendBean;

@Service
public class TUserFriendService {

	private static final Logger log = Logger.getLogger(TProductService.class);
	@Resource
	private TFriendDao tFriendDao;
	@Resource
	private FriendDao friendDao;
	
	@Resource
	private TUserDao tUserDao;

	/**
	 * @author Seek
	 * @param
	 * @return 根据userId显示二级好友 pageList
	 */
	public List<FriendBean> getFriend(int page, int userId) {
		PageList<FriendBean> friendList = new PageList<FriendBean>();
		ModelMap map = new ModelMap();
		List<FriendBean> frList = new ArrayList<>();
		friendList.setCurrentPage(page);
		friendList.setPageSize(Const.FONT_PAGE_SIZE);
		try {
			if(userId!=-1){
				map.put("userId", userId);
				frList = friendDao.getFriendList(map);
			}else{
				log.error("获取用户ID失败");
			}
		
		} catch (Exception e) {
			log.error("获取好友列表失败");
			e.printStackTrace();
		}
		return frList;
	}

	/**
	 * @author Seek
	 * @param
	 * @return 根据userId显示二级好友 List
	 */
	public List<TUser> getUserFriendTwo( int userId) {
		List<TUser> userFtList=new ArrayList<>();
		if(userId!=0){
			TUser user=new TUser();
			user.setParentId(userId);
			SqlSelect sqlSelect=new SqlSelect().where(TUser.SQL_parentId);
			userFtList=tUserDao.list(sqlSelect, user);
		}else{
			log.info("获取好友列表时没有获取到用户ID");
		}
		return userFtList;
	}
	
	
	/**
	 * @author Seek
	 * @param
	 * @return 根据userId显示三级好友 List
	 */
	public List<TUser> getUserFriendThree( int userId) {
		List<TUser> ftList=new ArrayList<>();
		List<TUser> userFthList=new ArrayList<>();

		if(userId!=0){
			TUser user=new TUser();
			user.setParentId(userId);
			SqlSelect sqlSelect=new SqlSelect().where(TUser.SQL_parentId);
			List<TUser> userFtList=tUserDao.list(sqlSelect, user);
			if(userFtList.size()!=0){
				for (TUser tUser : userFtList) {
					TUser u=new TUser();
					u.setParentId(tUser.getId());
				    SqlSelect sql=new SqlSelect().where(TUser.SQL_parentId);
					ftList=tUserDao.list(sql, u);
					if(ftList.size()!=0){
						userFthList.addAll(ftList);
					}
				}
			}else{
				log.info("本用户没有三级好友");
			}
			
		}else{
			log.info("获取好友列表时没有获取到用户ID");
		}
		return userFthList;
	}

	
}
