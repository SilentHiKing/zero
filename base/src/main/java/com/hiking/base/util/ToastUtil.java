package com.hiking.base.util;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.hiking.base.R;

import static com.hiking.base.util.Constant.NONE_VALUE;


public class ToastUtil {
    private static View mView;
    private static ImageView mImageView;
    private static TextView mTextView;
    private static Space mSpaceView;

    /**
     * 初始化ToastView
     */
    private static void initToastView() {
        mView = LayoutInflater.from(ContextUtil.getApplication()).inflate(R.layout.toast, null);
        mImageView = mView.findViewById(R.id.iv_toast);
        mTextView = mView.findViewById(R.id.tv_toast);
        mSpaceView = mView.findViewById(R.id.space_toast);
    }

    /**
     * Toast提示，兼容子线程
     *
     * @param string
     */
    public static void showToast(String string) {
        showToast(null, string);
    }

    /**
     * Toast提示，兼容子线程
     *
     * @param drawableId
     * @param textId
     */
    @SuppressLint("NewApi")
    public static void showToast(int drawableId, int textId) {
        Drawable drawable = null;
        String text = null;
        if (NONE_VALUE != drawableId) {
            drawable = ContextUtil.getApplication().getDrawable(drawableId);
        }
        if (NONE_VALUE != textId) {
            text = ContextUtil.getApplication().getText(textId).toString();
        }
        showToast(drawable, text);
    }

    /**
     * Toast提示，兼容子线程
     *
     * @param drawable
     * @param text
     */
    public static void showToast(final Drawable drawable, final String text) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            HandlerUtil.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    makeToast(drawable, text);
                }
            });
        } else {
            makeToast(drawable, text);
        }
    }

    /**
     * 显示Toast
     *
     * @param drawable
     * @param text
     */
    private static void makeToast(Drawable drawable, String text) {
        if (mView == null) {
            synchronized (ToastUtil.class) {
                if (mView == null) {
                    initToastView();
                }
            }
        }
        mSpaceView.setVisibility(View.VISIBLE);

        if (drawable == null) {
            mImageView.setVisibility(View.GONE);
            mSpaceView.setVisibility(View.GONE);
        } else {
            mImageView.setVisibility(View.VISIBLE);
            mImageView.setImageDrawable(drawable);
        }

        if (TextUtils.isEmpty(text)) {
            mTextView.setVisibility(View.GONE);
            mSpaceView.setVisibility(View.GONE);
        } else {
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(text);
        }

        Toast toast = new Toast(ContextUtil.getApplication());
        toast.setView(mView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, DisplayUtil.dp2px(200));
        toast.show();
    }
}
