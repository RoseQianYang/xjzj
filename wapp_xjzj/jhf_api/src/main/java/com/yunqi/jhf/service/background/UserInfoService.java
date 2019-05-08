package com.yunqi.jhf.service.background;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.yunqi.common.json.ErrorResult;
import com.yunqi.common.json.JsonResult;
import com.yunqi.common.util.ConstantTool;
import com.yunqi.jhf.dao.OperatorDao;
import com.yunqi.jhf.dao.domain.TMenus;
import com.yunqi.jhf.dao.domain.TOperator;
import com.yunqi.jhf.dao.domain.TRole;
import com.yunqi.jhf.dao.domain.TSysLog;
import com.yunqi.jhf.dao.persistence.TOperatorDao;
import com.yunqi.jhf.dao.persistence.TRoleDao;
import com.yunqi.jhf.dao.persistence.TSysLogDao;
import com.yunqi.jhf.dao.util.PageList;
import com.yunqi.jhf.dao.util.SqlSelect;
import com.yunqi.jhf.dao.util.SqlUpdate;
import com.yunqi.jhf.vo.Menu;
import com.yunqi.jhf.vo.OperatorBean;

@Service
public class UserInfoService {

	protected Logger log = Logger.getLogger(UserInfoService.class);
	
	@Autowired
	private TOperatorDao operatorDao;

	@Autowired
	private OperatorDao operDao;
	
	@Autowired
	private TRoleDao tRoleDao;
	
	@Autowired
	private TSysLogDao tSysLogDao;
	
