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
import com.yunqi.jhf.dao.domain.TPhysicalStore;
import com.yunqi.jhf.service.background.PhysicalStoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author lianlh 实体门店相关API接口
 * 
 */
@CrossOrigin
@RestController
@Api(description = "实体门店相关接口")
@RequestMapping(value = "/api/adm/store")
public class PhysicalStoreAction {
	
	protected static Logger logger = Logger.getLogger(PhysicalStoreAction.class);
	
	@Autowired
	private PhysicalStoreService physicalStoreService;
	
	/**
	 * 根据实体门店名称(模糊搜索)获取实体门店列表
	 * 
	 * @param title
	 *        实体门店名称
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "查询实体门店列表", notes = "data{List TPhysicalStore}", response = JsonResult.class)
	@RequestMapping(value = "/getPhysicalStorePageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return physicalStoreService.getPageList(jsonInfo);
	}
	   
	/**
     * 新增实体门店
     ** input： 实体门店名称 title       活动  eventId 
			       
     */
    @RequestMapping(value = "/addPhysicalStore",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "新增实体门店",notes = "TPhysicalStore",response = JsonResult.class)
    public JsonResult create(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TPhysicalStore physicalStore) throws SQLException{
    	if(physicalStore == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	return physicalStoreService.create(physicalStore);
    }
    
    /**
     * 修改实体门店
     ** input： 实体门店名称 title       活动  eventId 
			       
     */
    @RequestMapping(value = "/updatePhysicalStore",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "修改实体门店",notes = "TPhysicalStore",response = JsonResult.class)
    public JsonResult update(
    		@ApiParam(value = "{title eventId}")
    		@RequestBody TPhysicalStore physicalStore) throws SQLException{
    	if(physicalStore == null) {
    		logger.info("未收到相关产品品牌信息，请重新填写");
    		return new ErrorResult("未收到相关产品品牌信息，请重新填写");
    	}
    	return physicalStoreService.update(physicalStore);
    }
    
	/**
	 * 根据门店Id来针对于门店删除
	 *
	 * @param storetId
	 *            门店id
	 * @return Json
	 */
	@RequestMapping(value = "/delPhysicalStore", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据产品id删除实体门店详情", notes = "data TPhysicalStore ", response = JsonResult.class)
	public JsonResult delete(Integer storeId) {
		if(storeId == null) {
    		logger.info("未收到相关产品品牌信息，请重新填写");
    		return new ErrorResult("未收到相关产品品牌信息，请重新填写");
    	}
		return physicalStoreService.delete(storeId);
	}

}
