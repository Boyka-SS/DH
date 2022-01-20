package com.jinke.driverhealth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.adapters.DataAdapter;
import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.beans.Temperature;
import com.jinke.driverhealth.interfaces.OkGoCallback;
import com.jinke.driverhealth.services.BloodPressureService;
import com.jinke.driverhealth.services.HeartService;
import com.jinke.driverhealth.services.TemperatureSevice;
import com.jinke.driverhealth.utils.JsonUtil;

import java.io.IOException;
import java.util.List;

public class DataFragment extends Fragment {
    private static final String TAG = "DataFragment";
    private RecyclerView mRecyclerView;
    private DataAdapter mHeartRateDataAdapter;


    public static DataFragment newInstance() {
        return new DataFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_fragment, container, false);
        //RecyclerView 使用
        //1、找到控件
        mRecyclerView = view.findViewById(R.id.recycler_data_view);
        //2、设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //3、设置适配器
        mHeartRateDataAdapter = new DataAdapter();
        mRecyclerView.setAdapter(mHeartRateDataAdapter);

        //获取 心率 体温 血压 数据
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void initData() throws IOException {
        //心率
        HeartService.requestHeartRateDataFromTuDY(new OkGoCallback() {
            @Override
            public void onSuccess(String reponseData) {
                if (reponseData != "") {
                    HeartRate heartRate = JsonUtil.parseJson(reponseData, HeartRate.class);
                    if (heartRate.getStatus() == 0) {
                        upHeartRateData(heartRate.getData().getResult());
                    }
                }
            }
        });

        //体温
        TemperatureSevice.requestTemperatureDataFromTuDY(new OkGoCallback() {
            @Override
            public void onSuccess(String reponseData) {
                if (reponseData != "") {
                    Temperature temperature = JsonUtil.parseJson(reponseData, Temperature.class);
                    if (temperature.getStatus() == 0) {
                        upTemperatureData(temperature.getData().getResult());
                    }
                }
            }
        });

        //血压
        BloodPressureService.requestBloodPressureDataFromTuDY(new OkGoCallback() {
            @Override
            public void onSuccess(String reponseData) {
                if (reponseData != "") {
                    BloodPressure bloodPressure = JsonUtil.parseJson(reponseData, BloodPressure.class);
                    if (bloodPressure.getStatus() == 0) {
                        upBloodPressureData(bloodPressure.getData().getResult());
                    }
                }
            }
        });
    }

    private void upHeartRateData(List<HeartRate.DataDTO.ResultDTO> result) {

    }

    private void upTemperatureData(List<Temperature.DataDTO.ResultDTO> result) {
    }

    private void upBloodPressureData(List<BloodPressure.DataDTO.ResultDTO> result) {

    }



    /*private void upDataUI(Object obj) {

        if (obj instanceof HeartRate) {
            mHeartRateDataAdapter.setHeartRate((HeartRate) obj);
        } else if (obj instanceof Temperature) {
            mHeartRateDataAdapter.setTemperature((Temperature) obj);
        } else if (obj instanceof BloodPressure) {
            mHeartRateDataAdapter.setBloodPressure((BloodPressure) obj);
        }

    }*/

}