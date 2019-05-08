package com.yunqi.jhf.web.portal.share;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.service.front.TShareCommentService;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

/**
 * 分享评论相关接口
 * @author llh
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/sharecomment")
public class TShareCommentAction {

	@Autowired
	private TShareCommentService tShareCommentService;
	
	/**
	 * 挑转到所有分享页面判断是否是同一个人
	 * @param req
	 * @param sess
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/redirectMyshare")
	public JsonResult redirectMyshare(HttpServletRequest req, HttpSession sess) throws Exception {
		JsonResult result = new JsonResult();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		int shareCommentUserId = StrUtil.getParamInt(req, "shareCommentUserId", -1);
		if (shareCommentUserId == userId) {
			result.error("error");
		} else {
			result.success("success");
		}
		return result;
	}
	
	
	/**
	 * 发布分享评论： 
	 * 		1 点击首次评论      shareId传被评论的帖子id, 
	 * 				    parentCommId传个0, commentContent评论内容,
	 * 
	 *      2 点击给他人回复评论    shareId传被评论的帖子id, 
	 * 					   parentCommId传被评论的用户的id, commentContent评论内容,
	 * @param req
	 * @param sess
	 * @return JsonResult
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/insert")
	public JsonResult insert(HttpServletRequest req, HttpSession sess) throws Exception {
		JsonResult result = new JsonResult();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		String userName = sessUser.getNickname();

		int shareId = StrUtil.getParamInt(req, "shareId", -1);
		int parentCommId = StrUtil.getParamInt(req, "parentCommId", -1);
		String commentContent = StrUtil.getParamStr(req, "commentContent");
		result = tShareCommentService.
				insertShareComment(userId, shareId, parentCommId, commentContent, userName);
		//result.setData(userId);
		return result;
	}
	

	/**
	 * 删除分享评论
	 */
	@RequestMapping(value = "/deleteShareComment")
	public JsonResult delete(HttpServletRequest req, HttpSession sess)
			throws Exception {
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		JsonResult jsonresult = new JsonResult();
		int shareCommentId = StrUtil.getParamInt(req, "shareCommentId", -1);
		int shareCommentUserId = StrUtil.getParamInt(req, "shareCommentUserId", -1);
		try {
			jsonresult = tShareCommentService.delete(shareCommentId,shareCommentUserId,userId);
		} catch (Exception e) {
			jsonresult.error(e.getMessage());
		}
		return jsonresult;
	}

}
