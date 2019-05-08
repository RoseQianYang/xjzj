package com.yunqi.jhf.web.api.adm;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.service.background.StatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lianlh 统计相关API接口
 * 
 */
@CrossOrigin
@RestController
//@Api(tags = {"event","user"})
@Api(description = "统计相关接口")
@RequestMapping(value = "/api/adm/stats")
public class StatisticsAction {
	
	protected static Logger log = Logger.getLogger(StatisticsAction.class);
	
	@Autowired
	private StatisticsService statisticsService;
	
	 public final String NO_DEFINE = "no_define";//未定义的字段
	 
	 public final String DEFAULT_DATE_PATTERN="yyyy年MM月dd日";//默认日期格式
	 
	 public final int DEFAULT_COLOUMN_WIDTH = 17;
	
	/**
	 * 获取用户的月统计信息接口
	 * 
	 * @param title
	 *        活动名称
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "获取用户的月统计信息", notes = "data{List}", response = JsonResult.class)
	@RequestMapping(value = "/getUserStatsByMonth", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getUserStatsByMonth(@RequestBody ModelMap jsonInfo) throws SQLException {

		return statisticsService.getUserStatsByMonth(jsonInfo);
	}
	
	/**
	 * 获取用户的年统计信息接口
	 * 
	 * @param title
	 *        活动名称
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "获取用户的年统计信息", notes = "data{List}", response = JsonResult.class)
	@RequestMapping(value = "/getUserStatsByYear", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getUserStatsByYear(@RequestBody ModelMap jsonInfo) throws SQLException {
		
		return statisticsService.getUserStatsByYear(jsonInfo);
	}
	
	/**
	 * 获取用户积分统计流水接口
	 * 
	 * @param userName
	 *        用户名称
	 *        createTime
	 *        创建时间
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "获取用户积分统计流水", notes = "data{List}", response = JsonResult.class)
	@RequestMapping(value = "/getIntegralStats", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getIntegralStats(@RequestBody ModelMap jsonInfo) throws SQLException {
		
		return statisticsService.getIntegralStats(jsonInfo);
	}
	
	/**
	 * 获取产品销量统计列表接口
	 * 
	 * @param 
	 *        用户名称
	 *        createTime
	 *        创建时间
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "获取产品销量统计列表", notes = "data{List}", response = JsonResult.class)
	@RequestMapping(value = "/getSalesVolumeStatsList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getSalesVolumeStatsList(@RequestBody ModelMap jsonInfo) throws SQLException {
		
		return statisticsService.getSalesVolumeStatsList(jsonInfo);
	}
	
	/**
	 * 获取单品年销量统计接口
	 * 
	 * @param 
	 *        用户名称
	 *        createTime
	 *        创建时间
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "获取单品年销量统计接口", notes = "data{List}", response = JsonResult.class)
	@RequestMapping(value = "/getSalesVolumeStatsByYear", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getSalesVolumeStatsByYear(@RequestBody ModelMap jsonInfo) throws SQLException {
		
		return statisticsService.getSalesVolumeStatsByYear(jsonInfo);
	}
	
	/**
	 * 获取单品月销量统计接口
	 * 
	 * @param 
	 *        用户名称
	 *        createTime
	 *        创建时间
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "获取单品月销量统计接口", notes = "data{List}", response = JsonResult.class)
	@RequestMapping(value = "/getSalesVolumeStatsByMonth", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getSalesVolumeStatsByMonth(@RequestBody ModelMap jsonInfo) throws SQLException {
		
		return statisticsService.getSalesVolumeStatsByMonth(jsonInfo);
	}
	
	/**
	 * 获取销售明细列表
	 * 
	 * @param 
	 *        用户名称
	 *        createTime
	 *        创建时间
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "获取销售明细列表", notes = "data{List}", response = JsonResult.class)
	@RequestMapping(value = "/getOrderDetailList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getOrderDetailList(@RequestBody ModelMap jsonInfo) {  
		
       return statisticsService.getOrderDetailList(jsonInfo);
	}
	
	/**
	 * 导出销量Excel
	 * 
	 * @param 
	 *        用户名称
	 *        createTime
	 *        创建时间
	 * @return Json`
	 * @throws SQLException
	 *             sql书写异常
	 */
	@ApiOperation(value = "导出销量Excel", notes = "data{List}", response = JsonResult.class)
	@RequestMapping(value = "/exportExcel", method = RequestMethod.GET, produces = "application/json")
	public void exportExcel(HttpServletResponse response, ModelMap jsonInfo) {  
		statisticsService.exportExcel(response,jsonInfo);
	}

}
