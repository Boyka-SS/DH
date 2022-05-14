package com.jinke.driverhealth.activity.navigation;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.amap.api.navi.AmapRouteActivity;

/**
 * @author: fanlihao
 * @date: 2022/5/4
 */
public class CustomAmapRouteActivity extends AmapRouteActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
