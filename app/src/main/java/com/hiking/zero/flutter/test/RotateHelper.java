package com.hiking.zero.flutter.test;

import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;


public class RotateHelper {

    public static void rotateIfLandscape(final View view) {
        Configuration cf = view.getContext().getResources().getConfiguration();
        //横屏
        if (cf.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    int width = view.getWidth();
                    int height = view.getHeight();

                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = height;
                    layoutParams.height = width;
                    view.setLayoutParams(layoutParams);

                    view.setPivotX(height);
                    view.setPivotY(0);
                    view.setTranslationX(-height);
                    view.setRotation(-90f);

                }
            });
        }
    }
}
