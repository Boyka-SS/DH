package com.jinke.driverhealth.activity.hr;

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
import com.jinke.driverhealth.activity.detail.HRDetailActivity;
import com.jinke.driverhealth.beans.SingleHr;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.viewmodels.SingleDataViewModel;
import com.jinke.driverhealth.views.TitleLayout;

import java.text.ParseException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HrActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HrActivity";


    private TextView mTextView, mLoadMore, mCreatedTime;

    private SingleDataViewModel mSingleDataViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr);
        hideActionBar("获取最近一次心率数据");
        initView();


        initData();

    }

    private void initView() {
        mTextView = findViewById(R.id.hr_single_data);
        mCreatedTime = findViewById(R.id.create_time_hr);
        mLoadMore = findViewById(R.id.load_more_hr);
        mLoadMore.setOnClickListener(this);

    }


    /**
     * 心率不正常 发出提示
     */
    private void isShowAlert() {
        int i = Integer.parseInt((String) mTextView.getText());
        Log.d(TAG, i + "<-- hr");
        if (i > 60 && i < 100) {

        } else {
            //警报提示
            new SweetAlertDialog(HrActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                    formatCalendar = CalendarUtil.getFormatCalendar(created, "yyyy-MM-dd HH:mm:ss");
                    mCreatedTime.setText(formatCalendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                isShowAlert();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.load_more_hr:
                Intent intent = new Intent(HrActivity.this, HRDetailActivity.class);
                intent.putExtra("create_time", mCreatedTime.getText());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}