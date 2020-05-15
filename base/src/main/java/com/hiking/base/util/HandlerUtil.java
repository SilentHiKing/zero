package com.hiking.base.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;


public class HandlerUtil {
    // 连接主线程的Handler
    private static Handler sMainThreadHandler;
    // 连接工作线程的Handler
    private static Handler sWorkThreadHandler;

    /**
     * 获取主线程Handler
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        if (sMainThreadHandler == null) {
            synchronized (HandlerUtil.class) {
                if (sMainThreadHandler == null) {
                    sMainThreadHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return sMainThreadHandler;
    }

    /**
     * 获取工作线程Handler
     *
     * @return
     */
    public static Handler getWorkThreadHandler() {
        if (sWorkThreadHandler == null) {
            synchronized (HandlerUtil.class) {
                if (sWorkThreadHandler == null) {
                    HandlerThread workThread = new HandlerThread("Work Thread");
                    workThread.start();
                    sWorkThreadHandler = new Handler(workThread.getLooper());
                }
            }
        }
        return sWorkThreadHandler;
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}
