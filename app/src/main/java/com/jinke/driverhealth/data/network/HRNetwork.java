package com.jinke.driverhealth.data.network;

import com.jinke.driverhealth.data.network.beans.HeartRate;
import com.jinke.driverhealth.data.network.beans.SingleHr;
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
     * @param token     公共参数
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param page      页数
     * @param limit     每页条数
     * @param sort      数据排序方式
     * @param callback  请求数据的回调
     * @return
     */
    public void requestHRData(String token, String startTime, String endTime, String page, String limit, String sort, Callback<HeartRate> callback) {
        if (sort == "") {
            //数据默认降序
            sort = Config.DESC_DATA;
        }
        Map<String, String> header = new HashMap<>();
        String transId = Config.getTransId(Config.HR_TRANSID_SUFFIX);
        header.put("transid", transId);
        header.put("token", token);

        Map<String, String> hrQueryParams = new HashMap<>();
        hrQueryParams.put("start_time", startTime);
        hrQueryParams.put("end_time", endTime);
        hrQueryParams.put("page", page);
        hrQueryParams.put("limit", limit);
        hrQueryParams.put("sort", sort);

        mHRService.getHRData(header, hrQueryParams).enqueue(callback);
    }

    /**
     * 获取最近一次心率数据
     *
     * @param token
     * @param callback
     */
    public void requestRecentHRData(String token, String imei, Callback<SingleHr> callback) {
        Map<String, String> header = new HashMap<>();
        String transId = Config.getTransId(Config.RECENTLY_HR_TRANSID_SUFFIX);
        header.put("transid", transId);
        header.put("token", token);

        Map<String, String> hrQueryParams = new HashMap<>();
        hrQueryParams.put("imei_sn", imei);
        mHRService.getRecentHRData(header, hrQueryParams).enqueue(callback);
    }
}
