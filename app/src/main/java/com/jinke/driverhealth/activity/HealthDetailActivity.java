package com.jinke.driverhealth.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;

public class HealthDetailActivity extends AppCompatActivity {

    private static final String TAG = "HealthDetailActivity";

    private String mHeartRate;
    private String mTemperature;
    private String mBloodMaxPressure;
    private String mBloodMinPressure;
    private String mAlcohol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_detail);


        Bundle bundle = getIntent().getExtras();
        mHeartRate = bundle.getString("heart_rate");
        mTemperature = bundle.getString("temperature");
        mBloodMaxPressure = bundle.getString("blood_max_pressure");
        mBloodMinPressure = bundle.getString("blood_min_pressure");
        mAlcohol = bundle.getString("alcohol");
        Log.d(TAG, "mHeartRate" + mHeartRate);
        Log.d(TAG, "mTemperature" + mTemperature);
        Log.d(TAG, "mBloodMaxPressure" + mBloodMaxPressure);
        Log.d(TAG, "mBloodMinPressure" + mBloodMinPressure);
        Log.d(TAG, "mAlcohol" + mAlcohol);

    }
}