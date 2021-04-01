package com.hiking.base.view.nested;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

public class NestedChildView extends View implements NestedScrollingChild {
    public static final String TAG = "NestedChildView";

    private final NestedScrollingChildHelper childHelper = new NestedScrollingChildHelper(this);
    private float downY;

    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    public NestedChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        setNestedScrollingEnabled(true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int actionMasked = MotionEventCompat.getActionMasked(event);

        // 取第一个接触屏幕的手指Id
        final int pointerId = MotionEventCompat.getPointerId(event, 0);
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:

                // 取得当前的Y，并赋值给lastY变量
                downY = getPointerY(event, pointerId);
                // 找不到手指，放弃掉这个触摸事件流
                if (downY == -1) {
                    return false;
                }

                // 通知父View，开始滑动
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:

                // 获得当前手指的Y
                final float pointerY = getPointerY(event, pointerId);

                // 找不到手指，放弃掉这个触摸事件流
                if (pointerY == -1) {
                    return false;
                }

                // 计算出滑动的偏移量
                float deltaY = pointerY - downY;


                // 通知父View, 子View想滑动 deltaY 个偏移量，父View要不要先滑一下，然后把父View滑了多少，告诉子View一下
                // 下面这个方法的前两个参数为在x，y方向上想要滑动的偏移量
                // 第三个参数为一个长度为2的整型数组，父View将消费掉的距离放置在这个数组里面
                // 第四个参数为一个长度为2的整型数组，父View在屏幕里面的偏移量放置在这个数组里面
                // 返回值为 true，代表父View有消费任何的滑动.
                if (dispatchNestedPreScroll(0, (int) deltaY, consumed, offsetInWindow)) {

                    // 偏移量需要减掉被父View消费掉的,如果不减掉，子view和父view滑动的距离是不一样的
                    deltaY -= consumed[1];

                }

                // 上面的 (int)deltaY 会造成精度丢失，这里把精度给舍弃掉
                if (Math.floor(Math.abs(deltaY)) == 0) {
                    deltaY = 0;
                }

                // 这里移动子View，下面的min,max是为了控制边界，避免子View越界
                setY(Math.min(Math.max(getY() + deltaY, 0), ((View) getParent()).getHeight() - getHeight()));


                break;
        }
        return true;
    }

    /**
     * 这个方法通过pointerId获取pointerIndex,然后获取Y
     */
    private float getPointerY(MotionEvent event, int pointerId) {
        final int pointerIndex = MotionEventCompat.findPointerIndex(event, pointerId);
        if (pointerIndex < 0) {
            return -1;
        }
        return MotionEventCompat.getY(event, pointerIndex);
    }

    /**
     * 设置事件分发允许
     *
     * @param enabled
     */
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        Log.d(TAG, String.format("setNestedScrollingEnabled , enabled = %b", enabled));
        childHelper.setNestedScrollingEnabled(enabled);
    }

    /**
     * 判断事件分发
     *
     * @return
     */
    @Override
    public boolean isNestedScrollingEnabled() {
        Log.d(TAG, "isNestedScrollingEnabled");
        return childHelper.isNestedScrollingEnabled();
    }

    /**
     * 开始滑动前
     *
     * @param axes
     * @return
     */
    @Override
    public boolean startNestedScroll(int axes) {
        Log.d(TAG, String.format("startNestedScroll , axes = %d", axes));
        return childHelper.startNestedScroll(axes);
    }

    /**
     * 停止滑动
     */
    @Override
    public void stopNestedScroll() {
        Log.d(TAG, "stopNestedScroll");
        childHelper.stopNestedScroll();
    }

    /**
     * 判断是否滑动父类
     *
     * @return
     */
    @Override
    public boolean hasNestedScrollingParent() {
        Log.d(TAG, "hasNestedScrollingParent");
        return childHelper.hasNestedScrollingParent();
    }

    /**
     * 回调中获取父类消费了的距离
     *
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param offsetInWindow
     * @return
     */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        final boolean b = childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
        return b;
    }

    /**
     * 也是获取值
     *
     * @param dx
     * @param dy
     * @param consumed
     * @param offsetInWindow
     * @return
     */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        final boolean b = childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
        return b;
    }

    /**
     * filing的时候获取
     *
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, String.format("dispatchNestedFling , velocityX = %f, velocityY = %f, consumed = %b", velocityX, velocityY, consumed));
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.d(TAG, String.format("dispatchNestedPreFling , velocityX = %f, velocityY = %f", velocityX, velocityY));
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }
}