package com.jinke.driverhealth.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.ContacterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomePageFragment extends Fragment {

    private static final String TAG = "HomePageFragment";
    private Unbinder unbinder;

    @BindView(R.id.add_contacter)
    FloatingActionButton mAddContacter;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_page_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.add_contacter,})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_contacter:
                Intent intent = new Intent(getActivity(), ContacterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();//视图销毁时必须解绑,否则会造成内存泄漏.
    }
}