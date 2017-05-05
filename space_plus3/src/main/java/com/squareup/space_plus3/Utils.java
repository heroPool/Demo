package com.squareup.space_plus3;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by yao on 2017/5/2.
 */

public class Utils {
    private static Utils mInstance;
    private static Activity mContext;

    public static Utils getInstance(Activity context) {
        if (mInstance == null) {
            synchronized (Utils.class) {
                if (mInstance == null) {
                    mInstance = new Utils();
                }
            }
        }
        mContext = context;
        return mInstance;
    }

    public static void setTextView(TextView textView) {
        textView.setText(mContext.getResources().getString(R.string.hello_android));
    }

    public static void release() {
        mInstance = null;
        mContext = null;
    }
}
