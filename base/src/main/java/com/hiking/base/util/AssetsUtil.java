package com.hiking.base.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class AssetsUtil {
    public static String loadJsonFromAsset(Context context, String filename) throws IOException {
        InputStream is = context.getAssets().open(filename);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }
}
