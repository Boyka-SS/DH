package com.jinke.driverhealth.activity.consult.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jinke.driverhealth.DHapplication;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.activity.consult.make.MakeQuesActivity;
import com.jinke.driverhealth.adapters.HistoryQuestionItemAdapter;
import com.jinke.driverhealth.data.db.beans.Question;
import com.jinke.driverhealth.data.db.dao.QuestionDao;
import com.jinke.driverhealth.views.TitleLayout;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

public class HistoryQuestionActivity extends AppCompatActivity {

    private SwipeRecyclerView mSwipeRecyclerView;

    private QuestionDao mQuestionDao = DHapplication.mAppDatabase.getQuestionDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_question);
        hideActionBar("历史提问");

        mQuestionDao.loadAllQuestion().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                initRecyclerView(questions);
            }
        });

    }

    private void initRecyclerView(List<Question> questions) {
        mSwipeRecyclerView = findViewById(R.id.history_question);
        HistoryQuestionItemAdapter historyQuestionItemAdapter = new HistoryQuestionItemAdapter(questions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mSwipeRecyclerView.setLayoutManager(linearLayoutManager);
        View footerView = getLayoutInflater().inflate(R.layout.empty_data_footview, mSwipeRecyclerView, false);
        mSwipeRecyclerView.addFooterView(footerView);
        mSwipeRecyclerView.setAdapter(historyQuestionItemAdapter);


    }

    //隐藏系统自带 头部导航栏
    private void hideActionBar(String title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        TitleLayout titleLayout = new TitleLayout(this);
        titleLayout.setTitleText(title).setLeftIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleLayout.setRightIco(R.mipmap.center_report);
        titleLayout.setRightIcoListening(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryQuestionActivity.this, MakeQuesActivity.class));
            }
        });
    }


}