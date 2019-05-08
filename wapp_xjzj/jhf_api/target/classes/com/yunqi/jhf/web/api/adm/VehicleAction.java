package com.yunqi.jhf.web.api.adm;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.domain.TVehicle;
import com.yunqi.jhf.service.background.VehicleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 *  
 * @author wangsong  房车相关API接口
 *
 */
@CrossOrigin
@RestController
@Api(description = "房车品鉴相关接口")
@RequestMapping(value = "/api/adm/vehicle")
public class VehicleAction {

	protected static Logger logger = Logger.getLogger(VehicleAction.class);
	
	@Autowired
	private VehicleService vehicleService;

	/**
	 * 根据房车名称(模糊搜索)获取房车列表
	 * 
	 * @param vehicleName
	 *            房车名称 
	 * 
	 * @return Json 
	 */
	@ApiOperation(value = "查询房车列表", notes = "data{List TVehicle}", response = JsonResult.class)
	@RequestMapping(value = "/getVehicleList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getVehicleList(@ApiParam(value = "{vehicleName}", required = true)@RequestBody ModelMap JsonInfo) {
		if (JsonInfo == null) {
			JsonInfo = new ModelMap();
		}
		return vehicleService.getVehicleList(JsonInfo);
	}

	
	/**
	 *  获取房车品牌 不分页
	 *  用于房车中取 房车名称
	 *  
	 * @return
	 */
	@ApiOperation(value = "房车列表", notes = "data{List TVehicle}", response = JsonResult.class)
	@RequestMapping(value = "/vehicleList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult vehicleList() {
		return vehicleService.vehicleList();
	}
	

	/**
	 * 新增 房车
	 * 
	 * @return Json
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "新增", notes = "新增房车,添加房车所有属性 Tvehicle", response = JsonResult.class)
	public JsonResult create(
			@ApiParam(value = "{vehicleName cover  price spec gears isSencond isSales functionType license bedNum}") @RequestBody TVehicle vehicle) {
		
		return vehicleService.create(vehicle);
	}

	/**
	 * 修改房车信息 update:
	 * 
	 * @param vehicleId
	 *            房车Id
	 * @return Json
	 */
	
	@ApiOperation(value = "修改", notes = "修改房车信息 Tvehicle", response = JsonResult.class)
	@RequestMapping(value = "/updateVehicle", method = RequestMethod.POST, produces = "application/json")
	public JsonResult updateVehicle(
			@ApiParam(value = "{vehicleName cover introduction price spec gears isSencond isSales functionType license bedNum}") @RequestBody TVehicle vehicle) {
		return vehicleService.updateVehicle(vehicle);
	}
	
	/**
	 * 删除 房车
	 * @param vehicle
	 * @return
	 */
	@ApiOperation(value = "删除房车", notes = "data{List TVehicleBrand}", response = JsonResult.class)
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public JsonResult delete( @RequestBody TVehicle vehicle) {
		return vehicleService.delete(vehicle);
	}
	
}
