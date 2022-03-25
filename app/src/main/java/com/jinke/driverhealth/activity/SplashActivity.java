package com.jinke.driverhealth.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.MainActivity;
import com.jinke.driverhealth.R;

public class SplashActivity extends AppCompatActivity {



    private int i;
    private int count = 5;
    private Button mBtnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow();
/*如果之前是办透明模式，要加这一句需要取消半透明的Flag
window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);



        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2500);//使程序休眠五秒
                    Intent it = new Intent(getApplicationContext(), MainActivity.class);//启动MainActivity
                    startActivity(it);
                    finish();//关闭当前活动
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }







}