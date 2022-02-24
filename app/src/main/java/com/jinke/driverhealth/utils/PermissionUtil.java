package com.jinke.driverhealth.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jinke.driverhealth.interfaces.ApplyPermissionCallback;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * android6.0后 动态申请 权限
 *
 * @author: fanlihao
 * @date: 2022/2/24
 */
public class PermissionUtil {


    /**
     * 动态权限
     */
    public static void addPermissByPermissionList(Activity activity, String[] permissions, int request, ApplyPermissionCallback callback) {
        //Android 6.0开始的动态权限，这里进行版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //未授权的权限集合
            ArrayList<String> mPermissionList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(activity, permissions[i])
                        != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }

            //非初次进入App且已授权
            if (mPermissionList.isEmpty()) {
                callback.success();
                Toast.makeText(activity, "已授权", Toast.LENGTH_SHORT).show();
            } else {
                //请求权限方法
                //将List转为数组
                String[] permissionsNew = mPermissionList.toArray(new String[mPermissionList.size()]);
                //这个触发下面onRequestPermissionsResult这个回调
                ActivityCompat.requestPermissions(activity, permissionsNew, request);
            }
        }
    }

    /**
     * 用户 拒绝 权限  引导
     *
     * @param context
     * @param permission
     */
    public static void dealwithPermiss(final Activity context, String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {

            new SweetAlertDialog(context)
                    .setTitleText("操作提示")
                    .setContentText("注意：当前缺少必要权限！\n请点击“设置”-“权限”-打开所需权限\n最后点击两次后退按钮，即可返回")
                    .setCancelText("取消")
                    .setConfirmText("去授权")
                    .setConfirmButtonBackgroundColor(Color.parseColor("#2469df"))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                            intent.setData(uri);

                            context.startActivity(intent);
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            Toast.makeText(context, "您拒绝了权限的申请", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();


        }
    }
}
