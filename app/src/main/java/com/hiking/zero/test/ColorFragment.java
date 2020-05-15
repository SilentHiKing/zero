package com.hiking.zero.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.test.data.ColorItem;

import butterknife.BindView;


public class ColorFragment extends BaseFragment {
    public static final String TYPE_ONE = "TYPE_ONE";
    ColorItem mData;

    @BindView(R.id.title)
    TextView title;

    public static ColorFragment newInstance(ColorItem data) {
        Bundle args = new Bundle();
        ColorFragment fragment = new ColorFragment();
        args.putParcelable(TYPE_ONE, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initView(View v) {
        super.initView(v);
        mData = getArguments().getParcelable(TYPE_ONE);
        if (mData != null) {
            title.setText("Page: " + mData.hex);
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_test_tab;
    }
}
