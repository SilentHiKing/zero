package com.hiking.zero.temp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hiking.base.util.DisplayUtil;

public class MyScrollView extends FrameLayout {
    public MyScrollView(@NonNull Context context) {
        this(context, null);
    }

    public MyScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    Scroller scroller;

    private void initView(Context context) {
        scroller = new Scroller(context);
    }

    public void start() {
        scroller.startScroll((int) getScrollX(), (int) getScrollY(), 0, DisplayUtil.dp2px(50), 2 * 100);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        } else {
            super.computeScroll();
        }
    }

    VelocityTracker mVelocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        int action = event.getActionMasked();
        int index = -1;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(event);
        }
        return super.onTouchEvent(event);
    }
}
