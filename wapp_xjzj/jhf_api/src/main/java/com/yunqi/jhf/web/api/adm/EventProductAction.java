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
import com.yunqi.jhf.dao.domain.TEventProduct;
import com.yunqi.jhf.service.background.EventProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author lianlh 活动产品相关API接口
 * 
 */
@CrossOrigin
@RestController
@Api(description = "活动产品相关接口")
@RequestMapping(value = "/api/adm/eventproduct")
public class EventProductAction {
	
	protected static Logger logger = Logger.getLogger(EventProductAction.class);
	
	@Autowired
	private EventProductService eventProductService;
	
	/**
	 * @param title
	 *        活动产品名称
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "查询活动产品列表", notes = "data{List TEventProduct}", response = JsonResult.class)
	@RequestMapping(value = "/getEventProductList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPageList(@RequestBody ModelMap jsonInfo) throws SQLException {

		return eventProductService.getPageList(jsonInfo);
	}
	
	/**
     * 新增活动产品
     ** input：  产品  productId       活动分类id  eventcateId
     *         活动id  eventId         活动价格  eventPrice
			       活动创建时间 createTime     活动修改时间 updateTime
     */
    @RequestMapping(value = "/addEventProduct",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "活动产品",notes = "TEventProduct",response = JsonResult.class)
    public JsonResult create(@ApiParam(value = "{productId eventcateId eventId eventPrice}")
    		@RequestBody TEventProduct eventProduct) throws SQLException{
    	if(eventProduct == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	
    	return eventProductService.create(eventProduct);
    }
    
    /**
     * 修改活动产品
     * **  input：活动产品名称 id       产品id  productId       活动分类id  eventcateId
     *            活动id  eventId         活动价格  eventPrice
			               活动创建时间 createTime     活动修改时间  updateTime
     */
    @RequestMapping(value = "/updateEventProduct",method = RequestMethod.POST,produces = "application/json")
    @ApiOperation(value = "编辑活动产品信息", notes = "TEventProduct")
    public JsonResult update(@ApiParam(value = "{productId eventcateId eventId eventPrice}")
    		@RequestBody TEventProduct eventProduct) throws SQLException{
    	if(eventProduct == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
    	return eventProductService.update(eventProduct);
    }
    
    /**
	 * 根据活动产品id删除活动产品
	 *
	 * @param eventproductId
	 *            活动产品id
	 * @return Json
	 */
	@RequestMapping(value = "/delEventProduct", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据活动商品id删除活动商品", notes = "data TEventProduct", response = JsonResult.class)
	public JsonResult delete(Integer eventProductId) {
    	if(eventProductId == null) {
			logger.info("未收到相关产品品牌信息，请重新填写");
			return new ErrorResult("未收到相关产品品牌信息，请重新填写");
		}
		return eventProductService.delete(eventProductId);
	}
}
