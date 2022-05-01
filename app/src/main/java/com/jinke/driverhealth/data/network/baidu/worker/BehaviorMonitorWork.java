package com.jinke.driverhealth.data.network.baidu.worker;

import com.jinke.driverhealth.data.network.ServiceCreator;
import com.jinke.driverhealth.data.network.baidu.api.BehaviorMonitorService;
import com.jinke.driverhealth.data.network.baidu.beans.BehaviorMonitorData;
import com.jinke.driverhealth.utils.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/5/1
 */
public class BehaviorMonitorWork {
    private ServiceCreator serviceCreator = new ServiceCreator(Config.BAIDU_DRIVER_BEHAVIOR_URL);

    private BehaviorMonitorService mBehaviorMonitorService = serviceCreator.create(BehaviorMonitorService.class);

    public BehaviorMonitorWork() {

    }


    public void requestBehaviorMonitorData(String token,String imageUrl,Callback<BehaviorMonitorData> callback) {
        Map<String, String> tokenQueryParams = new HashMap<>();
        tokenQueryParams.put("access_token",token);
        mBehaviorMonitorService.getBMData(tokenQueryParams,imageUrl).enqueue(callback);
    }
}
