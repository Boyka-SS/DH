package com.jinke.driverhealth.utils;

/**
 * 项目配置类
 *
 * @author: fanlihao
 * @date: 2022/2/6
 */
public class Config {

    /**
     * 兔盯云
     */
    //数据起始时间
    public final static String START_TIME = "2021-10-01 00:00:00";
    //数据升序
    public final static String ASC_DATA = "asc";
    //数据降序
    public final static String DESC_DATA = "desc";
    //高德地图
    public final static String GAODE_MAP = "";


    //alcohol device name
    public final static String REMOTE_DEVICE_NAME = "";
    // alcohol device mac
    public final static String REMOTE_DEVICE_MAC = "";
    //SPP服务UUID号
    public final static String DEVICE_UUID = "";
    /**
     * 兔盯云平台
     */
    public static final String BASE_URL = "https://openapi.xu5g.com/";

    /**
     * APPKEY+SECRET：兔盯云手表秘钥
     * <p>
     * IMEI：设备号
     */
    public static final String APPKEY = "";
    public static final String SECRET = "";
    public static final String IMEI = "";

    /**
     * 事务序列号后缀
     */
    public static final String TOKEN_TRANSID_SUFFIX = "sB0iH2iC3gQ1";
    public static final String HR_TRANSID_SUFFIX = "vB3dB3nD7mA1";
    public static final String TEMP_TRANSID_SUFFIX = "pB3aK2kH3aF1";
    public static final String BP_TRANSID_SUFFIX = "aA5bF5eJ1dG5";
    public static final String RECENTLY_HR_TRANSID_SUFFIX = "Ze05Q7pNnzF9";
    public static final String RECENTLY_TEMP_TRANSID_SUFFIX = "udzN5qJB50oh";
    public static final String RECENTLY_BP_TRANSID_SUFFIX = "vr6vMS9dRbbQ";

    /**
     * 事务序列号
     */
    public static String getTransId(String suffix) {
        return APPKEY + "" + CalendarUtil.getNowFormatCalendar() + "" + suffix;
    }


}
