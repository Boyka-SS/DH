package com.jinke.driverhealth.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author: fanlihao
 * @date: 2022/2/7
 */

public class Executors {
    public static ExecutorService diskIO = java.util.concurrent.Executors.newSingleThreadExecutor();
    public static ExecutorService networkIO = java.util.concurrent.Executors.newFixedThreadPool(3);
    public static Executor mainThread = new MainThreadExecutor();

    private static class MainThreadExecutor implements Executor {
        Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            handler.post(runnable);
        }
    }
}