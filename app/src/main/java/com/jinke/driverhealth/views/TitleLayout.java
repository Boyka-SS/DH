package com.jinke.driverhealth.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinke.driverhealth.R;

/**
 * @author: fanlihao
 * @date: 2022/1/23
 */
public class TitleLayout extends LinearLayout {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.title_bar, this);
        TextView textView = this.findViewById(R.id.tv_title);
        textView.setText("添加联系人");

        ImageButton back = this.findViewById(R.id.ib_title_back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }


}
