package com.jinke.driverhealth.activity.navigation;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.adapters.PolicyPagerAdapter;
import com.jinke.driverhealth.fragments.traveltips.CoachTipsFragment;
import com.jinke.driverhealth.fragments.traveltips.PlaneTipsFragment;
import com.jinke.driverhealth.fragments.traveltips.TrainTipsFragment;
import com.jinke.driverhealth.views.TitleLayout;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolicyActivity extends AppCompatActivity {


    private static final String TAG = "PolicyActivity";

    private TextView mOrigin, mDestination;
    private ImageView mExchange;

    private CityPickerView mPicker = new CityPickerView();
    //出行贴士
    private static final String[] CHANNELS = new String[]{ "汽车","飞机","火车"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private PolicyPagerAdapter mPolicyPageAdapter = new PolicyPagerAdapter(mDataList);
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
    private CityConfig cityConfig = new CityConfig.Builder()
            .title("选择城市")
            .setCityWheelType(CityConfig.WheelType.PRO_CITY)
            .titleBackgroundColor("#FFFFFF")
            .confirTextColor("#1E90FF")
            .cancelTextColor("#999999")
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        hideActionBar("地区疫情防控政策");
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);

        initView();
        initEvent(this);

        initFragments();
        initMagicIndicator();

        mFragmentContainerHelper.handlePageSelected(0, false);
        switchPages(0);
    }

    private void switchPages(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        for (int i = 0, j = mFragments.size(); i < j; i++) {
            if (i == index) {
                continue;
            }
            fragment = mFragments.get(i);
            if (fragment.isAdded()) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragment = mFragments.get(index);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container_policy, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void initFragments() {
        CoachTipsFragment coachTipsFragment = new CoachTipsFragment();
        PlaneTipsFragment planeTipsFragment = new PlaneTipsFragment();
        TrainTipsFragment trainTipsFragment = new TrainTipsFragment();
        mFragments.add(coachTipsFragment);
        mFragments.add(planeTipsFragment);
        mFragments.add(trainTipsFragment);
    }


    private void initEvent(Context context) {
        mOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPicker.setConfig(cityConfig);
                //监听选择点击事件及返回结果
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        if (province.getName().equals("北京市") || province.getName().equals("上海市") || province.getName().equals("天津市")) {
                            mOrigin.setText(province.getName());
                        } else {
                            mOrigin.setText(city.getName());
                        }

                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showLongToast(context, "已取消");
                    }
                });

                //显示
                mPicker.showCityPicker();
            }
        });

        mDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPicker.setConfig(cityConfig);
                //监听选择点击事件及返回结果
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        if (province.getName().equals("北京市") || province.getName().equals("上海市") || province.getName().equals("天津市")) {
                            mDestination.setText(province.getName());
                        } else {
                            mDestination.setText(city.getName());
                        }
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showLongToast(context, "已取消");
                    }
                });

                //显示
                mPicker.showCityPicker();
            }
        });

        mExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = mDestination.getText();
                mDestination.setText(mOrigin.getText());
                mOrigin.setText(text);
            }
        });
    }

    private void initView() {
        mOrigin = findViewById(R.id.origin);
        mDestination = findViewById(R.id.destination);
        mExchange = findViewById(R.id.origin_exchange_destination);
    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator4);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#1E90FF"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(index);
                        switchPages(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#ebe4e3"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
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