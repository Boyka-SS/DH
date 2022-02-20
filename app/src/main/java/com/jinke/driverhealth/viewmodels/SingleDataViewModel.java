package com.jinke.driverhealth.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jinke.driverhealth.data.network.beans.SingleBp;
import com.jinke.driverhealth.data.network.beans.SingleHr;
import com.jinke.driverhealth.data.network.beans.SingleTemp;
import com.jinke.driverhealth.data.repository.BPRepository;
import com.jinke.driverhealth.data.repository.HRRepository;
import com.jinke.driverhealth.data.repository.TempRepository;

/**
 * @author: fanlihao
 * @date: 2022/2/17
 */
public class SingleDataViewModel extends ViewModel {
    private MutableLiveData<SingleBp> mSingleBloodPressureMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<SingleHr> mSingleHeartRateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<SingleTemp> mSingleTemperatureMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<SingleBp> getSingleBloodPressureMutableLiveData() {
        return mSingleBloodPressureMutableLiveData;
    }

    public void setSingleBloodPressureMutableLiveData(MutableLiveData<SingleBp> singleBloodPressureMutableLiveData) {
        mSingleBloodPressureMutableLiveData = singleBloodPressureMutableLiveData;
    }

    public MutableLiveData<SingleHr> getSingleHeartRateMutableLiveData() {
        return mSingleHeartRateMutableLiveData;
    }

    public void setSingleHeartRateMutableLiveData(MutableLiveData<SingleHr> singleHeartRateMutableLiveData) {
        mSingleHeartRateMutableLiveData = singleHeartRateMutableLiveData;
    }

    public MutableLiveData<SingleTemp> getSingleTemperatureMutableLiveData() {
        return mSingleTemperatureMutableLiveData;
    }

    public void setSingleTemperatureMutableLiveData(MutableLiveData<SingleTemp> singleTemperatureMutableLiveData) {
        mSingleTemperatureMutableLiveData = singleTemperatureMutableLiveData;
    }

    //====================================================兔盯云获取数据===========================================================
    public MutableLiveData<SingleBp> loadSingleBPData(String token, String imei) {
        return BPRepository.fetchRecentBpData(token, imei);
    }

    public MutableLiveData<SingleHr> loadSingleHRData(String token, String imei) {
        return HRRepository.fetchRecentHrData(token, imei);
    }

    public MutableLiveData<SingleTemp> loadSingleTempData(String token, String imei) {
        return TempRepository.fetchRecentTempData(token, imei);
    }
}
