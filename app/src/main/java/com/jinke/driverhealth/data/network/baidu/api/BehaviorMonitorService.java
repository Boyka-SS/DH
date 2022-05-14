package com.jinke.driverhealth.data.network.baidu.api;

import com.jinke.driverhealth.data.network.baidu.beans.BehaviorMonitorData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author: fanlihao
 * @date: 2022/4/30
 */
public interface BehaviorMonitorService {
    @FormUrlEncoded
    @POST("rest/2.0/image-classify/v1/driver_behavior")
    Call<BehaviorMonitorData> getBMData(@QueryMap Map<String, String> queryParams, @Field("image") String imageUrl);
}

