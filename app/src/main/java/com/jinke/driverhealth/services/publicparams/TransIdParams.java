package com.jinke.driverhealth.services.publicparams;

import com.jinke.driverhealth.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 事务序号
 * @author: fanlihao
 * @date: 2022/1/16
 */
public class TransIdParams {


    /**
     * trans_id : appkey(6 位数字) + 年月日时分秒 + 12 位任意字符串  1001420211221102817KQ1GXnGPHfpB
     *
     * @return
     */
    public String getTransIdParams() {
        return Constant.APPKEY + "" + getNowFormatCalendar() + "CnoWe6JURymQ";
    }

    /**
     * 获取当前符合格式化的 年月日时分秒
     *
     * @return
     */
    private String getNowFormatCalendar() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

}
