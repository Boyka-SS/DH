package com.jinke.driverhealth.activity.temp;

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

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.detail.TempDetailActivity;
import com.jinke.driverhealth.beans.SingleTemp;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.viewmodels.SingleDataViewModel;
import com.jinke.driverhealth.views.TitleLayout;

import java.text.ParseException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TempActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TempActivity";

    private SingleDataViewModel mSingleDataViewModel;

    private ArcProgress mArcProgress;
    private TextView mLoadMore, mCreatedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_temp);

        hideActionBar("获取最近一次体温数据");

        initView();
        initData();
    }

    private void initView() {
        mArcProgress = findViewById(R.id.arc_progress);
        mCreatedTime = findViewById(R.id.create_time_temp);
        mLoadMore = findViewById(R.id.load_more_temp);
        mLoadMore.setOnClickListener(this);
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
        mSingleDataViewModel.loadSingleTempData(token, Config.IMEI).observe(this, new Observer<SingleTemp>() {
            @Override
            public void onChanged(SingleTemp singleTemp) {
                Log.d(TAG, singleTemp.getData().getTemperature() + " <-- fetch single temp data");
                String created = singleTemp.getData().getCreated();
                mArcProgress.setProgress(Float.parseFloat(singleTemp.getData().getTemperature()));
                String formatCalendar = null;
                try {
                    formatCalendar = CalendarUtil.getFormatCalendar(created, "yyyy-MM-dd HH:mm:ss");
                    mCreatedTime.setText(formatCalendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                isShowAlert(singleTemp.getData().getTemperature());
            }
        });
    }

    /**
     * 体温不正常 发出提示
     *
     * @param temperature
     */
    private void isShowAlert(String temperature) {
        float v = Float.parseFloat(temperature);
        Log.d(TAG, v + "<-- temp");
        if (v > 35.5 && v < 37.5) {

        } else {
            //警报提示
            new SweetAlertDialog(TempActivity.this, SweetAlertDialog.WARNING_TYPE)
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.load_more_temp:
                //点击查看更多,跳转 line chart temp
                Intent intent = new Intent(TempActivity.this, TempDetailActivity.class);
                intent.putExtra("create_time", mCreatedTime.getText());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("temp", "" + Math.round(mArcProgress.getProgress()));
        setResult(3, intent);
        super.onBackPressed();
    }
}