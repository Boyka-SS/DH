package com.jinke.driverhealth;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.jinke.driverhealth.beans.Token;
import com.jinke.driverhealth.interfaces.OkGoCallback;
import com.jinke.driverhealth.services.publicparams.TokenParams;
import com.jinke.driverhealth.utils.JsonUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * @author: fanlihao
 * @date: 2022/1/19
 */
public class DHapplication extends Application {

    private static final String TAG = "DHapplication";

    private String mToken = "";

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            initPublicParams();
            initOkGo();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 公共参数 token + transID（每次请求都要更新，不添加成全局参数）
     */
    private void initPublicParams() throws IOException {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        if (sharedPreferences.getString("token", "") == "") {
            //准备token，不过期即可
            storageOrUpgradeToken();
            mToken = sharedPreferences.getString("token", "");
        }
    }

    /**
     * 初始化OkGo
     */
    private void initOkGo() throws ParseException, IOException {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        String expire = sharedPreferences.getString("expire", "");
        mToken = getEffectiveToken(expire, sharedPreferences);

        //公共头
        HttpHeaders headers = new HttpHeaders();
        Log.d(TAG, "" + mToken);
        headers.put("token", "" + mToken);


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
     * 检测 token  是否过期
     * 不过期 用本地 否则 重新发送请求
     * ps:java 比较日期大小： www.cnblogs.com/MrYangSX/p/11684482.html
     */
    private String getEffectiveToken(String expireTime, SharedPreferences sharedPreferences) throws ParseException, IOException {
        String token = sharedPreferences.getString("token", "");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        Date endTime = format.parse(expireTime);
        //有效
        if (currentTime.before(endTime)) {
            return token;
        } else {
            //无效，更新本地token+expire
            storageOrUpgradeToken();
            token = sharedPreferences.getString("token", "");
            return token;
        }
    }


    /**
     * token + expire 存入本地 —— SharedPreferences
     * <p>
     * 因主线程无法网络请求数据，只能异步，又无法突破onSuccess获得数据，只能存储本地（待升级)
     *
     * @throws IOException
     */
    private void storageOrUpgradeToken() throws IOException {

        TokenParams.requestToken(new OkGoCallback() {
            @Override
            public void onSuccess(String reponseData) {
                if (reponseData != "") {
                    Token token = JsonUtil.parseJson(reponseData, Token.class);
                    SharedPreferences.Editor edit = getSharedPreferences("data", MODE_PRIVATE).edit();
                    edit.putString("token", token.getData().getToken());
                    edit.putString("expire", token.getData().getExpired());
                    edit.apply();
                }
            }
        });

    }
}
