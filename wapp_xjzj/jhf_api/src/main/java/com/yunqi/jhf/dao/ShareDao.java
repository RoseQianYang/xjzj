package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.ShareBean;


@MapperScan
@Repository("shareDao")
public interface ShareDao {
	
	//根据分享的分类id  分享用户 获取分享信息列表 
	List<ShareBean> getShareByNameOrCate(ModelMap jsonInfo);
	
	//根据分享的分类id  分享用户 获取分享信息列表 
	List<ShareBean> getShareByNameOrCatePage(ModelMap jsonInfo);
	
	//根据 分享的分类id   分享用户 获取分享信息列表   总数
	int getShareByCount(ModelMap jsonInfo);
	
	//不含参数的全查分享
	List<ShareBean> getIndexShareList();
	
	//根据shareId查询分享详情
	ShareBean getShareOne(int shareId);
	
	//全查帖子
	List<ShareBean> getShareAll(ModelMap jsonInfo);
	//全查帖子总数  
	int getShareAllCount(ModelMap jsonInfo);

	
}
