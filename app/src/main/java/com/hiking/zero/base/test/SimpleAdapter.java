package com.hiking.zero.base.test;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hiking.base.recycle_view.BaseAdapter;
import com.hiking.base.recycle_view.OnItemClickListener;

public class SimpleAdapter extends BaseAdapter<String, SimpleViewHolder> {

    OnItemClickListener<String> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<String> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SimpleViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        holder.bindData(mData.get(position), position, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return hasData() ? mData.size() : 0;

    }
}