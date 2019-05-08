package com.yunqi.jhf.web.api.adm;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yunqi.common.json.ErrorResult;
import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.dao.domain.TImgCate;
import com.yunqi.jhf.service.background.ImageCateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author lianlh 图片 分类相关接口
 *
 */
@CrossOrigin
@RestController
@Api(description = "图片相关接口")
@RequestMapping(value = "/api/adm/imagecate")
public class ImageCateAction {


	protected static Logger logger = Logger.getLogger(ImageCateAction.class);
	
	@Autowired
	private ImageCateService imageCateService;

	/**
	 * 获取图片分类列表
	 * 
	 * @param page
	 *        页数
	 * @return Json
	 * @throws SQLException
	 */
	@ApiOperation(value = "查询图片分类列表", notes = "data{List TImgCate}", response = JsonResult.class)
	@RequestMapping(value = "/getImageCateList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList() throws SQLException {

		return imageCateService.getPageList();
	}

	/**
	 * 新增图片分类
	 * 
	 * 添加图片分类             分类名称 ：title 
	 *              父分类：parentId 
	 *                 
	 * @param    TImgCate
	 *            图片分类实体
	 * @return Json
	 * @throws SQLException
	 */
	@RequestMapping(value = "/addImageCate", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "新增图片分类", notes = "data{title,parentId}", response = JsonResult.class)
	public JsonResult create(@ApiParam(value = "{title parentId}") @RequestBody TImgCate imgCate)
			throws SQLException {
		if (imgCate == null) {
			return new ErrorResult("未收到相关图片分类信息，请重新填写");
		}
		return imageCateService.create(imgCate);
	}

	/**
	 * 修改图片分类
	 * 
	 * 修改图片分类             分类名称 ：title 
	 *              父分类：parentId 
	 * 
	 * @param     TImgCate
	 *            图片分类实体
	 * @param     id
	 *            产品分类id
	 * @return Json
	 */
	@RequestMapping(value = "/updateImgCate", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "根据图片分类id修改信息", notes = "data{title,parentId}", response = JsonResult.class)
	public JsonResult update(
			@ApiParam(value = "{title parentId}") @RequestBody TImgCate imgCate) {
		return imageCateService.update(imgCate);
	}
	
}
