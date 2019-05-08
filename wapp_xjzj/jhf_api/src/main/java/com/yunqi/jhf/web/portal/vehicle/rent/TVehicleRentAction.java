package com.yunqi.jhf.web.portal.vehicle.rent;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.service.front.TVehicleRentService;
import com.yunqi.jhf.vo.VehicleRentBean;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.StrUtil;

/**
 * @author WangSong
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/vehicle/rent")
public class TVehicleRentAction {

	protected static Logger logger = Logger.getLogger(TVehicleRentAction.class);
	
	@Autowired
	private TVehicleRentService tVehicleRentService;
	

	/**
	 * 根据当前页   获取 房车租赁列表
	 * @return 页面vm
	 */
	@RequestMapping(value = "/TVehicleRentAction")
	public ModelAndView vehicleRentList(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) {
		ModelAndView mav = new ModelAndView("/vehicle/rent/vehicleRentlist");
		int page = StrUtil.getParamInt(req, "page", 1);
		try {
			PageList<VehicleRentBean> tvehicleRentList = tVehicleRentService.getTvehicleRentList(page);
			mav.addObject("tvehicleRentList", tvehicleRentList);
			logger.info("微信端获取房车租赁列表成功");
		} catch (Exception e) {
			logger.info("微信端获取房车租赁列表失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return mav;
	}
	
    /**
     * 异步获取 房车租赁列表
     * @param JsonInfo
     * @return json
     */
	@RequestMapping(value = "/getTvehicleRentPageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getTvehicleRentPageList(@RequestBody ModelMap JsonInfo) {
		
		return tVehicleRentService.getTvehicleRentPageList(JsonInfo);
	}
	
	/**
	 * 
	 * 根据 vehicleRentId 获取房车租赁详情信息
     * @param vehicleRentId 
     * @return VehicleRentBean对象
	 */
	@RequestMapping(value = "/TVehicleRentDetail")
	public ModelAndView vehicleRentDetail(HttpServletRequest req,
			HttpSession sess,HttpServletResponse res) {
		ModelAndView mav = new ModelAndView("/vehicle/rent/vehicleRentDetail");
		try {
			int vehicleRentId = StrUtil.getParamInt(req, "vehicleRentId",-1);
			VehicleRentBean tvehicleRentById = tVehicleRentService.getTvehicleRentById(vehicleRentId);
			// 房车素材图片
		    List<String> imgSrcList = null;
		    if (tvehicleRentById != null) {
		    	String imageId = tvehicleRentById.getImageId();
		    	imgSrcList =  SplitStrUtil.splitStrToStr(imageId);
				if (imgSrcList.size()>=4) {
					imgSrcList = imgSrcList.subList(0, 4);
				}
			}
			mav.addObject("tvehicleRentById", tvehicleRentById);
			mav.addObject("imgSrcList", imgSrcList);
			logger.info("微信端获取房车租赁详情成功");
		} catch (Exception e) {
			logger.info("微信端获取房车租赁详情失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return mav;
	}

}
