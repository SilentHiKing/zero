package com.hiking.base.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Log工具，根据build任务决定log级别
 */
public final class TLog {

    private static final String DEFAULT_TAG = "Hello";


    private TLog() {
    }

    public static void v(String content) {
        v(null, content);
    }

    public static void v(String tag, String content) {
        v(tag, content, null);
    }

    public static void v(String tag, String content, Throwable tr) {
        String gt = generateTag(Log.VERBOSE, tag);
        if (!TextUtils.isEmpty(gt)) {
            Log.v(gt, content, tr);
        }
    }

    public static void d(String content) {
        d(null, content);
    }

    public static void d(String tag, String content) {
        d(tag, content, null);
    }

    public static void d(String tag, String content, Throwable tr) {
        String gt = generateTag(Log.DEBUG, tag);
        if (!TextUtils.isEmpty(gt)) {
            Log.d(gt, content, tr);
        }
    }

    public static void i(String content) {
        i(null, content);
    }

    public static void i(String tag, String content) {
        i(tag, content, null);
    }

    public static void i(String tag, String content, Throwable tr) {
        String gt = generateTag(Log.INFO, tag);
        if (!TextUtils.isEmpty(gt)) {
            Log.i(gt, content, tr);
        }
    }

    public static void w(String content) {
        w(null, content);
    }

    public static void w(String tag, String content) {
        w(tag, content, null);
    }

    public static void w(String tag, String content, Throwable tr) {
        String gt = generateTag(Log.WARN, tag);
        if (!TextUtils.isEmpty(gt)) {
            Log.w(gt, content, tr);
        }
    }

    public static void e(String content) {
        e(null, content);
    }

    public static void e(String tag, String content) {
        e(tag, content, null);
    }

    public static void e(String tag, String content, Throwable tr) {
        String gt = generateTag(Log.ERROR, tag);
        if (!TextUtils.isEmpty(gt)) {
            Log.e(gt, content, tr);
        }
    }

    private static String generateTag(int logLevel, String tag) {
        if (getTagLevel() > logLevel) {
            return null;
        }
        if (!TextUtils.isEmpty(tag)) {
            return tag;
        }
        return generateTag();
    }


    private static String generateTag() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[7];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    private static int getTagLevel() {
        if (isDebug()) {
            return Log.VERBOSE;
        }
        return Log.ERROR;
    }

    public static boolean isDebug() {
        return true;
    }

}
