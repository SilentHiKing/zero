package com.hiking.zero.test;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hiking.base.recycle_view.OnItemClickListener;
import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.base.test.SimpleAdapter;
import com.hiking.zero.flutter.Test1Activity;
import com.hiking.zero.flutter.TestFlutter1Activity;
import com.hiking.zero.test.data.ColorItem;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * https://www.jianshu.com/p/b987fad8fcb4
 */
public class AppBarLayoutFragment extends BaseFragment {
    public static final String TYPE_ONE = "TYPE_ONE";
    ColorItem mData;


    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    NavController mNavController;

    public static AppBarLayoutFragment newInstance(ColorItem data) {
        Bundle args = new Bundle();
        AppBarLayoutFragment fragment = new AppBarLayoutFragment();
        args.putParcelable(TYPE_ONE, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View v) {
        super.initView(v);
        mData = getArguments().getParcelable(TYPE_ONE);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        SimpleAdapter mAdapter = new SimpleAdapter();
        mAdapter.setData(generateData());
        rv_list.setAdapter(mAdapter);
        mNavController = Navigation.findNavController(getActivity(), R.id.main_fragment_container);
        mAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(View v, String s, int position) {
                switch (position){
                    case 0:
                        mNavController.navigate(R.id.action_home_to_move);
                        break;
                    case 1:
                        mNavController.navigate(R.id.action_home_to_type_one);
                        break;
                    case 2:
                        mNavController.navigate(R.id.action_home_to_nested_one);
                        break;
                    case 3:
                        TestFlutter1Activity.Companion.start(getContext());
                        break;
                    case 4:
                        Test1Activity.intentFor(getContext());
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private List<String> generateData() {
        Resources res = getResources();
        String[] titles = res.getStringArray(R.array.click_title);
        return Arrays.asList(titles);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.layout_test_app_bar_01;
    }
}
