package com.hiking.zero.test.tabs;

import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.test.ColorFragment;
import com.hiking.zero.test.PopupWindowFragment;
import com.hiking.zero.test.data.ColorItem;

public class FragmentUtil {

    public static BaseFragment generate(ColorItem item) {
        switch (item.name) {
            case "PopupWindow":
                return PopupWindowFragment.newInstance(item);
            default:
                return ColorFragment.newInstance(item);
        }

    }
}
