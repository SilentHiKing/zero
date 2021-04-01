package com.hiking.base.view.nested;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;

public class NestedParentView  extends FrameLayout implements NestedScrollingParent {

    public static final String TAG = NestedParentView.class.getSimpleName();

    private NestedScrollingParentHelper parentHelper;

    public NestedParentView(Context context) {
        super(context);
    }

    public NestedParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parentHelper = new NestedScrollingParentHelper(this);
    }
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, String.format("onStartNestedScroll, child = %s, target = %s, nestedScrollAxes = %d", child, target, nestedScrollAxes));
        return true;
    }
    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, String.format("onNestedScrollAccepted, child = %s, target = %s, nestedScrollAxes = %d", child, target, nestedScrollAxes));
        parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.d(TAG, "onStopNestedScroll");
        parentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG, String.format("onNestedScroll, dxConsumed = %d, dyConsumed = %d, dxUnconsumed = %d, dyUnconsumed = %d", dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed));
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //dy，就是直接表示一次的偏移量，方向和坐标系的相同，而且每一次的
        // 应该移动的Y距离
        Log.i("onNestedPreScroll","===================");
        Log.i("onNestedPreScroll","dy==="+dy);
        Log.i("onNestedPreScroll","getY==="+getY());

        final float shouldMoveY = getY() + dy;//表示最终停留的位置
        Log.i("onNestedPreScroll","shouldMoveY==="+shouldMoveY);
        // 获取到父View的容器的引用，这里假定父View容器是View
        final View parent = (View) getParent();

        int consumedY;
        // 如果超过了父View的上边界，只消费子View到父View上边的距离
        if (shouldMoveY <= 0) {
            consumedY = - (int) getY();//将它移动回来
        } else if (shouldMoveY >= parent.getHeight() - getHeight()) {
            // 如果超过了父View的下边界，只消费子View到父View
            consumedY = (int) (parent.getHeight() - getHeight() - getY());
        } else {
            // 其他情况下全部消费
            consumedY = dy;
        }
        Log.i("onNestedPreScroll"," getHeight()==="+  getHeight());
        Log.i("onNestedPreScroll"," parent.getHeight()==="+ parent.getHeight());
        Log.i("onNestedPreScroll","consumedY==="+consumedY);
        Log.i("onNestedPreScroll","===================");
        // 对父View进行移动
        setY(getY() + consumedY);

        // 将父View消费掉的放入consumed数组中
        consumed[1] = consumedY;

       }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, String.format("onNestedFling, velocityX = %f, velocityY = %f, consumed = %b", velocityX, velocityY, consumed));
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.d(TAG, String.format("onNestedPreFling, velocityX = %f, velocityY = %f", velocityX, velocityY));
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.d(TAG, "getNestedScrollAxes");
        return parentHelper.getNestedScrollAxes();
    }
}