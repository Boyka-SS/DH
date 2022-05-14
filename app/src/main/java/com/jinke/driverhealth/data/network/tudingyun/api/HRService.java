package com.jinke.driverhealth.data.network.tudingyun.api;

import com.jinke.driverhealth.data.network.tudingyun.beans.HeartRate;
import com.jinke.driverhealth.data.network.tudingyun.beans.SingleHr;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

/**
 * @author: fanlihao
 * @date: 2022/2/8
 */
public interface HRService {

    @GET("tsp/hearts/get")
    Call<HeartRate> getHRData(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> queryParams);

    @GET("tsp/heart/get")
    Call<SingleHr> getRecentHRData(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> queryParams);
}
