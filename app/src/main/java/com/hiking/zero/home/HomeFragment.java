package com.hiking.zero.home;

import android.util.ArraySet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {
    NavController mHomeController;
    Fragment mFragmentContainer;

    @BindView(R.id.bnv_fragment_nav)
    BottomNavigationView bnv_fragment_nav;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View v) {
        super.initView(v);

        mFragmentContainer = getChildFragmentManager().findFragmentById(R.id.fragment_container);
//        bnv_fragment_nav = v.findViewById(R.id.bnv_fragment_nav);
        Log.d("Hello",mFragmentContainer.toString());
        mHomeController = NavHostFragment.findNavController(mFragmentContainer);
//        NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(),mHomeController);
        NavigationUI.setupWithNavController(bnv_fragment_nav, mHomeController);
    }

    @Override
    public void initData() {
        super.initData();
    }

}
