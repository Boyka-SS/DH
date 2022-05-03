package com.jinke.driverhealth.activity.navigation;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.adapters.PolicyPagerAdapter;
import com.jinke.driverhealth.data.network.juhe.beans.CityId;
import com.jinke.driverhealth.data.network.juhe.beans.PolicyContent;
import com.jinke.driverhealth.data.network.juhe.beans.RiskRegion;
import com.jinke.driverhealth.data.network.juhe.worker.PolicyContentNetWork;
import com.jinke.driverhealth.data.network.juhe.worker.RiskRegionNetwork;
import com.jinke.driverhealth.fragments.traveltips.CoachTipsFragment;
import com.jinke.driverhealth.fragments.traveltips.PlaneTipsFragment;
import com.jinke.driverhealth.fragments.traveltips.TrainTipsFragment;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.views.TitleLayout;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.sunfusheng.marqueeview.MarqueeView;

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
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolicyActivity extends AppCompatActivity {


    private static final String TAG = "PolicyActivity";

    private TextView mOrigin, mDestination;
    private TextView mOriginPolicy, mOriginPolicyContent, mOriginRiskRank;
    private TextView mDestinationPolicy, mDestinationPolicyContent, mDestinationRiskRank;
    private ImageView mExchange;

    private CityPickerView mPicker = new CityPickerView();

    //出行贴士
    private static final String[] CHANNELS = new String[]{"汽车", "飞机", "火车"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private PolicyPagerAdapter mPolicyPageAdapter = new PolicyPagerAdapter(mDataList);
    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

    //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
    private CityConfig cityConfig = new CityConfig.Builder()
            .title("选择城市")
            .setCityWheelType(CityConfig.WheelType.PRO_CITY)
            .titleBackgroundColor("#FFFFFF")
            .confirTextColor("#1E90FF")
            .cancelTextColor("#999999")
            .build();

    private String mOriginToCityId, mDestinationToCityId;
    private int mOriginCityId, mDestinationCityId;
    private CityId mCityIdList;
    private String mOriginProvinceName, mDestinationProvinceName;

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


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        //请求城市ID
       /* new CityIdNetwork()
                .requestCityIdData(new Callback<CityId>() {
                    @Override
                    public void onResponse(Call<CityId> call, Response<CityId> response) {
                        Log.d(TAG, "start https");
                        if (response.isSuccessful()) {
                            mCityIdList = response.body();
                            Log.d(TAG, "https response successful");
//                            setCityId(mCityIdList);
                        }
                    }

                    @Override
                    public void onFailure(Call<CityId> call, Throwable t) {
                        Toast.makeText(PolicyActivity.this, "请求城市ID失败", Toast.LENGTH_SHORT).show();
                    }
                });*/

    }

    private void initData() {
        //监测出发地和目的地 疫情风险等级
        new RiskRegionNetwork().requestRiskRegionData(Config.JUHE_KEY_FAN, new Callback<RiskRegion>() {
            @Override
            public void onResponse(Call<RiskRegion> call, Response<RiskRegion> response) {
                if (response.isSuccessful() && response.code() == 0) {
                    RiskRegion body = response.body();
                    updateRegionRiskRankUI(body);
                }
            }

            @Override
            public void onFailure(Call<RiskRegion> call, Throwable t) {
                Toast.makeText(PolicyActivity.this, "检测地区是否为风险地区失败", Toast.LENGTH_SHORT).show();
            }
        });
        //获取隔离政策
        new PolicyContentNetWork().requestRiskRegionData(Config.JUHE_KEY_FAN, "", "", new Callback<PolicyContent>() {
            @Override
            public void onResponse(Call<PolicyContent> call, Response<PolicyContent> response) {
                if (response.isSuccessful() && response.code() == 0) {
                    PolicyContent policyContent = response.body();

                    PolicyContent.ResultDTO.FromInfoDTO fromInfo = policyContent.getResult().getFrom_info();
                    String fromOutDesc = fromInfo.getOut_desc();
                    mOriginPolicyContent.setText(fromOutDesc);
                    PolicyContent.ResultDTO.ToInfoDTO toInfo = policyContent.getResult().getTo_info();
                    String toLowInDesc = toInfo.getLow_in_desc();
                    mDestinationPolicyContent.setText(toLowInDesc);
                }
            }

            @Override
            public void onFailure(Call<PolicyContent> call, Throwable t) {
                Toast.makeText(PolicyActivity.this, "请求隔离政策失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //监测出发地和目的地 疫情风险等级
    private void updateRegionRiskRankUI(RiskRegion body) {

        int originFlag = 0, destinationFlag = 0;
        List<RiskRegion.ResultDTO.HighListDTO> high_list = body.getResult().getHigh_list();
        Iterator<RiskRegion.ResultDTO.HighListDTO> iterator = high_list.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getArea_name().contains(mOriginToCityId)) {
                //出发地为高风险地区
                originFlag = 1;
                break;
            }
        }
        while (iterator.hasNext()) {
            if (iterator.next().getArea_name().contains(mDestinationToCityId)) {
                //目的地为高风险地区
                destinationFlag = 1;
                break;
            }
        }
        if (originFlag == 1) {
        } else if (destinationFlag == 1) {
        } else {
            List<RiskRegion.ResultDTO.MiddleListDTO> middle_list = body.getResult().getMiddle_list();
            Iterator<RiskRegion.ResultDTO.MiddleListDTO> it = middle_list.iterator();
            while (it.hasNext()) {
                if (it.next().getArea_name().contains(mOriginToCityId)) {
                    //出发地为中风险地区
                    originFlag = 2;
                    break;
                }
            }
            while (it.hasNext()) {
                if (it.next().getArea_name().contains(mDestinationToCityId)) {
                    //目的地为中风险地区
                    destinationFlag = 2;
                    break;
                }
            }
        }

        //出发地
        switch (originFlag) {
            case 0:
                //低分险
                mOriginRiskRank.setVisibility(View.VISIBLE);
                break;
            case 1:
                //高风险
                mOriginRiskRank.setVisibility(View.VISIBLE);
                mOriginRiskRank.setText("高风险");
                mOriginRiskRank.setTextColor(Color.parseColor("#DC143C"));
                mOriginRiskRank.setBackground(getResources().getDrawable(R.drawable.border_high_risk));
                break;
            case 2:
                //中风险
                mOriginRiskRank.setVisibility(View.VISIBLE);
                mOriginRiskRank.setText("中风险");
                mOriginRiskRank.setTextColor(Color.parseColor("#FFD700"));
                mOriginRiskRank.setBackground(getResources().getDrawable(R.drawable.border_middle_risk));
                break;
            default:
                break;

        }
        //目的地
        switch (destinationFlag) {
            case 0:
                //低分险
                mDestinationRiskRank.setVisibility(View.VISIBLE);
                break;
            case 1:
                //高风险
                mDestinationRiskRank.setVisibility(View.VISIBLE);
                mDestinationRiskRank.setText("高风险");
                mDestinationRiskRank.setTextColor(Color.parseColor("#DC143C"));
                mDestinationRiskRank.setBackground(getResources().getDrawable(R.drawable.border_high_risk));
                break;
            case 2:
                //中风险
                mDestinationRiskRank.setVisibility(View.VISIBLE);
                mDestinationRiskRank.setText("中风险");
                mDestinationRiskRank.setTextColor(Color.parseColor("#FFD700"));
                mDestinationRiskRank.setBackground(getResources().getDrawable(R.drawable.border_middle_risk));
                break;
            default:
                break;
        }
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
                            mOriginPolicy.setText(province.getName());
                        } else {
                            mOrigin.setText(city.getName());
                            mOriginPolicy.setText(city.getName());
                        }
                        mOriginToCityId = mOrigin.getText().toString();
                        mOriginProvinceName = province.getName();
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
                            mDestinationPolicy.setText(province.getName());
                        } else {
                            mDestination.setText(city.getName());
                            mDestinationPolicy.setText(city.getName());
                        }
                        mDestinationToCityId = mDestination.getText().toString();
                        mDestinationProvinceName = province.getName();

                        if (!mOrigin.getText().toString().equals("出发地") && !mDestination.getText().toString().equals("目的地")) {
                            initData();
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

        mOriginPolicy = findViewById(R.id.origin_region);
        mOriginPolicyContent = findViewById(R.id.origin_policy_content);
        mOriginRiskRank = findViewById(R.id.origin_risk_region);

        mDestinationPolicy = findViewById(R.id.destination_region);
        mDestinationPolicyContent = findViewById(R.id.destination_policy_content);
        mDestinationRiskRank = findViewById(R.id.destination_risk_region);


        MarqueeView marqueeView = (MarqueeView) findViewById(R.id.marqueeView);
        List<String> messages = new ArrayList<>();
        messages.add("1. 疫情期间，如非必要请勿远行");
        messages.add("2. 请持有健康码绿码和48h内的核酸检测证明出行");
        messages.add("3. 出行前请检查地区疫情防控政策");
        marqueeView.startWithList(messages);
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
        //隐藏状态栏
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new TitleLayout(this)
                .setTitleText(title)
                .setTitleBackgroundColor("#0855C3")
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }
}