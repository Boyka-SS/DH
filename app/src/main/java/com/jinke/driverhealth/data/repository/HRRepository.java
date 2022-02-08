package com.jinke.driverhealth.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.data.network.HRNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: fanlihao
 * @date: 2022/2/8
 */
public class HRRepository {

    private static final String TAG = "HRRepository";


    public static MutableLiveData<HeartRate> fetchHRData(String startTime, String endTime, String page, String limit) {

        MutableLiveData<HeartRate> liveData = new MutableLiveData<>();

        new HRNetwork().requestHRData(startTime, endTime, page, limit, new Callback<HeartRate>() {
            @Override
            public void onResponse(Call<HeartRate> call, Response<HeartRate> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "hr data --> " + response.body().getData().getResult().get(0).getHeart_rate());
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<HeartRate> call, Throwable t) {
                //TODO request hr data fail
                Log.e(TAG, "request heart rate data fail");
            }
        });

        return liveData;
    }
}
