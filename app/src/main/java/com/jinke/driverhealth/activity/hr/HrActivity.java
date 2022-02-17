package com.jinke.driverhealth.activity.hr;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.beans.SingleHr;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.viewmodels.SingleDataViewModel;
import com.jinke.driverhealth.views.TitleLayout;

public class HrActivity extends AppCompatActivity {

    private static final String TAG = "HrActivity";

    private TextView mTextView;

    private SingleDataViewModel mSingleDataViewModel;
    private int hr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr);
//        hideActionBar("获取最近一次心率");
        mTextView = findViewById(R.id.single_hr_data);
        initData();

    }

    private void initData() {
        mSingleDataViewModel = new ViewModelProvider(this).get(SingleDataViewModel.class);
        String token = getSharedPreferences("data", MODE_PRIVATE).getString("token", "");
        mSingleDataViewModel.loadSingleHRData(token, Config.IMEI).observe(this, new Observer<SingleHr>() {
            @Override
            public void onChanged(SingleHr singleHr) {
                mTextView.setText(singleHr.getData().getHeart_rate()+"");
            }
        });
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