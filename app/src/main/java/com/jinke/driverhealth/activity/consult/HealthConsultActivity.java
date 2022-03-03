package com.jinke.driverhealth.activity.consult;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.consult.history.HistoryQuestionActivity;
import com.jinke.driverhealth.activity.consult.make.MakeQuesActivity;
import com.jinke.driverhealth.views.TitleLayout;

public class HealthConsultActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mMakeQuestion, mHistoryQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_consult);

        hideActionBar("健康咨询");
        initView();
    }

    private void initView() {
        mMakeQuestion = findViewById(R.id.data_fragment_make_question);
        mMakeQuestion.setOnClickListener(this);
        mHistoryQuestion = findViewById(R.id.data_fragment_history_question);
        mHistoryQuestion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.data_fragment_make_question:
                navigateToQuestionPage();
                break;
            case R.id.data_fragment_history_question:
                navigateToHistoryQuestionPage();
                break;
            default:
                break;
        }
    }

    /**
     * 历史提问
     */
    private void navigateToHistoryQuestionPage() {
        startActivity(new Intent(HealthConsultActivity.this, HistoryQuestionActivity.class));
    }

    /**
     * 提问内容页面
     */
    private void navigateToQuestionPage() {
        startActivity(new Intent(HealthConsultActivity.this, MakeQuesActivity.class));
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