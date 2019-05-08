package com.yunqi.jhf.web.api.adm;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.yunqi.common.json.JsonResult;
import com.yunqi.jhf.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author wangsong
 *
 */

@CrossOrigin
@RestController
@Api(description = "系统日志接口")
@RequestMapping(value = "/api/adm/syslog")
public class SysLogAction {
	
	protected static Logger logger = Logger.getLogger(SysLogAction.class);
	
	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping(value = "/getSysLogList", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "查询系统日志列表", notes = "List<TSysLog> ", response = JsonResult.class)
	public JsonResult getSysLogList(@RequestBody ModelMap jsonInfo) {
		return sysLogService.getSysLogList(jsonInfo);
	}

}
