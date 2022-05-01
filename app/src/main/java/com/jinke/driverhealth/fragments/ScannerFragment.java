package com.jinke.driverhealth.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.jinke.driverhealth.R;
import com.jinke.driverhealth.data.network.baidu.beans.BehaviorMonitorData;
import com.jinke.driverhealth.data.network.baidu.worker.BehaviorMonitorWork;
import com.jinke.driverhealth.utils.CalendarUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <ul>
 *     <li>检测和访问相机 - 创建用于检查设备是否配有相机和请求访问权限的代码。</li>
 *     <li> 创建预览类 - 创建可扩展 SurfaceView 并实现 SurfaceHolder 接口的相机预览类。此类会预览相机拍摄的实时图片。</li>
 *     <li>构建预览布局 - 获取相机预览类后，创建一个视图布局，将预览功能和所需界面控件整合在一起。</li>
 *     <li>设置监听器以进行拍摄 - 为界面控件连接监听器，以开始拍摄图片或视频，从而响应用户操作（例如，按下按钮）。</li>
 *     <li>进行拍摄并保存文件 - 设置用于拍摄照片或视频并保存输出的代码。</li>
 *     <li>释放相机 - 在用完相机之后，您的应用必须正确地释放相机，以供其他应用使用。</li>
 * </ul>
 */

/**
 * fanlihao
 * 2022-4-30
 */

public class ScannerFragment extends Fragment {
    private static final String TAG = "ScannerFragment";

