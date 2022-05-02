package com.jinke.driverhealth.data.network.juhe.worker;

import com.jinke.driverhealth.data.network.ServiceCreator;
import com.jinke.driverhealth.data.network.juhe.api.RiskCityService;
import com.jinke.driverhealth.data.network.juhe.beans.RiskRegion;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/5/2
 */
public class RiskRegionNetwork {
    private ServiceCreator serviceCreator = new ServiceCreator("http://apis.juhe.cn/");
    private RiskCityService mRiskCityService = serviceCreator.create(RiskCityService.class);

    public void requestRiskRegionData(String key, Callback<RiskRegion> callback) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("key", key);
        mRiskCityService.getAllRiskRegions(queryParams).enqueue(callback);
    }
}
