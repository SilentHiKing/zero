package com.hiking.zero.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    protected View mRootView;
    public static final String POSITION = "POSITION";
    protected T mViewBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Class obtainedClass = getClass();
        Type genericSuperclass = null;
        for (; ; ) {
            if (obtainedClass == null) {
                break;
            }
            genericSuperclass = obtainedClass.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                break;
            }
            obtainedClass = obtainedClass.getSuperclass();
        }
        if (obtainedClass != null && genericSuperclass instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericSuperclass;
            Class cls = (Class) type.getActualTypeArguments()[0];
            try {
                Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                mViewBinding = (T) inflate.invoke(null, inflater, container, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mRootView = mViewBinding.getRoot();
        } else {
            mRootView = inflater.inflate(getLayoutRes(), container, false);
        }


        ButterKnife.bind(this, mRootView);
        initView(mRootView);
        initData();
        return mRootView;
    }

    public void initData() {
    }


    public void initView(View v) {
    }


    public abstract @LayoutRes
    int getLayoutRes();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewBinding = null;
    }
}