    private int REQUEST_CODE_PERMISSIONS = 10;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView mPreviewView;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private Executor executor = Executors.newSingleThreadExecutor();
    private Camera mCamera;
    private ProcessCameraProvider mCameraProvider;
    private ImageCapture mImageCapture;
    private Button mStart, mEnd;
    private View mView;
    private TextView mNoSmoke, mNoPhone, mNoBuckUp, mNoSteerWheel, mNoWarning, mNoMask;
    private TextView mMonitorTime;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_scanner, container, false);

        initView(mView);

        startCamera(mView);//start camera if permission has been granted by user
        //开始监测
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimerToTakePhoto.start();
            }
        });
        //结束监测
        mEnd.setOnClickListener(v -> {

        });
        String nowFormatCalendar = CalendarUtil.getNowFormatCalendar("yyyy-MM-dd HH:mm:ss");
        mMonitorTime.setText(nowFormatCalendar);

        return mView;
    }

    private void initView(@NonNull View view) {
        mPreviewView = view.findViewById(R.id.previewView);
        mStart = view.findViewById(R.id.start);
        mEnd = view.findViewById(R.id.end);

        mNoSmoke = view.findViewById(R.id.no_smoke);
        mNoPhone = view.findViewById(R.id.makephone);
        mNoBuckUp = view.findViewById(R.id.buckup);
        mNoSteerWheel = view.findViewById(R.id.steeringwheel);
        mNoWarning = view.findViewById(R.id.warning);
        mNoMask = view.findViewById(R.id.mask);

        mMonitorTime = view.findViewById(R.id.monitor_time);


    }

    private void startCamera(View view) {

        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());

        cameraProviderFuture.addListener(() -> {
            try {
                mCameraProvider = cameraProviderFuture.get();
                bindPreview(mCameraProvider, view);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(getActivity()));

    }

    @SuppressLint("RestrictedApi")
    private void bindPreview(ProcessCameraProvider cameraProvider, View view) {

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build();

        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());

        mImageCapture = new ImageCapture.Builder()
                .setTargetRotation(view.getDisplay().getRotation())
                .build();

        cameraProvider.unbindAll();
        mCamera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, mImageCapture);


    }

    private boolean allPermissionsGranted() {
        //check if req permissions have been granted
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * CountDownTimer 实现倒计时
     */
    private CountDownTimer countDownTimerToTakePhoto = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            mStart.setText("开始监测（" + value + "）s");
        }

        @Override
        public void onFinish() {
            mStart.setText("开始监测");
            SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            File file = new File(getBatchDirectoryName(getActivity()), mDateFormat.format(new Date()) + ".jpg");

            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();

            mImageCapture.takePicture(
                    outputFileOptions,
                    executor,
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            Log.d("path-->", String.valueOf(outputFileResults.getSavedUri()));
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "图片保存成功", Toast.LENGTH_SHORT).show();
                                    searchByBaiduAI(outputFileResults);
                                }
                            });
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            exception.printStackTrace();
                        }
                    });
        }
    };

    private void searchByBaiduAI(ImageCapture.OutputFileResults outputFileResults) {
        try {
            //本地文件路径
            Log.d(TAG, String.valueOf(outputFileResults.getSavedUri()));
            String filePath = String.valueOf(outputFileResults.getSavedUri()).substring(7);
            String param = imageToBase64(filePath);

            String token = getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("bmtoken", "");
            new BehaviorMonitorWork().requestBehaviorMonitorData(token, param, new Callback<BehaviorMonitorData>() {
                @Override
                public void onResponse(Call<BehaviorMonitorData> call, Response<BehaviorMonitorData> response) {
                    if (response.isSuccessful()) {
                        updateUI(response.body());
                    }
                }

                @Override
                public void onFailure(Call<BehaviorMonitorData> call, Throwable t) {
                    Toast.makeText(getActivity(), "Sorry，请求百度AI有误", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据返回的结果，更新UI
    private void updateUI(BehaviorMonitorData data) {
        if (data.getDriver_num() == 0) {
            Toast.makeText(getActivity(), "未检测到驾驶员", Toast.LENGTH_SHORT).show();
        } else {
            BehaviorMonitorData.PersonInfoDTO.AttributesDTO attributes = data.getPerson_info().get(0).getAttributes();
            BehaviorMonitorData.PersonInfoDTO.AttributesDTO.SmokeDTO smoke = attributes.getSmoke();
            if (smoke.getScore() > smoke.getThreshold()) {
                mNoSmoke.setText("检测异常");
                mNoSmoke.setTextColor(Color.parseColor("#DC143C"));
            }
            BehaviorMonitorData.PersonInfoDTO.AttributesDTO.CellphoneDTO cellphone = attributes.getCellphone();
            if (cellphone.getScore() > cellphone.getThreshold()) {
                mNoPhone.setText("检测异常");
                mNoPhone.setTextColor(Color.parseColor("#DC143C"));
            }
            BehaviorMonitorData.PersonInfoDTO.AttributesDTO.NotBucklingUpDTO bucklUp = attributes.getNot_buckling_up();
            if (bucklUp.getScore() > bucklUp.getThreshold()) {
                mNoBuckUp.setText("检测异常");
                mNoBuckUp.setTextColor(Color.parseColor("#DC143C"));
            }
            BehaviorMonitorData.PersonInfoDTO.AttributesDTO.BothHandsLeavingWheelDTO handsLeaveWheel = attributes.getBoth_hands_leaving_wheel();
            if (handsLeaveWheel.getScore() > handsLeaveWheel.getThreshold()) {
                mNoSteerWheel.setText("检测异常");
                mNoSteerWheel.setTextColor(Color.parseColor("#DC143C"));
            }
            BehaviorMonitorData.PersonInfoDTO.AttributesDTO.NotFacingFrontDTO faceFront = attributes.getNot_facing_front();
            if (faceFront.getScore() > faceFront.getThreshold()) {
                mNoWarning.setText("检测异常");
                mNoWarning.setTextColor(Color.parseColor("#DC143C"));
            }
            BehaviorMonitorData.PersonInfoDTO.AttributesDTO.NoFaceMaskDTO faceMask = attributes.getNo_face_mask();
            if (faceMask.getScore() > faceMask.getThreshold()) {
                mNoMask.setText("检测异常");
                mNoMask.setTextColor(Color.parseColor("#DC143C"));
            }
        }

    }

    public String getBatchDirectoryName(Context context) {

        String app_folder_path = "";
        app_folder_path =
                context.getFilesDir().getAbsolutePath() + "/images";
        File dir = new File(app_folder_path);
        if (!dir.exists() && !dir.mkdirs()) {

        }
        return app_folder_path;
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


}