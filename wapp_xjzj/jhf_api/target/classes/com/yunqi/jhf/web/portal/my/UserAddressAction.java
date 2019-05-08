package com.yunqi.jhf.web.portal.my;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.domain.TUserAddress;
import com.yunqi.jhf.dao.persistence.TUserAddressDao;
import com.yunqi.jhf.service.front.TUserAddressService;
import com.yunqi.jhf.web.StrUtil;
import com.yunqi.jhf.web.portal.WechatSessionUser;

/**
 * 前台个人中心用户地址
 * @author llh
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/front/user")
public class UserAddressAction {
	
	@Autowired
	private TUserAddressService tUserAddressService;
	
	@Autowired
	private TUserAddressDao tUserAddressDao;

	/**
	 * 通过userID获取地址详情列表
	 */
	@RequestMapping(value = "/userAddressList")
	public ModelAndView userAddressList(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) throws Exception {
	    ModelAndView mav = new ModelAndView("/my/tuseraddress_list");
	    WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		List<TUserAddress> useraddList = tUserAddressService.getUserAddressList(userId);
		mav.addObject("useraddList", useraddList);
		return mav;
	}

	
	/**
	 * 添加用户地址
	 */
	@RequestMapping(value = "/addUserAddress")
	public ModelAndView add(HttpServletRequest req, HttpSession sess) throws Exception {
		 ModelAndView mav = new ModelAndView("/my/tadduseraddress");
		 return mav;
	}
	@RequestMapping(value = "/insert")
	public JsonResult insert(
			HttpServletRequest req, HttpSession sess) throws Exception {
		JsonResult result = new JsonResult();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		int userId = sessUser.getUserId();
		String consignee = StrUtil.getParamStr(req, "consignee");
		String address = StrUtil.getParamStr(req, "address");
		String addressDetail = StrUtil.getParamStr(req, "addressDetail");
		int isDefault = StrUtil.getParamInt(req, "addressId",-1);
		String mobile = StrUtil.getParamStr(req, "mobile");
		String postcode = StrUtil.getParamStr(req, "postcode");
		result=tUserAddressService.insertUserAdd(userId, consignee, address,addressDetail, isDefault, mobile, postcode);
		return result;
	}
	
	/**
	 * 编辑添加地址信息
	 */
	@RequestMapping(value = "/editUserAddress")
	public ModelAndView edit(HttpServletRequest req,HttpSession sess) throws Exception {
		int useraddressId = StrUtil.getParamInt(req, "useraddId", -1);
		JsonResult result = new JsonResult();
		TUserAddress userAddress = new TUserAddress();
		if (useraddressId>0) {
			userAddress = tUserAddressDao.loadById(useraddressId);
		}else {
			result.error("用户地址id获取失败");
		}
		ModelAndView mav = new ModelAndView("/my/tedituseraddress");
		mav.addObject("userAddress", userAddress);
		return mav;
	}

	/**
	 * 修改地址
	 */
	@RequestMapping(value = "/updateUserAddress")
	public JsonResult update(HttpServletRequest req, HttpSession sess){
		JsonResult result = new JsonResult();
		WechatSessionUser sessUser = WechatSessionUser.getUser(req);
		try {
			int userId = sessUser.getUserId();
			int useraddressId = StrUtil.getParamInt(req, "useraddId", -1);
			String consignee = StrUtil.getParamStr(req,"consignee");
			String address = StrUtil.getParamStr(req,"address");
			String addressDetail = StrUtil.getParamStr(req, "addressDetail");
			int isDefault = StrUtil.getParamInt(req, "addressId",-1);
			String mobile = StrUtil.getParamStr(req,"mobile");
			String postcode = StrUtil.getParamStr(req,"postcode");
			result = tUserAddressService.updateUserAddress(userId,useraddressId, consignee, address, addressDetail, isDefault, mobile, postcode);
		} catch (Exception e) {
			result.error(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		return result;
	}
	
	
	/**
	 * 删除用户地址
	 */
	@RequestMapping(value = "/deleteUserAddress")
	public JsonResult delete(HttpServletRequest req, HttpSession sess)
			throws Exception {
		JsonResult result = new JsonResult();
		int useraddressId = StrUtil.getParamInt(req, "useraddId", -1);
		try {
			result = tUserAddressService.delete(useraddressId);
			result.success("删除用户地址成功！");
		} catch (Exception e) {
			result.error(e.getMessage());
		}
		return result;
	}

}
