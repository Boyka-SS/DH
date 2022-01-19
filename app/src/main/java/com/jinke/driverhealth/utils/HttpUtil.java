package com.jinke.driverhealth.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;

import okhttp3.Response;

/**
 * @author: fanlihao
 * @date: 2022/1/17
 */
public class HttpUtil {


    private static final String TAG = "HttpUtil";
    private static final String BASE_URL = "https://openapi.xu5g.com/tsp/auth/token";

    /**
     * 异步GET请求，仅执行网络请求，对于请求结果的操作的callback里面
     *
     * @param
     * @param address
     * @param callback
     */
    public static void getByOkGo(String address, StringCallback callback) {
        OkGo.<String>get(BASE_URL + address).execute(callback);
    }

    /**
     * 同步 get 请求
     *
     * @param address
     */
    public static String getByOkGoSync(String address) throws IOException {
        Response response = OkGo.<String>get(BASE_URL + address).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return "";
    }

}