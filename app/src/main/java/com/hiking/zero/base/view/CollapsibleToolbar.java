package com.hiking.zero.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewParent;

import androidx.constraintlayout.motion.widget.MotionLayout;

import com.google.android.material.appbar.AppBarLayout;

public class CollapsibleToolbar extends MotionLayout implements AppBarLayout.OnOffsetChangedListener {
    public CollapsibleToolbar(Context context) {
        this(context, null);
    }

    public CollapsibleToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent != null && parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).addOnOffsetChangedListener(this);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (appBarLayout != null) {
            float total = appBarLayout.getTotalScrollRange();
            float progress = -verticalOffset / total;
            Log.d("hello","verticalOffset = "+verticalOffset+ "progress="+progress);
            setProgress(progress);
        }

    }
}
