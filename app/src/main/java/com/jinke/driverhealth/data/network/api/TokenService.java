package com.jinke.driverhealth.data.network.api;

import com.jinke.driverhealth.beans.Token;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author: fanlihao
 * @date: 2022/1/26
 */
public interface TokenService {
        @GET("tsp/auth/token/{querryParams}")
        Call<Token> getToken(@Path("querryParams") String params);
}
