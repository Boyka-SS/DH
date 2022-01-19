package com.jinke.driverhealth.services.publicparams;

import android.util.Log;

import com.jinke.driverhealth.interfaces.OkGoCallback;
import com.jinke.driverhealth.utils.Constant;
import com.jinke.driverhealth.utils.EncryptUtil;
import com.jinke.driverhealth.utils.HttpUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.IOException;

/**
 * @author: fanlihao
 * @date: 2022/1/16
 */

public class TokenParams {


    private static final String TAG = "TokenParams";


    public static void requestToken(final OkGoCallback okGoCallback) throws IOException {
        //事务序号
//        final String[] transId = {new TransIdParams().getTransIdParams()};
        String transId = new TransIdParams().getTransIdParams();
        //签名算法
        String sign = EncryptUtil.sha1("" + Constant.SECRET + "" + Constant.APPKEY + "" + transId + "" + Constant.SECRET);

        String queryParams = "?appkey=10014&transid=" + transId + "&sign=" + sign;

        HttpUtil.getByOkGo(queryParams, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.isSuccessful()) {
                    okGoCallback.onSuccess(response.body());
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e(TAG, "TokenParams");
            }
        });

    }
}
