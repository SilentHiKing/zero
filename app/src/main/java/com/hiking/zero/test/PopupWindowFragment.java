package com.hiking.zero.test;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hiking.base.util.DisplayUtil;
import com.hiking.base.util.ToastUtil;
import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.test.data.ColorItem;

import butterknife.BindView;
import butterknife.OnClick;


public class PopupWindowFragment extends BaseFragment {
    public static final String TYPE_ONE = "TYPE_ONE";
    ColorItem mData;
    @BindView(R.id.title)
    TextView title;

    public static PopupWindowFragment newInstance(ColorItem data) {
        Bundle args = new Bundle();
        PopupWindowFragment fragment = new PopupWindowFragment();
        args.putParcelable(TYPE_ONE, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View v) {
        super.initView(v);
        mData = getArguments().getParcelable(TYPE_ONE);
        if (mData != null) {
            title.setText("Page: " + mData.hex);
        }
    }

    @OnClick(R.id.title)
    public void clickOne(View v) {
        TextView textView = new TextView(getContext());
        textView.setLineSpacing(DisplayUtil.dp2px(getContext(), 4), 1.0f);
        int padding = DisplayUtil.dp2px(getContext(), 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setBackgroundResource(R.color.azure);
        textView.setText("QMUIBasePopup 可以设置其位置以及显示和隐藏的动画");
        Rect rect = new Rect();
        int[] anchorLocation = new int[2];
        title.getLocationOnScreen(anchorLocation);

        final PopupWindow popupWindow = new PopupWindow(textView,
                FrameLayout.MarginLayoutParams.WRAP_CONTENT, FrameLayout.MarginLayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setClippingEnabled(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //可响应外部点击事件
        popupWindow.setFocusable(false);
        popupWindow.showAsDropDown(title, 200, 0);

    }

    @OnClick(R.id.title_two)
    public void clickTwo(View v) {
        ToastUtil.showToast("点击");
    }
    @OnClick(R.id.container)
    public void clickContainer(View v) {
        ToastUtil.showToast("点击container");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_test_tab;
    }
}
