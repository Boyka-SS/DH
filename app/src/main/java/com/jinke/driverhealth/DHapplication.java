package com.jinke.driverhealth;

import android.app.Application;
import android.content.SharedPreferences;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.jinke.driverhealth.data.network.TokenNetwork;
import com.jinke.driverhealth.data.network.beans.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: fanlihao
 * @date: 2022/1/19
 */
public class DHapplication extends Application {

    private static final String TAG = "DHapplication";


    private static AppDatabase mAppDatabase;

    private static DHapplication mInstance;



    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = AppDatabase.getInstance(getApplicationContext());
        fetchToken();



    }


    public static DHapplication getInstance() {
        return mInstance;
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
     * 获取兔盯云平台健康数据的token
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
    }
}
