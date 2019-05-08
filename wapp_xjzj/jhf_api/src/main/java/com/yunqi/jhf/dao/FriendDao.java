package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.FriendBean;
import com.yunqi.jhf.vo.FriendUserBean;

@MapperScan
@Repository("friendDao")
public interface FriendDao {
	List<FriendBean> getFriendList(ModelMap map);
	
	// 根据当前 friendId 查询到 对应的 2级和3级 列表
	FriendUserBean getUserByFriendId(ModelMap map);
	
}