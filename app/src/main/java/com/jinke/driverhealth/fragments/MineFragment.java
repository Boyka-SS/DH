package com.jinke.driverhealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.center.CenterActivity;
import com.jinke.driverhealth.activity.contactor.ContactorActivity;
import com.jinke.driverhealth.activity.feedback.AdviseActivity;
import com.jinke.driverhealth.activity.us.AboutUsActivity;
import com.jinke.driverhealth.activity.user.LoginActivity;
import com.jinke.driverhealth.activity.user.UserInfoActivity;

public class MineFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MineFragment";

    private ActivityResultLauncher<Intent> mIntentActivityResultLauncher;

    private LinearLayout mContactor, mHistoryData, mUserInfo, mAddvise, mAboutUs;
    private TextView mLoginStatus, mLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //registerForActivityResult() 方法注册结果回调（在 onStart() 之前调用）

        mIntentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == 1) {
                    Intent data = result.getData();
                    String username = data.getStringExtra("username");
                    mLoginStatus.setText("用户名：" + username);
                    mLogin.setText("");
                    mLogin.setClickable(false);
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setElevation(0);
        View view = inflater.inflate(R.layout.mine_fragment, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mContactor = view.findViewById(R.id.mine_contactor);
        mContactor.setOnClickListener(this);
        mHistoryData = view.findViewById(R.id.mine_history_data);
        mHistoryData.setOnClickListener(this);
        mUserInfo = view.findViewById(R.id.mine_user_info);
        mUserInfo.setOnClickListener(this);
        mAddvise = view.findViewById(R.id.mine_addvise);
        mAddvise.setOnClickListener(this);
        mAboutUs = view.findViewById(R.id.mine_about_us);
        mAboutUs.setOnClickListener(this);

        mLoginStatus = view.findViewById(R.id.mine_login_status);
        mLoginStatus.setOnClickListener(this);
        mLogin = view.findViewById(R.id.mine_login);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_contactor:
                startActivity(new Intent(getActivity(), ContactorActivity.class));
                break;
            case R.id.mine_history_data:
                startActivity(new Intent(getActivity(), CenterActivity.class));
                break;
            case R.id.mine_user_info:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.mine_addvise:
                startActivity(new Intent(getActivity(), AdviseActivity.class));
                break;
            case R.id.mine_about_us:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.mine_login:
                mIntentActivityResultLauncher.launch(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.mine_login_status:
                Toast.makeText(getActivity(), "6", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
