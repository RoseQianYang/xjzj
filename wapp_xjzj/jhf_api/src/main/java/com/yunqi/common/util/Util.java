package com.yunqi.common.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * put this util into velocity
 * 
 * @author tuweiguo
 */
public class Util {

	private static Logger log = Logger.getLogger(Util.class);

	private static DateUtil dateUtil = new DateUtil();

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "ABC打印测试信息\n";
		System.out.println(Util.byteArrayToHexString(s.getBytes("GBK")).toUpperCase());
		s = "中";
		System.out.println(Util.byteArrayToHexString(s.getBytes("GBK")).toUpperCase());

		System.out.println("src: " + Util.byteArrayToHexString(new byte[] { 27, 33, 0 }).toUpperCase());

		byte cmd = 0;
		System.out.println("bold " + Util.byteArrayToHexString(((byte) (0x8 | cmd))).toUpperCase());
		System.out.println("height " + Util.byteArrayToHexString((byte) (0x10 | cmd)).toUpperCase());
		System.out.println("width " + Util.byteArrayToHexString((byte) (0x20 | cmd)).toUpperCase());
		System.out.println("underline " + Util.byteArrayToHexString((byte) (0x80 | cmd)).toUpperCase());
		System.out.println("small " + Util.byteArrayToHexString((byte) (0x1 | cmd)).toUpperCase());

