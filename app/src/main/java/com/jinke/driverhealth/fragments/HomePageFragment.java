package com.jinke.driverhealth.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jinke.driverhealth.AppDatabase;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.ContacterActivity;
import com.jinke.driverhealth.activity.alcohol.AlcoholActivity;
import com.jinke.driverhealth.activity.hr.HrActivity;
import com.jinke.driverhealth.beans.Contactor;
import com.jinke.driverhealth.dao.ContactorDao;
import com.jinke.driverhealth.repository.ContactorRepository;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomePageFragment extends Fragment {
    private static final String TAG = "HomePageFragment";


    private ContactorDao mContactorDao;

    //View
    private CardView tempCard, hrCard, bpCard, alcoholCard;
    private TextView mAlcoholConcentration;
    private FloatingActionButton mAddContacter, mMakePhone;

    private ActivityResultLauncher<Intent> mIntentActivityResultLauncher;

    //data


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //registerForActivityResult() 方法注册结果回调（在 onStart() 之前调用）

        mIntentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //获取检测到的酒精数据，并存入room
                if (result.getResultCode() == 1) {
                    Intent data = result.getData();
                    String alcohol = data.getStringExtra("alcohol");
                    String alcoholCreateTime = data.getStringExtra("alcoholCreateTime");
                    mAlcoholConcentration.setText("获取本日酒精浓度：" + alcohol + " %");
                    //TODO add room
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_page_fragment, container, false);
        initView(view);
        setOnCardClickEvent();
        initViewEvent(view);
        return view;
    }


    private void initView(View view) {
        alcoholCard = view.findViewById(R.id.alcohol_data_get);
        hrCard = view.findViewById(R.id.hr_data_get);
        tempCard = view.findViewById(R.id.temp_data_get);
        bpCard = view.findViewById(R.id.bp_data_get);
        mAlcoholConcentration = view.findViewById(R.id.alcohol_concentration_txt);
        mAddContacter = view.findViewById(R.id.add_contacter);
        mMakePhone = view.findViewById(R.id.make_phone);
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
                //TODO
                Log.d(TAG, "--> temp");
            }
        });

        bpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Log.d(TAG, "--> bp");
            }
        });


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


    /**
     * 悬浮按钮 点击事件
     */
    private void initViewEvent(View view) {
        mAddContacter = view.findViewById(R.id.add_contacter);
        mAddContacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ContacterActivity.class);
                startActivity(intent1);
            }
        });
        mMakePhone = view.findViewById(R.id.make_phone);
        mMakePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Intent.ACTION_DIAL);
                try {
                    intent2.setData(Uri.parse("tel:" + getFirstContactorPhone(getContext())));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(intent2);
            }
        });
    }

    /**
     * 拨打第一联系人，如果没有就打120
     *
     * @return
     */
    private String getFirstContactorPhone(Context context) throws ExecutionException, InterruptedException {
        mContactorDao = AppDatabase.getInstance(context).getContactorDao();

        List<Contactor> contactors = new ContactorRepository(mContactorDao).getAllContactorData();
        Log.d(TAG, "contactors.size :" + contactors.size());

        if (contactors.size() != 0) {
            Iterator<Contactor> iterator = contactors.iterator();
            while (iterator.hasNext()) {
                Contactor next = iterator.next();
                if (next.isFirstContactor() == 1) {
                    Log.d(TAG, "name :" + next.getName());
                    return next.getPhone();
                }
            }
        }

        return "120";
    }


}