package com.jinke.driverhealth.views;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.driverhealth.R;

/**
 * @author: fanlihao
 * @date: 2022/1/23
 */

public class TitleLayout {

    public View getTitleView() {
        return titleView;
    }

    public RelativeLayout getRl_title_bar() {
        return rl_title_bar;
    }

    public LinearLayout getLl_title_bar() {
        return ll_title_bar;
    }

    public ImageView getIv_left_icon() {
        return iv_left_icon;
    }

    public ImageView getIv_rightIco() {
        return iv_rightIco;
    }

    public TextView getTv_title_middle() {
        return tv_title_middle;
    }

    /**
     * 左侧/右侧图标和中间标题
     */
    private View titleView;

    /**
     * 左侧/右侧图标和中间标题
     */
    private RelativeLayout rl_title_bar;

    /**
     * 跟布局
     */
    private LinearLayout ll_title_bar;

    /**
     * 左侧图标
     */
    private ImageView iv_left_icon;

    /**
     * 右侧图标
     */
    private ImageView iv_rightIco;

    /**
     * 中间标题
     */
    private TextView tv_title_middle;


    /**
     * 构造方法：用于获取对象
     * */
    public TitleLayout(Activity context){
        titleView = context.findViewById(R.id.rl_title_bar);
        rl_title_bar = (RelativeLayout)titleView.findViewById(R.id.rl_title_bar);
        ll_title_bar = (LinearLayout) context.findViewById(R.id.ll_title_bar);
        tv_title_middle = (TextView)titleView.findViewById(R.id.tv_title_middle);
        iv_left_icon = (ImageView)titleView.findViewById(R.id.iv_left_icon);
        iv_rightIco = (ImageView)titleView.findViewById(R.id.iv_rightIco);
    }



    /**
     * 用于设置标题栏文字
     * @param titleText  传入要设置的标题
     * @return
     */
    public TitleLayout setTitleText(String titleText){
        if(titleText!=""){
            tv_title_middle.setText(titleText);
        }
        return this;
    }

    /**
     * 设置标题栏文字颜色
     * @return
     */
    public TitleLayout setTitleTextColor(){
        tv_title_middle.setTextColor(Color.WHITE);
        return this;
    }




    /**
     * 用于设置标题栏左边要显示的图片
     * @param resId  标题栏左边的图标的id，一般为返回图标
     * @return
     */
    public TitleLayout setLeftIco(int resId){
        iv_left_icon.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        iv_left_icon.setImageResource(resId);
        return this;
    }


    /**
     * 用于设置标题栏右边要显示的图片
     * @param resId 标题栏右边的图标id
     * @return
     */
    public TitleLayout setRightIco(int resId){
        iv_rightIco.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        iv_rightIco.setImageResource(resId);
        return this;
    }



    /**
     * 用于设置标题栏左边图片的单击事件
     * @param listener 传入的事件对象
     * @return
     */
    public TitleLayout setLeftIcoListening(View.OnClickListener listener){
        if(iv_left_icon.getVisibility() == View.VISIBLE){
            iv_left_icon.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 用于设置标题栏右边图片的单击事件
     * @param listener  传入的事件对象
     * @return
     */
    public TitleLayout setRightIcoListening(View.OnClickListener listener){
        if(iv_rightIco.getVisibility() == View.VISIBLE){
            iv_rightIco.setOnClickListener(listener);
        }
        return this;
    }

}