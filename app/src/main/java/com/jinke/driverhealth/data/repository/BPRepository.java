package com.jinke.driverhealth.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.data.network.BPNetWork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: fanlihao
 * @date: 2022/2/8
 */
public class BPRepository {
    private static final String TAG = "BPRepository";



    public static MutableLiveData<BloodPressure> fetchBPData(String startTime, String endTime, String page, String limit) {

        MutableLiveData<BloodPressure> liveData = new MutableLiveData<>();

        new BPNetWork().requestBPData(startTime, endTime, page, limit, new Callback<BloodPressure>() {
            @Override
            public void onResponse(Call<BloodPressure> call, Response<BloodPressure> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG,"bp data --> "+response.body().getData().getResult().get(0).getBlood_rate());
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BloodPressure> call, Throwable t) {
                //TODO request bp data fail
                Log.e(TAG, "request bp data fail");
            }
        });

        return liveData;
    }
}
