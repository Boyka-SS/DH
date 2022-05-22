package com.jinke.driverhealth.activity.contactor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.adapters.ContactorAdapter;
import com.jinke.driverhealth.data.db.beans.Contactor;
import com.jinke.driverhealth.interfaces.ApplyPermissionCallback;
import com.jinke.driverhealth.utils.PermissionUtil;
import com.jinke.driverhealth.viewmodels.ContactorViewModel;
import com.jinke.driverhealth.views.TitleLayout;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ContactorActivity extends AppCompatActivity {
    private static final String TAG = "ContactorActivity";

    private SwipeRecyclerView mSwipeRecyclerView;
    private ContactorViewModel mContactorViewModel;

    private static final int PERMISS_CONTACT = 1;


    private List<Contactor> mContactors = new ArrayList<>();
    private ContactorAdapter mContactorAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactor);

        hideActionBar("联系人管理");
        applyForPermission(ContactorActivity.this);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * android6.0 以后 必须 动态 申请通讯录权限
     */
    private void applyForPermission(Activity activity) {
        String[] permissList = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_CONTACTS};

        PermissionUtil.addPermissByPermissionList(activity, permissList, PERMISS_CONTACT, new ApplyPermissionCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void success() {
                getContactorsData();
            }

            @Override
            public void fail() {
                //TODO 拒绝授权
            }
        });
    }

//永不删除，学习 menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //getMenuInflater()获得MenuIflater对象，inflate()方法给当前活动创建菜单
//        getMenuInflater().inflate(R.menu.contactot_op, menu);
//        //true 代表允许创建的菜单显示出来
//        return true;
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.contactor_add:
//                //跳转 系统 添加联系人界面
//                navigativeToAddContactorPage();
//                break;
//            case R.id.contactor_refresh:
//                //todo 刷新列表获取新的联系人列表
//                break;
//            default:
//                break;
//        }
//        return true;
//    }


    //跳转 系统 添加联系人界面
    private void navigativeToAddContactorPage() {
        Intent addIntent = new Intent(Intent.ACTION_INSERT, Uri.withAppendedPath(Uri.parse("content://com.android.contacts"), "contacts"));
        addIntent.setType("vnd.android.cursor.dir/person");
        addIntent.setType("vnd.android.cursor.dir/contact");
        addIntent.setType("vnd.android.cursor.dir/raw_contact");
        startActivity(addIntent);
    }

    /**
     * 滑动  recyclerView
     *
     * @param contactors
     */
    private void initAdapter(List<Contactor> contactors) {
        mContactorAdapter = new ContactorAdapter(contactors);

        mSwipeRecyclerView = findViewById(R.id.contactor_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mSwipeRecyclerView.setLayoutManager(linearLayoutManager);


        // 设置监听器，创建菜单
        mSwipeRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {

                // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;

                // 设置 menu 中的 item 宽高 字体 背景
                SwipeMenuItem modifyItem = new SwipeMenuItem(ContactorActivity.this)
                        .setBackground(R.color.light_blue)
                        .setText("修改")
                        .setTextSize(12)
                        .setTextColor(Color.parseColor("#ffffff"))
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(180);
                SwipeMenuItem deleteItem = new SwipeMenuItem(ContactorActivity.this)
                        .setBackground(R.color.red)
                        .setText("删除")
                        .setTextSize(12)
                        .setTextColor(Color.parseColor("#ffffff"))
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(180);

                SwipeMenuItem setIsFirstManToContact = new SwipeMenuItem(ContactorActivity.this)
                        .setBackground(R.color.deep_orange)
                        .setText("设为第一联系人")
                        .setTextSize(12)
                        .setTextColor(Color.parseColor("#ffffff"))
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(250);

                rightMenu.addMenuItem(modifyItem);
                rightMenu.addMenuItem(setIsFirstManToContact);
                rightMenu.addMenuItem(deleteItem);
            }
        });
        // 菜单点击监听
        mSwipeRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                Contactor contactor = mContactors.get(position);
                if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                    switch (menuPosition) {
                        case 0:
                            //modify
                            mContactorViewModel.modifyContactorInfo(ContactorActivity.this, contactor.sys_id, contactor.lookUpKey);
                            break;
                        case 1:
                            //setIsFirstManToContact
                            mContactorViewModel.insertFirstManToContact(contactor, new OpCallback() {
                                @Override
                                public void success() {
                                    new SweetAlertDialog(ContactorActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setContentText("设置成功")
                                            .show();
                                }

                                @Override
                                public void fail() {

                                }
                            });
                            break;
                        case 2:
                            //delete
                            mContactorViewModel.deleteContactorByLookUpKey(ContactorActivity.this, contactor.lookUpKey, new OpCallback() {
                                @Override
                                public void success() {
                                    mContactorAdapter.removeData(position);
                                    new SweetAlertDialog(ContactorActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setContentText("删除成功")
                                            .show();

                                }

                                @Override
                                public void fail() {
                                    // update fail
                                }
                            });
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        // recyclerView-item 监听点击事件 点击 前往电话拨打页面
        mSwipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mContactors.get(position).phone));
                startActivity(intent);
            }
        });
        // add  FooterView。
        View footerView = getLayoutInflater().inflate(R.layout.contactor_list_footerview, mSwipeRecyclerView, false);
        mSwipeRecyclerView.addFooterView(footerView);

        mSwipeRecyclerView.setAdapter(mContactorAdapter);
    }

    /**
     * 获取系统联系人数据
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getContactorsData() {
        mContactorViewModel = new ViewModelProvider(this).get(ContactorViewModel.class);
        mContactorViewModel.loadAllContactors(ContactorActivity.this, getContentResolver()).observe(this, new Observer<List<Contactor>>() {
            @Override
            public void onChanged(List<Contactor> contactors) {
                if (!contactors.isEmpty()) {
                    mContactors = contactors;
                    initAdapter(contactors);
                }
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

    public interface OpCallback {
        void success();

        void fail();
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

