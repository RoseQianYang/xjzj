package com.yunqi.jhf.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统数据字典定义
 * 
 * @author tuweiguo
 */
public class Dict {

	public static Map<String, Map<Object, String>> dictMap = new HashMap<String, Map<Object, String>>();

	// 用户角色规定
	public final static String ROLE_MAP_KEY = "roleMap";
	public final static Integer ROLE_ADMIN = 1;
	public final static Integer ROLE_MANAGER = 2;
	public final static Integer ROLE_SALES_DIRECTOR = 3;
	public final static Integer ROLE_SALES = 4;
	public final static Integer ROLE_SERVICER_DIRECTOR = 5;
	public final static Integer ROLE_SERVICER = 6;
	public final static Integer ROLE_ACCOUNTANT = 7;

	// 用户角色map
	public final static Map<Object, String> roleMap = new LinkedHashMap<>();
	static {
		roleMap.put(ROLE_ADMIN, "管理员");
		roleMap.put(ROLE_MANAGER, "总经办");
		roleMap.put(ROLE_SALES_DIRECTOR, "销售总监");
		roleMap.put(ROLE_SALES, "销售");
		roleMap.put(ROLE_SERVICER_DIRECTOR, "客服总监");
		roleMap.put(ROLE_SERVICER, "客服");
		roleMap.put(ROLE_ACCOUNTANT, "财务人员");
		dictMap.put(ROLE_MAP_KEY, roleMap);
	}

	// 拖车类型 map自行式1 拖挂式2 皮卡3"
	public final static String vehicle_function_MAP_KEY = "vehicleFunctionMap";
	public final static Integer vehicle_function_type_zixing = 1;
	public final static Integer vehicle_function_type_tuogua = 2;
	public final static Integer vehicle_function_type_pika = 3;

	public final static Map<Object, String> vehicleFunctionTypeMap = new LinkedHashMap<>();
	static {
		vehicleFunctionTypeMap.put(vehicle_function_type_zixing, "自行式");
		vehicleFunctionTypeMap.put(vehicle_function_type_tuogua, "拖挂式");
		vehicleFunctionTypeMap.put(vehicle_function_type_pika, "皮卡");
		dictMap.put(vehicle_function_MAP_KEY, vehicleFunctionTypeMap);
	}

}
