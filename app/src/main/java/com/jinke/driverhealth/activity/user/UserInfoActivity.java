package com.jinke.driverhealth.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.DHapplication;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.db.beans.UserInfo;
import com.jinke.driverhealth.data.db.dao.UserInfoDao;
import com.jinke.driverhealth.views.TitleLayout;

import es.dmoral.toasty.Toasty;

public class UserInfoActivity extends AppCompatActivity {


    private EditText mName, mAddress, mIdCard, mSex, mWeight, mHeight;
    private Button mSubmit;
    private UserInfoDao mUserInfoDao = DHapplication.mAppDatabase.getUserInfoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        hideActionBar("个人资料");

        initView();
    }

    private void initView() {
        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mIdCard = findViewById(R.id.id_card);
        mSex = findViewById(R.id.sex);
        mWeight = findViewById(R.id.weight);
        mHeight = findViewById(R.id.height);
        mSubmit = findViewById(R.id.advise_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String address = mAddress.getText().toString();
                String idCard = mIdCard.getText().toString();
                String sex = mSex.getText().toString();
                String weight = mWeight.getText().toString();
                String height = mHeight.getText().toString();

                UserInfo userInfo = new UserInfo(name, address, idCard, sex, weight, height);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mUserInfoDao.insertUserInfo(userInfo);

                    }
                }).start();

                Toasty.success(UserInfoActivity.this, "添加成功", Toasty.LENGTH_SHORT).show();
                finish();
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