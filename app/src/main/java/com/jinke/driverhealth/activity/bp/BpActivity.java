package com.jinke.driverhealth.activity.bp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.detail.BPDetailActivity;
import com.jinke.driverhealth.beans.SingleBp;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.viewmodels.SingleDataViewModel;
import com.jinke.driverhealth.views.TitleLayout;

import java.text.ParseException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BpActivity";

    private TextView mMaxBp, mMinBp, mLoadMore, mCreateTime;

    private SingleDataViewModel mSingleDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);
        hideActionBar("获取最近一次血压数据");

        initView();

        initData();
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
    private void initData() {
        mSingleDataViewModel = new ViewModelProvider(this).get(SingleDataViewModel.class);
        String token = getSharedPreferences("data", MODE_PRIVATE).getString("token", "");
        mSingleDataViewModel.loadSingleBPData(token, Config.IMEI).observe(this, new Observer<SingleBp>() {
            @Override
            public void onChanged(SingleBp singleBp) {
                mMaxBp.setText(singleBp.getData().getMax_rate()+"");
                mMinBp.setText(singleBp.getData().getMin_rate()+"");
                String created = singleBp.getData().getCreated();
                String formatCalendar = null;
                try {
                    formatCalendar = CalendarUtil.getFormatCalendar(created, "yyyy-MM-dd HH:mm:ss");
                    mCreateTime.setText(formatCalendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                isShowAlert();
            }
        });
    }

    private void initView() {
        mMaxBp = findViewById(R.id.max_bp_single_data);
        mMinBp = findViewById(R.id.min_bp_single_data);
        mCreateTime = findViewById(R.id.create_time_bp);
        mLoadMore = findViewById(R.id.load_more_temp);
        mLoadMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.load_more_temp:
                Intent intent = new Intent(BpActivity.this, BPDetailActivity.class);
                intent.putExtra("create_time", mCreateTime.getText());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 血压不正常 发出提示
     */
    private void isShowAlert() {
        int max = Integer.parseInt((String) mMaxBp.getText());
        int min = Integer.parseInt((String) mMinBp.getText());
        Log.d(TAG, max + "<-- max bp");
        Log.d(TAG, min + "<-- min bp");
        if (max > 90 && max < 140 && min > 60 && min < 90) {

        } else {
            //警报提示
            new SweetAlertDialog(BpActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("是否拨打紧急电话")
                    .setConfirmText("Yes,do it!")
                    .setCancelText("No,thanks!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:120"));
                            startActivity(intent);
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .show();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("max", "" + mMaxBp.getText());
        intent.putExtra("min", "" + mMinBp.getText());
        setResult(4, intent);
        super.onBackPressed();
    }
}