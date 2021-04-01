package com.hiking.zero.temp.typeone;

import android.view.View;

import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.databinding.FragmentNestedOneBinding;

public class NestedOneFragment extends BaseFragment<FragmentNestedOneBinding> {

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_nested_one;
    }

    @Override
    public void initView(View v) {
        super.initView(v);
        mViewBinding.npvVew.post(new Runnable() {
            @Override
            public void run() {

            }
        });

    }


}
