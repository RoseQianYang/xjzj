package com.yunqi.jhf.service.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.ShareCommentDao;
import com.yunqi.jhf.dao.ShareDao;
import com.yunqi.jhf.dao.domain.TShare;
import com.yunqi.jhf.dao.domain.TShareComment;
import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.dao.persistence.TShareCommentDao;
import com.yunqi.jhf.dao.persistence.TShareDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlDelete;
import com.yunqi.jhf.vo.ShareBean;
import com.yunqi.jhf.vo.ShareCommentBean;
import com.yunqi.jhf.web.SensitiveWord;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;



@Service
public class TShareService {

	private static final Logger log = Logger.getLogger(TShareService.class);

	@Resource
	private TShareDao tShareDao;
	
	@Resource
	private ShareDao shareDao;
	
	@Resource
	private TUserService tUserService;
	
	@Resource
	private TShareCommentDao tShareCommentDao;
	
	@Resource
	private ShareCommentDao shareCommentDao;
	
	/**
	 * 根据传入参数来进行查询分享列表
	 * @param   page     当前页
	 * @param   cateId   1:结伴出游     2:营地分享
	 * @param   suserName   用户名
	 * @return  PageList
	 */
	public PageList<ShareBean> sharePageList(int page,int cateId,String suserName) {
		ModelMap jsonInfo = new ModelMap();
		JsonResult result = new JsonResult();
		PageList<ShareBean> pageList = new PageList<ShareBean>();
		try {
			if (page != -1) {
				pageList.setCurrentPage(page);
				pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
				jsonInfo.put("page",pageList.getFromPos());
				jsonInfo.put("size",Const.TEST_FONT_PAGE_SIZE);																			;
				if (cateId != -1) {
					jsonInfo.put("cateId", cateId);
					List<ShareBean> sharecommentlist = 
											shareDao.getShareByNameOrCate(jsonInfo);
					pageList.setList(sharecommentlist);
					pageList.setTotalSize(shareDao.getShareByCount(jsonInfo));
				} else if (suserName!=null&&!"null".equals(suserName)) {
					jsonInfo.put("suserName", suserName);
					List<ShareBean> sharecommentlist = 
											shareDao.getShareByNameOrCate(jsonInfo);
					pageList.setList(sharecommentlist);
					pageList.setTotalSize(shareDao.getShareByCount(jsonInfo));
				} else if ("null".equals(suserName)) {
					List<ShareBean> sharecommentlist = 
											shareDao.getShareAll(jsonInfo);
					pageList.setList(sharecommentlist);
					pageList.setTotalSize(shareDao.getShareAllCount(jsonInfo));
				} else {
					List<ShareBean> sharecommentlist = 
											shareDao.getShareAll(jsonInfo);
					pageList.setList(sharecommentlist);
					pageList.setTotalSize(shareDao.getShareAllCount(jsonInfo));
				}
			}	
		} catch (Exception e) {
			result.error("获取分享列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;
	}
	
	
	/**
	 * 异步 获取 根据传入参数来进行查询分享列表
	 * 
	 * @param jsonInfo
	 * @param   page     当前页
	 * @param   cateId   1:结伴出游     2:营地分享
	 * @param   suserName   用户名
	 * @return Json
	 */
	public JsonResult getSharePageList(ModelMap jsonInfo) {
		JsonResult result = new JsonResult();
		PageList<ShareBean> pageList = new PageList<ShareBean>();
		try {
			if (jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) != null) {
				pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			} 
			pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			jsonInfo.put("page", pageList.getFromPos());
			jsonInfo.put("size",Const.TEST_FONT_PAGE_SIZE);	
			
			if (jsonInfo.get("cateId") != null) {
				jsonInfo.put("cateId", jsonInfo.get("cateId"));
				List<ShareBean> sharecommentlist = 
										shareDao.getShareByNameOrCatePage(jsonInfo);
				if (sharecommentlist.size() != 0) {
					pageList.setList(sharecommentlist);
					pageList.setTotalSize(shareDao.getShareByCount(jsonInfo));
					if (pageList.getTotalPage() != 0) {
						showShare(pageList);
						result.success("分享获取成功");
						result.setData(pageList);
						log.info("分享获取成功");
					} 
				} else {
					log.info("分享获取失败");
					result.setData(pageList);
					return result.error("分享获取失败");
				}
			} else if (jsonInfo.get("suserName")!=null&&!"null".equals(jsonInfo.get("suserName"))) {
				jsonInfo.put("suserName", jsonInfo.get("suserName"));
				List<ShareBean> sharecommentlist = 
										shareDao.getShareByNameOrCatePage(jsonInfo);
				if (sharecommentlist.size() != 0) {
					pageList.setList(sharecommentlist);
					pageList.setTotalSize(shareDao.getShareByCount(jsonInfo));
					if (pageList.getTotalPage() != 0) {
						showShare(pageList);
						result.success("分享获取成功");
						result.setData(pageList);
						log.info("分享获取成功");
					} 
				} else {
					log.info("分享获取失败");
					result.setData(pageList);
					return result.error("分享获取失败");
				}
			} else if ("null".equals(jsonInfo.get("suserName"))) {
				List<ShareBean> sharecommentlist = 
										shareDao.getShareAll(jsonInfo);
				if (sharecommentlist.size() != 0) {
					pageList.setList(sharecommentlist);
					pageList.setTotalSize(shareDao.getShareAllCount(jsonInfo));
					if (pageList.getTotalPage() != 0) {
						showShare(pageList);
						result.success("分享获取成功");
						result.setData(pageList);
						log.info("分享获取成功");
					} 
				} else {
					log.info("分享获取失败");
					result.setData(pageList);
					return result.error("分享获取失败");
				}
			} else {
				List<ShareBean> sharecommentlist = 
										shareDao.getShareAll(jsonInfo);
				if (sharecommentlist.size() != 0) {
					pageList.setList(sharecommentlist);
					pageList.setTotalSize(shareDao.getShareAllCount(jsonInfo));
					if (pageList.getTotalPage() != 0) {
						showShare(pageList);
						result.success("分享获取成功");
						result.setData(pageList);
						log.info("分享获取成功");
					} 
				} else {
					log.info("分享获取失败");
					result.setData(pageList);
					return result.error("分享获取失败");
				}
			}
		} catch (Exception e) {
			result.error("获取分享列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 查询我的分享列表
	 * @param   page     当前页
	 * @param   userId   当前用户id
	 * @return  PageList
	 */
	public PageList<ShareBean> mySharePageList(int page,int userId) {
		ModelMap jsonInfo = new ModelMap();
		JsonResult result = new JsonResult();
		PageList<ShareBean> pageList = new PageList<ShareBean>();
		try {
			if (page != -1) {
				pageList.setCurrentPage(page);
				pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
				jsonInfo.put("page",pageList.getFromPos());
				jsonInfo.put("size",Const.TEST_FONT_PAGE_SIZE);
				jsonInfo.put("userId", userId);
				List<ShareBean> sharelist = 
										shareDao.getShareByNameOrCate(jsonInfo);
				pageList.setList(sharelist);
				pageList.setTotalSize(shareDao.getShareByCount(jsonInfo));
			}
		} catch (Exception e) {
			result.error("获取我的分享列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return pageList;
	}
	
	/**
	 * 异步 获取 根据传入参数来进行查询我的分享列表
	 * 
	 * @param jsonInfo
	 * @param   page     当前页
	 * @param   userId   当前用户id
	 * @return Json
	 */
	public JsonResult getMySharePageList(HttpServletRequest req,ModelMap jsonInfo) {
 		JsonResult result = new JsonResult();
		PageList<ShareBean> pageList = new PageList<ShareBean>();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		try {
			if ((int)jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE) >= 2) {
				pageList.setCurrentPage((int) jsonInfo.get(Const.FONT_PAGE_CURRENTPAGE));
			} 
			pageList.setPageSize(Const.TEST_FONT_PAGE_SIZE);
			jsonInfo.put("page", pageList.getFromPos());
			jsonInfo.put("size",Const.TEST_FONT_PAGE_SIZE);	
			jsonInfo.put("userId", userId);
			List<ShareBean> sharelist = 
									shareDao.getShareByNameOrCatePage(jsonInfo);
			if (sharelist.size() != 0) {
				pageList.setList(sharelist);
				pageList.setTotalSize(shareDao.getShareByCount(jsonInfo));
				if (pageList.getTotalPage() != 0) {
					showShare(pageList);
					result.success("我的分享获取成功");
					result.setData(pageList);
					log.info("我的分享获取成功");
				} 
			} else {
				log.info("我的分享获取失败");
				result.setData(pageList);
				return result.error("我的分享获取失败");
			}
		} catch (Exception e) {
			result.error("获取我的分享列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 首页分享列表
	 * @return  List
	 */
	public List<ShareBean> indexShareList() {
		JsonResult result = new JsonResult();
		List<ShareBean> sharelist = new ArrayList<ShareBean>();
		try {
			sharelist = shareDao.getIndexShareList();
		} catch (Exception e) {
			result.error("获取首页分享列表失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return sharelist;
	}
	
	
	/**
	 * 分享详情
	 * @param shareId 分享id
	 * @return  ShareBean
	 */
	public ShareBean shareDetail(int shareId) {
		JsonResult result = new JsonResult();
		ShareBean share = new ShareBean();
		ModelMap jsonInfo = new ModelMap();
		try {
			if (shareId != -1) {
				jsonInfo.put("shareId", shareId);
				share = shareDao.getShareOne(shareId);
			} else {
				result.error("获取分享id失败");
				log.error("获取分享id失败");
			}
		} catch (Exception e) {
			result.error("获取分享详情失败");
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return share;
	}
	
	
	/**
	 * 发布分享
	 * @param userId 当前用户id
	 * @param cateId 发布分享的类别
	 * @param contentText 分享内容
	 * @param contentImgId  分享的图片 
	 * @param publishAddress 发布地点
	 * @return JsonResult
	 */
	public JsonResult insertShare(int userId, int cateId, String contentText, 
			String contentImgId,String publishAddress) {
		JsonResult result = new JsonResult();
		try{
			if(userId > 1){
				TShare share = new TShare();
				share.setUserId(userId);
				share.setIsShow(ConstantTool.ISDELETE_NO);
				share.setIsEnabled(ConstantTool.ISDELETE_NO);
				if (cateId != -1) {
					share.setCategoryId(cateId);
				} else {
					log.error("分享类别不能为空");
					result.error("分享类别不能为空");
				}
				if (contentImgId!=null||"".equals(contentImgId)) {
					share.setContentImgId(contentImgId); 
				} 
				if (publishAddress!=null||"".equals(publishAddress)) {
					share.setPublishAddress(publishAddress);
				} else {
					log.error("发布地址不能为空");
					result.error("发布地址不能为空");
				}
				
				if (contentText!=null||"".equals(contentText)) {
					SensitiveWord.InitializationWork();
					String strStar = SensitiveWord.filterInfo(contentText);
					if (strStar.contains("*")) {
						log.error("分享内容含有敏感字，不可分享");
						result.error("分享内容含有敏感字，不可分享");
					} else {
						share.setContentText(contentText);
						int ishare = tShareDao.insert(share);
						if (ishare>0) {
							log.info("用户分享添加成功");
							result.success("用户分享添加成功");
						}
					}
				} else {
					log.error("分享内容不能为空");
					result.error("分享内容不能为空");
				}
			}
		}catch(Exception e){
				result.error("分享失败");
				log.error(e.getMessage(), e);
				e.printStackTrace();
		 }
		return result;
	}
	
	/**
	 * 删除分享及评论
	 */
	public JsonResult delete(int shareId) {
		JsonResult result = new JsonResult();
		TShare tShare = tShareDao.loadById(shareId);
		TShareComment tShareComment = new TShareComment();
		if (tShare == null) {
			log.info("分享及评论不存在或已删除");
			result.error("删除失败");
		} else {
			tShareComment.setShareId(tShare.getId());
			SqlDelete sqlDelete = new SqlDelete().where(TShareComment.SQL_shareId);
			tShareCommentDao.delete(sqlDelete, tShareComment);
			int res = tShareDao.deleteById(shareId);
			if(res > 0) {
				log.info("后台删除分享及评论执行成功");
				result.success("删除成功");
			}else {
				log.info("后台删除分享及评论执行失败");
				result.error("删除失败");
			}
		}
		return result;
	}
	
	
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
	 * 显示获取的分享下的所有信息
	 * @param List<ShareCommentBean>
	 */
	
	public void showShare(PageList<ShareBean> pageList) {
		for (ShareBean share : pageList.getList()) {
			String shareImg = share.getContentImage();
			List<String> imageList = SplitStrUtil.splitStrToStr(shareImg);
			share.setImages(imageList);
			int shareId = share.getId();
			List<ShareCommentBean> shareAllCommList = getShareCommentBySid(shareId);
			share.setCommentCount(shareAllCommList.size());
			
			List<ShareCommentBean> shareCommList = new ArrayList<>();
			if (shareAllCommList.size() >= 3) {
				shareCommList = shareAllCommList.subList(0, 3);
				showShareComment(share, shareCommList);
			} else {
				showShareComment(share, shareAllCommList);
			}
		} 
	}
	
	
	
}
