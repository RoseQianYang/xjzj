package com.yunqi.jhf.web;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class StrUtil {
	private static final Logger log = Logger.getLogger(StrUtil.class);


	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Calendar getCurrentDateCalendar() {
		Calendar c = Calendar.getInstance();
		c.set(11, 0);
		c.set(12, 0);
		c.set(13, 0);
		c.set(14, 0);
		return c;
	}

	public static Timestamp getCurrentDate() {
		Calendar c = getCurrentDateCalendar();
		return new Timestamp(c.getTimeInMillis());
	}

	public static BigDecimal strToBigDecimal(String str) {
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
		}
		return null;
	}

	public static int parseInt(String str) {
		if (StringUtils.isEmpty(str)) {
			return 0;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return 0;
	}

	public static Timestamp str2Timestamp(String str, String format) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date d;
		try {
			d = dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Timestamp timestamp = new Timestamp(d.getTime());
		return timestamp;
	}

	public static Timestamp dateStr2Timestamp(String str) {
		return str2Timestamp(str, "yyyy-MM-dd");
	}

	public static Timestamp timeStr2Timestamp(String str) {
		return str2Timestamp(str, "yyyy-MM-dd HH:mm:ss");
	}

	public static Timestamp timestamp2DateStr(String str) {
		return str2Timestamp(str, "yyyy-MM-dd HH:mm:ss");
	}

	public static String md5(String src) {
		return md5(src, "");
	}

	public static String md5(String src, String key) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			if (key != null) {
				md.update(key.getBytes());
			}
			return byteArrayToHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			log.fatal("not support md5!");
		}
		return src;
	}

	public static String byteArrayToHexString(byte[] in) {
		byte ch = 0;
		int i = 0;
		if ((in == null) || (in.length <= 0)) {
			return null;
		}
		String[] pseudo = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

		StringBuffer out = new StringBuffer(in.length * 2);
		while (i < in.length) {
			ch = (byte) (in[i] & 0xF0);
			ch = (byte) (ch >>> 4);
			ch = (byte) (ch & 0xF);
			out.append(pseudo[ch]);
			ch = (byte) (in[i] & 0xF);
			out.append(pseudo[ch]);
			i++;
		}
		String rslt = new String(out);
		return rslt.toLowerCase();
	}

	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		str = str.trim();
		boolean needDot = false;
		if (str.length() > len) {
			needDot = true;
		}
		return StringUtils.left(str, len) + (needDot ? "..." : "");
	}

	public static String showDate(Timestamp timeStamp) {
		if (timeStamp == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(timeStamp);
		StringBuffer sb = new StringBuffer();
		sb.append(c.get(1));
		sb.append("-");
		sb.append(numFormat(c.get(2) + 1, 2));
		sb.append("-");
		sb.append(numFormat(c.get(5), 2));
		return sb.toString();
	}

	public static String showTime(Timestamp timeStamp) {
		if (timeStamp == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(timeStamp);
		StringBuffer sb = new StringBuffer();
		sb.append(c.get(1));
		sb.append("-");
		sb.append(numFormat(c.get(2) + 1, 2));
		sb.append("-");
		sb.append(numFormat(c.get(5), 2));
		sb.append(" ");
		sb.append(numFormat(c.get(11), 2));
		sb.append(":");
		sb.append(numFormat(c.get(12), 2));
		sb.append(":");
		sb.append(numFormat(c.get(13), 2));
		return sb.toString();
	}
	
	public static String showTimeTo(Timestamp timeStamp) {
		if (timeStamp == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(timeStamp);
		StringBuffer sb = new StringBuffer();
		sb.append(c.get(1));
		sb.append("-");
		sb.append(numFormat(c.get(2) + 1, 2));
		sb.append("-");
		sb.append(numFormat(c.get(5), 2));
		sb.append(" ");
		sb.append(numFormat(c.get(11), 2));
		sb.append(":");
		sb.append(numFormat(c.get(12), 2));
//		sb.append(":");
//		sb.append(numFormat(c.get(13), 2));
		return sb.toString();
	}
	
	
	public static String showTimeHour(Timestamp timeStamp) {
		if (timeStamp == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(timeStamp);
		StringBuffer sb = new StringBuffer();
		sb.append(numFormat(c.get(2) + 1, 2));
		sb.append("月");
		sb.append(numFormat(c.get(5), 2));
		sb.append("日");
		sb.append(" ");
		sb.append(numFormat(c.get(11), 2));
		sb.append(":");
		sb.append(numFormat(c.get(12), 2));
		sb.append(":");
		sb.append(numFormat(c.get(13), 2));
		return sb.toString();
	}
	

	public static String numFormat(int i, int size) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(size);
		nf.setMinimumIntegerDigits(size);
		return nf.format(i);
	}

	public static String html2Text(String src, int len) {
		if ((src == null) || (src.length() == 0)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean isTagBegin = false;
		int size = 0;
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (c == '<') {
				isTagBegin = true;
			} else if (c == '>') {
				isTagBegin = false;
			} else {
				if (!isTagBegin) {
					sb.append(c);
					size++;
				}
				if (size >= len) {
					break;
				}
			}
		}
		return sb.toString();
	}

	public static String randomStr(int num) {
		if (num == 0) {
			return "";
		}
		if (num > 10) {
			num = 10;
		}
		long max = 1L;
		for (int i = 0; i < num; i++) {
			max *= 10L;
		}
		double d = Math.random();
		String str = String.valueOf(Math.ceil(d * max));
		while (str.length() < num) {
			str = str + "0";
		}
		return str;
	}

	public static int random(int max) {
		if (max == 0) {
			return 0;
		}
		double d = Math.random();
		int random = (int) Math.ceil(d * max);
		return random;
	}

	public static String nl2br(String note) {
		if (note == null) {
			return null;
		}
		return note.replaceAll("\n", "<br />");
	}

	public static String safeHtml(String str) {
		if (str != null) {
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("\n", "<br/>");
			str = str.replaceAll("\"", "&quot;");
			str = str.replaceAll(" ", "&nbsp;");
		} else {
			str = "";
		}
		return str;
	}

	public static String safeShowHtml(String string) {
		if (string == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer(string.length());

		boolean lastWasBlankChar = false;
		int len = string.length();
		for (int i = 0; i < len; i++) {
			char c = string.charAt(i);
			if (c == ' ') {
				if (lastWasBlankChar) {
					lastWasBlankChar = false;
					sb.append("&nbsp;");
				} else {
					lastWasBlankChar = true;
					sb.append(' ');
				}
			} else {
				lastWasBlankChar = false;
				if (c == '"') {
					sb.append("&quot;");
				} else if (c == '&') {
					sb.append("&amp;");
				} else if (c == '<') {
					sb.append("&lt;");
				} else if (c == '>') {
					sb.append("&gt;");
				} else if (c == '\n') {
					sb.append("&lt;br/&gt;");
				} else {
					int ci = 0xFFFF & c;
					if (ci < 160) {
						sb.append(c);
					} else {
						sb.append("&#");
						sb.append(new Integer(ci).toString());
						sb.append(';');
					}
				}
			}
		}
		return sb.toString();
	}

	public static long rmbStr2fen(String str) {
		try {
			BigDecimal bd = new BigDecimal(str);
			bd = bd.multiply(new BigDecimal(100));
			return bd.longValue();
		} catch (Exception e) {
		}
		return 0L;
	}

	public static int rmbStr2FenInt(String str) {
		try {
			BigDecimal bd = new BigDecimal(str);
			bd = bd.multiply(new BigDecimal(100));
			return bd.intValue();
		} catch (Exception e) {
		}
		return 0;
	}


	public static String rmb(int amount) {
		return rmb2(amount);
	}

	public static String rmb(long amount) {
		return rmb2(amount) + "元";
	}

	public static String rmb2(long amount) {
		if (amount == 0L) {
			return "0.00";
		}
		StringBuilder sb = new StringBuilder();
		if (amount < 0L) {
			sb.append("-");
			amount = Math.abs(amount);
		}
		long yuan = amount / 100L;
		long fen = amount % 100L;
		String strFen = String.valueOf(fen);
		if (fen < 10L) {
			strFen = "0" + fen;
		}
		if (fen == 0L) {
			return yuan + ".00";
		}
		return yuan + "." + strFen;
	}

	public static String rmbNoFen(long amount) {
		if (amount == 0L) {
			return "0.00";
		}
		StringBuilder sb = new StringBuilder();
		if (amount < 0L) {
			sb.append("-");
			amount = Math.abs(amount);
		}
		long yuan = amount / 100L;
		return yuan + ".00";
	}

	public static String sqlStr(String str) {
		return str.replaceAll(".*([';]+|(--)+).*", " ");
	}

	public static int getYear(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(1);
	}

	public static int getMonth(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(2);
	}

	public static int getDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(5);
	}

	public static boolean isMobileNo(String mobileNum) {
		if (StringUtils.isEmpty(mobileNum)) {
			return false;
		}
		if (mobileNum.length() != 11) {
			return false;
		}
		for (int i = 0; i < mobileNum.length() - 1; i++) {
			char c = mobileNum.charAt(i);
			if ((c < '0') || (c > '9')) {
				return false;
			}
		}
		return true;
	}
//前台页面显示图片为——小图_s结尾的图片
	public static String uploadPath(String str) {
		String url="http://wapp.ncyunqi.com/upload/" + str;
		String newUrl=pathMiddle(url);
		return newUrl;
		
	}
	
//前台页面显示图片为——原图_o结尾的图片
	public static String uploadPathO(String str) {
		String url="http://wapp.ncyunqi.com/upload/" + str;
		String newUrl=pathOrg(url);
		return newUrl;
		
	}
//前台页面显示图片为——原图_0结尾的图片
		public static String uploadPathM(String str) {
			String url="http://wapp.ncyunqi.com/upload/" + str;
			String newUrl=pathOrg(url);
			return newUrl;
			
		}

	public static String uploadPathSquare(String str) {
		return uploadPath(pathSquare(str));
	}

	public static String uploadPathSmall(String str) {
		return uploadPath(pathSmall(str));
	}

	public static String uploadPathMiddle(String str) {
		return uploadPath(pathMiddle(str));
	}

	public static String uploadPathOrg(String str) {
		return uploadPath(pathOrg(str));
	}
	//原图
	public static String pathOrg(String filePath) {
		return pathString(filePath, "_o");
	}

	public static String pathBig(String filePath) {
		return pathString(filePath, "_b");
	}

	public static String pathMiddle(String filePath) {
		return pathString(filePath, "_m");
	}

	//小图
	public static String pathSmall(String filePath) {
		return pathString(filePath, "_s");
	}

	public static String pathSquare(String filePath) {
		return pathString(filePath, "_sq");
	}

	private static String pathString(String src, String str) {
		if (src == null) {
			return null;
		}
		int pos = src.lastIndexOf(".");
		if (pos <= 0) {
			return src;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(src.substring(0, pos));
		sb.append(str);
		sb.append(src.substring(pos));
		return sb.toString();
	}

	public static int getParamInt(HttpServletRequest req, String param, int defaultValue) {
		String str = req.getParameter(param);
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(req.getParameter(param));
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static int getParamInt(HttpServletRequest req, String param) {
		return getParamInt(req, param, 0);
	}

	public static String getParamStr(HttpServletRequest req, String param, String defaultValue) {
		String str = req.getParameter(param);
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		}
		return str;
	}

	public static Timestamp getParamDateStrTimestamp(HttpServletRequest req, String param) {
		String timeStr = getParamStr(req, param);
		if (timeStr == null) {
			return null;
		}
		return dateStr2Timestamp(timeStr);
	}

	public static Timestamp getParamTimeStrTimestamp(HttpServletRequest req, String param) {
		String timeStr = getParamStr(req, param);
		if (timeStr == null) {
			return null;
		}
		return timeStr2Timestamp(timeStr);
	}

	public static String getParamStr(HttpServletRequest req, String param) {
		return getParamStr(req, param, null);
	}

	public static Map<String, String> parseParams(String urlEncodedString) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isEmpty(urlEncodedString)) {
			return map;
		}
		String[] contentArray = urlEncodedString.split("&");
		for (String str : contentArray) {
			String[] strArray = str.split("=");
			if (strArray.length >= 2) {
				map.put(strArray[0], strArray[1]);
			}
		}
		return map;
	}

	public static String getFileExt(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return null;
		}
		if (fileName.lastIndexOf(".") == -1) {
			return null;
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	
	//获取UUID
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	
	
	
}
