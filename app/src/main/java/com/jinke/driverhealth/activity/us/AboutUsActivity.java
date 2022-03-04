package com.jinke.driverhealth.activity.us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.us.contactus.ContactUsActivity;
import com.jinke.driverhealth.activity.us.waring.WaringTipsActivity;
import com.jinke.driverhealth.views.TitleLayout;

import es.dmoral.toasty.Toasty;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout mUpgrade, mContactUs, mWaring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        hideActionBar("关于我们");

        initView();
    }

    private void initView() {
        mUpgrade = findViewById(R.id.upgrade);
        mContactUs = findViewById(R.id.contact_us);
        mWaring = findViewById(R.id.waring);
        mUpgrade.setOnClickListener(this);
        mContactUs.setOnClickListener(this);
        mWaring.setOnClickListener(this);
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
            case R.id.upgrade:
                Toasty.info(AboutUsActivity.this, "当前已是最新版本", Toasty.LENGTH_LONG).show();
                break;
            case R.id.contact_us:
                startActivity(new Intent(AboutUsActivity.this, ContactUsActivity.class));
                break;
            case R.id.waring:
                startActivity(new Intent(AboutUsActivity.this, WaringTipsActivity.class));
                break;
            default:
                break;
        }
    }
}