package com.jinke.driverhealth.data.network.tudingyun.api;

import com.jinke.driverhealth.data.network.tudingyun.beans.SingleTemp;
import com.jinke.driverhealth.data.network.tudingyun.beans.Temperature;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

/**
 * @author: fanlihao
 * @date: 2022/2/8
 */
public interface TempService {
    @GET("tsp/temperatures/get")
    Call<Temperature> getTempData(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> queryParams);

    @GET("tsp/temperature/get")
    Call<SingleTemp> getRecentTempData(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> queryParams);
}
