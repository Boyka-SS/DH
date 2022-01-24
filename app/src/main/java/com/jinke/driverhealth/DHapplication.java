package com.jinke.driverhealth;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.jinke.driverhealth.beans.Token;
import com.jinke.driverhealth.interfaces.OkGoCallback;
import com.jinke.driverhealth.services.publicparams.TokenParams;
import com.jinke.driverhealth.utils.JsonUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * @author: fanlihao
 * @date: 2022/1/19
 */
public class DHapplication extends Application {

    private static final String TAG = "DHapplication";
    private AppDatabase mAppDatabase;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "android_room_dev.db")
                .allowMainThreadQueries()
                .build();
        try {
            initPublicParams();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取 room database
     * @return
     */
    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }

    /**
     * 公共参数 token
     */
    private void initPublicParams() throws IOException {
        //获取token,存入本地,初始化 OKGO
        storageOrUpgradeToken(new FetchTokenCallback() {
            @Override
            public void onFetchTokenSuccess(String token, String expire) throws IOException, ParseException {
                initOkGo(token);
            }
        });
    }

    ;

    /**
     * 初始化OkGo
     */
    private void initOkGo(String token) throws ParseException, IOException {
        //公共头
        HttpHeaders headers = new HttpHeaders();
        headers.put("token", "" + token);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);

        try {
            OkGo.getInstance().init(this).addCommonHeaders(headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param callback
     * @throws IOException
     */
    private void storageOrUpgradeToken(FetchTokenCallback callback) throws IOException {

        TokenParams.requestToken(new OkGoCallback() {
            @Override
            public void onSuccess(String reponseData) throws IOException, ParseException {
                if (reponseData != "") {
                    Token token = JsonUtil.parseJson(reponseData, Token.class);
                    callback.onFetchTokenSuccess(token.getData().getToken(), token.getData().getExpired());
                }
            }
        });

    }

    public interface FetchTokenCallback {
        void onFetchTokenSuccess(String token, String expire) throws IOException, ParseException;
    }
}
