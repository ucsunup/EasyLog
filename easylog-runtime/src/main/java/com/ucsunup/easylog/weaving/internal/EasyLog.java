package com.ucsunup.easylog.weaving.internal;

/**
 * Created by ucsunup on 2017/12/5.
 */

public class EasyLog {
    public static volatile boolean enable = true;

    public static void setEnable(boolean enable) {
        enable = enable;
    }
}
