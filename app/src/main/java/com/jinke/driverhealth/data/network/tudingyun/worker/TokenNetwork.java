package com.jinke.driverhealth.data.network.tudingyun.worker;

import com.jinke.driverhealth.data.network.ServiceCreator;
import com.jinke.driverhealth.data.network.tudingyun.beans.Token;
import com.jinke.driverhealth.data.network.api.TokenService;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.utils.EncryptUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * @author: fanlihao
 * @date: 2022/2/6
 */
public class TokenNetwork {


    private ServiceCreator serviceCreator = new ServiceCreator(Config.BASE_URL);

    private TokenService mTokenService = serviceCreator.create(TokenService.class);

    public TokenNetwork() {

    }


    public void requestToken(Callback<Token> callback) {
        Map<String, String> tokenQueryParams = new HashMap<>();
        String transId = Config.getTransId(Config.TOKEN_TRANSID_SUFFIX);
        String sign = EncryptUtil.sha1("" + Config.SECRET + "" + Config.APPKEY + "" + transId + "" + Config.SECRET);

        tokenQueryParams.put("appkey", Config.APPKEY);
        tokenQueryParams.put("transid", transId);
        tokenQueryParams.put("sign", sign);

        mTokenService.getToken(tokenQueryParams).enqueue(callback);
    }

}
