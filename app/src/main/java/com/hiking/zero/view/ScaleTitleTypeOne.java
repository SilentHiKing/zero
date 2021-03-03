package com.hiking.zero.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hiking.base.util.TLog;
import com.hiking.zero.R;

public class ScaleTitleTypeOne extends LinearLayout {
    TextView tv_title;
    int mWidth;
    int mTitleWidth;
    ValueAnimator mAnimator;
    boolean showOpen;

    public ScaleTitleTypeOne(Context context) {
        this(context, null);
    }

    public ScaleTitleTypeOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleTitleTypeOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.scale_title_type_one, this);
        tv_title = findViewById(R.id.tv_title);
        post(new Runnable() {
            @Override
            public void run() {
                mWidth = getMeasuredWidth();
                mTitleWidth = tv_title.getMeasuredWidth();
                changeWidth(tv_title,mTitleWidth);
                TLog.d("mWidth=" + mWidth + "mTitleWidth=" + mTitleWidth);
                if (!showOpen) {
                    changeWidth(ScaleTitleTypeOne.this, mWidth - mTitleWidth);
                }
            }
        });
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(200L);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                changeWidth(ScaleTitleTypeOne.this, (int) value);
            }
        });
    }

    private void changeWidth(View v, int value) {
        ViewGroup.LayoutParams p = v.getLayoutParams();
        p.width = value;
        v.setLayoutParams(p);
    }


    public void open() {
        if (isOpen()) {
            return;
        }
        cancelAnimator();
        mAnimator.setFloatValues(mWidth - mTitleWidth, mWidth);
        mAnimator.start();

    }

    public void close() {
        if (!isOpen()) {
            return;
        }
        cancelAnimator();
        mAnimator.setFloatValues(mWidth, mWidth - mTitleWidth);
        mAnimator.start();
    }

    public boolean isOpen() {
        return getWidth() == mWidth;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimator();
    }

    private void cancelAnimator() {
        if (mAnimator != null && mAnimator.isStarted()) {
            mAnimator.cancel();
        }
    }


}
