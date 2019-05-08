package com.yunqi.common.util;


public class ConstantTool {

	public final static String ISDELETE_YES = "Y"; //定义参数有效
	
	public final static String ISDELETE_NO = "N"; //定义参数无效
	
	public final static Integer ROLE_ADMIN = 0; //超级管理员
	
	public final static Integer ROLE_MANAGER = 1; //管理员
	
	public final static Integer ROLE_USER = 2; //用户
	
	public final static String PAGE_CURRENTPAGE = "page"; //分页页码数
	
	public final static String PAGE_CURRENTSIZE = "size"; //分页参数条数

	public final static String DESC = "desc"; //说明
	
	public final static String SESSION_USER = "sessionUser" ; //session中的用户信息键
	
	public final static String PASSWORD_REGULAR = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";//校验密码的正则
	
	public final static String USERNAME_REGULAR = "^[a-zA-Z][a-zA-Z0-9_]*$";//校验用户名的正则
	
	public final static String MOBILE_REGULAR = "^((13[0-9])|(15[^4])|((14[5,7]))|(18[0-9])|(17[0-8])|(147))\\d{8}$";//校验手机号的正则
	
	public final static String REALNAME_REGULAR = "^[\u4E00-\u9FA5]{2,8}$";//校验真实姓名的正则
	
	public final static String STATUS = "status"; //状态说明 
	
	public final static String ISSENCOND_YES = "Y"; //定义房车品鉴 二手置换
	
	public final static String ISSALES_YES = "Y"; //定义房车品鉴  当季促销
	
    public final static String SYSLOG_LOGTYPE_ONE = "1"; //系统日志类型为  后台来源类型
	
	public final static String SYSLOG_LOGTYPE_TWO = "2"; //系统日志类型为  微信端来源类型
	
	public final static String SYSLOG_LOGACTION_ONE = "1"; //系统日志操作类型为  登录操作
	
	public final static String SYSLOG_LOGACTION_TWO = "2"; //系统日志操作类型为  密码修改操作
	
	public final static String SYSLOG_LOGACTION_THREE = "3"; //系统日志操作类型为  订单支付成功操作
	
}
