package com.jinke.driverhealth.activity.alcohol;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;
import com.jinke.driverhealth.utils.CalendarUtil;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.views.TitleLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AlcoholActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AlcoholActivity";


    private InputStream mInputStream;    //输入流，用来接收蓝牙数据
    private BluetoothDevice mRemoteBTDevice = null;     //蓝牙设备
    private BluetoothSocket socket = null;      //蓝牙通信socket

    private int threshold = 20, flag = 0;
    private Button connectBTDevice, thresholdValueAdd, thresholdValueSub;
    private TextView mAlcoholConcentration, mToday, mThreshold;
    private ImageView mWarnImg;

    boolean bRun = true;
    boolean bThread = false;
    //酒精测量值
    private int h;
    //获取本地蓝牙适配器，即蓝牙设备
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter nativeMachineBTAdapter;
    //测量事件
    private String currentTime = CalendarUtil.getNowFormatCalendar("yyyy-MM-dd HH:mm");
    /**
     * 接收酒精浓度显示的函数
     * 酒精浓度数据显示
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    byte buffer[] = (byte[]) msg.obj;
                    /*接收到数据后显示*/
                    refreshView(buffer);
                    break;
                default:
                    break;
            }
        }

        private void refreshView(byte[] buffer) {
            h = (buffer[0]);
            if (h >= threshold) {

                //检测数据超过阈值，颜色变红，并且提示是否需要拨打电话
                mAlcoholConcentration.setTextColor(Color.parseColor("#DD6B55"));
                if (flag == 0) {
                    //警报提示
                    new SweetAlertDialog(AlcoholActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("是否拨打紧急电话")
                            .setConfirmText("Yes,do it!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:120"));
                                    startActivity(intent);
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
                mWarnImg.setVisibility(View.VISIBLE);
                flag++;
            } else {
                mAlcoholConcentration.setTextColor(Color.parseColor("#FFFFFFFF"));
                mWarnImg.setVisibility(View.INVISIBLE);
            }
            mAlcoholConcentration.setText(h + " %");
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol);   //设置画面为主画面 main.xml
        //检测酒精+隐藏系统自带标题
        hideActionBar("检测今日酒精浓度");
        initView();
        initBT();
    }


    /**
     * 初始化蓝牙。
     * 首先检查设备是否具有蓝牙功能，然后检查是否打开，如果没有，则提示打开蓝牙
     */
    private void initBT() {
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        nativeMachineBTAdapter = mBluetoothManager.getAdapter();
//        nativeMachineBTAdapter= BluetoothAdapter.getDefaultAdapter(); //outmoded way
        if (nativeMachineBTAdapter == null) {
            Toast.makeText(AlcoholActivity.this, "设备不支持蓝牙功能", Toast.LENGTH_LONG).show();
        } else if (!nativeMachineBTAdapter.isEnabled()) {//判断蓝牙是否开启
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //请求打开蓝牙
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Log.d(TAG, "open bluetooth success");
                        }
                    }).launch(enableBtIntent);
        }
    }

    private void initView() {
        connectBTDevice = findViewById(R.id.connect_bt);
        connectBTDevice.setOnClickListener(this);

        mAlcoholConcentration = findViewById(R.id.alcohol_concentration_txt);

        mToday = findViewById(R.id.alcohol_concentration_today);
        mToday.setText("今日日期：" + currentTime);

        mThreshold = findViewById(R.id.alcohol_concentration_threshold);
        mThreshold.setText("阈值：" + threshold + " %");

        thresholdValueAdd = findViewById(R.id.add_threshold);
        thresholdValueAdd.setOnClickListener(this);

        thresholdValueSub = findViewById(R.id.sub_threshold);
        thresholdValueSub.setOnClickListener(this);

        mWarnImg = findViewById(R.id.alcohol_concentration_warn);
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
            case R.id.connect_bt:
                connectBtDevice();
                break;
            case R.id.add_threshold:
                addThreshold();
                break;
            case R.id.sub_threshold:
                subThreshold();
                break;
            default:
                break;
        }
    }


    /**
     * 阈值+
     */

    private void addThreshold() {
        mThreshold.setText("阈值：" + ++threshold + " %");
        String str = "a";
        byte[] fasong1 = str.getBytes();
        if (socket != null) {
            try {
                OutputStream os1 = socket.getOutputStream();   //蓝牙连接输出流
                os1.write(fasong1);
            } catch (IOException e) {
            }
        } else {
            Toast.makeText(this, "未连接蓝牙设备", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 阈值-
     */
    private void subThreshold() {
        mThreshold.setText("阈值：" + --threshold + " %");
        String str = "b";
        byte[] fasong1 = str.getBytes();
        if (socket != null) {
            try {
                OutputStream os1 = socket.getOutputStream();   //蓝牙连接输出流
                os1.write(fasong1);
            } catch (IOException e) {
            }
        } else {
            Toast.makeText(this, "未连接蓝牙设备", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 连接蓝牙设备,并开始接受数据
     */
    private void connectBtDevice() {


        //如果蓝牙服务不可用则提示
        if (nativeMachineBTAdapter.isEnabled() == false) {
            Toast.makeText(AlcoholActivity.this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
            return;
        }

        // 得到蓝牙设备句柄
        mRemoteBTDevice = nativeMachineBTAdapter.getRemoteDevice(Config.REMOTE_DEVICE_MAC);
        // 用服务号得到socket
        try {
            socket = mRemoteBTDevice.createRfcommSocketToServiceRecord(UUID.fromString(Config.DEVICE_UUID));
            if (socket == null) {
                Log.e(TAG, "connect BL device fail");
            }
        } catch (IOException e) {
            Log.e(TAG, "socket=null");
            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
        }
        //连接socket
        try {
            socket.connect();
            Toast.makeText(this, "连接" + mRemoteBTDevice.getName() + "成功！", Toast.LENGTH_SHORT).show();
            //	btn.setText("断开");
        } catch (IOException e) {
            Log.e(TAG, "socket connect fail");
            try {
                Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                socket.close();
                socket = null;
            } catch (IOException ee) {
                Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "socket close fail");
            }
            return;
        }

        //打开接收线程
        try {
            mInputStream = socket.getInputStream();   //得到蓝牙数据输入流
        } catch (IOException e) {
            Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (bThread == false) {
            ReadThread.start();
            bThread = true;
        } else {
            bRun = true;
        }

    }

    /*接收数据线程*/
    private Thread ReadThread = new Thread() {
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            bRun = true;

            while (true) {
                try {
                    while (mInputStream.available() == 0) {
                        while (bRun == false) {
                        }
                    }
                    /*在采集单个数据的时候把while(true给去掉)*/
                    /*读入数据*/
                    mInputStream.read(buffer);

                    /*通知界面*/
                    Message message = new Message();
                    message.what = 2;
                    message.obj = buffer;
                    mHandler.sendMessage(message);

                } catch (IOException e) {
                }
            }
        }
    };


    /**
     * 关闭程序掉用处理部分
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        threshold = 20;
        /* 关闭连接socket */
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        //	_bluetooth.disable();  //关闭蓝牙服务
    }

    /**
     * 调用onBackPressed()方法，点击返回键返回数据给上一个Activity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("alcohol", h);
        intent.putExtra("alcoholCreateTime", currentTime);
        Log.d(TAG, "酒精测量值 --> " + h);
        setResult(1, intent);

        super.onBackPressed();

        //返回数据后结束当前活动
        finish();
    }
}
// Android App开发进阶与项目实战

/**
 * 蓝牙使用步骤
 * 1、初始化蓝牙：通过BlueToothManger获取BlueToothAdapter
 * 2、判断蓝牙是否开启：BlueToothAdapter.isEnabled()
 * 3、搜索设备：
 * ①特定设备（只扫描含有特定 UUID Service 的蓝牙设备）
 * ②全部设备
 * /
 * <p>
 * /**
 * 蓝牙搜索设备。搜索之前我们需要判断是否正在搜索，如果正在搜索则取消搜索后再搜索
 * <p>
 * 搜索动作是个异步的过程，startDiscovery方法并不直接返回搜索发现的设备结果，
 * 而是通过广播BluetoothDevice.ACTION_FOUND返回新发现的蓝牙设备
 * <p>
 * 需要注册一个蓝牙搜索结果的广播接收器，
 * 在接收器中解析蓝牙设备信息，
 * 再把新设备添加到蓝牙设备列表。
 */