package com.jinke.driverhealth.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jinke.driverhealth.data.network.tudingyun.beans.BloodPressure;
import com.jinke.driverhealth.data.network.tudingyun.beans.SingleBp;
import com.jinke.driverhealth.data.network.tudingyun.BPNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: fanlihao
 * @date: 2022/2/8
 */
public class BPRepository {
    private static final String TAG = "BPRepository";


    /**
     * 获取血压数据
     * @param token
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @param sort
     * @return
     */
    public static MutableLiveData<BloodPressure> fetchBPData(String token, String startTime, String endTime, String page, String limit, String sort) {

        MutableLiveData<BloodPressure> liveData = new MutableLiveData<>();

        new BPNetwork().requestBPData(token, startTime, endTime, page, limit, sort, new Callback<BloodPressure>() {
            @Override
            public void onResponse(Call<BloodPressure> call, Response<BloodPressure> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "bp data --> " + response.body().getData().getResult().get(0).getBlood_rate());
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

    /**
     * 获取最近一次血压数据
     */
    public static MutableLiveData<SingleBp> fetchRecentBpData(String token, String imei) {
        MutableLiveData<SingleBp> liveData = new MutableLiveData<>();

        new BPNetwork().requestRecentBPData(token, imei, new Callback<SingleBp>() {
            @Override
            public void onResponse(Call<SingleBp> call, Response<SingleBp> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SingleBp> call, Throwable t) {
                //TODO request recently bp data fail
                Log.e(TAG, "request recently bp data fail");
            }
        });
        return liveData;
    }
}
