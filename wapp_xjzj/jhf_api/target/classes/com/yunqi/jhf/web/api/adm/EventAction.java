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
import com.yunqi.jhf.service.background.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author lianlh 活动相关API接口
 * 
 */
@CrossOrigin
@RestController
//@Api(tags = {"event","user"})
@Api(description = "活动相关接口")
@RequestMapping(value = "/api/adm/event")
public class EventAction {
	
	protected static Logger logger = Logger.getLogger(EventAction.class);
	
	@Autowired
	private EventService eventService;
	
	/**
	 * 根据活动名称(模糊搜索)获取活动列表
	 * 
	 * @param title
	 *        活动名称
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "查询活动列表", notes = "data{List TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/getEventList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		
		return eventService.getPageList(jsonInfo);
	}
	
	/**
     * 新增活动
     ** input： 活动名称 title    活动封面cover   活动图文详情 content
			       活动开始时间 startTime           活动结束时间 endTime
			       活动创建时间 createTime          活动修改时间 updateTime
     */
    @RequestMapping(value = "/addEventInfo",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "新增活动",notes = "TEvent",response = JsonResult.class)
    public JsonResult create(
    		@ApiParam(value = "{title cover content startTime endTime}")
    		@RequestBody ModelMap jsonInfo) throws SQLException{
    	if(jsonInfo == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	return eventService.create(jsonInfo);
    }
    
    /**
     * 修改活动
     * **  input： 活动  id             活动名称 title    
     *            活动封面cover        活动图文详情 content
			               活动开始时间 startTime    活动结束时间 endTime
			               活动创建时间 createTime   活动修改时间 updateTime
     */
    @RequestMapping(value = "/updateEvent",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "编辑活动信息", notes = "TEvent")
    public JsonResult update(
    		@ApiParam(value = "{title cover content startTime endTime}")
    		@RequestBody ModelMap jsonInfo) throws SQLException{
    	if(jsonInfo == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	return eventService.update(jsonInfo);
    }

    /**
	 * 根据活动id查询活动详情，直接set一个对象到data中
	 *
	 * @param eventId
	 *            活动id
	 * @return Json
	 */
	@RequestMapping(value = "/getEventById", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据活动id查询活动详情", notes = "data TEvent ", response = JsonResult.class)
	public JsonResult get(Integer eventId) {
		if(eventId == null || eventId == 0) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
		return eventService.get(eventId);
	}

    
}
