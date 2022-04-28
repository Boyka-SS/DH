package com.jinke.driverhealth.data.network.tudingyun;

import com.jinke.driverhealth.data.network.tudingyun.beans.BloodPressure;
import com.jinke.driverhealth.data.network.tudingyun.beans.SingleBp;
import com.jinke.driverhealth.data.network.tudingyun.api.BPService;
import com.jinke.driverhealth.utils.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/2/7
 */
public class BPNetwork {


    private ServiceCreator serviceCreator = new ServiceCreator();
    private BPService mBPService = serviceCreator.create(BPService.class);

    /**
     * @param token
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @param sort
     * @param callback
     */
    public void requestBPData(String token, String startTime, String endTime, String page, String limit, String sort, Callback<BloodPressure> callback) {
        if (sort == "") {
            //数据默认降序
            sort = Config.DESC_DATA;
        }
        Map<String, String> header = new HashMap<>();
        String transId = Config.getTransId(Config.BP_TRANSID_SUFFIX);
        header.put("transid", transId);
        header.put("token", token);

        Map<String, String> bpQueryParams = new HashMap<>();
        bpQueryParams.put("start_time", startTime);
        bpQueryParams.put("end_time", endTime);
        bpQueryParams.put("page", page);
        bpQueryParams.put("limit", limit);
        bpQueryParams.put("sort", sort);

        mBPService.getBPData(header, bpQueryParams).enqueue(callback);
    }

    /**
     * 获取最近一次血压数据
     *
     * @param token
     * @param callback
     */
    public void requestRecentBPData(String token, String imei, Callback<SingleBp> callback) {
        Map<String, String> header = new HashMap<>();
        String transId = Config.getTransId(Config.RECENTLY_BP_TRANSID_SUFFIX);
        header.put("transid", transId);
        header.put("token", token);

        Map<String, String> hrQueryParams = new HashMap<>();
        hrQueryParams.put("imei_sn", imei);
        mBPService.getRecentBPData(header, hrQueryParams).enqueue(callback);
    }
}
