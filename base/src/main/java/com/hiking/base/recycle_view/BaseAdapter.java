package com.hiking.base.recycle_view;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public List<T> mData;

    public void setData(List<T> list) {
        if (list != null) {
            mData = list;
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return mData;
    }

    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        if (mData != null) {
            mData.addAll(list);
        } else {
            mData = list;
        }
        notifyDataSetChanged();
    }


    public boolean hasData() {
        return mData != null && mData.size() > 0;
    }
}
