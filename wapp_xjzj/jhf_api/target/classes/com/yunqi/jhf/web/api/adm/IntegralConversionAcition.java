package com.yunqi.jhf.web.api.adm;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.service.background.IntegralConversionSerivce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lianlh 返点申请相关API接口
 * 
 */
@CrossOrigin
@RestController
@Api(description = "返点申请相关接口")
@RequestMapping(value = "/api/adm/integralConversion")
public class IntegralConversionAcition {

	protected static Logger logger = Logger.getLogger(IntegralConversionAcition.class);
	
	@Autowired
	private IntegralConversionSerivce integralConversionSerivce;
	
	/**
	 * 获取返点申请列表
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "查询返点申请列表", notes = "data{List Map}", response = JsonResult.class)
	@RequestMapping(value = "/getIntegralConversionList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return integralConversionSerivce.getPageList(jsonInfo);
	}
	
	/**
	 * 审核通过返点申请
	 * @return Json
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "审核通过返点申请", notes = "data{List Map}", response = JsonResult.class)
	@RequestMapping(value = "/updateIntegralConversionStatus", method = RequestMethod.POST, produces = "application/json")
	public JsonResult update(@RequestBody ModelMap jsonInfo) throws SQLException {
		
		return integralConversionSerivce.update(jsonInfo);
	}
	
}
