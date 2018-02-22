package com.ucsunup.easylog;

import com.ucsunup.easylog.weaving.internal.LogHelper;

/**
 * LogUtil to log in code block.
 * Created by ucsunup on 2018/1/2.
 */

public class Logi {
    private static final String TAG = "EasyLog-";

    public static void v(String info) {
        v("", info);
    }

    public static void v(String key, String info) {
        LogHelper.logInternal(Level.VERBOSE, TAG + key, info);
    }

    public static void d(String info) {
        d("", info);
    }

    public static void d(String key, String info) {
        LogHelper.logInternal(Level.DEBUG, TAG + key, info);
    }

    public static void i(String info) {
        i("", info);
    }

    public static void i(String key, String info) {
        LogHelper.logInternal(Level.INFO, TAG + key, info);
    }

    public static void w(String info) {
        w("", info);
    }

    public static void w(String key, String info) {
        LogHelper.logInternal(Level.WARN, TAG + key, info);
    }

    public static void e(String info) {
        e("", info);
    }

    public static void e(String key, String info) {
        LogHelper.logInternal(Level.ERROR, TAG + key, info);
    }
}
