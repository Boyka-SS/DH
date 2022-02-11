package com.jinke.driverhealth.data.network;

import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.data.network.api.BPService;
import com.jinke.driverhealth.utils.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/2/7
 */
public class BPNetWork {


    private ServiceCreator serviceCreator = new ServiceCreator();

    private BPService mBPService = serviceCreator.create(BPService.class);

    /**
     *
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
}
