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
import com.yunqi.jhf.dao.domain.TCampsite;
import com.yunqi.jhf.service.background.CampsiteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author lianlh 营地相关API接口
 * 
 */
@CrossOrigin
@RestController
@Api(description = "营地相关接口")
@RequestMapping(value = "/api/adm/campsite")
public class CampsiteAciton {

	protected static Logger logger = Logger.getLogger(CampsiteAciton.class);
	
	@Autowired
	private CampsiteService campsiteService;
	
	/**
	 * 根据营地名称(模糊搜索)获取营地列表
	 * 
	 * @param title
	 *        营地名称
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "查询营地列表", notes = "data{List Tcampsite}", response = JsonResult.class)
	@RequestMapping(value = "/getCampsitePageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return campsiteService.getPageList(jsonInfo);
	}
	   
	/**
     * 新增营地
     ** input： 营地名称 title       活动  eventId 
			       
     */
    @RequestMapping(value = "/addCampsite",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "新增营地",notes = "Tcampsite",response = JsonResult.class)
    public JsonResult create(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TCampsite campsite) throws SQLException{
    	if(campsite == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	return campsiteService.create(campsite);
    }
    
    /**
     * 修改营地
     ** input： 营地名称 title       活动  eventId 
			       
     */
    @RequestMapping(value = "/updateCampsite",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "修改营地",notes = "Tcampsite",response = JsonResult.class)
    public JsonResult update(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TCampsite campsite) throws SQLException{
    	if(campsite == null) {
    		logger.info("未收到相关产品品牌信息，请重新填写");
    		return new ErrorResult("未收到相关产品品牌信息，请重新填写");
    	}
    	return campsiteService.update(campsite);
    }
    
	/**
	 * 根据营地Id来针对于营地删除
	 *
	 * @param storetId
	 *            营地id
	 * @return Json
	 */
	@RequestMapping(value = "/delCampsite", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据产品id删除营地详情", notes = "data Tcampsite ", response = JsonResult.class)
	public JsonResult delete(Integer campsiteId) {
		if(campsiteId == null) {
    		logger.info("未收到相关产品品牌信息，请重新填写");
    		return new ErrorResult("未收到相关产品品牌信息，请重新填写");
    	}
		return campsiteService.delete(campsiteId);
	}
	
}
