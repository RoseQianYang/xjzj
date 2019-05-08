package com.yunqi.jhf.web.api.adm;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.json.SuccessResult;
import com.yunqi.jhf.dao.domain.TShoppingCart;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author lianlh 购物车相关API接口
 * 
 */

@CrossOrigin
@RestController
@Api(description = "购物车相关接口")
@RequestMapping(value = "/api/adm/shoppingcart")
public class ShoppingCartAction {

	protected static Logger logger = Logger.getLogger(ShoppingCartAction.class);

	/**
	 * 获取购物车列表
	 * 
	 * @param page
	 *        页数
	 * @return Json
	 * @throws SQLException
	 */
	@ApiOperation(value = "查询购物车列表", notes = "data{List TShoppingCart}", response = JsonResult.class)
	@RequestMapping(value = "/pageList/{page}", method = RequestMethod.GET, produces = "application/json")
	public JsonResult getList(@PathVariable int page) throws SQLException {

		List<TShoppingCart> list = new ArrayList<TShoppingCart>();
		
		for (int i = 0; i < 10; i++) {
			TShoppingCart tsh1 = new TShoppingCart();
			tsh1.setId(10001+i);
			tsh1.setUserId(10001);
			tsh1.setCreateTime(new Timestamp(System.currentTimeMillis()));
			list.add(tsh1);
		}
		return new SuccessResult().setData(list);
	}

	/**
	 * 根据购物车id查询购物车详情，直接set一个对象到data中
	 *
	 * @param shoppingcartId
	 *                购物车id
	 * @return Json
	 */
	@RequestMapping(value = "/{shoppingcartId}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据房车id查询房车详情", notes = "data TShoppingCart ", response = JsonResult.class)
	public JsonResult get(@PathVariable Integer shoppingcartId) {
		TShoppingCart tsh1 = new TShoppingCart();
		tsh1.setId(10001);
		tsh1.setUserId(10001);
		tsh1.setCreateTime(new Timestamp(System.currentTimeMillis()));
		return new SuccessResult().setData(tsh1);
	}
	
}
