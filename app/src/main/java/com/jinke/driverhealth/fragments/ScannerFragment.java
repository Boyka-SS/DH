package com.jinke.driverhealth.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import java.util.concurrent.ExecutionException;

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


public class ScannerFragment extends Fragment {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView mPreviewView;


    private Camera mCamera;
    private ProcessCameraProvider mCameraProvider;
    private ImageCapture mImageCapture;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scanner, container, false);

        mPreviewView = view.findViewById(R.id.previewView);
        Button start = view.findViewById(R.id.start);
        Button end = view.findViewById(R.id.end);


        //开始监测
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera(view);
            }
        });
        //结束监测
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

    private void startCamera(View view) {

        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());

        cameraProviderFuture.addListener(() -> {
            try {
                mCameraProvider = cameraProviderFuture.get();

                bindPreview(mCameraProvider, view);
                ImageCapture.OutputFileOptions outputFileOptions =
                        new ImageCapture.OutputFileOptions.Builder(new File("/")).build();

                mImageCapture.takePicture(
                        outputFileOptions,
                        ContextCompat.getMainExecutor(getActivity()) ,
                        new ImageCapture.OnImageSavedCallback() {
                            @Override
                            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                                Toast.makeText(getActivity(),"获取照片失败",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(@NonNull ImageCaptureException exception) {
                                Toast.makeText(getActivity(),"获取照片失败",Toast.LENGTH_SHORT).show();
                            }
                        });
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


        mCamera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, mImageCapture, preview);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}