package com.hiking.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hiking.base.util.DisplayUtil;


public class MoveTouchView extends FrameLayout {

    private int mLastTouchX;
    private int mLastTouchY;
    private int mTouchSlop;
    private boolean mCanMove;
    private int mScrollPointerId;

    public MoveTouchView(Context context) {
        this(context, null);
    }

    public MoveTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int actionIndex = event.getActionIndex();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mScrollPointerId = event.getPointerId(0);
                mLastTouchX = (int) (event.getX() + 0.5f);
                mLastTouchY = (int) (event.getY() + 0.5f);
                mCanMove = false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mScrollPointerId = event.getPointerId(actionIndex);
                mLastTouchX = (int) (event.getX(actionIndex) + 0.5f);
                mLastTouchY = (int) (event.getY(actionIndex) + 0.5f);
                break;
            case MotionEvent.ACTION_MOVE:
                final int index = event.findPointerIndex(mScrollPointerId);
                int x = (int) (event.getX(index) + 0.5f);
                int y = (int) (event.getY(index) + 0.5f);
                int dx = mLastTouchX - x;
                int dy = mLastTouchY - y;
                if (!mCanMove) {
                    if (Math.abs(dy) >= mTouchSlop) {
                        if (dy > 0) {
                            dy -= mTouchSlop;
                        } else {
                            dy += mTouchSlop;
                        }
                        mCanMove = true;
                    }
                    if (Math.abs(dy) >= mTouchSlop) {
                        if (dy > 0) {
                            dy -= mTouchSlop;
                        } else {
                            dy += mTouchSlop;
                        }
                        mCanMove = true;
                    }
                }
                if (mCanMove) {
                    updateView(dx, dy);
                    /*offsetTopAndBottom(-dy);
                    offsetLeftAndRight(-dx);*/
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(event);
                break;
            case MotionEvent.ACTION_UP:
            default:
                updateFinalView();
                if (!mCanMove) {
                    performClick();
                }
                break;
        }
        return true;
    }

    private void updateFinalView() {
        float dxc = getX() + mWidth / 2;
        int dx = 0;
        int dy = 0;
        if (mParentWidth / 2 > dxc) {
            dx = (int) (getX() + 0.5f);
        } else {
            dx = -(int) (mParentWidth - getX() + 0.5f - mWidth);
        }

        if (getY() < 0) {
            dy = (int) getY();
        } else if (getY() > mParentHeight - mHeight - mButtomY) {
            dy = (int) (getY() - (mParentHeight - mHeight - mButtomY));
        }
        updateView(dx, dy);
    }

    private int mWidth;
    private int mHeight;
    private int mParentWidth;
    private int mParentHeight;
    ViewGroup mParentView;

    private int mButtomY = DisplayUtil.dp2px(20);


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mParentView = (ViewGroup) getParent();
        mParentWidth = mParentView.getWidth();
        mParentHeight = mParentView.getHeight();
    }

    public void updateView(int dx, int dy) {
        setX(getX() - dx);
        setY(getY() - dy);
        invalidate();

    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = e.getActionIndex();
        if (e.getPointerId(actionIndex) == mScrollPointerId) {
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mScrollPointerId = e.getPointerId(newIndex);
            mLastTouchX = (int) (e.getX(newIndex) + 0.5f);
            mLastTouchY = (int) (e.getY(newIndex) + 0.5f);
        }
    }
}