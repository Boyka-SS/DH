package com.jinke.driverhealth.data.network;

import static android.util.Log.VERBOSE;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: fanlihao
 * @date: 2022/2/6
 */
public class ServiceCreator {

    private String url;

    public ServiceCreator(String url) {
        this.url = url;
    }

    public <T> T create(Class<T> serviceClass) {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        //添加打印拦截器
        okHttpClient
                .addInterceptor(new LoggingInterceptor.Builder()
                        .setLevel(Level.BASIC)
                        .log(VERBOSE)
                        .request("Request")
                        .response("Reponse")
                        .build());

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(serviceClass);
    }
}
/**
 * Retrofit2 打印网络请求日志参考链接:
 * https://blog.csdn.net/baiyicanggou_wujie/article/details/80365836
 * Retrofit2 添加公共参数:
 * https://www.codeleading.com/article/86562498785/
 */