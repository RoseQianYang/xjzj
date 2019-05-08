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
import com.yunqi.jhf.dao.domain.TProductBrand;
import com.yunqi.jhf.service.background.PrdBrandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author lianlh 产品品牌相关API接口
 * 
 */

@CrossOrigin
@RestController
@Api(description = "产品品牌相关接口")
@RequestMapping(value = "/api/adm/productBrand")
public class ProductBrandAction {

	protected static Logger logger = Logger.getLogger(ProductBrandAction.class);
	
	@Autowired
	private PrdBrandService prdBrandService;

	/**
	 * 获取产品品牌列表
	 * 
	 * @param page
	 *        页数
	 * @return Json
	 * @throws SQLException
	 */
	@ApiOperation(value = "查询产品品牌分页列表", notes = "data{List TProductBrand}", response = JsonResult.class)
	@RequestMapping(value = "/getProductBrandList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return prdBrandService.getPageList(jsonInfo);
	}
	
	@ApiOperation(value = "查询产品品牌信息列表，用来下拉框展示", notes = "data{List TProductBrand}", response = JsonResult.class)
	@RequestMapping(value = "/getProductBrandInfoList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult getList() throws SQLException {
		
		return prdBrandService.getList();
	}

	/**
	 * 新增产品品牌
	 * 
	 * 添加产品品牌             品牌名称 ：title                  
	 *              品牌logo图：cover
	 * 
	 * @param TProductBrand
	 *            产品品牌实体
	 * @return Json
	 * @throws SQLException
	 */
	@RequestMapping(value = "/addProductBrand", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "新增产品品牌", notes = "data{title,cover}", response = JsonResult.class)
	public JsonResult create(@ApiParam(value = "{title,cover}") @RequestBody TProductBrand productBrand)
			throws SQLException {
		if (productBrand == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
			
		return prdBrandService.create(productBrand);
	}

	/**
	 * 修改产品品牌
	 * 
	 * 修改产品品牌             品牌名称 ：title                  
	 *              品牌logo图：cover
	 * 
	 * @param TProductBrand
	 *        产品品牌实体
	 * @param id
	 *        产品品牌id
	 * @return Json
	 */
	@RequestMapping(value = "/updateProductBrand", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "根据产品品牌id修改信息", notes = "data{title,cover}", response = JsonResult.class)
	public JsonResult update(@ApiParam(value = "{title cover}") 
		@RequestBody TProductBrand productBrand) {
		
		return prdBrandService.update(productBrand);
	}
	
}
