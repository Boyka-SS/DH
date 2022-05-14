package com.jinke.driverhealth.data.network.juhe.worker;

import com.jinke.driverhealth.data.network.ServiceCreator;
import com.jinke.driverhealth.data.network.juhe.api.PolicyContentService;
import com.jinke.driverhealth.data.network.juhe.beans.PolicyContent;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/5/3
 */
public class PolicyContentNetWork {
    private ServiceCreator serviceCreator = new ServiceCreator("http://apis.juhe.cn/");
    private PolicyContentService mPolicyContentService = serviceCreator.create(PolicyContentService.class);

    public void requestRiskRegionData(String key, String originCityId, String destinationCityId, Callback<PolicyContent> callback) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("key", key);
        queryParams.put("from", originCityId);
        queryParams.put("to", destinationCityId);
        mPolicyContentService.getPolicyContent(queryParams).enqueue(callback);
    }
}
