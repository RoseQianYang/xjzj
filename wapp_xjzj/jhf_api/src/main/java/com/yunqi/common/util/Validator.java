package com.yunqi.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Validator {

	public static boolean isUserId(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		return str.matches("[0-9A-Za-z_]{4,16}");
	}

	public static boolean isUserName(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		return str.matches("[\u0391-\uFFE5\\pP0-9A-Za-z]{2,10}");
	}

	public static boolean isMobileNo(String phone) {
		int len = phone.length();
		boolean rs = false;
		switch (len) {
		case 7:
			if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4}$", phone)) {
				rs = true;
			}
			break;
		case 11:
			if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", phone)) {
				rs = true;
			}
			break;
		default:
			rs = false;
			break;
		}
		return rs;
	}

	public static boolean isEmail(String str) {
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(str);
		return m.find();
	}

	private static boolean matchingText(String expression, String text) {
		Pattern p = Pattern.compile(expression); // 正则表达式
		Matcher m = p.matcher(text); // 操作的字符串
		boolean b = m.matches();
		return b;
	}

	public static boolean isIdCard(String idCard) {
		if (StringUtils.isEmpty(idCard)) {
			return false;
		}
		return new Validator().verifyIdCard(idCard);
	}

	private boolean verifyIdCard(String idCard) {
		return new VerifyIdCard().verify(idCard);
	}

	private class VerifyIdCard {
		// wi =2(n-1)(mod 11);加权因子
		final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,
				1 };
		// 校验码
		final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
		private int[] ai = new int[18];

		public VerifyIdCard() {
		}

		// 校验身份证的校验码
		public boolean verify(String idcard) {
			if (idcard.length() == 15) {
				idcard = uptoeighteen(idcard);
			}
			if (idcard.length() != 18) {
				return false;
			}
			String verify = idcard.substring(17, 18);
			if (verify.equals(getVerify(idcard))) {
				return true;
			}
			return false;
		}

		// 15位转18位
		public String uptoeighteen(String fifteen) {
			StringBuffer eighteen = new StringBuffer(fifteen);
			eighteen = eighteen.insert(6, "19");
			return eighteen.toString();
		}

		// 计算最后一位校验值
		public String getVerify(String eighteen) {
			int remain = 0;
			if (eighteen.length() == 18) {
				eighteen = eighteen.substring(0, 17);
			}
			if (eighteen.length() == 17) {
				int sum = 0;
				for (int i = 0; i < 17; i++) {
					String k = eighteen.substring(i, i + 1);
					ai[i] = Integer.valueOf(k);
				}
				for (int i = 0; i < 17; i++) {
					sum += wi[i] * ai[i];
				}
				remain = sum % 11;
			}
			return remain == 2 ? "X" : String.valueOf(vi[remain]);

		}
	}

	/**
	 * check if the image is supported
	 */

	private static String[] imgExts = { "jpg", "png","jpeg" };

	public static boolean isValidImage(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return false;
		}
		int pos = fileName.lastIndexOf(".");
		if (pos == -1) {
			return false;
		}
		String ext = fileName.substring(pos + 1);
		for (String str : imgExts) {
			if (str.equals(ext.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

}
