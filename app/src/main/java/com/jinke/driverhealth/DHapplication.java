package com.jinke.driverhealth;

import android.app.Application;
import android.content.SharedPreferences;

import com.jinke.driverhealth.data.network.baidu.beans.BehaviorMonitorToken;
import com.jinke.driverhealth.data.network.baidu.worker.BMTokenNetwork;
import com.jinke.driverhealth.data.network.tudingyun.worker.TokenNetwork;
import com.jinke.driverhealth.data.network.tudingyun.beans.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: fanlihao
 * @date: 2022/1/19
 */
public class DHapplication extends Application {

    private static final String TAG = "DHapplication";


    public static AppDatabase mAppDatabase;




    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = AppDatabase.getInstance(getApplicationContext());
        fetchToken();
    }




    /**
     * 获取 room database
     *
     * @return
     */
    public static AppDatabase getAppDatabase() {
        return mAppDatabase;
    }

    /**
     * 获取兔盯云平台健康数据的token+百度AI的行为驾驶检测token
     */
    private void fetchToken() {

        new TokenNetwork().requestToken(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token token = response.body();
                    SharedPreferences.Editor data = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE).edit();
                    data.putString("token", token.getData().getToken());
                    data.putString("expireTime", token.getData().getExpired());
                    data.apply();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                //Tip: request token fail
            }
        });


        new BMTokenNetwork().requestBehaviorMonitorToken(new Callback<BehaviorMonitorToken>() {
            @Override
            public void onResponse(Call<BehaviorMonitorToken> call, Response<BehaviorMonitorToken> response) {
                if (response.isSuccessful()) {
                    BehaviorMonitorToken body = response.body();
                    SharedPreferences.Editor data = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE).edit();
                    data.putString("bmtoken", body.getAccess_token());
                    data.apply();
                }
            }

            @Override
            public void onFailure(Call<BehaviorMonitorToken> call, Throwable t) {

            }
        });
    }
}
