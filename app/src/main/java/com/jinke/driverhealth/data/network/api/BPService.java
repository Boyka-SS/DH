package com.jinke.driverhealth.data.network.api;

import com.jinke.driverhealth.beans.BloodPressure;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

/**
 * @author: fanlihao
 * @date: 2022/2/7
 */
public interface BPService {

    @GET("tsp/bloods/get")
    Call<BloodPressure> getBPData(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> queryParams);
}
/**
 * https://www.cnblogs.com/ganchuanpu/p/8570218.html
 */