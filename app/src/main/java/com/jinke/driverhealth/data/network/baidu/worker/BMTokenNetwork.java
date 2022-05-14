package com.jinke.driverhealth.data.network.baidu.worker;

import com.jinke.driverhealth.data.network.ServiceCreator;
import com.jinke.driverhealth.data.network.api.TokenService;
import com.jinke.driverhealth.data.network.baidu.beans.BehaviorMonitorToken;
import com.jinke.driverhealth.utils.Config;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/4/30
 */
public class BMTokenNetwork {
    private ServiceCreator serviceCreator = new ServiceCreator(Config.BAIDU_TOKEN_URL);

    private TokenService mTokenService = serviceCreator.create(TokenService.class);

    public BMTokenNetwork() {

    }


    public void requestBehaviorMonitorToken(Callback<BehaviorMonitorToken> callback) {
        Map<String, String> tokenQueryParams = new HashMap<>();

        tokenQueryParams.put("grant_type", "client_credentials");
        tokenQueryParams.put("client_id",  "YG3vcfAkLn3RXEccAmdpOMhn");
        tokenQueryParams.put("client_secret", "wBqughFFz5X5BYIZKHdHjeVsqK3ws7m3");
        mTokenService.getBehaviorMonitorToken(tokenQueryParams).enqueue(callback);
    }
}
