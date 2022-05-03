package com.jinke.driverhealth.data.network.juhe.api;

import com.jinke.driverhealth.data.network.juhe.beans.PolicyContent;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author: fanlihao
 * @date: 2022/5/3
 */
public interface PolicyContentService {
    @GET("springTravel/query")
    Call<PolicyContent> getPolicyContent(@QueryMap Map<String, String> queryParams);
}
