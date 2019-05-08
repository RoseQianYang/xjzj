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
import com.yunqi.jhf.dao.domain.TEventCate;
import com.yunqi.jhf.service.background.EventCateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author lianlh 活动分类相关API接口
 * 
 */
@CrossOrigin
@RestController
@Api(description = "活动分类相关接口")
@RequestMapping(value = "/api/adm/eventCate")
public class EventCateAction {
	
	protected static Logger logger = Logger.getLogger(EventCateAction.class);
	
	@Autowired
	private EventCateService eventCateService;
	
	/**
	 * 根据活动分类名称(模糊搜索)获取活动产品列表
	 * 
	 * @param title
	 *        活动分类名称
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "查询活动分类列表", notes = "data{List TEventCate}", response = JsonResult.class)
	@RequestMapping(value = "/getEventCatePageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return eventCateService.getPageList(jsonInfo);
	}
	   
	@ApiOperation(value = "查询活动分类列表", notes = "data{List TEventCate}", response = JsonResult.class)
	@RequestMapping(value = "/getEventCateList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult getList() throws SQLException {
		
		return eventCateService.getList();
	}
	
	/**
     * 新增活动分类
     ** input： 活动分类名称 title       活动  eventId 
			       活动分类创建时间 createTime    活动分类修改时间 updateTime
     */
    @RequestMapping(value = "/addEventCateInfo",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "新增活动分类",notes = "TEventCate",response = JsonResult.class)
    public JsonResult create(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TEventCate eventCate) throws SQLException{
    	if(eventCate == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	return eventCateService.create(eventCate);
    }
    
    /**
     * 修改活动分类
     * **  input： 活动分类  id       活动  eventId      活动分类名称 title       
			                活动分类创建时间 createTime    活动分类修改时间 updateTime
     */
    @RequestMapping(value = "/updateEventCate",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "编辑活动分类信息", notes = "TEventCate")
    public JsonResult update(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TEventCate eventCate) throws SQLException{
    	if(eventCate == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	return eventCateService.update(eventCate);
    }
    
}
