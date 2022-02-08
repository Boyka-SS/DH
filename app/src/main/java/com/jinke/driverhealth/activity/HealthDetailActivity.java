package com.jinke.driverhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.detail.AlcoholDeatilActivity;
import com.jinke.driverhealth.activity.detail.BPDetailActivity;
import com.jinke.driverhealth.activity.detail.HRDetailActivity;
import com.jinke.driverhealth.activity.detail.TempDetailActivity;
import com.jinke.driverhealth.views.TitleLayout;

/**
 * 健康数据详情页面
 */
public class HealthDetailActivity extends AppCompatActivity {

    private static final String TAG = "HealthDetailActivity";

    //相关数据
    private String mHeartRate;
    private String mTemperature;
    private String mBloodMaxPressure;
    private String mBloodMinPressure;
    private String mAlcohol;
    private String mCreateDate;


    private TextView temperature, heartRate, maxRate, minRate, alcohol, tip;
    private ImageView tempImage;
    private ImageView hrImg;
    private ImageView bpImg;
    private ImageView aldoImg;
    private CardView tempCard, hrCard, bpCard, aldoCard;

    private Bundle mBundle; //用Bundle携带数据 在页面之间传递数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_detail);

        hideActionBar();

        initView();
        initData();
        setOnCardClickEvent();
    }

    /**
     * 隐藏系统自带 头部导航栏
     */
    private void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        new TitleLayout(this).setTitleText("健康数据详情").setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setOnCardClickEvent() {


        tempCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthDetailActivity.this, TempDetailActivity.class);
                mBundle = new Bundle();
                mBundle.putString("create_time", mCreateDate);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        hrCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthDetailActivity.this, HRDetailActivity.class);
                mBundle = new Bundle();
                mBundle.putString("create_time", mCreateDate);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        bpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthDetailActivity.this, BPDetailActivity.class);
                mBundle = new Bundle();
                mBundle.putString("create_time", mCreateDate);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        aldoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthDetailActivity.this, AlcoholDeatilActivity.class);
                mBundle = new Bundle();
                mBundle.putString("create_time", mCreateDate);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

    }


    private void initView() {
        tempCard = this.findViewById(R.id.item_temperature);
        temperature = this.findViewById(R.id.item_temperature_value);
        tempImage = this.findViewById(R.id.item_temperature_image);

        heartRate = this.findViewById(R.id.item_heart_rate_value);
        hrImg = this.findViewById(R.id.item_heart_rate_image);
        hrCard = this.findViewById(R.id.item_heart_rate);

        maxRate = this.findViewById(R.id.item_blood_pressure_maxvalue);
        minRate = this.findViewById(R.id.item_blood_pressure_minvalue);
        bpImg = this.findViewById(R.id.item_blood_pressure_image);
        bpCard = this.findViewById(R.id.item_blood_pressure);

        alcohol = this.findViewById(R.id.item_alcohol_value);
        aldoImg = this.findViewById(R.id.item_alcohol_image);
        aldoCard = this.findViewById(R.id.item_alcohol);


        tip = this.findViewById(R.id.textView);

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        //心率
        mHeartRate = bundle.getString("heart_rate");
        if (Integer.parseInt(mHeartRate) >= 60 && Integer.parseInt(mHeartRate) <= 100) {
            hrImg.setImageResource(R.mipmap.good1);
        } else {
            hrImg.setImageResource(R.mipmap.bad1);
        }
        heartRate.setText(mHeartRate + " 次/分");
        //体温
        mTemperature = bundle.getString("temperature");
        if (
                Double.parseDouble(mTemperature) >= 35.5 &&
                        Double.parseDouble(mTemperature) <= 37.5) {
            tempImage.setImageResource(R.mipmap.good1);
        } else {
            tempImage.setImageResource(R.mipmap.bad1);
        }
        temperature.setText(mTemperature + " ℃");
        //血压
        mBloodMaxPressure = bundle.getString("blood_max_pressure");
        maxRate.setText(mBloodMaxPressure + " mmHg");
        mBloodMinPressure = bundle.getString("blood_min_pressure");
        minRate.setText(mBloodMinPressure + " mmHg");

        if (
                Integer.parseInt(mBloodMaxPressure) >= 90 &&
                        Integer.parseInt(mBloodMaxPressure) <= 120 &&
                        Integer.parseInt(mBloodMinPressure) >= 60 &&
                        Integer.parseInt(mBloodMinPressure) <= 80) {
            bpImg.setImageResource(R.mipmap.good1);
        } else {
            bpImg.setImageResource(R.mipmap.bad1);
        }
        //酒精
        mAlcohol = bundle.getString("alcohol");
        if (Double.parseDouble(mAlcohol) <= 20) {
            aldoImg.setImageResource(R.mipmap.good1);
        } else {
            aldoImg.setImageResource(R.mipmap.bad1);
        }
        alcohol.setText(mAlcohol + " g/L");

        //心率生成日期
        mCreateDate = bundle.getString("create_date");
        tip.setText(mCreateDate);
    }
}