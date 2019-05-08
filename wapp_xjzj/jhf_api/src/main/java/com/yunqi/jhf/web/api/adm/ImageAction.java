package com.yunqi.jhf.web.api.adm;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yunqi.common.json.ErrorResult;
import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.config.Const;
import com.yunqi.jhf.dao.domain.TImage;
import com.yunqi.jhf.service.ImageFileService;
import com.yunqi.jhf.service.background.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author lianlh 图片相关API接口
 * 
 */
@CrossOrigin
@RestController
@Api(description = "图片相关接口")
@RequestMapping(value = "/api/adm/image")
public class ImageAction {

	protected static Logger logger = Logger.getLogger(ImageAction.class);

	@Autowired
	private ImageService imageService;

	@Autowired
	private ImageFileService imageFileService;

	/**
	 * 根据图片列表
	 * 
	 * @param title
	 *            图片名称
	 * @return Json
	 * @throws SQLException
	 *             sql书写异常 imgSrc imgCateId
	 */
	@ApiOperation(value = "查询图片列表", notes = "data{List TImage}", response = JsonResult.class)
	@RequestMapping(value = "/getImageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return imageService.getPageList(jsonInfo);
	}

	@ApiOperation(value = "查询图片信息列表,用于下拉框展示", notes = "data{List TImage}", response = JsonResult.class)
	@RequestMapping(value = "/getImageInfoList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult getList() throws SQLException {

		return imageService.getList();
	}

	/**
	 * 添加一个图片
	 * 
	 * @return Json
	 */
	@RequestMapping(value = "/addImageInfo", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "新增图片", notes = "data TImage", response = JsonResult.class)
	public JsonResult create(@ApiParam(value = "{title imgSrc imgCateId}") @RequestBody TImage image) {
		if (image == null) {
			return new ErrorResult("前台信息不可为空");
		}
		return imageService.create(image);
	}
	
	/**
	 * 添加一个图片
	 * 
	 * @return Json
	 */
	@RequestMapping(value = "/delImageInfo", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "删除图片", notes = "data TImage", response = JsonResult.class)
	public JsonResult delete(@ApiParam(value = "{imageId}") int imageId) {
		if (imageId == 0) {
			return new ErrorResult("前台信息不可为空");
		}
		return imageService.delete(imageId);
	}

	@RequestMapping(value = "/doUpload", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "上传图片", notes = "data file", response = JsonResult.class)
	public JsonResult doUpload(HttpServletRequest req, Model model, HttpSession sess,
			@RequestParam(required = false, value = "file") MultipartFile imageFile) throws Exception {
		JsonResult result = new JsonResult();
		try {
			try {
				String imageUri = imageFileService.uploadImageFile(imageFile, Const.UPLOAD_BASE_DIR);
				result.success("上传成功").setData(imageUri);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("文件处理失败");
			}
			logger.info(result);
		} catch (Exception e) {
			logger.error(e);
			result.error("上传失败:" + e.getMessage());
		}
		return result;
	}

	/**
	 * 老的方式上传图片， 改为新的方式，有略缩图 doUpload 上传图片信息 upload:
	 * 
	 * @param file
	 * @return Json
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "上传图片", notes = "data file", response = JsonResult.class)
	public JsonResult upload(@RequestParam(value = "file") CommonsMultipartFile file, HttpServletRequest request) {

		return imageService.upload(file, request);
	}

	/**
	 * 修改图片信息 update:
	 * 
	 * @param imageId
	 *            图片id
	 * @return Json
	 */
	@RequestMapping(value = "/updateImage", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "修改图片", notes = "data TImage", response = JsonResult.class)
	public JsonResult update(@ApiParam(value = "{title imgSrc imgCateId}") @RequestBody ModelMap jsonInfo) {

		return imageService.update(jsonInfo);
	}
}
