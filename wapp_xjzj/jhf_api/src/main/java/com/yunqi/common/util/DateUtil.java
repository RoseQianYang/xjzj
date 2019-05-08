package com.yunqi.common.util;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

public class DateUtil {
	private static Logger logger = Logger.getLogger(DateUtil.class);

	public static final int YEAR = Calendar.YEAR;

	public static final int MONTH = Calendar.MONTH;

	public static final int DAY = Calendar.DAY_OF_MONTH;

	public static final int HOUR = Calendar.HOUR_OF_DAY;

	public static final int MINUTE = Calendar.MINUTE;

	public static final int SECOND = Calendar.SECOND;

	public DateUtil() {
	}

	public static int getYear(java.sql.Date d) {
		Calendar c = java.util.Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(java.sql.Date d) {
		Calendar c = java.util.Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.MONTH);
	}

	public static int getDay(java.sql.Date d) {
		Calendar c = java.util.Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getCurrentYear() {
		Calendar c = java.util.Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}

	public static int getCurrentMonth() {
		Calendar c = java.util.Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH);
	}

	public static int getCurrentDay() {
		Calendar c = java.util.Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static void main(String[] args) {

		DateUtil.dateClearance("2003-10-20", "2003-10-30");
		String time = DateUtil.getCurrentTime();
		System.out.println(time);

	}
	
	public static String getFormatDatetime(Timestamp timeStamp) {
		return DateUtil.getTimeFormat(timeStamp, 2);
	}

	public static String getFormatDate(Timestamp timeStamp) {
		return getTimeFormat(timeStamp, 1);
	}

	public static String getFormatDateYYYYMM(Timestamp timeStamp) {
		return getTimeFormat(timeStamp, 3);
	}

	public static String getFormatDatetimeMMDDHHMM(Timestamp timeStamp) {
		return DateUtil.getTimeFormat(timeStamp, 4);
	}

	public static String getCurrentTime() {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		StringBuffer sb = new StringBuffer();

		sb.append(c.get(Calendar.YEAR));
		sb.append(DateUtil.fillZero(c.get(Calendar.MONTH) + 1));
		sb.append(DateUtil.fillZero(c.get(Calendar.DAY_OF_MONTH)));
		sb.append(DateUtil.fillZero(c.get(Calendar.HOUR_OF_DAY)));
		sb.append(DateUtil.fillZero(c.get(Calendar.MINUTE)));
		sb.append(DateUtil.fillZero(c.get(Calendar.SECOND)));

		return sb.toString();
	}

	/**
	 * type = 1 : "YYYY-MM-DD" type = 2 : "YYYY-MM-DD HH:MM:SS"
	 */
	public static String getTimeFormat(Timestamp timeStamp, int type) {

		if (timeStamp == null)
			return null;

		Calendar c = Calendar.getInstance();
		c.setTime(timeStamp);

		StringBuffer sb = new StringBuffer();
		switch (type) {

		case 1:
			sb.append(c.get(Calendar.YEAR));
			sb.append("-");
			sb.append(DateUtil.fillZero(c.get(Calendar.MONTH) + 1));
			sb.append("-");
			sb.append(DateUtil.fillZero(c.get(Calendar.DAY_OF_MONTH)));

			break;

		case 2:
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
			break;

		case 3:
			sb.append(c.get(Calendar.YEAR));
			sb.append("-");
			sb.append(DateUtil.fillZero(c.get(Calendar.MONTH) + 1));

			break;

		case 4:
			sb.append(DateUtil.fillZero(c.get(Calendar.MONTH) + 1));
			sb.append("-");
			sb.append(DateUtil.fillZero(c.get(Calendar.DAY_OF_MONTH)));
			sb.append(" ");
			sb.append(DateUtil.fillZero(c.get(Calendar.HOUR_OF_DAY)));
			sb.append(":");
			sb.append(DateUtil.fillZero(c.get(Calendar.MINUTE)));
			break;
		case 5:
			sb.append(c.get(Calendar.YEAR));
			sb.append("-");
			sb.append(DateUtil.fillZero(c.get(Calendar.MONTH) + 1));
			sb.append("-");
			sb.append(DateUtil.fillZero(c.get(Calendar.DAY_OF_MONTH)));
			sb.append(" ");
			sb.append(DateUtil.fillZero(c.get(Calendar.HOUR_OF_DAY)));
			sb.append(":");
			sb.append(DateUtil.fillZero(c.get(Calendar.MINUTE)));
			break;

		default:
			sb.append("unkonw date format type");
			break;

		}
		return sb.toString();

	}

	public static String fillZero(int i) {

		if (i < 10)
			return ("0" + String.valueOf(i));
		else
			return String.valueOf(i);

	}

	public static Timestamp getCurrentTimeAsTimestamp() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
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
		return Timestamp.valueOf(sb.toString());

	}

