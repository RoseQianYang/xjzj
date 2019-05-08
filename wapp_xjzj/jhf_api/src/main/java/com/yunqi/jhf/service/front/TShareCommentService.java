package com.yunqi.jhf.service.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.ShareCommentDao;
import com.yunqi.jhf.dao.domain.TShareComment;
import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.dao.persistence.TShareCommentDao;
import com.yunqi.jhf.vo.ShareBean;
import com.yunqi.jhf.vo.ShareCommentBean;
import com.yunqi.jhf.web.SensitiveWord;


@Service
public class TShareCommentService {

	private static final Logger log = Logger.getLogger(TShareCommentService.class);

	@Resource
	private ShareCommentDao shareCommentDao;
	
	@Resource
	private TUserService tUserService;
	
	@Resource
	private TShareCommentDao tShareCommentDao;
	
	/**
	 * 通过分享id获取分享评论信息
	 * @param shareId 分享id
	 * @return ShareCommentBean
	 */
	
	public List<ShareCommentBean> getShareCommentBySid(int shareId) {
		JsonResult result = new JsonResult();
		ModelMap jsonInfo = new ModelMap();
		List<ShareCommentBean> tscList = new ArrayList<ShareCommentBean>();
		try {
			if (shareId != -1) {
				jsonInfo.put("shareId", shareId);
				tscList= shareCommentDao.getShareCommBySid(jsonInfo);
			} else {
				log.error("分享id获取失败");
			}
		} catch (Exception e) {
			result.error("获取分享评论信息失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return tscList;
	}
	
	
	/**
	 * 显示获取的分享评论信息
	 * @param List<ShareCommentBean>
	 */
	
	public void showShareComment(ShareBean share,List<ShareCommentBean> shareCommList) {
		for (ShareCommentBean tShareComment : shareCommList) {
			int parentComm = tShareComment.getParentComment();
			if (parentComm == 0) {
		    	share.setSharecomms(shareCommList);
			} else {
				TUser user = tUserService.getUserByUid(parentComm);
				if (user!=null) {
					String bhname = user.getUserName();
					tShareComment.setParentCommentName(bhname);
					share.setSharecomms(shareCommList);
				}
			}
		}
	}
	
	/**
	 * 加入用户分享评论
	 * @param userId 子评论用户id shareId 分享id 
	 * @param parentCommId 父评论i, commentContent评论内容
	 * @param userName 子评论用户名
	 * @return result
	 */
	public JsonResult insertShareComment(int userId,int shareId,
			int parentCommId,String commentContent,String userName) {
		JsonResult result = new JsonResult();
		try{
			if(userId!=-1){
				TShareComment shareComm = new TShareComment();
				shareComm.setUserId(userId);
				if (shareId != -1) {
					shareComm.setShareId(shareId);
				} else {
					log.error("分享id不能为空");
					result.error("分享id不能为空");
				}
				if (parentCommId != -1) {
					shareComm.setParentComment(parentCommId);
				} else {
					log.error("评论父id不能为空");
					result.error("评论父id不能为空");
				}
				if (userName!=null||"".equals(userName)) {
					shareComm.setUserName(userName); 
				} else {
					log.error("用户名不能为空");
					result.error("用户名不能为空");
				}
				if (commentContent!=null||"".equals(commentContent)) {
					SensitiveWord.InitializationWork();
					String strStar = SensitiveWord.filterInfo(commentContent);
					if (strStar.contains("*")) {
						log.error("评论内容含有敏感字，不可评论");
						result.error("评论内容含有敏感字，不可评论");
					} else {
						shareComm.setCommentContent(commentContent); 
						int sharec = tShareCommentDao.insert(shareComm);
						if (sharec>0) {
							log.info("用户评论添加成功");
							result.success("评论添加成功");
						}
					}
				} else {
					log.error("评论内容不能为空");
					result.error("评论内容不能为空");
				}
			}
		}catch(Exception e){
				result.error("添加用户评论失败");
				log.error(e.getMessage(), e);
				e.printStackTrace();
		 }
		return result;
	}
	
	
	
	/**
	 * 删除评论
	 */
	public JsonResult delete(int shareCommentId,int shareCommentUserId, int userId) {
		JsonResult result = new JsonResult();
		TShareComment tShareComment = tShareCommentDao.loadById(shareCommentId);
		if (shareCommentUserId == userId) {
			if (tShareComment == null) {
				log.info("评论不存在或已删除");
				result.error("删除失败");
			} else {
				int res = tShareCommentDao.deleteById(shareCommentId);
				if(res > 0) {
					log.info("后台删除评论执行成功");
					result.success("删除成功");
				}else {
					log.info("后台删除评论执行失败");
					result.error("删除失败");
				}
			}
		} else {
			log.info("评论用户不存在或已删除");
			result.error("删除失败");
		}
		return result;
	}
	
}
