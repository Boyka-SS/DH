package com.jinke.driverhealth.data.network.juhe.api;

import com.jinke.driverhealth.data.network.juhe.beans.RiskRegion;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author: fanlihao
 * @date: 2022/5/2
 */
public interface RiskCityService {
    @GET("springTravel/risk")
    Call<RiskRegion> getAllRiskRegions(@QueryMap Map<String, String> queryParams);
}
