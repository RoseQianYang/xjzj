package com.yunqi.jhf.web;

import java.util.ArrayList;
import java.util.List;

public class SplitStrUtil {
	
	//private static final Logger log = Logger.getLogger(SplitStrUtil.class);
	
	/**
	 * 用逗号分割字符串
	 * @param str
	 * @return intList
	 */
	public static List<Integer> splitString(String str) {
		List<Integer> list = new ArrayList<Integer>();
		if (str!=null) {
			String[] StringArr = str.split(",");
			for (String strlist : StringArr) {
				int intList = StrUtil.parseInt(strlist);
				list.add(intList);
			}
		}
		return list;
	}
	
	
    public static List<String> splitStrToStr(String str) {
		
		List<String> list = new ArrayList<String>();
		if (str!=null) {
			String[] StringArr = str.split(",");
			for (String strlist : StringArr) {
				list.add(strlist);
			}
		}
		return list;
	}
    
    
	public static void main(String[] args) {
//		List<Integer> list = splitString("10000,10001,10002,10003");
//		for (Integer integer : list) {
//			System.out.println(integer);
//		}
		List<String> list = splitStrToStr("红色,绿色,紫色");
		for (String str : list) {
			System.out.println(str);
		}
	}
}