	/**
	 * 
	 * @param intSecond
	 *            this param is the total second
	 * @return 5h24m23s example intSecond=133 return 0h2m13s intSecond=88927
	 *         return 24h42m7s
	 */
	public static String getTimeSession(int intSecond) {
		int s = intSecond;
		int h = s / (60 * 60);
		s = s - h * 60 * 60;
		int m = s / (60);
		s = s - m * 60;
		StringBuffer sb = new StringBuffer();
		if (h != 0)
			sb.append(h + "h");
		if (m != 0)
			sb.append(m + "m");
		sb.append(s + "s");
		return sb.toString();

	}

	public static Hashtable<String, String> getDateSession(int intSecond) {
		int s = intSecond;
		int d = s / (24 * 60 * 60);
		s = s - d * (24 * 60 * 60);
		int h = s / (60 * 60);
		s = s - h * 60 * 60;
		int m = s / (60);
		s = s - m * 60;
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		hashTable.put("day", String.valueOf(d));
		hashTable.put("hour", String.valueOf(h));
		hashTable.put("minute", String.valueOf(m));
		hashTable.put("second", String.valueOf(s));
		return hashTable;
	}

	public static Timestamp TimestampAdd(int part, Timestamp tspSrc,
			int intAmount) {
		Timestamp t = tspSrc;
		Calendar c = Calendar.getInstance();
		java.sql.Date d = java.sql.Date.valueOf(DateUtil.getFormatDate(t));
		c.setTime(d);
		try {
			c.add(part, intAmount);
		} catch (Exception e) {
			logger.debug(e);
			return null;
		}

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
		t = Timestamp.valueOf(sb.toString());
		return t;
	}

	public static Timestamp convertPeriodTime(int hour, int min) {
		try {
			return Timestamp.valueOf("1970-01-01 " + DateUtil.fillZero(hour)
					+ ":" + DateUtil.fillZero(min) + ":00");
		} catch (Exception e) {
			logger.warn("convert period time exception : " + e);
			return null;
		}
	}

	public static String displayPeriodTime(Timestamp time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumIntegerDigits(2);
		nf.setMinimumIntegerDigits(2);
		return nf.format(cal.get(Calendar.HOUR_OF_DAY)) + ":"
				+ nf.format(cal.get(Calendar.MINUTE)) + ":00";
	}

	public static String convertDiscountToDate(int year, int month, int day) {
		return String.valueOf(year) + "-" + DateUtil.fillZero(month) + "-"
				+ DateUtil.fillZero(day) + " 00:00:00";
	}


	public static String convertDiscountFromDate(int year, int month, int day) {
		return String.valueOf(year) + "-" + DateUtil.fillZero(month) + "-"
				+ DateUtil.fillZero(day) + " 00:00:00";
	}
	public static long dateClearance(String dateBefore, String dateAfter) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(dateAfter);

			java.util.Date mydate = myFormatter.parse(dateBefore);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return day;

	}


	public static long dateClearance(Date dateBefore, Date dateAfter) {
		long day = 0;
		day = (dateAfter.getTime() - dateBefore.getTime())
				/ (24 * 60 * 60 * 1000);

		return day;

	}
	public static int getDatePart(java.sql.Timestamp tsp, int part) {
		Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(tsp.getTime());
		try {
			if (part != Calendar.MONTH)
				return c.get(part);
			else
				return c.get(part) + 1;
		} catch (Exception e) {
			logger.debug(e);
			return -1;
		}
	}
}