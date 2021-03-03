package com.hiking.zero.base.test;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hiking.base.recycle_view.BaseViewHolder;
import com.hiking.base.util.DefUtil;
import com.hiking.zero.R;

import butterknife.BindView;

public class SimpleViewHolder extends BaseViewHolder {

    public static SimpleViewHolder create(ViewGroup parent) {
        return new SimpleViewHolder(createView(R.layout.item_default, parent));
    }

    @BindView(R.id.title)
    TextView title;

    public SimpleViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Object o, int postion) {
        super.bindData(o, postion);
        title.setText(o.toString());
        title.setBackgroundColor(DefUtil.getIns().randomColor());
    }
}