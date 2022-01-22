package com.jinke.driverhealth.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;

public class HealthDetailActivity extends AppCompatActivity {

    private static final String TAG = "HealthDetailActivity";

    private String mHeartRate;
    private String mTemperature;
    private String mBloodMaxPressure;
    private String mBloodMinPressure;
    private String mAlcohol;


    private TextView temperature, heartRate, maxRate, minRate, alcohol;
    private ImageView tempImage, hrImg, bpImg, alcoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_detail);
        initView();
        initData();
    }

    private void initView() {
        temperature = this.findViewById(R.id.item_temperature_value);
        tempImage = this.findViewById(R.id.item_temperature_image);

        heartRate = this.findViewById(R.id.item_heart_rate_value);
        hrImg = this.findViewById(R.id.item_heart_rate_image);

        maxRate = this.findViewById(R.id.item_blood_pressure_maxvalue);
        minRate = this.findViewById(R.id.item_blood_pressure_minvalue);
        bpImg = this.findViewById(R.id.item_blood_pressure_image);

        alcohol = this.findViewById(R.id.item_alcohol_value);
        alcoImg = this.findViewById(R.id.item_alcohol_image);

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        mHeartRate = bundle.getString("heart_rate");
        if (Integer.parseInt(mHeartRate) >= 60 && Integer.parseInt(mHeartRate) <= 100) {
            hrImg.setImageResource(R.mipmap.good1);
        } else {
            hrImg.setImageResource(R.mipmap.bad1);
        }
        heartRate.setText(mHeartRate + " 次/分");

        mTemperature = bundle.getString("temperature");
        if (Double.parseDouble(mTemperature) >= 36.0 && Double.parseDouble(mTemperature) <= 37.0) {
            hrImg.setImageResource(R.mipmap.good1);
        } else {
            hrImg.setImageResource(R.mipmap.bad1);
        }
        temperature.setText(mTemperature + " ℃");

        mBloodMaxPressure = bundle.getString("blood_max_pressure");
        maxRate.setText(mBloodMaxPressure + " mmHg");
        mBloodMinPressure = bundle.getString("blood_min_pressure");
        minRate.setText(mBloodMinPressure + " mmHg");
        if (Integer.parseInt(mBloodMaxPressure) >= 90 && Integer.parseInt(mBloodMaxPressure) <= 120 && Integer.parseInt(mBloodMinPressure) >= 60 && Integer.parseInt(mBloodMinPressure) <= 80) {
            bpImg.setImageResource(R.mipmap.good1);
        } else {
            bpImg.setImageResource(R.mipmap.bad1);
        }

        mAlcohol = bundle.getString("alcohol");
        if (Double.parseDouble(mAlcohol) <= 0.2) {
            hrImg.setImageResource(R.mipmap.good1);
        } else {
            hrImg.setImageResource(R.mipmap.bad1);
        }
        alcohol.setText(mAlcohol + " g/L");
    }
}