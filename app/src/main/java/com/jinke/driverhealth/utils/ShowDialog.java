package com.jinke.driverhealth.utils;

import android.content.Context;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author: fanlihao
 * @date: 2022/2/14
 */
public class ShowDialog {

    public static void onlyShowMessage(Context context, String tip) {
        new SweetAlertDialog(context)
                .setTitleText(tip)
                .show();
    }

    public static void showWarnDialog(Context context, String tip) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(tip)
                .setConfirmText("Yes,do it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Toast.makeText(context, "click yes", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();
    }

}
