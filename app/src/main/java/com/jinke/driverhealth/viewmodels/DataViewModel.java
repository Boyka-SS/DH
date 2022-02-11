package com.jinke.driverhealth.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.beans.Temperature;
import com.jinke.driverhealth.data.repository.BPRepository;
import com.jinke.driverhealth.data.repository.HRRepository;
import com.jinke.driverhealth.data.repository.TempRepository;

/**
 * @author: fanlihao
 * @date: 2022/2/8
 */
public class DataViewModel extends ViewModel {

    private MutableLiveData<BloodPressure> mBloodPressureMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<HeartRate> mHeartRateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Temperature> mTemperatureMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<HeartRate> getHeartRateMutableLiveData() {
        if (mHeartRateMutableLiveData == null) {
            mHeartRateMutableLiveData = new MutableLiveData<>();
        }
        return mHeartRateMutableLiveData;
    }

    public void setHeartRateMutableLiveData(MutableLiveData<HeartRate> heartRateMutableLiveData) {
        mHeartRateMutableLiveData = heartRateMutableLiveData;
    }

    public MutableLiveData<Temperature> getTemperatureMutableLiveData() {
        if (mTemperatureMutableLiveData == null) {
            mTemperatureMutableLiveData = new MutableLiveData<>();
        }
        return mTemperatureMutableLiveData;
    }

    public void setTemperatureMutableLiveData(MutableLiveData<Temperature> temperatureMutableLiveData) {
        mTemperatureMutableLiveData = temperatureMutableLiveData;
    }

    public MutableLiveData<BloodPressure> getBloodPressureMutableLiveData() {
        if (mBloodPressureMutableLiveData == null) {
            mBloodPressureMutableLiveData = new MutableLiveData<>();
        }
        return mBloodPressureMutableLiveData;
    }

    public void setBloodPressureMutableLiveData(MutableLiveData<BloodPressure> bloodPressureMutableLiveData) {
        mBloodPressureMutableLiveData = bloodPressureMutableLiveData;
    }

    //====================================================兔盯云获取数据===========================================================

    public MutableLiveData<BloodPressure> loadBPData(String token, String startTime, String endTime, String page, String limit, String sort) {
        return BPRepository.fetchBPData(token, startTime, endTime, page, limit, sort);
    }

    public MutableLiveData<HeartRate> loadHRData(String token, String startTime, String endTime, String page, String limit, String sort) {
        return HRRepository.fetchHRData(token, startTime, endTime, page, limit, sort);
    }

    public MutableLiveData<Temperature> loadTempData(String token, String startTime, String endTime, String page, String limit,String sort) {
        return TempRepository.fetchTempData(token, startTime, endTime, page, limit,sort);
    }
}
