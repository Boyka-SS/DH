package com.jinke.driverhealth.activity.center.temp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.adapters.TempCenterAdapter;
import com.jinke.driverhealth.data.network.tudingyun.beans.Temperature;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.viewmodels.DataViewModel;
import com.jinke.driverhealth.views.TitleLayout;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

public class TempActivity extends AppCompatActivity {

    private SwipeRecyclerView mSwipeRecyclerView;
    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp2);
        hideActionBar("体温列表");

        getData("", "2022-03-20 00:00:00", "1", "20");
    }
    private void getData(String startTime, String endTime, String page, String limit) {

        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        if (startTime == "") {
            startTime = Config.START_TIME;
        }
        String token = getSharedPreferences("data", MODE_PRIVATE).getString("token", "");
        //hr
        mDataViewModel.loadTempData(token, startTime, endTime, page, limit, "").observe(this, new Observer<Temperature>() {
            @Override
            public void onChanged(Temperature temperature) {

                if (temperature.getStatus() == 0) {
                    List<Temperature.DataDTO.ResultDTO> result = temperature.getData().getResult();
                    initRecyclerView(result);
                }
            }
        });
    }

    private void initRecyclerView(List<Temperature.DataDTO.ResultDTO> result) {
        TempCenterAdapter tempCenterAdapter = new TempCenterAdapter(result);
        mSwipeRecyclerView = findViewById(R.id.temp_list_center);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mSwipeRecyclerView.setLayoutManager(linearLayoutManager);


        View footerView = getLayoutInflater().inflate(R.layout.empty_data_footview, mSwipeRecyclerView, false);
        mSwipeRecyclerView.addFooterView(footerView);

        mSwipeRecyclerView.setAdapter(tempCenterAdapter);
    }
    //隐藏系统自带 头部导航栏
    private void hideActionBar(String title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        new TitleLayout(this).setTitleText(title).setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}