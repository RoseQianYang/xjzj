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
import com.yunqi.jhf.service.SysConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lianlh 营地相关API接口
 * 
 */
@CrossOrigin
@RestController
@Api(description = "系统配置接口")
@RequestMapping(value = "/api/adm/sysconfig")
public class SysConfigAciton {

	protected static Logger logger = Logger.getLogger(SysConfigAciton.class);

	@Autowired
	private SysConfigService sysConfigService;

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "获取配置列表", notes = "List<TSysConfig> ", response = JsonResult.class)
	public JsonResult list() {
		return new JsonResult().success("success").setData(sysConfigService.getConfigList());
	}

	@ApiOperation(value = "修改用户信息", notes = "data{data TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	public JsonResult save(@RequestBody ModelMap JsonInfo) {
		sysConfigService.saveAll(JsonInfo);
		return new JsonResult().success("success");
	}

}
