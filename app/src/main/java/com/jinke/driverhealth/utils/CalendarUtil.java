package com.jinke.driverhealth.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期转化类
 *
 * @author: fanlihao
 * @date: 2022/2/11
 */
public class CalendarUtil {

    /**
     * 获取当前符合transid格式的 年月日时分秒
     *
     * @return
     */
    public static String getNowFormatCalendar() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    public static String getNowFormatCalendar(String pattern) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    public static String getFormatCalendar(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date parseDate = format.parse(date);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formatDate = sdf.format(parseDate);
        return formatDate;
    }


    /**
     * 获取指定日期前后 past 天
     *
     * @param date  指定日期
     * @param past  变化天数
     * @param isAdd 控制前后
     * @return
     */
    public static String getCalCalendar(String date, int past, boolean isAdd) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date briefDate = format.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(briefDate);
        if (isAdd) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + past);
        } else {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
        }
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatCalendar = sdf.format(today);
        return formatCalendar;
    }


    /**
     * 判断某个日期是否在指定范围内
     *
     * @param begin
     * @param end
     * @param target
     * @return
     * @throws ParseException
     */
    public static boolean judgeCalendarBeforeOrAfter(String begin, String end, String target) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = sdf.parse(begin);
        Date endDate = sdf.parse(end);
        Date targetDate = sdf.parse(target);
        if (targetDate.before(endDate) && targetDate.after(beginDate)) {
            return true;
        } else {
            return false;
        }
    }
}
