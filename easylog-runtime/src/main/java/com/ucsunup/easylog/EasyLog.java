package com.ucsunup.easylog;

/**
 * Created by ucsunup on 2017/12/5.
 */

public class EasyLog {
    private static volatile boolean mEnable = true;
    private static volatile Level mLevel = Level.VERBOSE;

    public static boolean enable() {
        return mEnable;
    }

    public static void setmEnable(boolean enable) {
        mEnable = enable;
    }

    public static Level getLevel() {
        return mLevel;
    }

    public static void setLevel(Level level) {
        mLevel = level;
    }
}
