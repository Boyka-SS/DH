package com.jinke.driverhealth.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jinke.driverhealth.beans.Temperature;
import com.jinke.driverhealth.data.network.TempNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: fanlihao
 * @date: 2022/2/8
 */
public class TempRepository {
    private static final String TAG = "TempRepository";


    public static MutableLiveData<Temperature> fetchTempData(String startTime, String endTime, String page, String limit) {

        MutableLiveData<Temperature> liveData = new MutableLiveData<>();

        new TempNetwork().requestTempData(startTime, endTime, page, limit, new Callback<Temperature>() {
            @Override
            public void onResponse(Call<Temperature> call, Response<Temperature> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "temp data --> " + response.body().getData().getResult().get(0).getTemperature());
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Temperature> call, Throwable t) {
                //TODO request hr data fail
                Log.e(TAG, "request temperature data fail");
            }
        });

        return liveData;
    }
}
