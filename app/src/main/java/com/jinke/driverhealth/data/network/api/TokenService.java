package com.jinke.driverhealth.data.network.api;

import com.jinke.driverhealth.data.network.baidu.beans.BehaviorMonitorToken;
import com.jinke.driverhealth.data.network.tudingyun.beans.Token;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 *  fanlihao
 * 2022/2/6
 */
public interface TokenService {
    @GET("tsp/auth/token")
    Call<Token> getToken(@QueryMap Map<String, String> queryParams);

    @GET("oauth/2.0/token")
    Call<BehaviorMonitorToken> getBehaviorMonitorToken(@QueryMap Map<String, String> queryParams);

}
