package com.jinke.driverhealth.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: fanlihao
 * @date: 2022/2/6
 */
public class Config {

    public static volatile String TOKEN = "";


    /**
     * 兔盯云平台
     */
    public static final String BASE_URL = "https://openapi.xu5g.com/";

    /**
     * APPKEY+SECRET：兔盯云手表秘钥
     * <p>
     * IMEI：设备号
     */
    public static final String APPKEY = "10014";
    public static final String SECRET = "2c5058d97630269bf0867035764f81db";
    public static final String IMEI = "689466010023530";

    /**
     * 事务序列号后缀
     */
    public static final String TOKEN_TRANSID_SUFFIX = "sB0iH2iC3gQ1";
    public static final String HR_TRANSID_SUFFIX = "vB3dB3nD7mA1";
    public static final String TEMP_TRANSID_SUFFIX = "pB3aK2kH3aF1";
    public static final String BP_TRANSID_SUFFIX = "aA5bF5eJ1dG5";

    /**
     * 事务序列号
     */
    public static String getTransId(String suffix) {
        return APPKEY + "" + getNowFormatCalendar() + "" + suffix;
    }

    /**
     * 获取当前符合格式化的 年月日时分秒
     *
     * @return
     */
    private static String getNowFormatCalendar() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }
}
