package com.yunqi.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    //24*3600*1000
    public static Long TIME_INVERSE = 86400000L;

    /**
     * 时间字符串转时间戳
     * @param args 时间字符串
     * @return 时间戳
     */
    public static Timestamp StringtoTimestamp(String args) {
        return Timestamp.valueOf(args);
    }

    /**
     * @author  ZhangQ
     * 得到当前时间戳
     */
    public static Timestamp getNow(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = simpleDateFormat.format(date);
        return Timestamp.valueOf(time);
    }


    /**
     * @author  ZhangQ
     * UK date String to  Simple Date String
     */
    public static String stringToDate(String x) throws ParseException{
        SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date=sdf1.parse(x);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
    }

    /**
     * 传入时间戳获取年份
     * @param ts 时间
     * @return 年份
     */
    public static int getYear(Timestamp ts) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ts);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 传入时间戳获取月份
     * @param ts 时间
     * @return 月份
     */
    public static int getMonths(Timestamp ts) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ts);
        return calendar.get(Calendar.MONTH);
    }
}
