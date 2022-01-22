package com.jinke.driverhealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.HealthDetailActivity;
import com.jinke.driverhealth.adapters.DataAdapter;
import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.beans.Temperature;
import com.jinke.driverhealth.interfaces.OkGoCallback;
import com.jinke.driverhealth.interfaces.OnItemClickListener;
import com.jinke.driverhealth.services.BloodPressureService;
import com.jinke.driverhealth.services.HeartService;
import com.jinke.driverhealth.services.TemperatureSevice;
import com.jinke.driverhealth.utils.CalendarFormatUtil;
import com.jinke.driverhealth.utils.JsonUtil;
import com.jinke.driverhealth.utils.WrapContentLinearLayoutManager;

import java.io.IOException;
import java.util.List;

public class DataFragment extends Fragment {

    private static final String TAG = "DataFragment";
    private RecyclerView mRecyclerView;
    private DataAdapter mDataAdapter;


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

        //解决 RecyclerView 存在的 数组越界 BUG
        //参考链接：1、https://www.jianshu.com/p/a15764f6d673
        //2、https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //3、设置适配器
        mDataAdapter = new DataAdapter();
        //添加点击事件
        mDataAdapter.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemLongClick(View view, int position) {
                //TODO:监听长按事件
                Toast.makeText(getActivity(), "long click " + position + " item", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onLongClick  --> item" + position);
            }

            @Override
            public void onItemClick(View itemView, int pos, BloodPressure.DataDTO.ResultDTO bpResDTO, Temperature.DataDTO.ResultDTO tempResDTO, HeartRate.DataDTO.ResultDTO hrResDTO, String mockAlcoholData) {

                Log.d(TAG, "onClick -->  item" + pos);
                Intent intent = new Intent(getActivity(), HealthDetailActivity.class);

                //用Bundle携带数据
                Bundle bundle = new Bundle();

                bundle.putString("heart_rate", "" + hrResDTO.getHeart_rate());
                bundle.putString("temperature", "" + tempResDTO.getTemperature());
                bundle.putString("blood_max_pressure", "" + bpResDTO.getMax_rate());
                bundle.putString("blood_min_pressure", "" + bpResDTO.getMin_rate());
                bundle.putString("alcohol", mockAlcoholData);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mDataAdapter);

        //4、获取 心率 体温 血压 数据
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void initData() throws IOException {

        String currentFormatTime = CalendarFormatUtil.getCurrentFormatTime();

        //心率
        HeartService.requestHeartRateDataFromTuDY(currentFormatTime, new OkGoCallback() {
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
        TemperatureSevice.requestTemperatureDataFromTuDY(currentFormatTime, new OkGoCallback() {
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
        BloodPressureService.requestBloodPressureDataFromTuDY(currentFormatTime, new OkGoCallback() {
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
        mDataAdapter.setHeartRateResult(result);

    }

    private void upTemperatureData(List<Temperature.DataDTO.ResultDTO> result) {
        mDataAdapter.setTemperatureResult(result);
    }

    private void upBloodPressureData(List<BloodPressure.DataDTO.ResultDTO> result) {
        mDataAdapter.setBloodPressureResult(result);
    }

}