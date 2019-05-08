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
import com.yunqi.jhf.dao.domain.TVehicleBrand;
import com.yunqi.jhf.service.background.VehicleBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author wangsong 房车品牌相关API接口
 * 
 */

@CrossOrigin
@RestController
@Api(description = "房车品牌相关接口")
@RequestMapping(value = "/api/adm/vehicleBrand")
public class VehicleBrandAtion {

	protected static Logger logger = Logger.getLogger(VehicleBrandAtion.class);
	
	@Autowired
	private VehicleBrandService vehicleBrandService;

	/**
	 * 获取房车品牌列表 分页
	 * 
	 * @return Json
	 * 
	 */
	@ApiOperation(value = "查询房车品牌列表", notes = "data{List TVehicleBrand}", response = JsonResult.class)
	@RequestMapping(value = "/getVehicleBrandList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getVehicleBrandList(@RequestBody ModelMap JsonInfo) {
		return vehicleBrandService.getVehicleBrandList(JsonInfo);
	}
	
	/**
	 *  获取房车品牌 不分页
	 *  用于房车中取 房车名称
	 *  
	 * @return
	 */
	@ApiOperation(value = "查询房车品牌列表", notes = "data{List TVehicleBrand}", response = JsonResult.class)
	@RequestMapping(value = "/vehicleBrandList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult vehicleBrandList() {
		return vehicleBrandService.vehicleBrandList();
	}

	/**
	 * 新增房车品牌
	 * 
	 * 添加房车品牌 输入brandName  cover
	 * 
	 * @param vehicleBrand
	 *            房车品牌实体
	 * @return Json
	 * 
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "新增房车品牌", notes = "data{brandName,cover}", response = JsonResult.class)
	public JsonResult create(@ApiParam(value = "{brandName,cover}", required = true) @RequestBody TVehicleBrand vehicleBrand){
		
		return vehicleBrandService.create(vehicleBrand);
	}

	/**
	 * 修改房车品牌
	 * 
	 * 修改房车品牌 输入brandName cover
	 * 
	 * @param vehicleBrand
	 *        房车品牌实体
	 * @param id
	 *        房车品牌id
	 * @return Json
	 */
	@RequestMapping(value = "/updateVehicleBrand", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "根据房车品牌id修改信息", notes = "data{brandName,cover}", response = JsonResult.class)
	public JsonResult updateVehicleBrand(@ApiParam(value = "{brandName,cover}" , required = true) @RequestBody TVehicleBrand vehicleBrand) {
		
		return vehicleBrandService.updateVehicleBrand(vehicleBrand);
	}
	
	/**
	 * 删除 房车品牌
	 * @param vehicleBrand
	 * @return
	 */
	@ApiOperation(value = "删除房车品牌", notes = "data{List TVehicleBrand}", response = JsonResult.class)
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public JsonResult delete( @RequestBody TVehicleBrand vehicleBrand) {
		return vehicleBrandService.delete(vehicleBrand);
	}
	
}
