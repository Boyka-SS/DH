package com.jinke.driverhealth;

import android.app.Application;
import android.content.SharedPreferences;

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
    private String mExpire = "";

    private StorageCallback mStorageCallback;

    @Override
    public void onCreate() {
        super.onCreate();


        try {
            initPublicParams();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 公共参数 token
     */
    private void initPublicParams() throws IOException {
        //获取token存入本地
        storageOrUpgradeToken(new StorageCallback() {
            @Override
            public void onStorageSuccess(String token, String expire) throws IOException, ParseException {
                initOkGo(token, expire);
            }
        });
    }

    ;

    /**
     * 初始化OkGo
     */
    private void initOkGo(String token, String expire) throws ParseException, IOException {

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

//        String effectiveToken = getEffectiveToken(expire, sharedPreferences);

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
     * @param expireTime
     * @param sharedPreferences
     * @return
     * @throws ParseException
     * @throws IOException    java 比较日期大小： www.cnblogs.com/MrYangSX/p/11684482.html
     */
    private String getEffectiveToken(String expireTime, SharedPreferences sharedPreferences) throws ParseException, IOException {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date currentTime = new Date();

        Date endTime = format.parse(expireTime);

        //有效
        if (currentTime.before(endTime)) {
            return sharedPreferences.getString("token","");
        } else {
            //无效，更新本地token+expire
           // storageOrUpgradeToken();
//            token = sharedPreferences.getString("token", "");
        }
        return mToken;
    }


    /**
     * token + expire 存入本地 —— SharedPreferences
     * <p>
     * 因主线程无法网络请求数据，只能异步，又无法突破onSuccess获得数据，只能存储本地（待升级)
     *
     * @throws IOException
     */
    private void storageOrUpgradeToken(StorageCallback callback) throws IOException {

        TokenParams.requestToken(new OkGoCallback() {
            @Override
            public void onSuccess(String reponseData) throws IOException, ParseException {
                if (reponseData != "") {
                    Token token = JsonUtil.parseJson(reponseData, Token.class);

                    SharedPreferences.Editor edit = getSharedPreferences("data", MODE_PRIVATE).edit();
                    //存放token
                    edit.putString("token", token.getData().getToken());
                    edit.putString("expire", token.getData().getExpired());

                    edit.apply();

                    callback.onStorageSuccess(token.getData().getToken(), token.getData().getExpired());
                }
            }
        });

    }

    public interface StorageCallback {
        void onStorageSuccess(String token, String expire) throws IOException, ParseException;
    }
}
