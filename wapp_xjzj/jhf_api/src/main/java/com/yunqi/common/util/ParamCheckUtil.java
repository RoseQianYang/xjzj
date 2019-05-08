package com.yunqi.common.util;

public class ParamCheckUtil {
	
	public static boolean checkParamIsNull(String str) {
		if(str != null && !"".equals(str.trim())) {
			return true;
		}else {
			return false;
		}
	}
}
