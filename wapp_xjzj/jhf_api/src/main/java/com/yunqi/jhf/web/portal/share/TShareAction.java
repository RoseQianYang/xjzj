package com.yunqi.jhf.web.portal.share;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.service.ImageFileService;
import com.yunqi.jhf.service.front.TShareCommentService;
import com.yunqi.jhf.service.front.TShareService;
import com.yunqi.jhf.vo.ShareBean;
import com.yunqi.jhf.vo.ShareCommentBean;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

/**
 * 分享相关接口
 * @author llh
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/share")
public class TShareAction {
	
	private static final Logger log = Logger.getLogger(TShareAction.class);

	@Autowired
	private TShareService tShareService;
	
	@Autowired
	private ImageFileService imageFileService;
	
	@Autowired
	private TShareCommentService tShareCommentService;
	
	/**
	 * 按照条件查询的分享列表
	 */
	@RequestMapping(value = "/shareList")
	public ModelAndView shareList(HttpServletRequest req, HttpSession sess)  {
	    ModelAndView mav = new ModelAndView("/share/tshare_list"); 
	    int page = StrUtil.getParamInt(req, "page", 1);
		int cateId = StrUtil.getParamInt(req, "cateId", -1);
		String suserName = StrUtil.getParamStr(req, "suserName",null);
		PageList<ShareBean> sharePageList = new PageList<ShareBean>();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		try{
			//获取所有分享列表
	    	sharePageList = tShareService.sharePageList(page,cateId,suserName);
	    	
	    	if (sharePageList != null) {
	    		for (ShareBean share : sharePageList.getList()) {
	    			//获取分享图片
					String shareImg = share.getContentImage();
					List<String> imageList = SplitStrUtil.splitStrToStr(shareImg);
					share.setImages(imageList);
					//查找此分享下的所有评论
					int shareId = share.getId();
					List<ShareCommentBean> shareAllCommList = 
								tShareCommentService.getShareCommentBySid(shareId);
					share.setCommentCount(shareAllCommList.size());
					List<ShareCommentBean> shareCommList = new ArrayList<ShareCommentBean>();
					if (shareAllCommList.size() >= 3) {
						shareCommList = shareAllCommList.subList(0, 3);
						//调用showShareComment展示分享的评论
						tShareCommentService.showShareComment(share, shareCommList);
					} else {
						tShareCommentService.showShareComment(share, shareAllCommList);
					}
				} 
			}
	    }catch(Exception e){
	    	log.error(e.getMessage(), e);
	    	log.info("分享调用失败");
			e.printStackTrace(); 
	    }
	    mav.addObject("sharePageList", sharePageList);
	    mav.addObject("suserName", suserName);
	    mav.addObject("cateId", cateId);
	    mav.addObject("sessUser", sessUser);
		return mav;
	}
	
	
	/**
	 * 异步获取 分享列表
	 * @param req
	 * @param sess
	 * @param JsonInfo
	 * @return Json
	 */
	@RequestMapping(value = "/getSharePageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getSharePageList(@RequestBody ModelMap JsonInfo) {
		return tShareService.getSharePageList(JsonInfo);
	}
	
	
	/**
	 * 我的分享列表
	 */
	@RequestMapping(value = "/myShareList")
	public ModelAndView myShareList(HttpServletRequest req, HttpSession sess)  {
	    ModelAndView mav = new ModelAndView("/share/tmyshare_list"); 
	    WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
	    int page = StrUtil.getParamInt(req, "page", 1);
		PageList<ShareBean> sharePageList = new PageList<ShareBean>();
		try{
	    	sharePageList = tShareService.mySharePageList(page,userId);
	    	if (sharePageList != null) {
	    		for (ShareBean share : sharePageList.getList()) {
	    			
					String shareImg = share.getContentImage();
					List<String> imageList = SplitStrUtil.splitStrToStr(shareImg);
					share.setImages(imageList);
					
					int shareId = share.getId();
					List<ShareCommentBean> shareAllCommList = tShareCommentService.getShareCommentBySid(shareId);
					share.setCommentCount(shareAllCommList.size());
					
					List<ShareCommentBean> shareCommList = new ArrayList<>();
					if (shareAllCommList.size() >= 3) {
						shareCommList = shareAllCommList.subList(0, 3);
						tShareCommentService.showShareComment(share, shareCommList);
					} else {
						tShareCommentService.showShareComment(share, shareAllCommList);
					}
				} 
			}
	    }catch(Exception e){
	    	log.error(e.getMessage(), e);
	    	log.info("分享调用失败");
			e.printStackTrace(); 
	    }
	    mav.addObject("sharePageList", sharePageList);
	    mav.addObject("sessUser", sessUser);
		return mav;
	}
	
	/**
	 * 异步获取 我的分享列表
	 * @param req
	 * @param sess
	 * @param JsonInfo
	 * @return Json
	 */
	@RequestMapping(value = "/getMySharePageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getMySharePageList(HttpServletRequest req,@RequestBody ModelMap JsonInfo) {
		return tShareService.getMySharePageList(req,JsonInfo);
	}
	
	
	/**
	 * 分享的详情列表
	 */
	@RequestMapping(value = "/getShareDetail")
	public ModelAndView getShareDetail(HttpServletRequest req, HttpSession sess){
	    ModelAndView mav = new ModelAndView("/share/tsharedetail"); 
	    ShareBean share = new ShareBean();
	    int shareId = StrUtil.getParamInt(req, "shareId", -1);
	    WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		try{
			share = tShareService.shareDetail(shareId);
	    	if (share != null) {
				List<String> imageList = SplitStrUtil.splitStrToStr(share.getContentImage());
				share.setImages(imageList);
				List<ShareCommentBean> shareCommList = 
								tShareCommentService.getShareCommentBySid(shareId);
				tShareCommentService.showShareComment(share, shareCommList);
			}
	    }catch(Exception e){
	    	log.error(e.getMessage(), e);
	    	log.info("分享调用失败");
			e.printStackTrace(); 
	    }
	    mav.addObject("share", share);
	    mav.addObject("sessUser", sessUser);
		return mav;
	}
	
	
	/**
	 * 添加用户分享
	 */
	@RequestMapping(value = "/addShare")
	public ModelAndView add(HttpServletRequest req, HttpSession sess) throws Exception {
		 ModelAndView mav = new ModelAndView("/share/taddshare");
		 return mav;
	}
	@RequestMapping(value = "/insert")
	public JsonResult insert(HttpServletRequest req, HttpSession sess) {
		JsonResult result = new JsonResult();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		int cateId = StrUtil.getParamInt(req, "cateId", -1);
		String contentText = req.getParameter("contentText");
		String contentImgId = req.getParameter("contentImgId");
		String publishAddress = req.getParameter("publishAddress");
		result = tShareService.
				insertShare(userId, cateId, contentText, contentImgId, publishAddress);
		return result;
	}
	/**
	 * 上传图片
	 * @param imageFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doUploadImg")
	public JsonResult doUpload(
			@RequestParam(required = false, value = "file") MultipartFile imageFile) {
		JsonResult result = new JsonResult();
		try {
			try {
				String imageUri = imageFileService.uploadImageFile(imageFile, Const.UPLOAD_BASE_DIR);
				result.success("上传成功").setData(imageUri);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				throw new Exception("您好，请上传 10M以内的图片");
			}
			log.info(result);
		} catch (Exception e) {
			log.error(e);
			result.error("上传失败:" + e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 删除分享
	 */
	@RequestMapping(value = "/deleteShare")
	public JsonResult delete(HttpServletRequest req, HttpSession sess)
			throws Exception {
		JsonResult result = new JsonResult();
		int shareId = StrUtil.getParamInt(req, "shareId", -1);
		try {
			result = tShareService.delete(shareId);
			result.success("删除此分享成功！");
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		return result;
	}

	
	
}
