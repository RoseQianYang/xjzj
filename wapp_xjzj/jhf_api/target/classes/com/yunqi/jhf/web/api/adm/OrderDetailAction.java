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
import com.yunqi.jhf.service.background.OrderDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@Api(description = "订单详情相关接口")
@RequestMapping(value = "/api/adm/orderDetail")
public class OrderDetailAction {
	
	protected static Logger logger = Logger.getLogger(OrderDetailAction.class);

	@Autowired
	private OrderDetailService orderDetailService;
	
	/**
	 * 根据订单id 查询订单详情
	 * @param JsonInfo
	 * @return
	 */
	@ApiOperation(value = "查询订单详情", notes = "data{List TOrderDetail}", response = JsonResult.class)
	@RequestMapping(value = "/getOrderDetailList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getOrderDetailList(@ApiParam(value = "{orderId}", required = true)@RequestBody ModelMap JsonInfo){
		if (JsonInfo == null) {
			JsonInfo = new ModelMap();
		}
		return orderDetailService.getOrderDetailList(JsonInfo);
	}

	
}
