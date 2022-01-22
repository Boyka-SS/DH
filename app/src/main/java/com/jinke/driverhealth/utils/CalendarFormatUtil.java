package com.jinke.driverhealth.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author: fanlihao
 * @date: 2022/1/21
 */
public class CalendarFormatUtil {
    /**
     * 获取当前符合格式化的 年月日时分秒
     *
     * @return
     */
    public static String getCurrentFormatTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }
}
