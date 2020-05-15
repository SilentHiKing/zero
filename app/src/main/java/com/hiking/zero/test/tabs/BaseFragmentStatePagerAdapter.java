package com.hiking.zero.test.tabs;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.test.data.ColorItem;

import java.util.LinkedHashMap;
import java.util.List;

public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    SparseArray<BaseFragment> mRegisteredFragments = new SparseArray<>();

    List<ColorItem> mData;
    LinkedHashMap<String, String> mMap;


    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<ColorItem> items) {
        super(fm);
        mData = items;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentUtil.generate(mData.get(position));
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        BaseFragment fragment = (BaseFragment) super.instantiateItem(container, position);
        mRegisteredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public BaseFragment getRegisteredFragment(int position) {
        return mRegisteredFragments.get(position);
    }

    public ColorItem getColorItem(int position) {
        return mData.get(position);
    }

    /*@Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mData != null) {
            return mData.get(position);
        }
        return super.getPageTitle(position);
    }*/
}
