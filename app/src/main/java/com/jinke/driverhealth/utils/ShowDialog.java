package com.jinke.driverhealth.utils;

import android.content.Context;

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

}
