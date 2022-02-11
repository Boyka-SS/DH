package com.jinke.driverhealth.data.network;

import com.jinke.driverhealth.beans.Temperature;
import com.jinke.driverhealth.data.network.api.TempService;
import com.jinke.driverhealth.utils.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/2/8
 */
public class TempNetwork {
    private ServiceCreator serviceCreator = new ServiceCreator();

    private TempService mTempService = serviceCreator.create(TempService.class);

    /**
     * 获取心率数据
     *
     * @param startTime 开始时间 字符串，格式为 2006-01-01 00:00:00
     * @param endTime   结束时间 字符串，格式为 2006-01-01 00:00:00
     * @param page      页码 整数，范围为 1-5000
     * @param limit     每页条数 整数，范围为 1-100
     * @param callback
     */
    public void requestTempData(String token, String startTime, String endTime, String page, String limit, String sort, Callback<Temperature> callback) {

        if (sort == "") {
            //数据默认降序
            sort = Config.DESC_DATA;
        }
        Map<String, String> header = new HashMap<>();
        String transId = Config.getTransId(Config.TEMP_TRANSID_SUFFIX);
        header.put("token", token);
        header.put("transid", transId);

        Map<String, String> tempQueryParams = new HashMap<>();
        tempQueryParams.put("start_time", startTime);
        tempQueryParams.put("end_time", endTime);
        tempQueryParams.put("page", page);
        tempQueryParams.put("limit", limit);
        tempQueryParams.put("sort", sort);


        mTempService.getTempData(header, tempQueryParams).enqueue(callback);
    }
}
