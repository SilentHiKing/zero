package com.hiking.zero.temp.typeone;

import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.view.ScaleTitleTypeOne;

import java.util.Arrays;

import butterknife.BindView;

public class TypeOneFragment extends BaseFragment {
    @BindView(R.id.tl_tab)
    TabLayout tl_tab;
    @BindView(R.id.vp_pager)
    ViewPager vp_pager;
    String[] mTabTitle = new String[]{"直播", "音频", "视频回放"};

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_type_one;
    }

    @Override
    public void initView(View v) {
        super.initView(v);
        initTabLayout();
        tl_tab.setSelectedTabIndicator(null);
        vp_pager.setPadding(0, 0, 120, 0);
        vp_pager.setPageMargin(20);
        vp_pager.setClipToPadding(false);
        vp_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_tab));
        tl_tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp_pager) {
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                ScaleTitleTypeOne v = (ScaleTitleTypeOne) tab.getCustomView();
                v.close();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
                ScaleTitleTypeOne v = (ScaleTitleTypeOne) tab.getCustomView();
                v.open();
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                ScaleTitleTypeOne v = (ScaleTitleTypeOne) tab.getCustomView();
                v.open();
            }
        });

        MediaPagerAdapter adapter = new MediaPagerAdapter();

        adapter.setData(Arrays.asList(mTabTitle));
        vp_pager.setAdapter(adapter);

    }

    private void initTabLayout() {
        for (int i = 0, length = mTabTitle.length; i < length; i++) {
            TabLayout.Tab tab = tl_tab.getTabAt(i);
            if (tab == null) {
                tab = tl_tab.newTab();
                tab.setCustomView(R.layout.layout_tab_scale_title_type_one);
                tl_tab.addTab(tab);
            }
            View viewTab = tab.getCustomView();
            TextView tv_title = viewTab.findViewById(R.id.tv_title);
            tv_title.setText(mTabTitle[i]);

        }
        while (tl_tab.getTabAt(mTabTitle.length) != null) {
            tl_tab.removeTabAt(mTabTitle.length);
        }
    }

}
