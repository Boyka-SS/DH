package com.jinke.driverhealth.activity.alcohol;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jinke.driverhealth.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class AlcoholActivity extends AppCompatActivity {

    private final static int REQUEST_CONNECT_DEVICE = 1;    //宏定义查询设备句柄

    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号

    private InputStream is;    //输入流，用来接收蓝牙数据
    Button lianjie, guanbi;
    BluetoothDevice _device = null;     //蓝牙设备
    BluetoothSocket _socket = null;      //蓝牙通信socket
    boolean _discoveryFinished = false;
    boolean bRun = true;
    boolean bThread = false;
    int t, h;
    TextView wendutext;
    //获取本地蓝牙适配器，即蓝牙设备
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();

    Button fazhijiabtn, fazhijianbtn;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol);   //设置画面为主画面 main.xml
        lianjie = (Button) findViewById(R.id.connectToBT);

        lianjie.setOnClickListener(new lianjie());
        wendutext = (TextView) findViewById(R.id.alcohol_concentration);

        fazhijiabtn = (Button) findViewById(R.id.add_threshold);
        fazhijianbtn = (Button) findViewById(R.id.sub_threshold);
        fazhijiabtn.setOnClickListener(new fazhijia());
        fazhijianbtn.setOnClickListener(new fazhijian());

        //如果打开本地蓝牙设备不成功，提示信息，结束程序
        if (_bluetooth == null) {
            Toast.makeText(this, "无法打开手机蓝牙，请确认手机是否有蓝牙功能！", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // 设置设备可以被搜索
        new Thread() {
            @Override
            public void run() {
                if (_bluetooth.isEnabled() == false) {
//                    _bluetooth.enable();
                    _bluetooth.enable();
                }
            }
        }.start();
    }


    class fazhijia implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String str = "a";
            byte[] fasong1 = str.getBytes();
            try {
                OutputStream os1 = _socket.getOutputStream();   //蓝牙连接输出流

                os1.write(fasong1);
            } catch (IOException e) {
            }

        }

    }


    class fazhijian implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String str = "b";
            byte[] fasong1 = str.getBytes();
            try {
                OutputStream os1 = _socket.getOutputStream();   //蓝牙连接输出流
                os1.write(fasong1);
            } catch (IOException e) {
            }

        }

    }


    class lianjie implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (_bluetooth.isEnabled() == false) {  //如果蓝牙服务不可用则提示
                Toast.makeText(AlcoholActivity.this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
                return;
            }


            //如未连接设备则打开DeviceListActivity进行设备搜索
            //	Button btn = (Button) findViewById(R.id.Button03);
            if (_socket == null) {
                Intent serverIntent = new Intent(AlcoholActivity.this, DeviceListActivity.class); //跳转程序设置
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
            } else {
                //关闭连接socket
                try {

                    is.close();
                    _socket.close();
                    _socket = null;
                    bRun = false;
                    //	btn.setText("连接");
                } catch (IOException e) {
                }
            }
            return;

        }


    }


    //接收活动结果，响应startActivityForResult()
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:     //连接结果，由DeviceListActivity设置返回
                // 响应返回结果
                if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                    // MAC地址，由DeviceListActivity设置返回
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙设备句柄
                    _device = _bluetooth.getRemoteDevice(address);

                    // 用服务号得到socket
                    try {
                        _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                    } catch (IOException e) {
                        Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                    //连接socket
                    //Button btn = (Button) findViewById(R.id.Button03);
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
                        is = _socket.getInputStream();   //得到蓝牙数据输入流
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
                break;
            default:
                break;
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
                    while (is.available() == 0) {
                        while (bRun == false) {
                        }
                    }
                    /*在采集单个数据的时候把while(true给去掉)*/
                    while (true) {
                        /*读入数据*/
                        num = is.read(buffer);
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


}