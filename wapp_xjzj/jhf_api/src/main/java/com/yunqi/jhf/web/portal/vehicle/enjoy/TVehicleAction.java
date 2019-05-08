package com.yunqi.jhf.web.portal.vehicle.enjoy;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.domain.TVehicle;
import com.yunqi.jhf.dao.domain.TVehicleBrand;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.service.front.TVehicleBrandService;
import com.yunqi.jhf.service.front.TVehicleService;
import com.yunqi.jhf.web.SplitStrUtil;
import com.yunqi.jhf.web.StrUtil;

import io.swagger.annotations.ApiOperation; 

/**
 * @author WangSong
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/vehicle/enjoy")
public class TVehicleAction{

	@Autowired
	private TVehicleService tVehicleService;
	
	@Autowired
	private TVehicleBrandService tVehicleBrandService;
	
	
	private static final Logger logger = Logger.getLogger(TVehicleAction.class);
	
	/**
	 * 房车品鉴首页列表
	 * @param req
	 * @param sess
	 * @param model
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/TVehicleHomeAction")
	public ModelAndView vehicleHome(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView("/vehicle/enjoy/vehicleHome");
		try {
			//获取房车品鉴列表，按时间先后排序，然后选前二个在首页展示
			List<TVehicle> twoVehicleLists = tVehicleService.getTwoVehicleLists();
			
			//获取房 当季促销的房车，按时间先后排序，然后选前二个在首页展示
			List<TVehicle> twoVehicleListsYesIsSales = tVehicleService.getTwoVehicleListsYesIsSales();
			
			//获取房 二手置换的房车列表 ，按时间先后排序，然后选前二个在首页展示
			List<TVehicle> twoVehicleListsYesIsSencond = tVehicleService.getTwoVehicleListsYesIsSencond();
			
			
			// 获取房车品牌列表，按时间先后排序，然后选前五个 房车品牌 logo 在首页展示
			List<TVehicleBrand> fiveVehicleBrandList = tVehicleBrandService.getFiveVehicleBrandList();
		
			mav.addObject("twoVehicleLists", twoVehicleLists);
			mav.addObject("twoVehicleListsYesIsSales",twoVehicleListsYesIsSales);
			mav.addObject("twoVehicleListsYesIsSencond",twoVehicleListsYesIsSencond);
			mav.addObject("fiveVehicleBrandList",fiveVehicleBrandList);
			logger.info("获取房车品鉴列表成功");
		} catch (Exception e) {
			logger.info("获取房车品鉴列表失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * 房车品鉴列表
	 * @param req
	 * @param sess
	 * @param model
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/TVehicleListAction")
	public ModelAndView vehicleList(HttpServletRequest req, HttpSession sess,
			Model model, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView("/vehicle/enjoy/vehicleList");
		int page = StrUtil.getParamInt(req, "page", 1);
		int status = StrUtil.getParamInt(req, "status", 1);
		int vehicleBrandId = StrUtil.getParamInt(req, "vehicleBrandId", -1);
		try {
			//根据当前页   获取所有房车品鉴 分页列表   status状态  1 房车所有列表 2 当季促销列表 3 二手置换 vehicleBrandId 获取房车列表
			PageList<TVehicle> vehickeLists = tVehicleService.getVehicleLists(page,status,vehicleBrandId);
			mav.addObject("vehickeLists", vehickeLists);
			logger.info("获取房车品鉴列表成功");
		} catch (Exception e) {
			logger.info("获取房车品鉴列表失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return mav;
	}
	
	/**
	 * 异步获取 房车品鉴列表
	 * @param req
	 * @param sess
	 * @param JsonInfo
	 * @return Json
	 */
	@ApiOperation(value = "查询房车列表", notes = "data{List TVehicle}", response = JsonResult.class)
	@RequestMapping(value = "/getVehiclePageLists", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getVehiclePageLists(@RequestBody ModelMap JsonInfo) {
		
		return tVehicleService.getVehiclePageLists(JsonInfo);
	}
	
	
	/**
	 * 根据 vehicleId 获取 房车详情
	 * @param req
	 * @param sess
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/vehicleDetailList")
	public ModelAndView vehicleDetailList(HttpServletRequest req, HttpSession sess, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView("/vehicle/enjoy/vehicleDetailList");
		int vehicleId = StrUtil.getParamInt(req, "vehicleId", -1);
		try {
			TVehicle vehicleById = tVehicleService.getVehicleById(vehicleId);
			// 素材图片
			List<String> imgsrcList=new ArrayList<String>();
			if (vehicleById != null) {
				String imageId = vehicleById.getImageId();
				imgsrcList =  SplitStrUtil.splitStrToStr(imageId);
				if (imgsrcList.size()>4) {
					imgsrcList = imgsrcList.subList(0, 4);
					
				}
			}
			mav.addObject("vehicleById", vehicleById);
			mav.addObject("imgSrcList", imgsrcList);
			logger.info("获取房车详情成功");
		} catch (Exception e) {
			logger.info("获取房车详情失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return mav;
	}
	
}
