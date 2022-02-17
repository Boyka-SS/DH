package com.jinke.driverhealth.activity.hr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.beans.SingleHr;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.viewmodels.SingleDataViewModel;
import com.jinke.driverhealth.views.TitleLayout;

import java.text.ParseException;

public class HrActivity extends AppCompatActivity {

    private static final String TAG = "HrActivity";

    private TextView mTextView;

    private SingleDataViewModel mSingleDataViewModel;
    private int hr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr);
        hideActionBar("获取最近一次数据");
        mTextView = findViewById(R.id.hr_single_data);
        initData();

    }

    private void initData() {
        mSingleDataViewModel = new ViewModelProvider(this).get(SingleDataViewModel.class);
        String token = getSharedPreferences("data", MODE_PRIVATE).getString("token", "");
        mSingleDataViewModel.loadSingleHRData(token, Config.IMEI).observe(this, new Observer<SingleHr>() {
            @Override
            public void onChanged(SingleHr singleHr) {
                mTextView.setText(singleHr.getData().getHeart_rate() + "");
                String created = singleHr.getData().getCreated();
                String formatCalendar = null;
                try {
                    formatCalendar = CalendarUtil.getFormatCalendar(created, "yyyy/MM/dd HH:mm");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new TitleLayout(HrActivity.this).setTitleText(formatCalendar);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("hr", "" + mTextView.getText());
        setResult(2, intent);
        super.onBackPressed();
    }
}