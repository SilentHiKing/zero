package com.hiking.zero.temp.typeone;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.hiking.zero.R;

import java.util.List;

public class MediaPagerAdapter extends PagerAdapter {

    List<String> mDatas;


    public void setData(List<String> datas) {
        this.mDatas = datas;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.item_default, null);
        v.setBackgroundResource(R.color.color_7fffd4);
        ((TextView)v.findViewById(R.id.title)).setText(mDatas.get(position));
        container.addView(v);
        return v;
    }
}
