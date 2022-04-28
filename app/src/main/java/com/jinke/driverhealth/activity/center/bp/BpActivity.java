package com.jinke.driverhealth.activity.center.bp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.adapters.BpCenterAdapter;
import com.jinke.driverhealth.data.network.tudingyun.beans.BloodPressure;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.viewmodels.DataViewModel;
import com.jinke.driverhealth.views.TitleLayout;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

public class BpActivity extends AppCompatActivity {


    private SwipeRecyclerView mSwipeRecyclerView;
    private DataViewModel mDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);
        hideActionBar("血压列表");

        getData("", "2022-03-20 00:00:00", "1", "20");
    }

    private void getData(String startTime, String endTime, String page, String limit) {

        mDataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        if (startTime == "") {
            startTime = Config.START_TIME;
        }
        String token = getSharedPreferences("data", MODE_PRIVATE).getString("token", "");
        //hr
        mDataViewModel.loadBPData(token, startTime, endTime, page, limit, "").observe(this, new Observer<BloodPressure>() {
            @Override
            public void onChanged(BloodPressure bloodPressure) {

                if (bloodPressure.getStatus() == 0) {

                    List<BloodPressure.DataDTO.ResultDTO> result = bloodPressure.getData().getResult();
                    initRecyclerView(result);
                }
            }
        });
    }

    private void initRecyclerView(List<BloodPressure.DataDTO.ResultDTO> result) {

        BpCenterAdapter bpCenterAdapter = new BpCenterAdapter(result);
        mSwipeRecyclerView = findViewById(R.id.bp_list_center);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mSwipeRecyclerView.setLayoutManager(linearLayoutManager);


        View footerView = getLayoutInflater().inflate(R.layout.empty_data_footview, mSwipeRecyclerView, false);
        mSwipeRecyclerView.addFooterView(footerView);

        mSwipeRecyclerView.setAdapter(bpCenterAdapter);
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