package com.yunqi.jhf.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.yunqi.jhf.vo.ShareCommentBean;


@MapperScan
@Repository("shareCommentDao")
public interface ShareCommentDao {
	
	//根据分享的分类id 评论信息列表 
	List<ShareCommentBean> getShareCommBySid(ModelMap jsonInfo);
	
}
