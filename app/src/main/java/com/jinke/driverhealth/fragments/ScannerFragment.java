package com.jinke.driverhealth.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.jinke.driverhealth.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView mPreviewView;

    private Executor executor = Executors.newSingleThreadExecutor();
    private Camera mCamera;
    private ProcessCameraProvider mCameraProvider;
    private ImageCapture mImageCapture;
    private Button mStart, mEnd;
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_scanner, container, false);

        mPreviewView = mView.findViewById(R.id.previewView);
        mStart = mView.findViewById(R.id.start);
        mEnd = mView.findViewById(R.id.end);

        startCamera(mView);
        //开始监测
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimerToTakePhoto.start();
            }
        });
        //结束监测
        mEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return mView;
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


        mCamera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, mImageCapture);


    }

    private String getBatchDirectoryName() {

        String app_folder_path = "";
        app_folder_path = Environment.getExternalStorageDirectory().toString() + "/images";
        File dir = new File(app_folder_path);
        if (!dir.exists() && !dir.mkdirs()) {

        }

        return app_folder_path;
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
    private CountDownTimer countDownTimerToTakePhoto = new CountDownTimer(6000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            String value = String.valueOf((int) (millisUntilFinished / 1000 - 1));
            mStart.setText("开始监测（" + value + "）s");
        }

        @Override
        public void onFinish() {
            mStart.setText("开始监测");

            SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            File file = new File(getBatchDirectoryName(), mDateFormat.format(new Date()) + ".jpg");

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
                                    //TODO:调用百度AI
                                    Toast.makeText(getActivity(), "图片保存成功", Toast.LENGTH_SHORT).show();
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
}