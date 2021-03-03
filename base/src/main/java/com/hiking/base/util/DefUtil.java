package com.hiking.base.util;

import android.graphics.Color;

import java.util.Random;

public class DefUtil {
    Random mRandom;

    private DefUtil() {
        mRandom = new Random();
    }

    static class DefaultUtilHolder {
        static DefUtil INSTANCE = new DefUtil();
    }

    public static DefUtil getIns() {
        return DefaultUtilHolder.INSTANCE;
    }

    public int randomColor() {
        return Color.argb(mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
    }
}
