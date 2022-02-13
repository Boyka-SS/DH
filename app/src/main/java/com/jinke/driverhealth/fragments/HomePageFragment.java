package com.jinke.driverhealth.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jinke.driverhealth.AppDatabase;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.ContacterActivity;
import com.jinke.driverhealth.activity.alcohol.AlcoholActivity;
import com.jinke.driverhealth.beans.Contactor;
import com.jinke.driverhealth.dao.ContactorDao;
import com.jinke.driverhealth.repository.ContactorRepository;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomePageFragment extends Fragment {
    private static final String TAG = "HomePageFragment";


    private FloatingActionButton mAddContacter;
    private FloatingActionButton mMakePhone;

    private ContactorDao mContactorDao;
    private CardView mCardView;
    private TextView mAlcoholTextView;
    //点击跳转 获取酒精浓度页面
    private Button mBtn;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_page_fragment, container, false);
        mBtn = view.findViewById(R.id.alcohol_data_get);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlcoholActivity.class);
                startActivity(intent);
            }
        });
        initViewEvent(view);
        return view;
    }



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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();//视图销毁时必须解绑,否则会造成内存泄漏.
    }


}