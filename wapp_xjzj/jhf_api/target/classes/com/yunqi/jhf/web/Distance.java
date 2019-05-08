package com.yunqi.jhf.web;

import java.math.BigDecimal;

public class Distance {
	// 输入 坐标1的经度和纬度 坐标2 的经度和纬度 格式 小数
	// 返回 结果 单位米
	// 如坐标1经度:113.1051555186082 纬度:36.166498515566445
	// 坐标2经度:113.1048121958543 纬度:36.16655481496116
	// 输入：
	// 115.920113,28.680262
	// 115.884677,28.66709
	// 113.1051555186082
	// 36.166498515566445
	// 113.1048121958543
	// 36.16655481496116
	// 返回结果 38.6856299051866米
	// 有方法 java.math.BigDecimal.doubleValue()
	// 经纬度转距离 结果单位米
	public static  Float jingWeiDuDistance(BigDecimal x, BigDecimal y, double x2, double y2) {
		double x1 = x.doubleValue();
		double y1 = y.doubleValue();
		double distance = 0;
		double d = 0;
		d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) / 180 * Math.PI * 6300000;
		distance  = d / 1000;
		BigDecimal   b   =   new   BigDecimal(distance);  
		Float   f   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue();  
		return f;
	}
	/*public static void main(String[] args) {
		BigDecimal x = new BigDecimal(115.920113);
		BigDecimal y = new BigDecimal(28.680262);
		double y2 = 115.884677;
		double x2 = 28.66709;
		Float distance = jingWeiDuDistance(x, y, x2, y2);
		System.out.println(distance);
	}*/
	

}
