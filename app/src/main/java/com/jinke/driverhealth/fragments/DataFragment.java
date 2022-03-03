package com.jinke.driverhealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.center.CenterActivity;
import com.jinke.driverhealth.activity.consult.HealthConsultActivity;
import com.jinke.driverhealth.adapters.DataAdapter;
import com.jinke.driverhealth.data.network.beans.BloodPressure;
import com.jinke.driverhealth.data.network.beans.HeartRate;
import com.jinke.driverhealth.data.network.beans.Temperature;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.viewmodels.DataViewModel;

import static android.content.Context.MODE_PRIVATE;

public class DataFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DataFragment";


    //view
    private LinearLayout mCenterMeasurement, mHealthConsult, mHealthReport, mStepNumber;

    private DataViewModel mDataViewModel;
    private DataAdapter mDataAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setElevation(0);
        getData("", "2022-03-02 00:00:00", "1", "20");
        View view = inflater.inflate(R.layout.data_fragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mCenterMeasurement = view.findViewById(R.id.center_measurement);
        mCenterMeasurement.setOnClickListener(this);
        mHealthConsult = view.findViewById(R.id.health_consult);
        mHealthConsult.setOnClickListener(this);
        mHealthReport = view.findViewById(R.id.health_report);
        mHealthReport.setOnClickListener(this);
        mHealthReport.setOnClickListener(this);
        mStepNumber = view.findViewById(R.id.step_number);
        mStepNumber.setOnClickListener(this);
    }


    /**
     * @param startTime 开始时间 2021-10-01 00:00:00
     * @param endTime   结束时间 2021-10-01 00:00:00
     * @param page      页码
     * @param limit     每页条数
     */
    private void getData(String startTime, String endTime, String page, String limit) {
        if (startTime == "") {
            startTime = Config.START_TIME;
        }

        String token = getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("token", "");
        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        //hr
        mDataViewModel.loadHRData(token, startTime, endTime, page, limit, "").observe(getViewLifecycleOwner(), new Observer<HeartRate>() {
            @Override
            public void onChanged(HeartRate heartRate) {
                if (heartRate.getStatus() == 200) {
                    mDataAdapter.setHeartRateResult(heartRate.getData().getResult());
                }
            }
        });

        //bp
        mDataViewModel.loadBPData(token, startTime, endTime, page, limit, "").observe(getViewLifecycleOwner(), new Observer<BloodPressure>() {
            @Override
            public void onChanged(BloodPressure bloodPressure) {
                if (bloodPressure.getStatus() == 200) {
                    mDataAdapter.setBloodPressureResult(bloodPressure.getData().getResult());
                }
            }
        });

        //temp
        mDataViewModel.loadTempData(token, startTime, endTime, page, limit, "").observe(getViewLifecycleOwner(), new Observer<Temperature>() {
            @Override
            public void onChanged(Temperature temperature) {
                if (temperature.getStatus() == 200) {
                    mDataAdapter.setTemperatureResult(temperature.getData().getResult());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.center_measurement:
                startActivity(new Intent(getActivity(), CenterActivity.class));
                break;
            case R.id.health_consult:
                startActivity(new Intent(getActivity(), HealthConsultActivity.class));
                break;
            case R.id.health_report:
                break;
            case R.id.step_number:
                break;
            default:
                break;
        }
    }
}
//解决 RecyclerView 存在的 数组越界 BUG 参考链接：
// 1、https://www.jianshu.com/p/a15764f6d673
//2、https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in