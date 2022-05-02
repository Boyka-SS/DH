package com.jinke.driverhealth.activity.navigation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

public class PolicyActivity extends AppCompatActivity {


    private static final String TAG = "PolicyActivity";

    private TextView mOrigin, mDestination;
    private ImageView mExchange;

    private CityPickerView mPicker = new CityPickerView();


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


        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);

        initView();
        initEvent(this);
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


}