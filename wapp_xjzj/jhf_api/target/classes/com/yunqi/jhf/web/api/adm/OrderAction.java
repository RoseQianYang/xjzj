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
import com.yunqi.jhf.dao.domain.TOrder;
import com.yunqi.jhf.service.background.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@Api(description = "订单相关接口")
@RequestMapping(value = "/api/adm/order")
public class OrderAction {
	
	protected static Logger logger = Logger.getLogger(OrderAction.class);
	
	@Autowired
	private OrderService orderService;
	
	
	/**
	 * 根据 用户id、用户名称、订单编号 (模糊查询)查询订单列表
	 * 
	 * @param JsonInfo
	 * @return
	 */
	@ApiOperation(value = "查询订单列表", notes = "data{List TOrder}", response = JsonResult.class)
	@RequestMapping(value = "/getOrderList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getOrderList(@RequestBody ModelMap JsonInfo){
		return orderService.getOrderList(JsonInfo);
	}
	
	/**
	 * 修改订单信息
	 * 
	 * @param logisticsName 物流名称
	 *        logisticsNo   物流编号
	 *        orderStatus   物流状态
	 *        
	 * @return Json
	 */
	@ApiOperation(value = "修改订单信息", notes = "data{data TOrder}", response = JsonResult.class)
	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST, produces = "application/json")
	public JsonResult updateOrder(
			@ApiParam(value = "{logisticsName,logisticsNo,orderStatus}", required = true) @RequestBody TOrder order) {
		return orderService.updateOrder(order);
	}
	
}
