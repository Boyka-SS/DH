package com.jinke.driverhealth.data.network;

import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.data.network.api.HRService;
import com.jinke.driverhealth.utils.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/2/8
 */
public class HRNetwork {
    private ServiceCreator serviceCreator = new ServiceCreator();

    private HRService mHRService = serviceCreator.create(HRService.class);

    /**
     * 获取心率数据
     * @param startTime 开始时间 字符串，格式为 2006-01-01 00:00:00
     * @param endTime   结束时间 字符串，格式为 2006-01-01 00:00:00
     * @param page      页码 整数，范围为 1-5000
     * @param limit     每页条数 整数，范围为 1-200
     * @param callback
     */
    public void requestHRData(String startTime, String endTime, String page, String limit, Callback<HeartRate> callback) {
        Map<String, String> header = new HashMap<>();
        String transId = Config.getTransId(Config.HR_TRANSID_SUFFIX);
        header.put("transid", transId);

        Map<String, String> hrQueryParams = new HashMap<>();
        hrQueryParams.put("start_time", startTime);
        hrQueryParams.put("end_time", endTime);
        hrQueryParams.put("page", page);
        hrQueryParams.put("limit", limit);

        mHRService.getHRData(header, hrQueryParams).enqueue(callback);
    }
}
