package com.jinke.driverhealth;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.jinke.driverhealth.adapters.MyAdapt;
import com.jinke.driverhealth.fragments.DataFragment;
import com.jinke.driverhealth.fragments.HomePageFragment;
import com.jinke.driverhealth.fragments.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private ViewPager2 viewPager;
    private LinearLayout tab1, tab2, tab3;
    private ImageView tab1_iv, tab2_iv, tab3_iv, current_iv;
    private TextView tab1_tv, tab2_tv, tab3_tv, current_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPager();
        initView();
    }

    private void initView() {
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);

        tab1_iv = findViewById(R.id.tab1_iv);
        tab2_iv = findViewById(R.id.tab2_iv);
        tab3_iv = findViewById(R.id.tab3_iv);

        tab1_tv = findViewById(R.id.tab1_tv);
        tab2_tv = findViewById(R.id.tab2_tv);
        tab3_tv = findViewById(R.id.tab3_tv);


        tab1_iv.setSelected(true);
        current_iv = tab1_iv;
        tab1_tv.setSelected(true);
        current_tv = tab1_tv;

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);

    }

    // 定义viewPager和创建适配器
    private void initPager() {
        viewPager = findViewById(R.id.vp_container);
        //Fragment
        List<Fragment> list = new ArrayList<>();
        list.add(new HomePageFragment());
        list.add(new DataFragment());
        list.add(new MineFragment());

        MyAdapt myFragmentAdapter = new MyAdapt(getSupportFragmentManager(), getLifecycle(), list);
        viewPager.setAdapter(myFragmentAdapter);

//        ViewPager2提供的滑动监听
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pageChage(position);
            }

        });
    }

    private void pageChage(int position) {
        current_iv.setSelected(false);
        current_tv.setSelected(false);
        switch (position) {
            case 0:
                tab1_iv.setSelected(true);
                current_iv = tab1_iv;
                tab1_tv.setSelected(true);
                current_tv = tab1_tv;
                break;
            case 1:
                tab2_iv.setSelected(true);
                current_iv = tab2_iv;
                tab2_tv.setSelected(true);
                current_tv = tab2_tv;
                break;
            case 2:
                tab3_iv.setSelected(true);
                current_iv = tab3_iv;
                tab3_tv.setSelected(true);
                current_tv = tab3_tv;
                break;
            default:
                break;
        }
    }

    //底部tab点击事件
    @Override
    public void onClick(View v) {
        pageChage(v.getId());
        switch (v.getId()) {
            case R.id.tab1:
                //smoothScroll:为false是表示不平滑滚动
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tab2:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.tab3:
                viewPager.setCurrentItem(2, true);
                break;
            default:
                break;
        }
    }
}