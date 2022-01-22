package com.jinke.driverhealth.services;

import com.jinke.driverhealth.interfaces.OkGoCallback;
import com.jinke.driverhealth.services.publicparams.TransIdParams;
import com.jinke.driverhealth.utils.Constant;
import com.jinke.driverhealth.utils.HttpUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
public class TemperatureSevice {
    private static final String TAG = "TemperatureSevice";


    public static void requestTemperatureDataFromTuDY(String endTime,final OkGoCallback callback) throws IOException {


        String url = "https://openapi.xu5g.com/tsp/temperatures/get" +
                "?start_time=2021-10-01 00:00:00" +
                "&end_time=" + endTime +
                "&page=1" +
                "&limit=" + Constant.limit;

        String transIdParams = new TransIdParams().getTransIdParams("Rglw1PyYCbow");

        HttpUtil.getByOkGo(url, transIdParams, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        callback.onSuccess(response.body());
                    } catch (IOException e) {


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
