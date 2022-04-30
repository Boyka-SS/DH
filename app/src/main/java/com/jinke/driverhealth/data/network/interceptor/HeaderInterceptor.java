package com.jinke.driverhealth.data.network.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 封装公共参数 ： token
 *
 * @author: fanlihao
 * @date: 2022/2/7
 */
public class HeaderInterceptor implements Interceptor {

    private final String token;

    public HeaderInterceptor(String token) {
        this.token = token;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("token", token);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
