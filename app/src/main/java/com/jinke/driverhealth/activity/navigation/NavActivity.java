package com.jinke.driverhealth.activity.navigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.jinke.driverhealth.R;

public class NavActivity extends AppCompatActivity  {

    private AMapNaviView aMapNaviView=null;
    private AMapNavi aMapNavi=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

    }

}