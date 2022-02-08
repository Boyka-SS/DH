package com.jinke.driverhealth;

import android.app.Application;
import android.content.SharedPreferences;

import com.jinke.driverhealth.beans.Token;
import com.jinke.driverhealth.data.network.TokenNetwork;
import com.jinke.driverhealth.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: fanlihao
 * @date: 2022/1/19
 */
public class DHapplication extends Application {

    private static final String TAG = "DHapplication";
    private AppDatabase mAppDatabase;


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
    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }


    private void fetchToken() {

        new TokenNetwork().requestToken(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token token = response.body();
                    Config.TOKEN = token.getData().getToken();
                    SharedPreferences.Editor data = getApplicationContext().getSharedPreferences("data", MODE_PRIVATE).edit();
                    data.putString("token", token.getData().getToken());
                    data.putString("expireTime", token.getData().getExpired());
                    data.apply();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                //TODO: request token fail
            }
        });
    }
}
