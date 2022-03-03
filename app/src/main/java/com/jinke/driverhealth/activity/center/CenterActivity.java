package com.jinke.driverhealth.activity.center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.center.alcohol.AlcoholCenterActivity;
import com.jinke.driverhealth.activity.center.bp.BpActivity;
import com.jinke.driverhealth.activity.center.hr.HrActivity;
import com.jinke.driverhealth.activity.center.temp.TempActivity;
import com.jinke.driverhealth.views.TitleLayout;

public class CenterActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout mHrList, mBpList, mTempList, mAlcohoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        hideActionBar("数据中心");

        initView();
    }

    private void initView() {
        mHrList = findViewById(R.id.hr_list);
        mBpList = findViewById(R.id.bp_list);
        mTempList = findViewById(R.id.temp_list);
        mAlcohoList = findViewById(R.id.alcohol_list);

        mHrList.setOnClickListener(this);
        mBpList.setOnClickListener(this);
        mTempList.setOnClickListener(this);
        mAlcohoList.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hr_list:
                startActivity(new Intent(CenterActivity.this, HrActivity.class));
                break;
            case R.id.bp_list:
                startActivity(new Intent(CenterActivity.this, BpActivity.class));
                break;
            case R.id.temp_list:
                startActivity(new Intent(CenterActivity.this, TempActivity.class));
                break;
            case R.id.alcohol_list:
                startActivity(new Intent(CenterActivity.this, AlcoholCenterActivity.class));
                break;
            default:
                break;
        }
    }
}