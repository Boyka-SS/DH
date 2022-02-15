package com.jinke.driverhealth.activity.alcohol;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.utils.Config;
import com.jinke.driverhealth.views.TitleLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class AlcoholActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AlcoholActivity";
    //宏定义查询设备句柄
    private final static int REQUEST_CONNECT_DEVICE = 1;


    private InputStream mInputStream;    //输入流，用来接收蓝牙数据
    private BluetoothDevice _device = null;     //蓝牙设备
    private BluetoothSocket _socket = null;      //蓝牙通信socket
    boolean _discoveryFinished = false;

    private Button connectBTDevice, thresholdValueAdd, thresholdValueSub;
    private ArcProgress mArcProgress;
    private TextView wendutext;

    boolean bRun = true;
    boolean bThread = false;
    int t, h;
    //获取本地蓝牙适配器，即蓝牙设备
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter nativeMachineBTAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol);   //设置画面为主画面 main.xml
        //检测酒精+隐藏系统自带标题
        hideActionBar("检测今日酒精浓度");

        initView();
        initBT();

        /**
         * 蓝牙搜索设备。搜索之前我们需要判断是否正在搜索，如果正在搜索则取消搜索后再搜索
         *
         * 搜索动作是个异步的过程，startDiscovery方法并不直接返回搜索发现的设备结果，
         * 而是通过广播BluetoothDevice.ACTION_FOUND返回新发现的蓝牙设备
         *
         * 需要注册一个蓝牙搜索结果的广播接收器，
         * 在接收器中解析蓝牙设备信息，
         * 再把新设备添加到蓝牙设备列表。
         */
        if (!nativeMachineBTAdapter.isDiscovering()) {
            nativeMachineBTAdapter.startDiscovery();
        }

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
            Log.d(TAG, "native machine don`t support BlueTooth");
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

        mArcProgress = findViewById(R.id.arc_progress);

        thresholdValueAdd = findViewById(R.id.add_threshold);
        thresholdValueAdd.setOnClickListener(this);

        thresholdValueSub = findViewById(R.id.sub_threshold);
        thresholdValueSub.setOnClickListener(this);
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
        String str = "a";
        byte[] fasong1 = str.getBytes();
        try {
            OutputStream os1 = _socket.getOutputStream();   //蓝牙连接输出流

            os1.write(fasong1);
        } catch (IOException e) {
        }
    }

    /**
     * 阈值-
     */
    private void subThreshold() {
        String str = "b";
        byte[] fasong1 = str.getBytes();
        try {
            OutputStream os1 = _socket.getOutputStream();   //蓝牙连接输出流
            os1.write(fasong1);
        } catch (IOException e) {
        }

    }

    /**
     * 连接蓝牙设备
     */
    private void connectBtDevice() {

        //如果蓝牙服务不可用则提示
        if (nativeMachineBTAdapter.isEnabled() == false) {
            Toast.makeText(AlcoholActivity.this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
            return;
        }

        // 得到蓝牙设备句柄
        _device = nativeMachineBTAdapter.getRemoteDevice(Config.REMOTE_DEVICE_MAC);
        Log.d(TAG, "remote name --> " + _device.getName());
        Log.d(TAG, "remote address --> " + _device.getAddress());
        // 用服务号得到socket
        try {
            _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(Config.DEVICE_UUID));
        } catch (IOException e) {
            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
        }
        //连接socket
        try {
            _socket.connect();
            Toast.makeText(this, "连接" + _device.getName() + "成功！", Toast.LENGTH_SHORT).show();
            //	btn.setText("断开");
        } catch (IOException e) {
            try {
                Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                _socket.close();
                _socket = null;
            } catch (IOException ee) {
                Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        //打开接收线程
        try {
            mInputStream = _socket.getInputStream();   //得到蓝牙数据输入流
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
    Thread ReadThread = new Thread() {

        @Override
        public void run() {
            int num = 0;
            byte[] buffer = new byte[1024];
            bRun = true;
            /*接收线程*/
            while (true) {
                try {
                    while (mInputStream.available() == 0) {
                        while (bRun == false) {
                        }
                    }
                    /*在采集单个数据的时候把while(true给去掉)*/
                    while (true) {
                        /*读入数据*/
                        num = mInputStream.read(buffer);
                        /*通知界面*/
                        Message message = new Message();
                        message.what = 2;
                        message.obj = buffer;
                        mHandler.sendMessage(message);


                    }
                } catch (IOException e) {
                }
            }
        }
    };

    /**
     * 接收温湿度显示的函数
     * <p>
     * 温湿度数据显示
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    byte buffer[] = (byte[]) msg.obj;
                    /*接收到数据后显示*/
                    refreshView(buffer);
                    break;
            }
        }

        private void refreshView(byte[] buffer) {
            h = (buffer[0]);
            wendutext.setText(h + "%");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
//        //需要过滤多个动作，则调用IntentFilter对象的addAction添加新动作
//        IntentFilter discoveryFilter = new IntentFilter();
//        discoveryFilter.addAction(BluetoothDevice.ACTION_FOUND);
//        //注册搜索结果的接收器
//        registerReceiver(discoveryReceiver, discoveryFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销蓝牙设备搜索的广播接收器
//        unregisterReceiver(discoveryReceiver);
    }

    /**
     * 关闭程序掉用处理部分
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        /* 关闭连接socket */
        if (_socket != null) {
            try {
                _socket.close();
            } catch (IOException e) {
            }
        }
        //	_bluetooth.disable();  //关闭蓝牙服务
    }

    // 蓝牙设备的搜索结果通过广播返回
    private BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive action = " + action);
            // 获得已经搜索到的蓝牙设备
            if (action.equals(BluetoothDevice.ACTION_FOUND)) { // 发现新的蓝牙设备
                _device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) { // 搜索完毕
                //mHandler.removeCallbacks(mRefresh); // 需要持续搜索就要注释这行
                Log.d(TAG, "search end");
            }
        }
    };
}
// Android App开发进阶与项目实战
/**
 * 蓝牙使用步骤
 * 1、初始化蓝牙：通过BlueToothManger获取BlueToothAdapter
 * 2、判断蓝牙是否开启：BlueToothAdapter.isEnabled()
 * 3、搜索设备：
 * ①特定设备（只扫描含有特定 UUID Service 的蓝牙设备）
 * ②全部设备
 */
