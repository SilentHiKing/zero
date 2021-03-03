package com.hiking.base.recycle_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    protected OnItemClickListener<T> listener;
    protected T t;
    protected int position = -1;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, t, position);
                }
            }
        });
    }

    public static View createView(int layoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return itemView;
    }

    public void bindData(T t, int postion) {

    }

    public void bindData(T t, int postion, OnItemClickListener<T> listener) {
        this.listener = listener;
        this.t = t;
        this.position = postion;
        bindData(t, postion);
    }


}