package com.hiking.base.recycle_view;

import android.view.View;

public abstract class OnItemClickListener<T> {
    public abstract void onClick(View v, T t, int position);
}
