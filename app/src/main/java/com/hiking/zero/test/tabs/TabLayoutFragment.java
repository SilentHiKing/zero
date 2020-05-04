package com.hiking.zero.test.tabs;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.hiking.base.view.RecyclerTabLayout;
import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.test.data.ColorItem;
import com.hiking.zero.test.data.DemoData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class TabLayoutFragment extends BaseFragment {

    @BindView(R.id.rtl_tabs)
    RecyclerTabLayout rtl_tabs;
    @BindView(R.id.vp_view_pager)
    ViewPager vp_view_pager;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_layout;
    }

    @Override
    public void initView(View v) {
        super.initView(v);
        List<ColorItem> items = DemoData.loadDemoColorItems(getContext());
        Collections.sort(items, new Comparator<ColorItem>() {
            @Override
            public int compare(ColorItem lhs, ColorItem rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });

        DemoColorPagerAdapter viewPagerAdapter = new DemoColorPagerAdapter();
        viewPagerAdapter.addAll(items);
        vp_view_pager.setAdapter(viewPagerAdapter);
        DemoCustomView01Adapter adapter = new DemoCustomView01Adapter(vp_view_pager);
        rtl_tabs.setUpWithAdapter(adapter);
    }
}
