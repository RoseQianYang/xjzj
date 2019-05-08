package com.yunqi.jhf.web.portal.my.team;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.jhf.dao.domain.TUser;
import com.yunqi.jhf.service.front.TUserFriendService;
import com.yunqi.jhf.web.portal.WechatSessionUser;

/**
 * 团队相关
 * 
 * @author seek
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/friend")
public class TFriendAction {
	@Autowired
	private TUserFriendService tUserFriendService;
	

	/**
	 * 获取好友列表
	 */
	@RequestMapping(value = "/friendList")
	public ModelAndView friendList(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView("my/my_team/tfriend");
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		List<TUser> twoFriendList = tUserFriendService.getUserFriendTwo(userId);
		List<TUser> ThreeFriendList = tUserFriendService.getUserFriendThree(userId);
		
		mav.addObject("twoFriendList", twoFriendList);
		mav.addObject("ThreeFriendList", ThreeFriendList);
		return mav;
	}
}
