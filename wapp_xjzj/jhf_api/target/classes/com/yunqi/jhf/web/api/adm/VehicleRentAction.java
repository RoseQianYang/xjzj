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
import com.yunqi.jhf.dao.domain.TVehicleRent;
import com.yunqi.jhf.service.background.VehicleRentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@Api(description = "房车租赁相关接口")
@RequestMapping(value = "/api/adm/vehicleRent")
public class VehicleRentAction {

	@Autowired
	private VehicleRentService vehicleRentService;
	
	protected static Logger logger = Logger.getLogger(VehicleRentAction.class);
	
	/**
	 * 根据房车名称(模糊搜索)获取房车租赁列表
	 * 
	 * @param vehicleName
	 *            房车名称 
	 * 
	 * @return Json 
	 */
	@ApiOperation(value = "查询房车租赁列表", notes = "data{List VehicleRent}", response = JsonResult.class)
	@RequestMapping(value = "/getVehicleRentList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getVehicleRentList(@ApiParam(value = "{vehicleName}", required = true)@RequestBody ModelMap JsonInfo) {
		if (JsonInfo == null) {
			JsonInfo = new ModelMap();
		}
		return vehicleRentService.getVehicleRentList(JsonInfo);
	}


	/**
	 * 新增 房车租赁
	 * 
	 * @return Json
	 */
	@ApiOperation(value = "新增", notes = "新增房车租赁,添加房车租赁所有属性 TvehicleRent", response = JsonResult.class)
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public JsonResult create(
			@ApiParam(value = "{vehicleName rentPrice address phone}") @RequestBody TVehicleRent vehicleRent) {
		
		return vehicleRentService.create(vehicleRent);
	}

	/**
	 * 修改房车租赁信息
	 * @param vehicleRent
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "data{List TVehicleRent}", response = JsonResult.class)
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public JsonResult update(
			@ApiParam(value = "{ rentPrice address phone}") @RequestBody TVehicleRent vehicleRent) {
		return vehicleRentService.update(vehicleRent);
	}
	
	/**
	 * 
	 * 物理删除 房车租赁
	 * @param vehicleRent
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "data{List TVehicleRent}", response = JsonResult.class)
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public JsonResult delete( @RequestBody TVehicleRent vehicleRent) {
		return vehicleRentService.delete(vehicleRent);
	}
	
}
