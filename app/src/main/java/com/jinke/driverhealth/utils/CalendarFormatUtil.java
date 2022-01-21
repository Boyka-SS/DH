package com.jinke.driverhealth.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }
}
