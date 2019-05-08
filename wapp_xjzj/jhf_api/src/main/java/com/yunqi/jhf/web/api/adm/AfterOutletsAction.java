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
import com.yunqi.jhf.dao.domain.TAfterOutlets;
import com.yunqi.jhf.service.background.AfterOutletsSerivce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author lianlh 售后网点相关API接口
 * 
 */
@CrossOrigin
@RestController
@Api(description = "售后网点相关接口")
@RequestMapping(value = "/api/adm/outlets")
public class AfterOutletsAction {

	protected static Logger logger = Logger.getLogger(AfterOutletsAction.class);
	
	@Autowired
	private AfterOutletsSerivce afterOutletsSerivce;
	
	/**
	 * 根据售后网点名称(模糊搜索)获取售后网点列表
	 * 
	 * @param title
	 *        售后网点名称
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "查询售后网点列表", notes = "data{List TAfterOutlets}", response = JsonResult.class)
	@RequestMapping(value = "/getAfterOutletsPageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return afterOutletsSerivce.getPageList(jsonInfo);
	}
	   
	/**
     * 新增售后网点
     ** input： 售后网点名称 title       活动  eventId 
			       
     */
    @RequestMapping(value = "/addAfterOutlets",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "新增售后网点",notes = "TAfterOutlets",response = JsonResult.class)
    public JsonResult create(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TAfterOutlets afterOutlets) throws SQLException{
    	if(afterOutlets == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	return afterOutletsSerivce.create(afterOutlets);
    }
    
    /**
     * 修改售后网点
     ** input： 售后网点名称 title       活动  eventId 
			       
     */
    @RequestMapping(value = "/updateAfterOutlets",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "修改售后网点",notes = "TAfterOutlets",response = JsonResult.class)
    public JsonResult update(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TAfterOutlets afterOutlets) throws SQLException{
    	if(afterOutlets == null) {
    		logger.info("未收到相关产品品牌信息，请重新填写");
    		return new ErrorResult("未收到相关产品品牌信息，请重新填写");
    	}
    	return afterOutletsSerivce.update(afterOutlets);
    }
    
	/**
	 * 根据售后网点Id来针对于售后网点删除
	 *
	 * @param storetId
	 *            售后网点id
	 * @return Json
	 */
	@RequestMapping(value = "/delAfterOutlets", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据产品删除售后网点详情", notes = "data TAfterOutlets ", response = JsonResult.class)
	public JsonResult delete(Integer outletId) {
		if(outletId == null) {
    		logger.info("未收到相关产品品牌信息，请重新填写");
    		return new ErrorResult("未收到相关产品品牌信息，请重新填写");
    	}
		return afterOutletsSerivce.delete(outletId);
	}

}
