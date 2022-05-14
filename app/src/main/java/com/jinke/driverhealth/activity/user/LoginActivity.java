package com.jinke.driverhealth.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.views.SrcScrollFrameLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {


    private SrcScrollFrameLayout mScanResultsCallback;

    private EditText userAccount, userPass;
    private CheckBox mIsSupport;
    private LinearLayout m13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        hideActionBarAndStatusBar();
        initView();
    }

    private void initView() {

        mScanResultsCallback = findViewById(R.id.fl_main);
        mIsSupport = findViewById(R.id.isSupport);
        userPass = findViewById(R.id.user_password);
        userAccount = findViewById(R.id.user_account);
        m13 = findViewById(R.id.linearLayout13);
        m13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsSupport.isChecked()) {
                    if ("admin".equals(userAccount.getText().toString()) && "123456".equals(userPass.getText().toString())) {
//                        Toasty.success(LoginActivity.this, "登陆成功", Toasty.LENGTH_SHORT).show();
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setContentText("登陆成功")
                                .show();
                        Intent intent = new Intent();
                        intent.putExtra("username", "admin");
                        setResult(1, intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this,"账号或密码有错，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Toasty.error(LoginActivity.this, "请勾选下方政策！", Toasty.LENGTH_SHORT).show();
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)

                            .setContentText("请勾选下方政策！")
                            .show();
                }
            }
        });
    }

    //隐藏系统自带 头部导航栏
    private void hideActionBarAndStatusBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}