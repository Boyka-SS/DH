package com.jinke.driverhealth.services;

import com.jinke.driverhealth.interfaces.OkGoCallback;
import com.jinke.driverhealth.utils.HttpUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.IOException;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
public class BloodPressureService {

    private static final String TAG = "BloodPressureService";
    public static void requestBloodPressureDataFromTuDY(final OkGoCallback callback) throws IOException {

        String url = "https://openapi.xu5g.com/tsp/bloods/get?start_time=2021-10-01 00:00:00&end_time=2022-01-21 00:00:00&page=1&limit=4";

        HttpUtil.getByOkGo(url, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                }
            }
        });

    }
}
