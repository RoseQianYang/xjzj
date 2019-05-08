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
import com.yunqi.jhf.service.background.ShareService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author lianlh 分享相关API接口
 * 
 */

@CrossOrigin
@RestController
@Api(description = "分享相关接口")
@RequestMapping(value = "/api/adm/share")
public class ShareAction {
	
	protected static Logger logger = Logger.getLogger(ShareAction.class);
	
	@Autowired
	private ShareService shareService;
	
	/**
	 * 获取帖子信息列表
	 * 
	 * @param page
	 *        页数
	 * @return Json
	 * @throws SQLException
	 */
	@ApiOperation(value = "获取分享帖子列表信息接口", notes = "data{List ShareBean}", response = JsonResult.class)
	@RequestMapping(value = "/getShareList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getShareList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return shareService.getShareList(jsonInfo);
	}
	
	/**
     * 修改分享帖子 
     *   input： 分享帖子 id            是否首页展示 	isShow  
     *           
     */
    @RequestMapping(value = "/updateShareIsShow",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "修改分享帖子  是否首页展示", notes = "TEvent")
    public JsonResult update(
    		@ApiParam(value = "{id,isShow}")
    		@RequestBody ModelMap jsonInfo) throws SQLException{
    	if(jsonInfo == null) {
			logger.info("未收到相关分享帖子信息，请重新填写");
			return new ErrorResult("未收到相关分享帖子信息，请重新填写");
		}
    	return shareService.updateShareIsShow(jsonInfo);
    }

    /**
     * 修改分享帖子    是否用户状态挂起 	isEnabled 
     * @param jsonInfo
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/updateShareIsEnabled",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "修改分享帖子  是否首页展示", notes = "TEvent")
    public JsonResult updateShareIsEnabled(
    		@ApiParam(value = "{id,isEnabled}")
    		@RequestBody ModelMap jsonInfo) throws SQLException{
    	if(jsonInfo == null) {
			logger.info("未收到相关分享帖子信息，请重新填写");
			return new ErrorResult("未收到相关分享帖子信息，请重新填写");
		}
    	return shareService.updateShareIsEnabled(jsonInfo);
    }

}
