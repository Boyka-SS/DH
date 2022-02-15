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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.HealthDetailActivity;
import com.jinke.driverhealth.adapters.DataAdapter;
import com.jinke.driverhealth.beans.BloodPressure;
import com.jinke.driverhealth.beans.HeartRate;
import com.jinke.driverhealth.beans.Temperature;
import com.jinke.driverhealth.interfaces.OnItemClickListener;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.utils.WrapContentLinearLayoutManager;
import com.jinke.driverhealth.viewmodels.DataViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class DataFragment extends Fragment {

    private static final String TAG = "DataFragment";
    private RecyclerView mRecyclerView;
    private DataAdapter mDataAdapter;
    private DataViewModel mDataViewModel;

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

        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //3、设置适配器
        mDataAdapter = new DataAdapter();
        //添加点击事件，通过接口回调的方式将recyclerView item中对应的数据 拿到，传给  detail页面
        mDataAdapter.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemLongClick(View view, int position) {
                //TODO:监听长按事件 暂时用不上
                Toast.makeText(getActivity(), "long click " + position + " item", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onLongClick  --> item" + position);
            }

            //点击跳转对应健康数据详情页面
            @Override
            public void onItemClick(View itemView, int pos, BloodPressure.DataDTO.ResultDTO bpResDTO, Temperature.DataDTO.ResultDTO tempResDTO, HeartRate.DataDTO.ResultDTO hrResDTO, String mockAlcoholData, String createDate) {

                Log.d(TAG, "onClick -->  item" + pos);
                Intent intent = new Intent(getActivity(), HealthDetailActivity.class);

                //用Bundle携带数据
                Bundle bundle = new Bundle();

                bundle.putString("heart_rate", "" + hrResDTO.getHeart_rate());
                bundle.putString("temperature", "" + tempResDTO.getTemperature());
                bundle.putString("blood_max_pressure", "" + bpResDTO.getMax_rate());
                bundle.putString("blood_min_pressure", "" + bpResDTO.getMin_rate());
                bundle.putString("alcohol", mockAlcoholData);
                bundle.putString("create_date", createDate);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mDataAdapter);
        String endTime = getCurrentTime();
        //fetch data
        getData("", endTime, "1", "20");


        return view;
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(new Date());
        return currentTime;
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
        mDataViewModel.loadHRData(token, startTime, endTime, page, limit,"").observe(getViewLifecycleOwner(), new Observer<HeartRate>() {
            @Override
            public void onChanged(HeartRate heartRate) {
                mDataAdapter.setHeartRateResult(heartRate.getData().getResult());
            }
        });

        //bp
        mDataViewModel.loadBPData(token, startTime, endTime, page, limit,"").observe(getViewLifecycleOwner(), new Observer<BloodPressure>() {
            @Override
            public void onChanged(BloodPressure bloodPressure) {
                mDataAdapter.setBloodPressureResult(bloodPressure.getData().getResult());
            }
        });

        //temp
        mDataViewModel.loadTempData(token, startTime, endTime, page, limit,"").observe(getViewLifecycleOwner(), new Observer<Temperature>() {
            @Override
            public void onChanged(Temperature temperature) {
                mDataAdapter.setTemperatureResult(temperature.getData().getResult());
            }
        });
    }
}
//解决 RecyclerView 存在的 数组越界 BUG 参考链接：
// 1、https://www.jianshu.com/p/a15764f6d673
//2、https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in