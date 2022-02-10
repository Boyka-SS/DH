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
     * 获取血压数据
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param page      页码 整数，范围为 1-5000
     * @param limit     每页条数 整数，范围为 1-200
     * @param callback
     */

    public void requestBPData(String token,String startTime, String endTime, String page, String limit, Callback<BloodPressure> callback) {

        Map<String, String> header = new HashMap<>();
        String transId = Config.getTransId(Config.BP_TRANSID_SUFFIX);
        header.put("transid", transId);
        header.put("token", token);

        Map<String, String> bpQueryParams = new HashMap<>();
        bpQueryParams.put("start_time", startTime);
        bpQueryParams.put("end_time", endTime);
        bpQueryParams.put("page", page);
        bpQueryParams.put("limit", limit);

        mBPService.getBPData(header, bpQueryParams).enqueue(callback);
    }
}
