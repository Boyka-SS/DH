package com.jinke.driverhealth.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.jinke.driverhealth.DHapplication;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.alcohol.AlcoholActivity;
import com.jinke.driverhealth.activity.bp.BpActivity;
import com.jinke.driverhealth.activity.hr.HrActivity;
import com.jinke.driverhealth.activity.temp.TempActivity;
import com.jinke.driverhealth.data.db.beans.Alcohol;
import com.jinke.driverhealth.data.db.dao.AlcoholDao;

public class HomePageFragment extends Fragment {
    private static final String TAG = "HomePageFragment";


    //View
    private CardView tempCard, hrCard, bpCard, alcoholCard;
    private TextView mAlcoholConcentration, mHr, mTemp, mBp;

    //android reuslt api  用于在activity（fragment)间通信
    private ActivityResultLauncher<Intent> mIntentActivityResultLauncher;
    private AlcoholDao mAlcoholDao = DHapplication.mAppDatabase.getAlcoholDao();

    //data


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //registerForActivityResult() 方法注册结果回调（在 onStart() 之前调用）

        mIntentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //向第二个页面拿回数据
                if (result.getResultCode() == 1) {
                    //获取检测到的酒精数据，并存入room
                    Intent data = result.getData();
                    String alcohol = data.getStringExtra("alcohol");
                    String alcoholCreateTime = data.getStringExtra("alcoholCreateTime");
                    mAlcoholConcentration.setText("获取本日酒精浓度：" + alcohol + " %");
                    storageData(alcohol, alcoholCreateTime, 1);
                } else if (result.getResultCode() == 2) {
                    //获取最近一次 心率 数据
                    Intent data = result.getData();
                    String hr = data.getStringExtra("hr");
                    mHr.setText(" 获取最近一次心率：" + hr + " bpm");
                } else if (result.getResultCode() == 3) {
                    //获取最近一次 体温 数据
                    Intent data = result.getData();
                    String temp = data.getStringExtra("temp");
                    mTemp.setText(" 获取最近一次体温：" + temp + " ℃");
                } else if (result.getResultCode() == 4) {
                    //获取最近一次 血压 数据
                    Intent data = result.getData();
                    String max = data.getStringExtra("max");
                    String min = data.getStringExtra("min");
                    mBp.setText(" 获取最近一次血压：" + max + "/" + min + " mmhg");
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_page_fragment, container, false);
        //initView(view);
       //setOnCardClickEvent();
        return view;
    }

    /**
     * 存取数据
     *
     * @param data              数据
     * @param alcoholCreateTime 数据生成时间
     * @param i                 数据类型：酒精、心率、血压等
     */
    private void storageData(String data, String alcoholCreateTime, int i) {
        if (i == 1) {
            //存储酒精数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Alcohol alcohol = new Alcohol(alcoholCreateTime, Integer.parseInt(data));
                    mAlcoholDao.insertAlcohol(alcohol);
                }
            }).start();
        }
    }


    private void initView(View view) {
//        alcoholCard = view.findViewById(R.id.alcohol_data_get);
//        hrCard = view.findViewById(R.id.hr_data_get);
//        tempCard = view.findViewById(R.id.temp_data_get);
//        bpCard = view.findViewById(R.id.bp_data_get);
//        mAlcoholConcentration = view.findViewById(R.id.alcohol_concentration_txt);
//        mHr = view.findViewById(R.id.hr_txt);
//        mTemp = view.findViewById(R.id.temp_txt);
//        mBp = view.findViewById(R.id.bp_txt);

    }

    /**
     * 卡片点击事件
     */
    private void setOnCardClickEvent() {
        alcoholCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTestAlcoholPage();
            }
        });
        hrCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHrPage();
            }
        });
        tempCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTempPage();
            }
        });

        bpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToBpPage();
            }
        });


    }

    /**
     * 跳转到 获取血压页面
     */
    private void navigateToBpPage() {
        mIntentActivityResultLauncher.launch(new Intent(getActivity(), BpActivity.class));
    }

    /**
     * 跳转到 获取体温页面
     */
    private void navigateToTempPage() {
        mIntentActivityResultLauncher.launch(new Intent(getActivity(), TempActivity.class));
    }

    /**
     * 跳转到 获取心率页面
     */
    private void navigateToHrPage() {
        mIntentActivityResultLauncher.launch(new Intent(getActivity(), HrActivity.class));
    }

    /**
     * 跳转到 获取酒精浓度页面
     */
    private void navigateToTestAlcoholPage() {
        mIntentActivityResultLauncher.launch(new Intent(getActivity(), AlcoholActivity.class));
    }


}