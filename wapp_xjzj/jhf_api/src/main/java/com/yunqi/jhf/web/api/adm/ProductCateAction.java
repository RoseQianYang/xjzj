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
import com.yunqi.jhf.dao.domain.TProductCate;
import com.yunqi.jhf.service.background.PrdCateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Seek 产品分类相关接口
 *
 */
@CrossOrigin
@RestController
@Api(description = "产品分类相关接口")
@RequestMapping(value = "/api/adm/productCate")
public class ProductCateAction {

	protected static Logger logger = Logger.getLogger(ProductCateAction.class);
	
	@Autowired
	private PrdCateService prdCateService;

	/**
	 * 获取产品分类列表
	 * 
	 * @param page
	 *        页数
	 * @return Json
	 * @throws SQLException
	 */
	@ApiOperation(value = "查询产品分类列表", notes = "data{List TProductCate}", response = JsonResult.class)
	@RequestMapping(value = "/getProductCateList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return prdCateService.getPageList(jsonInfo);
	}

	@ApiOperation(value = "查询产品分类列表", notes = "data{List TProductCate}", response = JsonResult.class)
	@RequestMapping(value = "/getProductCateInfoList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult getList() throws SQLException {

		return prdCateService.getList();
	}
	/**
	 * 新增产品分类
	 * 
	 * 添加产品分类             分类名称 ：title                  
	 *              分类图：cover
	 * 
	 * @param TProductCate
	 *            产品分类实体
	 * @return Json
	 * @throws SQLException
	 */
	@RequestMapping(value = "/addProductCate", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "新增产品分类", notes = "data{title,cover}", response = JsonResult.class)
	public JsonResult create(@ApiParam(value = "{title,cover}") @RequestBody TProductCate productcate)
			throws SQLException {
		if (productcate == null) {
			logger.info("未收到相关产品分类信息，请重新填写");
			return new ErrorResult("未收到相关产品分类信息，请重新填写");
		}
		return prdCateService.create(productcate);
	}

	/**
	 * 修改产品分类
	 * 
	 * 修改产品分类            分类名称 ：title                  
	 *              分类图：cover
	 * 
	 * @param TProductCate
	 *        产品分类实体
	 * @param id
	 *        产品分类id
	 * @return Json
	 */
	@RequestMapping(value = "/updateProductCate", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "根据产品分类id修改信息", notes = "data{title,cover}", response = JsonResult.class)
	public JsonResult update(@ApiParam(value = "{title cover}") 
				@RequestBody TProductCate productcate) {
		
		return prdCateService.update(productcate);
	}
	
}
