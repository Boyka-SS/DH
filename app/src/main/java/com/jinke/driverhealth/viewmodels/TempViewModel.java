package com.jinke.driverhealth.viewmodels;

import android.net.ParseException;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jinke.driverhealth.beans.Temperature;
import com.jinke.driverhealth.interfaces.OkGoCallback;
import com.jinke.driverhealth.services.TemperatureSevice;
import com.jinke.driverhealth.utils.JsonUtil;

import java.io.IOException;
import java.util.List;

/**
 * 体温详情页面 —— viewmodel
 *
 * @author: fanlihao
 * @date: 2022/1/29
 */

public class TempViewModel extends ViewModel {

    private static final String TAG = "TempViewModel";
    private MutableLiveData<List<Temperature.DataDTO.ResultDTO>> tempResult = new MutableLiveData<>();
    private String endTime;

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public MutableLiveData<List<Temperature.DataDTO.ResultDTO>> getTempResult() throws IOException {
        loadTempData(endTime);
        return tempResult;
    }


    private void loadTempData(String endTime) throws IOException {
        TemperatureSevice.requestTemperatureDataFromTuDY("", endTime, new OkGoCallback() {
            @Override
            public void onSuccess(String reponseData) throws IOException, ParseException {
                if (reponseData != "") {
                    Temperature temperature = JsonUtil.parseJson(reponseData, Temperature.class);
                    if (temperature.getStatus() == 0) {
                        tempResult.postValue(temperature.getData().getResult());
                    }

                }
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
