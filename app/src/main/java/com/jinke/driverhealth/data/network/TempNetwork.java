package com.jinke.driverhealth.data.network;

import com.jinke.driverhealth.beans.SingleTemp;
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
     * 获取体温数据，参数见文档
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

    /**
     * 获取最近一次体温数据
     */
    public void requestRecentTempData(String token, String imei, Callback<SingleTemp> callback) {
        Map<String, String> header = new HashMap<>();
        String transId = Config.getTransId(Config.RECENTLY_TEMP_TRANSID_SUFFIX);
        header.put("transid", transId);
        header.put("token", token);

        Map<String, String> hrQueryParams = new HashMap<>();
        hrQueryParams.put("imei_sn", imei);
        mTempService.getRecentTempData(header, hrQueryParams).enqueue(callback);
    }

}
