package com.jinke.driverhealth.utils;

/**
 * @author: fanlihao
 * @date: 2022/1/10
 */

public class Constant {



    /**
     * 兔盯云平台
     */
    public static final String BASE_URL = "https://openapi.xu5g.com/";

    /**
     * APPKEY+SECRET：兔盯云手表秘钥
     *
     * IMEI：设备号
     */
    public static final String APPKEY = "10014";
    public static final String SECRET = "2c5058d97630269bf0867035764f81db";
    public static final String IMEI = "689466010023530";

    //请求数量
    public static int limit = 20;

    //健康状况
    public final int GOOD = 1;//健康
    public final int BAD = 2;//虚弱

}