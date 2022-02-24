package com.jinke.driverhealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.contactor.ContactorActivity;

public class MineFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MineFragment";

    private LinearLayout mContactor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mine_fragment, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mContactor = view.findViewById(R.id.mine_contactor);
        mContactor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_contactor:
                //跳转联系人管理页面
                startActivity(new Intent(getActivity(), ContactorActivity.class));
                break;
            default:
                break;
        }
    }
}