	//获取操作员列表接口
	public JsonResult getUserList(ModelMap jsonInfo) {
		log.info("进入获取操作员列表接口");
		JsonResult result = new JsonResult();
		PageList<OperatorBean> pagelist = new PageList<>();
		if(jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE) != null) {
			pagelist.setCurrentPage((int) jsonInfo.get(ConstantTool.PAGE_CURRENTPAGE));
			jsonInfo.put(ConstantTool.PAGE_CURRENTPAGE, pagelist.getFromPos());
		}
		jsonInfo.put(TOperator.PROP_isDelete, ConstantTool.ISDELETE_YES);
		List<OperatorBean> operators = operDao.getOperatorList(jsonInfo);
		if(operators != null) {
			pagelist.setList(operators);
			pagelist.setTotalSize(operDao.getOperatorCount(jsonInfo));
			result.setData(pagelist);
			result.success("获取成功");
			log.info("获取操作员列表接口执行成功");
		}else {
			result.error("获取失败");
			log.info("获取操作员列表接口执行失败");
		}
		return result;
	}
	
	//修改项目信息
	public JsonResult updateOperator(ModelMap jsonInfo) {
		log.info("进入获取操作员列表接口");
		JsonResult result = new JsonResult();
		SqlUpdate sql = new SqlUpdate().where(TOperator.SQL_id).addColumn(TOperator.SQL_roleId);
		if(jsonInfo.get(TOperator.PROP_mobile) == null || 
			"".equals(((String) jsonInfo.get(TOperator.PROP_mobile)).trim())) {
			log.info("手机号不可为空");
			return result.error("手机号不可为空");
		}else {
			sql.addColumn(TOperator.SQL_mobile);
		}
		if(jsonInfo.get(TOperator.PROP_realName) == null ||
			"".equals(((String) jsonInfo.get(TOperator.PROP_realName)).trim())) {
			log.info("真实姓名不可为空");
			return result.error("真实姓名不可为空");
		}else {
			sql.addColumn(TOperator.SQL_realName);
		}
		int res = operatorDao.updateByMap(sql, jsonInfo);
		if(res > 0) {
			result.success("修改成功");
			result.setData(operDao.getOperatorById((int) jsonInfo.get(TOperator.PROP_id)));
			log.info("修改操作员信息接口执行成功");
		}else {
			result.error("修改失败");
			log.info("修改操作员信息接口执行失败");
		}
		return result;
	}
	
	//修改操作员密码
	public JsonResult updatePwd(ModelMap jsonInfo) {
		log.info("进入修改操作员密码的接口方法");
		JsonResult result = new JsonResult();
		SqlSelect sql = new SqlSelect().where(TOperator.SQL_id)
				.and(TOperator.SQL_password).and(TOperator.SQL_isEnabled);
		jsonInfo.put(TOperator.PROP_password, jsonInfo.get("oldPassword"));
		TOperator tor = operatorDao.loadByMap(sql, jsonInfo);
		if(tor != null) {
			if(jsonInfo.get("newPassword") != null && 
				!"".equals(((String) jsonInfo.get("newPassword")).trim())) {
				jsonInfo.put(TOperator.PROP_password, jsonInfo.get("newPassword"));
				SqlUpdate update = new SqlUpdate().addColumn(TOperator.SQL_password)
						.where(TOperator.SQL_id);
				int res = operatorDao.updateByMap(update, jsonInfo);
				if(res > 0) {
					log.info("修改操作员密码接口执行成功");
					result.success("修改成功");
					result.setData(operDao.getOperatorById((int) jsonInfo.get(TOperator.PROP_id)));
					// 修改密码成功 记录 系统日志
					TSysLog sysLog = new TSysLog();
					sysLog.setLogType(ConstantTool.SYSLOG_LOGTYPE_ONE); // 日志类型为  后台来源类型
					sysLog.setLogAction(ConstantTool.SYSLOG_LOGACTION_TWO); // 操作类型为 密码修改操作
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar ca = Calendar.getInstance();
					sysLog.setLogContent((String) jsonInfo.get(TOperator.SQL_realName) +"用户在" + sf.format(ca.getTime())+ "时,修改密码成功");
					tSysLogDao.insert(sysLog);
				}else {
					log.info("修改操作员密码接口执行失败");
					result.error("修改失败");
				}
			}else {
				log.info("你不可用空密码来设置新的密码");
				result.error("新密码不可为空");
			}
		}else {
			log.info("原始密码输入不正确");
			result.error("原始密码输入不正确");
		}
		return result;
	}
	
	//注销操作员    isDelete为 N 为 注销挂起操作员
	public JsonResult delOperator(ModelMap jsonInfo) {
		log.info("进入注销挂起操作员接口");
		JsonResult result = new JsonResult();
		if(jsonInfo != null && jsonInfo.get(TOperator.PROP_id) != null) {
			
			jsonInfo.put(TOperator.PROP_isDelete, ConstantTool.ISDELETE_NO);
			SqlUpdate sql = new SqlUpdate()
					.addColumn(TOperator.SQL_isDelete)
					.where(TOperator.SQL_id);
			int res = operatorDao.updateByMap(sql, jsonInfo);
			if(res > 0) {
				log.info("注销操作员接口执行成功");
				result.success("注销操作员成功");
			}else {
				log.info("注销操作员接口执行失败");
				result.error("注销操作员失败");
			}
		}else {
			log.info("传入参数为空");
			result.error("传入参数为空");
		}
		return result;
	}
	
	//挂起操作员    isEnabled为 N 为 挂起操作员
	public JsonResult OperatorisEnabled(ModelMap jsonInfo) {
		log.info("进入挂起挂起操作员接口");
		JsonResult result = new JsonResult();
		if(jsonInfo != null && jsonInfo.get(TOperator.PROP_id) != null) {
			SqlUpdate sql = new SqlUpdate()
					.addColumn(TOperator.SQL_isEnabled)
					.where(TOperator.SQL_id);
			int res = operatorDao.updateByMap(sql, jsonInfo);
			if(res > 0) {
				log.info("挂起操作员接口执行成功");
				result.success("执行成功");
			}else {
				log.info("挂起操作员接口执行失败");
				result.error("执行失败");
			}
		}else {
			log.info("传入参数为空");
			result.error("传入参数为空");
		}
		return result;
	}
	
	//新增操作员
	public JsonResult addOperator(ModelMap JsonInfo) {
		log.info("进入新增操作员信息接口");
		JsonResult result = new JsonResult();
		if(JsonInfo != null) {
			TOperator oper = new TOperator();
			// 默认 操作员  为 非注销状态为 Y 
			oper.setIsDelete(ConstantTool.ISDELETE_YES);
			oper.setRemark((String) JsonInfo.get(TOperator.PROP_remark));
			if(JsonInfo.get(TOperator.PROP_accountName) != null) {
				oper.setAccountName((String) JsonInfo.get(TOperator.PROP_accountName));
				SqlSelect select = new SqlSelect().where(TOperator.SQL_accountName)
						.and(TOperator.SQL_isDelete);
				TOperator o = operatorDao.load(select, oper);
				if(o != null) {
					log.info("用户名已存在");
					return result.error("用户名已存在");
				}
			}
			if(JsonInfo.get(TOperator.PROP_realName) != null) {
				oper.setRealName((String) JsonInfo.get(TOperator.PROP_realName));
			}
			if(JsonInfo.get(TOperator.PROP_mobile) != null) {
				oper.setMobile((String) JsonInfo.get(TOperator.PROP_mobile));
			}
			if(JsonInfo.get(TOperator.PROP_password) != null) {
				oper.setPassword((String) JsonInfo.get(TOperator.PROP_password));
			}
			if(JsonInfo.get(TOperator.PROP_roleId) != null) {
				oper.setRoleId((int) JsonInfo.get(TOperator.PROP_roleId));
			}
			int operId = operatorDao.insert(oper);
			if(operId > 0) {
				result.setData(operDao.getOperatorById(operId));
				result.success("添加成功");
				log.info("添加操作员接口执行成功");
			}else {
				result.error("添加失败");
				log.info("添加操作员接口执行失败");
			}
		}else {
			log.info("传入参数为空");
			result.error("传入参数为空");
		}
		return result;
	}
	
	//获取角色列表接口
	public JsonResult getRolePageList(ModelMap JsonInfo) {
		log.info("进入获取角色列表方法");
		JsonResult result = new JsonResult();
		int page = (int) JsonInfo.get("page");
		PageList<TRole> pagelist = new PageList<>(page);
		pagelist = tRoleDao.pageList(pagelist, true, null, null);
		if(pagelist != null) {
			result.success("获取成功");
			result.setData(pagelist);
			log.info("获取角色列表方法执行成功");
		}else {
			log.info("获取角色列表方法执行失败");
			return new ErrorResult("获取失败");
		}
		return result;
	}
	
	//新增角色信息接口
	public JsonResult addRoleInfo(ModelMap JsonInfo) {
		log.info("进入新增角色信息方法");
		JsonResult result = new JsonResult();
		TRole role = new TRole();
		role.setRemark((String) JsonInfo.get(TRole.PROP_remark));
		if(JsonInfo.get(TRole.PROP_menus) == null || "".equals(JsonInfo.get(TRole.PROP_menus))) {
			return new ErrorResult("菜单权限不可为空");
		}else {
			role.setMenus((String) JsonInfo.get(TRole.PROP_menus));
		}
		if(JsonInfo.get(TRole.PROP_permissions) == null 
				|| "".equals(JsonInfo.get(TRole.PROP_permissions))) {
			return new ErrorResult("访问权限不可为空");
		}else {
			role.setPermissions((String) JsonInfo.get(TRole.PROP_permissions));
		}
		if(JsonInfo.get(TRole.PROP_roleName) == null 
				|| "".equals(((String) JsonInfo.get(TRole.PROP_roleName)).trim())) {
			return new ErrorResult("角色名称不可为空");
		}else {
			role.setRoleName((String) JsonInfo.get(TRole.PROP_roleName));
		}
		int roleId = tRoleDao.insert(role);
		if(roleId != 0) {
			role = tRoleDao.loadById(roleId);
			result.setData(role);
			result.success("新增成功");
			log.info("新建角色信息方法执行成功");
		}else {
			log.info("新建角色信息方法执行失败");
			return new ErrorResult("新增失败");
		}
		return result;
	}
	
	//修改角色信息接口
	public JsonResult updateRole(TRole role) {
		log.info("进入修改角色信息方法");
		JsonResult result = new JsonResult();
		if(role.getMenus() == null || "".equals(role.getMenus())) {
			return new ErrorResult("菜单权限不可为空");
		}
		if(role.getPermissions() == null || "".equals(role.getPermissions())) {
			return new ErrorResult("访问权限不可为空");
		}
		if(role.getRoleName() == null || "".equals(((String) role.getRoleName()).trim())) {
			return new ErrorResult("角色名称不可为空");
		}
		int res = tRoleDao.updateAllById(role);
		if(res > 0) {
			result.success("修改成功");
			result.setData(tRoleDao.loadById(role.getId()));
			log.info("修改角色信息方法执行成功");
		}else {
			log.info("修改角色信息方法执行失败");
			return new ErrorResult("修改失败");
		}
		return result;
	}
	
	//删除角色信息接口
	public JsonResult delRoleInfo(Map<String, Object> map) {
		log.info("进入删除角色信息方法");
		JsonResult result = new JsonResult();
		SqlSelect sql = new SqlSelect().where(TOperator.SQL_roleId);
		List<TOperator> tor = operatorDao.listByMap(sql, map);
		if(tor !=null && tor.size() > 0) {
			log.info("该角色已有用户使用无法进行删除");
			return new ErrorResult("该角色已有用户使用无法进行删除");
		}else {
			int flag = tRoleDao.deleteById((int) map.get(TOperator.PROP_roleId));
			if(flag > 0) {
				log.info("删除角色信息方法执行成功");
				result.success("删除成功");
			}else {
				log.info("删除角色信息方法执行失败");
				result.success("删除失败");
			}
		}
		return result;
	}
	
	public List<Menu> treeMenus(int fid, List<TMenus> tm){
		List<Menu> menus = new ArrayList<>();
		for(TMenus t : tm) {
			if(t.getFid() == fid) {
				Menu  mb = new Menu();
				mb.setCode(t.getCode());
				mb.setName(t.getName());
				mb.setSubmenu(treeMenus(t.getId(), tm));
				menus.add(mb);
			}
		}
		return menus;
	}
}
