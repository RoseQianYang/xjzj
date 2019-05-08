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

import com.yunqi.common.json.ErrorResult;
import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.domain.TProduct;
import com.yunqi.jhf.dao.domain.TProductAttribute;
import com.yunqi.jhf.service.background.ProductInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Seek产品相关接口
 *
 */
@CrossOrigin
@RestController
@Api(description = "产品相关接口")
@RequestMapping(value = "/api/adm/product")
public class ProductAction {
	
	protected static Logger logger = Logger.getLogger(ProductAction.class);

	@Autowired
	private ProductInfoService productInfoService;
	
	/**
	 * 根据品牌，分类，产品名称(模糊搜索)获取产品列表
	 *  
	 * @param 
	 * @return Json
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "查询产品列表", notes = "data{List TProduct}", response = JsonResult.class)
	@RequestMapping(value = "/getProductList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return productInfoService.getPageList(jsonInfo);
	}
	
	/**
	 * 添加一个产品
	 * 
	 * @return Json
	 */
	@RequestMapping(value = "/addProductInfo", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "新增产品", notes = "新增产品,添加产品所有属性 TProduct", response = JsonResult.class)
	public JsonResult create(@ApiParam(value = "{cateId brandId title stock price description content productColor productSize updateTime createTime}") 
			@RequestBody TProduct product) {
		if(product == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
		return productInfoService.create(product);
	}

	/**
	 * 修改产品信息 
	 * 
	 * @param productId
	 *            产品Id
	 * @return Json
	 */
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "修改商品", notes = "修改产品信息 TProduct", response = JsonResult.class)
	public JsonResult update(
			@ApiParam(value = "cateId brandId title stock price description content productColor productSize updateTime createTime}")
			@RequestBody TProduct product) {
		if(product == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
		return productInfoService.update(product);
	}
	
	/**
	 * 商品上架下架操作
	 * 
	 * @param productId
	 *            产品Id
	 * @return Json
	 */
	@RequestMapping(value = "/putawayProduct", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "商品上架下架操作", notes = "商品上架下架操作 TProduct", response = JsonResult.class)
	public JsonResult putawayProduct(@RequestBody ModelMap jsonInfo) {
		if(jsonInfo == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
		return productInfoService.putawayProduct(jsonInfo);
	}
	
	/**
	 * 查询商品库存列表 :
	 * 
	 */
	@RequestMapping(value = "/getProductStockList", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "查询商品库存列表", notes = "查询商品库存列表 TProductAttribute", response = JsonResult.class)
	public JsonResult getProductStockList(
			@ApiParam(value = "page,productId")
			@RequestBody ModelMap jsonInfo) {
		if(jsonInfo == null) {
			logger.info("未收到相关产品库存信息，请重新填写");
			return new ErrorResult("未收到相关产品库存信息，请重新填写");
		}
		return productInfoService.getProductStockList(jsonInfo);
	}
	
	/**
	 * 新增商品库存信息接口:
	 * 
	 * @param productId 商品id
	 * 		  stock		库存数量
	 * 		  productColor	商品颜色
	 * 		  productSize	商品尺码
	 * @return Json
	 */
	@RequestMapping(value = "/addProductStock", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "新增商品库存信息接口", notes = "新增商品库存信息 TProductAttribute", response = JsonResult.class)
	public JsonResult addProductStock(
			@ApiParam(value = "{productId,stock,productColor,productSize}")
			@RequestBody TProductAttribute productAttribute) {
		if(productAttribute == null) {
			logger.info("未收到相关产品库存信息，请重新填写");
			return new ErrorResult("未收到相关产品库存信息，请重新填写");
		}
		return productInfoService.addProductStock(productAttribute);
	}
	
	/**
	 * 修改产品库存信息 update:
	 * 
	 * @param stock		库存数量
	 * 		  productColor	商品颜色
	 * 		  productSize	商品尺码
	 * @return Json
	 */
	@RequestMapping(value = "/updateProductStock", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "修改产品库存", notes = "修改产品库存信息 TProduct", response = JsonResult.class)
	public JsonResult updateProductStock(
			@ApiParam(value = "{id,stock,productColor,productSize}")
			@RequestBody TProductAttribute productAttribute) {
		if(productAttribute == null) {
			logger.info("未收到相关产品库存信息，请重新填写");
			return new ErrorResult("未收到相关产品库存信息，请重新填写");
		}
		return productInfoService.updateProductStock(productAttribute);
	}
	
	/**
	 * 删除产品库存信息 update:
	 * 
	 * @param stock		库存数量
	 * 		  productColor	商品颜色
	 * 		  productSize	商品尺码
	 * @return Json
	 */
	@RequestMapping(value = "/delProductStock", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "删除产品库存", notes = "修改产品库存信息 TProduct", response = JsonResult.class)
	public JsonResult delProductStock(@ApiParam(value = "{id}") int id) {
		if(id == 0) {
			logger.info("未收到相关产品库存信息，请重新填写");
			return new ErrorResult("未收到相关产品库存信息，请重新填写");
		}
		return productInfoService.delProductStock(id);
	}
	
}
