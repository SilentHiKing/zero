package com.hiking.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.hiking.base.R;

public class CornerLayout extends ViewGroup {
    public CornerLayout(Context context) {
        this(context, null);
    }

    public CornerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            // 只对第一个子View进行layout
            View first = getChildAt(0);
            CornerLayoutParams layoutParams = (CornerLayoutParams) first.getLayoutParams();
            int left;
            int top;
            switch (layoutParams.mCorner) {
                case CornerLayoutParams.CORNER_LEFT_TOP:
                    left = getPaddingLeft() + layoutParams.leftMargin;
                    top = getPaddingTop() + layoutParams.topMargin;
                    break;

                case CornerLayoutParams.CORNER_RIGHT_TOP:
                    top = getPaddingTop() + layoutParams.topMargin;
                    left = getWidth() - getPaddingRight() - first.getMeasuredWidth() - layoutParams.rightMargin;
                    break;

                case CornerLayoutParams.CORNER_LEFT_BOTTOM:
                    top = getHeight() - getPaddingBottom() - first.getMeasuredHeight() - layoutParams.bottomMargin;
                    left = getPaddingLeft() + layoutParams.leftMargin;
                    break;

                case CornerLayoutParams.CORNER_RIGHT_BOTTOM:
                    top = getHeight() - getPaddingBottom() - layoutParams.bottomMargin - first.getMeasuredHeight();
                    left = getWidth() - getPaddingRight() - layoutParams.rightMargin - first.getMeasuredWidth();
                    break;

                default:
                    left = getPaddingLeft() + layoutParams.leftMargin;
                    top = getPaddingTop() + layoutParams.topMargin;
            }
            first.layout(left, top, left + first.getMeasuredWidth(), top + first.getMeasuredHeight());
        }
    }

    /**
     * 系统创建布局参数的接口
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CornerLayoutParams(getContext(), attrs);
    }

    /**
     * 系统创建默认布局参数的接口。
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CornerLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * 检测参数类型是否合法。
     */
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CornerLayoutParams;
    }

    /**
     * 根据不合法的参数p, 重新创建CornerLayoutParams对象。
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        if (p instanceof MarginLayoutParams) {
            return new CornerLayoutParams((MarginLayoutParams) p);
        }
        return new CornerLayoutParams(p);
    }

    /**
     * &emsp;定义布局参数类。
     */
    public static class CornerLayoutParams extends ViewGroup.MarginLayoutParams {
        public static final int CORNER_LEFT_TOP = 0x01;
        public static final int CORNER_RIGHT_TOP = 0x02;
        public static final int CORNER_LEFT_BOTTOM = 0x04;
        public static final int CORNER_RIGHT_BOTTOM = 0x08;

        public int mCorner = CORNER_LEFT_TOP;

        public CornerLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CornerLayout_Layout);
            mCorner = a.getInt(R.styleable.CornerLayout_Layout_layout_corner, CORNER_LEFT_TOP);
            a.recycle();
        }

        public CornerLayoutParams(int width, int height) {
            super(width, height);
        }

        public CornerLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CornerLayoutParams(LayoutParams source) {
            super(source);
        }
    }

}