		// Bold
		cmd = ((byte) (0x8 | cmd));
		// Height
		cmd = ((byte) (0x10 | cmd));
		// Width
		cmd = ((byte) (0x20 | cmd));
		System.out.println("largest: " + Util.byteArrayToHexString(cmd).toUpperCase());

	}

	public void init() {
	}

	public static String html2Text(String src, int len) {
		if (src == null || src.length() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean isTagBegin = false;
		int size = 0;
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (c == '<') {
				isTagBegin = true;
				continue;
			}
			if (c == '>') {
				isTagBegin = false;
				continue;
			}
			if (!isTagBegin) {
				sb.append(c);
				size++;
			}
			if (size >= len) {
				break;
			}
		}

		return sb.toString();
	}

	public static String fileSize(long size) {
		if (size < 1024) {
			return "1k";
		} else {
			return (size / 1024) + "k";
		}
	}

	/**
	 * 
	 * 
	 * @param num
	 * @return
	 */
	public static String randomStr(int num) {
		if (num == 0) {
			return "";
		}
		if (num > 10) {
			num = 10;
		}
		long max = 1;
		for (int i = 0; i < num; i++) {
			max = max * 10;
		}
		double d = Math.random();
		String str = String.valueOf((long) Math.ceil((double) d * max));
		while (str.length() < num) {
			str += "0";
		}
		return str;
	}

	public static int random(int max) {
		if (max == 0) {
			return 0;
		}
		double d = Math.random();
		int random = (int) Math.ceil((double) d * max);
		return random;
	}

	public static boolean keyInList(String key, String[] list) {
		if (list == null || key == null) {
			return false;
		}
		for (int i = 0; i < list.length; i++) {
			String s = list[i];
			if (key.equals(s)) {
				return true;
			}
		}
		return false;
	}

	public static void mkdirs(String fileString) {
		if (StringUtils.isEmpty(fileString)) {
			return;
		}
		int p = fileString.indexOf(".");
		if (p > 0) {
			int pos = fileString.lastIndexOf("/");
			if (pos > 0) {
				String dirString = fileString.substring(0, pos);
				File f = new File(dirString);
				f.mkdirs();
			}
		}
	}

	public static String photoSmallPath(int userId) {
		StringBuffer sb = photoPath(userId);
		sb.append("_s.jpg");
		return sb.toString();
	}

	public static String photoMiddlePath(int userId) {
		StringBuffer sb = photoPath(userId);
		sb.append("_m.jpg");
		return sb.toString();
	}

	public static String photoBigPath(int userId) {
		StringBuffer sb = photoPath(userId);
		sb.append("_b.jpg");
		return sb.toString();
	}

	public static String photoOrgPath(int userId) {
		StringBuffer sb = photoPath(userId);
		sb.append("_o.jpg");
		return sb.toString();
	}

	private static StringBuffer photoPath(int userId) {
		String s = md5(String.valueOf(userId), "qianli@))^");
		StringBuffer sb = new StringBuffer();
		sb.append("/");
		sb.append(s.substring(0, 2));
		sb.append("/");
		sb.append(s.substring(2, 4));
		sb.append("/");
		sb.append(s.substring(4));
		return sb;
	}

	public static String pathOrg(String filePath) {
		return pathString(filePath, "_o");
	}

	public static String pathBig(String filePath) {
		return pathString(filePath, "_b");
	}

	public static String pathMiddle(String filePath) {
		return pathString(filePath, "_m");
	}

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

	public static String md5(String src, String key) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			if (key != null) {
				md.update(key.getBytes()); // key
			}
			return byteArrayToHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			log.fatal("not support md5!");
			return src;
		}
	}

	public static String byteArrayToHexString(byte inputByte) {
		byte[] b = new byte[1];
		b[0] = inputByte;
		return byteArrayToHexString(b);
	}

	public static String byteArrayToHexString(byte in[]) {
		byte ch = 0x00;
		int i = 0;
		if (in == null || in.length <= 0)
			return null;

		String pseudo[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		StringBuffer out = new StringBuffer(in.length * 2);

		while (i < in.length) {
			ch = (byte) (in[i] & 0xF0); // Strip offhigh nibble
			ch = (byte) (ch >>> 4); // shift the bits down
			ch = (byte) (ch & 0x0F); // must do this is high order bit is on!
			out.append(pseudo[(int) ch]); // convert thenibble to a String
			ch = (byte) (in[i] & 0x0F); // Strip offlow nibble
			out.append(pseudo[(int) ch]); // convert thenibble to a String
			i++;
		}
		String rslt = new String(out);
		return rslt.toLowerCase();
	}

	public static String timestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static String getParameter(String str) {
		if (str == null) {
			return null;
		}
		try {
			return new String(str.getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	public String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len <= 0) {
			return str;
		}
		boolean needDot = false;
		if (str.length() > len) {
			needDot = true;
		}
		return StringUtils.left(str, len) + (needDot ? "..." : "");
	}

    public String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len <= 0) {
            return str;
        }
        return StringUtils.right(str, len);
    }

	public String stripHttp(String url) {
		if (url != null && url.startsWith("http://")) {
			return url.substring(7);
		}
		return url;
	}

	public String nl2br(String note) {
		if (note == null)
			return null;
		return note.replaceAll("\n", "<br />");
	}

	public static File[] sortFileList(File[] list) {
		if (list == null || list.length == 0) {
			return new File[0];
		}
		for (int i = 1; i < list.length; i++) {
			for (int j = 0; j < i; j++) {
				if (list[i].isDirectory() && list[j].isFile()) {
					File temp = list[j];
					list[j] = list[i];
					list[i] = temp;
					continue;
				}
				if (list[i].isFile() && list[j].isDirectory()) {
					continue;
				}
				if (list[i].getName().compareTo(list[j].getName()) < 0) {
					File temp = list[j];
					list[j] = list[i];
					list[i] = temp;
					continue;
				}
			}
		}

		return list;
	}

	public static String safeShowHTML(String str) {
		if (str != null) {
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("\n", "<br>");
			str = str.replaceAll("\"", "&quot;");
		} else {
			str = "";
		}
		return str;
	}

	public static String safeHTML(String string) {

		if (string == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer(string.length());
		int len = string.length();
		char c;

		for (int i = 0; i < len; i++) {
			c = string.charAt(i);
			if (c == '<') {
				sb.append("&lt;");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String escapeHtml(String str) {
		return StringEscapeUtils.escapeHtml(str);
	}

	public static String stringToHTMLString(String string) {

		if (string == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer(string.length());
		// true if last char was blank
		boolean lastWasBlankChar = false;
		int len = string.length();
		char c;

		for (int i = 0; i < len; i++) {
			c = string.charAt(i);
			if (c == ' ') {
				// blank gets extra work,
				// this solves the problem you get if you replace all
				// blanks with &nbsp;, if you do that you loss
				// word breaking
				if (lastWasBlankChar) {
					lastWasBlankChar = false;
					sb.append("&nbsp;");
				} else {
					lastWasBlankChar = true;
					sb.append(' ');
				}
			} else {
				lastWasBlankChar = false;
				//
				// HTML Special Chars
				if (c == '"')
					sb.append("&quot;");
				else if (c == '&')
					sb.append("&amp;");
				else if (c == '<')
					sb.append("&lt;");
				else if (c == '>')
					sb.append("&gt;");
				else if (c == '\n')
					// Handle Newline
					sb.append("&lt;br/&gt;");
				else {
					int ci = 0xffff & c;
					if (ci < 160)
						// nothing special only 7 Bit
						sb.append(c);
					else {
						// Not 7 Bit use the unicode system
						sb.append("&#");
						sb.append(new Integer(ci).toString());
						sb.append(';');
					}
				}
			}
		}
		return sb.toString();
	}

	public static String getYear(Date d) {
		return String.valueOf(getYearInt(d));
	}

	public static String getMonth(Date d) {
		int month = getMonthInt(d);
		return (month < 10 ? "0" : "") + String.valueOf(month);
	}

	public static String getDay(Date d) {
		int day = getDayInt(d);
		return (day < 10 ? "0" : "") + String.valueOf(day);
	}

	public static int getYearInt(Date d) {
		Calendar c = java.util.Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.YEAR);
	}

	public static int getMonthInt(Date d) {
		Calendar c = java.util.Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getDayInt(Date d) {
		Calendar c = java.util.Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}

	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.MONTH);
	}

	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static String showCurrentDate() {
		return showDate(new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * "YYYY-MM-DD"
	 */
	public static String showYearAndMonth(Timestamp timeStamp) {
		if (timeStamp == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(timeStamp);
		StringBuffer sb = new StringBuffer();
		sb.append(c.get(Calendar.YEAR));
		sb.append("-");
		sb.append(DateUtil.fillZero(c.get(Calendar.MONTH) + 1));
		return sb.toString();
	}

	/**
	 * "YYYY-MM-DD"
	 */
	public static String showDate(Timestamp timeStamp) {

		if (timeStamp == null)
			return null;

		Calendar c = Calendar.getInstance();
		c.setTime(timeStamp);

		StringBuffer sb = new StringBuffer();
		sb.append(c.get(Calendar.YEAR));
		sb.append("-");
		sb.append(DateUtil.fillZero(c.get(Calendar.MONTH) + 1));
		sb.append("-");
		sb.append(DateUtil.fillZero(c.get(Calendar.DAY_OF_MONTH)));
		return sb.toString();

	}

	/**
	 * "YYYY-MM-DD HH:MM:SS"
	 */
	public static String showTime(Timestamp timeStamp) {

		if (timeStamp == null)
			return null;

		Calendar c = Calendar.getInstance();
		c.setTime(timeStamp);

		StringBuffer sb = new StringBuffer();
		sb.append(c.get(Calendar.YEAR));
		sb.append("-");
		sb.append(DateUtil.fillZero(c.get(Calendar.MONTH) + 1));
		sb.append("-");
		sb.append(DateUtil.fillZero(c.get(Calendar.DAY_OF_MONTH)));

		sb.append(" ");
		sb.append(DateUtil.fillZero(c.get(Calendar.HOUR_OF_DAY)));
		sb.append(":");
		sb.append(DateUtil.fillZero(c.get(Calendar.MINUTE)));
		sb.append(":");
		sb.append(DateUtil.fillZero(c.get(Calendar.SECOND)));
		return sb.toString();
	}

    public static String showTime(Timestamp timeStamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(timeStamp);
    }

	public static String splitDir(String dir) {
		if (StringUtils.isEmpty(dir) || dir.length() == 1) {
			return "<a href='/adm/res/list.do?currentDir=/'>根目录</a>";
		}
		String[] dirs = dir.split("/");
		StringBuffer str = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < dirs.length; i++) {

			str.append("<a href='/adm/res/list.do?currentDir=");
			if (StringUtils.isEmpty(dirs[i])) {
				str.append("/'>根目录");
			} else {
				str.append(temp);
				str.append("/");
				str.append(dirs[i]);
				str.append("'>");
				str.append(dirs[i]);
				temp.append("/");
				temp.append(dirs[i]);
			}
			str.append("</a>/");
		}
		return str.toString();
	}

	public static String showTime(long stamp) {
		Timestamp t = new Timestamp(stamp);
		return showTime(t);
	}

	@SuppressWarnings("unused")
	private static String fillZero(int i) {
		if (i < 10)
			return ("0" + String.valueOf(i));
		else
			return String.valueOf(i);
	}

	public static DateUtil getDateUtil() {
		return dateUtil;
	}

	public static void setDateUtil(DateUtil dateUtil) {
		Util.dateUtil = dateUtil;
	}

	public static String safeJS(String src) {
		if (StringUtils.isEmpty(src)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (c == '\r') {
				continue;
			}
			if (c == '\n') {
				continue;
			}
			if (c == '\"') {
				sb.append("\\\"");
			}
			sb.append(c);
		}
		return sb.toString();

	}

	public static String httpToHtml(String s) {
		if (StringUtils.isEmpty(s)) {
			return s;
		}
		int p1 = s.indexOf("http://");
		if (p1 <= 0) {
			return s;
		}
		int p2 = s.indexOf(" ", p1);
		if (p2 <= 0) {
			p2 = s.indexOf(",", p1);
		}
		if (p2 <= 0) {
			p2 = s.indexOf("\n", p1);
		}
		if (p2 <= 0) {
			return s;
		}
		String out = null;
		log.debug("p1 = " + p1 + " ; p2 = " + p2);
		String link = s.substring(p1, p2);
		out = s.substring(0, p1);
		out += "<a href=\"" + link + "\" target=\"_blank\">" + link + "</a>";
		out += s.substring(p2);
		return out;
	}

	/**
	 * hashId to userId
	 * 
	 * @param hashId
	 * @return
	 */
	public static int hashToUid(String hashId) {
		return 0;
	}
	
    public static String getClientIp(HttpServletRequest request) {
	    
         String ip = request.getHeader("x-forwarded-for");
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getHeader("Proxy-Client-IP");
             }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getHeader("WL-Proxy-Client-IP");
             }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getHeader("HTTP_CLIENT_IP");
             }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getHeader("HTTP_X_FORWARDED_FOR");
             }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getRemoteAddr();
             }
         return ip;
     }
}
