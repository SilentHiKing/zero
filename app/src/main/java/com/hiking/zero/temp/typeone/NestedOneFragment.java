package com.hiking.zero.temp.typeone;

import android.view.View;

import com.hiking.base.util.DisplayUtil;
import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.databinding.FragmentScrollOneBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NestedOneFragment extends BaseFragment<FragmentScrollOneBinding> {

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_scroll_one;
    }

    public static class A {
        public String hello = "world";
        public Double num;

        public A setNum(Double num) {
            this.num = num;
            return this;
        }

        public A setHello(String hello) {
            this.hello = hello;
            return this;
        }
    }

    @Override
    public void initView(View v) {
        super.initView(v);
        int dx = DisplayUtil.dp2px(70);
        List<A> objects = new ArrayList<>();
        objects.add(new A().setNum(1D).setHello("a"));
        objects.add(new A().setHello("b"));
        objects.add(new A().setHello("c"));
        objects.add(new A().setNum(-1D).setHello("d"));


    }


    public static final int ORDER_UP = -1;
    public static final int ORDER_DOWN = 1;

    private void sortBY(List<A> objects, int order) {
        Collections.sort(objects, new Comparator<A>() {
            @Override
            public int compare(A o1, A o2) {
                Double o1Value = o1.num;
                Double o2Value = o2.num;
                if (o1Value == null && o2Value == null) {
                    return 0;
                }
                if (o1Value == null) {
                    return ORDER_DOWN;
                }
                if (o2Value == null) {
                    return ORDER_UP;
                }
                if (o1Value > o2Value) {
                    return -order;
                } else if (o1Value < o2Value) {
                    return order;
                } else {
                    return 0;
                }
            }
        });
        System.out.println("shu:");
        for (A a : objects) {
            System.out.println(a.num + a.hello);
        }

    }


}
