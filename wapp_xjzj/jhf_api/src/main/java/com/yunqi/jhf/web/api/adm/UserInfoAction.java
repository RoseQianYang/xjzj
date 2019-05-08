package com.yunqi.jhf.web.api.adm;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yunqi.common.json.JsonResult;
import com.yunqi.common.json.SuccessResult;
import com.yunqi.jhf.dao.domain.TMenus;
import com.yunqi.jhf.dao.domain.TRole;
import com.yunqi.jhf.dao.persistence.TMenusDao;
import com.yunqi.jhf.dao.persistence.TPermissionsDao;
import com.yunqi.jhf.dao.persistence.TRoleDao;
import com.yunqi.jhf.service.background.UserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@Api(description = "用户及用户权限管理接口")
@RequestMapping(value = "/api/adm/user")
public class UserInfoAction {
	
	protected Logger logger = Logger.getLogger(UserInfoAction.class);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private TPermissionsDao permissionsDao;
	
	@Autowired
	private TMenusDao menusDao;
	
	@Autowired
	private TRoleDao tRoleDao;
	
	
	@ApiOperation(value = "查询用户列表", notes = "data{List TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getUserList(
			@ApiParam(value = "{realName,mobile}", required = true) @RequestBody ModelMap JsonInfo) {
		
		return userInfoService.getUserList(JsonInfo);
	}
	
	@ApiOperation(value = "修改用户信息", notes = "data{data TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/updateOperator", method = RequestMethod.POST, produces = "application/json")
	public JsonResult updateOperator(
			@ApiParam(value = "{realName,mobile}", required = true) @RequestBody ModelMap JsonInfo) {
		
		return userInfoService.updateOperator(JsonInfo);
	}
	
	@ApiOperation(value = "修改用户密码", notes = "data{data TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "application/json")
	public JsonResult updatePassword(
			@ApiParam(value = "{realName,mobile}", required = true) @RequestBody ModelMap JsonInfo) {
		
		return userInfoService.updatePwd(JsonInfo);
	}
	
	@ApiOperation(value = "新增用户信息", notes = "data{data TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/addOperator", method = RequestMethod.POST, produces = "application/json")
	public JsonResult addOperator(
			@ApiParam(value = "{realName,mobile}", required = true) @RequestBody ModelMap JsonInfo) {
		
		return userInfoService.addOperator(JsonInfo);
	}
	
	@ApiOperation(value = "注销操作员接口", notes = "data{data TOperator}", response = JsonResult.class)
	@RequestMapping(value = "/delOperator", method = RequestMethod.POST, produces = "application/json")
	public JsonResult delOperator(@RequestBody ModelMap JsonInfo) {
		
		return userInfoService.delOperator(JsonInfo);
	}
	
	@ApiOperation(value = "挂起操作员接口", notes = "data{data TOperator}", response = JsonResult.class)
	@RequestMapping(value = "/OperatorisEnabled", method = RequestMethod.POST, produces = "application/json")
	public JsonResult OperatorisEnabled(@RequestBody ModelMap JsonInfo) {
		
		return userInfoService.OperatorisEnabled(JsonInfo);
	}
	
	
	@ApiOperation(value = "查询角色分页列表", notes = "data{List TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/getRolePageList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getRolePageList(@RequestBody ModelMap JsonInfo) {
		
		return userInfoService.getRolePageList(JsonInfo);
	}
	
	@ApiOperation(value = "查询角色列表", notes = "data{List TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/getRoleList", method = RequestMethod.GET, produces = "application/json")
	public JsonResult getRoleList() {
		
		return new SuccessResult().setData(tRoleDao.list(null, null));
	}
	
	@ApiOperation(value = "新增角色信息", notes = "data{Object TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/addRoleInfo", method = RequestMethod.POST, produces = "application/json")
	public JsonResult addRoleInfo(
		@ApiParam(value = "{roleName,permissions,menus}", required = true) @RequestBody ModelMap JsonInfo) {
		
		return userInfoService.addRoleInfo(JsonInfo);
	}
	
	@ApiOperation(value = "修改角色信息", notes = "data{Object TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST, produces = "application/json")
	public JsonResult updateRole(
		@ApiParam(value = "{roleName,permissions,menus}", required = true) @RequestBody TRole role) {
		
		return userInfoService.updateRole(role);
	}
	
	@ApiOperation(value = "删除角色信息", notes = "data{Object TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/delRoleInfo", method = RequestMethod.POST, produces = "application/json")
	public JsonResult delRoleInfo(
		@ApiParam(value = "{roleId}", required = true)@RequestBody Map<String, Object> map) {
		
		return userInfoService.delRoleInfo(map);
	}
	
	@ApiOperation(value = "获取访问权限列表", notes = "data{list TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/getPermissionsList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getPermissionsList() {
		
		return new SuccessResult().setData(permissionsDao.list(null, null));
	}
	
	@ApiOperation(value = "获取菜单权限列表", notes = "data{list TEvent}", response = JsonResult.class)
	@RequestMapping(value = "/getMenusList", method = RequestMethod.POST, produces = "application/json")
	public JsonResult getMenusList() {
		List<TMenus> menus = menusDao.list(null, null);
		
		return new SuccessResult().setData(userInfoService.treeMenus(0, menus));
	}
	
}
