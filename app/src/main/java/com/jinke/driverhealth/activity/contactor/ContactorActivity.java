package com.jinke.driverhealth.activity.contactor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.db.beans.Contactor;
import com.jinke.driverhealth.interfaces.ApplyPermissionCallback;
import com.jinke.driverhealth.utils.PermissionUtil;
import com.jinke.driverhealth.viewmodels.ContactorViewModel;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

public class ContactorActivity extends AppCompatActivity {


    private static final String TAG = "ContactorActivity";

    private SwipeRecyclerView mSwipeRecyclerView;

    private ContactorViewModel mContactorViewModel;

    private static final int PERMISS_CONTACT = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactor);

        applyForPermission(ContactorActivity.this);
    }

    /**
     * 申请通讯录权限
     */
    private void applyForPermission(Activity activity) {
        String[] permissList = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE};
        PermissionUtil.addPermissByPermissionList(activity, permissList, PERMISS_CONTACT, new ApplyPermissionCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void success() {
                getContactorsData();
                Toast.makeText(activity, "同意授权", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void fail() {
                //TODO 拒绝授权
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater()获得MenuIflater对象，inflate()方法给当前活动创建菜单
        getMenuInflater().inflate(R.menu.contactot_op, menu);
        //true 代表允许创建的菜单显示出来
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contactor_add:
                //跳转 系统 添加联系人界面
                navigativeToAddContactorPage();
                break;
            case R.id.contactor_refresh:
                //todo 刷新列表获取新的联系人列表
                break;
            default:
                break;
        }
        return true;
    }


    //跳转 系统 添加联系人界面
    private void navigativeToAddContactorPage() {
        Intent addIntent = new Intent(Intent.ACTION_INSERT, Uri.withAppendedPath(Uri.parse("content://com.android.contacts"), "contacts"));
        addIntent.setType("vnd.android.cursor.dir/person");
        addIntent.setType("vnd.android.cursor.dir/contact");
        addIntent.setType("vnd.android.cursor.dir/raw_contact");
        startActivity(addIntent);
    }

    private void initAdapter() {
        mSwipeRecyclerView = findViewById(R.id.contactor_list);
    }

    /**
     * 获取系统联系人数据
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getContactorsData() {

        mContactorViewModel = new ViewModelProvider(this).get(ContactorViewModel.class);
        mContactorViewModel.loadAllContactors(getContentResolver()).observe(this, new Observer<List<Contactor>>() {
            @Override
            public void onChanged(List<Contactor> contactors) {
                //
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        //判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                break;
            }
        }
        //同意权限做的处理,开启服务提交通讯录
        if (hasAllGranted) {
            getContactorsData();
            Toast.makeText(this, "同意授权", Toast.LENGTH_SHORT).show();
        } else {    //拒绝授权做的处理，弹出弹框提示用户授权
            PermissionUtil.dealwithPermiss(ContactorActivity.this, permissions[0]);
        }
    }
}
/**
 * android 添加 menu:
 * https://blog.csdn.net/qq_38124598/article/details/78504448
 * https://www.cnblogs.com/linfenghp/p/5464016.html
 * android 跳转 添加联系人 界面
 * 一定要动态请求权限
 * https://www.jianshu.com/p/6a6a846b5363
 * https://www.jb51.net/article/107728.htm
 * https://blog.csdn.net/xingnan4414/article/details/76686283
 */

