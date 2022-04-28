package com.jinke.driverhealth.activity.feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.DHapplication;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.db.beans.Advise;
import com.jinke.driverhealth.data.db.dao.AdviseDao;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.jinke.driverhealth.views.TitleLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdviseActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private Button mButton;

    private AdviseDao mAdviseDao = DHapplication.mAppDatabase.getAdviseDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advise);
        hideActionBar("意见反馈");

        initView();
    }


    private void initView() {

        mEditText = findViewById(R.id.make_advise);
        mButton = findViewById(R.id.advise_submit);
        mButton.setOnClickListener(this);

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
            case R.id.advise_submit:
                storageAdviseData();
                new SweetAlertDialog(AdviseActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setContentText("提交成功")
                        .show();
                finish();
                break;
            default:
                break;
        }
    }

    private void storageAdviseData() {
        String time = CalendarUtil.getNowFormatCalendar("yyyy-MM-dd HH:mm:ss");
        String content = mEditText.getText().toString();

        Advise advise = new Advise(time, content);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAdviseDao.insertAdvise(advise);
            }
        }).start();
    }
}