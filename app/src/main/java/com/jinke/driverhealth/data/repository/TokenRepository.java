package com.jinke.driverhealth.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jinke.driverhealth.beans.Token;
import com.jinke.driverhealth.data.db.dao.TokenDao;
import com.jinke.driverhealth.data.network.TokenNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: fanlihao
 * @date: 2022/2/6
 */
public class TokenRepository {

    private TokenDao mTokenDao;
    private TokenNetwork mTokenNetwork;


    public TokenRepository(TokenDao tokenDao, TokenNetwork tokenNetwork) {
        mTokenDao = tokenDao;
        mTokenNetwork = tokenNetwork;
    }

    public LiveData<Token> fetchToken() {

        MutableLiveData<Token> liveData = new MutableLiveData<>();

        mTokenNetwork.requestToken(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    //mTokenDao.insertToken(response.body());
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                //TODO: request token fail
            }
        });
        return liveData;
    }
}
