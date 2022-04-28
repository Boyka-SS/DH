package com.jinke.driverhealth.data.network.tudingyun.api;

import com.jinke.driverhealth.data.network.tudingyun.beans.Token;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author: fanlihao
 * @date: 2022/2/6
 */
public interface TokenService {
    @GET("tsp/auth/token")
    Call<Token> getToken(@QueryMap Map<String, String> queryParams);



}
