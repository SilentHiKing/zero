package com.hiking.zero.flutter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.hiking.base.util.TLog;
import com.hiking.zero.R;

public class Test1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        View viewById = findViewById(R.id.fl_content);
        View bt = findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateIfLandscape(viewById);
            }
        });
    }

    public static void intentFor(Context context) {
        Intent intent = new Intent(context, Test1Activity.class);
        context.startActivity(intent);
    }


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
                    TLog.d("nihao");




                    /*view.setPivotX(view.getWidth() / 2);
                    view.setPivotY(view.getHeight() / 2);*/

                }
            });
        }
    }
}