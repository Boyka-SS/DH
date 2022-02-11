package com.jinke.driverhealth.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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





    /**
     * 获取指定日期前后 past 天
     *
     * @param date  指定日期
     * @param past  变化天数
     * @param isAdd 控制前后
     * @return
     */
    public static String getFormatCalendar(String date, int past, boolean isAdd) throws ParseException {

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
}
