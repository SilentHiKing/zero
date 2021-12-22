package com.hiking.zero.temp.typeone;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.base.test.SimpleAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class OneOneFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    public static Fragment newIns(int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        OneOneFragment fragment = new OneOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_scroll_one;
    }

    @Override
    public void initView(View v) {
        super.initView(v);

        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        SimpleAdapter mAdapter = new SimpleAdapter();
        mAdapter.setData(generateData());
        rv_list.setAdapter(mAdapter);
    }

    private List<String> generateData() {
        Resources res = getResources();
        String[] titles = res.getStringArray(R.array.click_title);
        return Arrays.asList(titles);
    }
}
