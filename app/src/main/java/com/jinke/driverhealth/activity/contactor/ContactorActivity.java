package com.jinke.driverhealth.activity.contactor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

public class ContactorActivity extends AppCompatActivity {


    private static final String TAG = "ContactorActivity";

    private SwipeRecyclerView mSwipeRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactor);

        initAdapter();
        getContactorsData();
    }

    /**
     * 获取 系统联系人 数据
     * <p>
     * 系统数据 ——> 本地DB
     */
    private void getContactorsData() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater()获得MenuIflater对象，inflate()方法给当前活动创建菜单
        getMenuInflater().inflate(R.menu.contactot_op, menu);
        //true 代表允许创建的菜单显示出来
        return true;
    }

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

    private void  initAdapter() {
        mSwipeRecyclerView = findViewById(R.id.contactor_list);


    }

}
/**
 * android 添加 menu:
 * https://blog.csdn.net/qq_38124598/article/details/78504448
 * https://www.cnblogs.com/linfenghp/p/5464016.html
 * android 跳转 添加联系人 界面
 * https://www.jb51.net/article/107728.htm
 * https://blog.csdn.net/xingnan4414/article/details/76686283
 */

