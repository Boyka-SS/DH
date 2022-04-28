package com.jinke.driverhealth.activity.consult.make;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.DHapplication;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.db.beans.Question;
import com.jinke.driverhealth.data.db.dao.QuestionDao;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.jinke.driverhealth.views.TitleLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MakeQuesActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText mEditText;
    private Button mButton;

    private QuestionDao mQuestionDao = DHapplication.mAppDatabase.getQuestionDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_ques);

        hideActionBar("提问内容");

        initView();


    }

    private void initView() {

        mEditText = findViewById(R.id.make_ques);
        mButton = findViewById(R.id.question_submit);
        mButton.setOnClickListener(this);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_submit:
                storageQuesData();
                new SweetAlertDialog(MakeQuesActivity.this)
                        .setTitleText("提交成功")
                        .show();

                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 向本机存储问题
     */
    private void storageQuesData() {
        String time = CalendarUtil.getNowFormatCalendar("yyyy-MM-dd HH:mm:ss");
        String content = mEditText.getText().toString();

        Question question = new Question(time, content);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mQuestionDao.insertQuestion(question);
            }
        }).start();


    }

}