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
import com.yunqi.jhf.dao.domain.TMobileRoute;
import com.yunqi.jhf.service.background.MobileRouteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * @author lianlh 路线相关API接口
 * 
 */
@CrossOrigin
@RestController
@Api(description = "路线相关接口")
@RequestMapping(value = "/api/adm/route")
public class MobileRouteAction {
													  
	protected static Logger logger = Logger.getLogger(MobileRouteAction.class);
													  
	@Autowired
	private MobileRouteService mobileRouteService;
	
	/**
	 * 根据路线名称(模糊搜索)获取路线列表
	 * 
	 * @param title
	 *        路线名称
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "查询路线列表", notes = "data{List TMobileRoute}", response = JsonResult.class)
	@RequestMapping(value = "/getMobileRoutePageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return mobileRouteService.getPageList(jsonInfo);
	}
	   
	/**
     * 新增路线
     ** input： 路线名称 title       活动  eventId 
			       
     */
    @RequestMapping(value = "/addMobileRoute",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "新增路线",notes = "TMobileRoute",response = JsonResult.class)
    public JsonResult create(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TMobileRoute MobileRoute) throws SQLException{
    	if(MobileRoute == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	return mobileRouteService.create(MobileRoute);
    }
    
    /**
     * 修改路线
     ** input： 路线名称 title       活动  eventId 
			       
     */
    @RequestMapping(value = "/updateMobileRoute",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "修改路线",notes = "TMobileRoute",response = JsonResult.class)
    public JsonResult update(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TMobileRoute mobileRoute) throws SQLException{
    	if(mobileRoute == null) {
    		logger.info("未收到相关产品品牌信息，请重新填写");
    		return new ErrorResult("未收到相关产品品牌信息，请重新填写");
    	}
    	return mobileRouteService.update(mobileRoute);
    }
    
	/**
	 * 根据路线Id来针对于路线删除
	 *
	 * @param storetId
	 *            路线id
	 * @return Json
	 */
	@RequestMapping(value = "/delMobileRoute", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据产品id查询路线详情", notes = "data TMobileRoute ", response = JsonResult.class)
	public JsonResult delete(Integer mobileRouteId) {
		if(mobileRouteId == null) {
    		logger.info("未收到相关产品品牌信息，请重新填写");
    		return new ErrorResult("未收到相关产品品牌信息，请重新填写");
    	}
		return mobileRouteService.delete(mobileRouteId);
	}
	
}
