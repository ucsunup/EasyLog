package com.ucsunup.easylog.weaving.internal;

import android.os.Build;
import android.os.Looper;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;

import com.ucsunup.easylog.EasyLog;
import com.ucsunup.easylog.Level;
import com.ucsunup.easylog.annotations.Loga;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by ucsunup on 2017/12/2.
 */

public class LogHelper {

    public static volatile Level mLevel = EasyLog.getLevel();
    private static final String TAG = "EasyLog-";

    public static void enterMethod(ProceedingJoinPoint joinPoint) {
        enterMethod(joinPoint, null);
    }

    /**
     * Enter method advice
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @param loga      {@link Loga}
     */
    public static void enterMethod(ProceedingJoinPoint joinPoint, Loga loga) {
        if (!enableLog(loga)) {
            return;
        }

        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Class<?> cls = codeSignature.getDeclaringType();
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();

        StringBuilder builder = new StringBuilder("\u21e2 ");
        builder.append(methodName).append('(');
        if (loga.printArgs()) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(parameterNames[i])
                        .append("=")
                        .append(Strings.toString(parameterValues[i]));
            }
        }
        builder.append(')');

        if (Looper.myLooper() != Looper.getMainLooper()) {
            builder.append(" [Thread:\"").append(Thread.currentThread().getName()).append("\"]");
        }

        logInternal(loga.level(), asTag(cls), builder.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            final String section = builder.toString().substring(2);
            Trace.beginSection(section);
        }
    }

    public static void exitMethod(ProceedingJoinPoint joinPoint, Object result, long costTime) {
        exitMethod(joinPoint, null, result, costTime);
    }

    /**
     * Exit method advice
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @param loga      {@link Loga}
     * @param result    method return object
     * @param costTime  method cost time
     */
    public static void exitMethod(ProceedingJoinPoint joinPoint, Loga loga,
                                  Object result, long costTime) {
        if (!enableLog(loga)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        }

        Signature signature = joinPoint.getSignature();

        Class<?> cls = signature.getDeclaringType();
        String methodName = signature.getName();
        boolean hasReturnType = signature instanceof MethodSignature
                && ((MethodSignature) signature).getReturnType() != void.class;

        StringBuilder builder = new StringBuilder("\u21E0 ")
                .append(methodName)
                .append(" \u21E0 ")
                .append("[")
                .append(costTime)
                .append("ms]");

        if (hasReturnType) {
            builder.append(" = ");
            builder.append(Strings.toString(result));
        }

        logInternal(loga.level(), asTag(cls), builder.toString());
    }

    /**
     * Log info internal
     *
     * @param level {@link Level}
     * @param key   log key
     * @param info  log info
     */
    public static void logInternal(Level level, String key, String info) {
        if (!enableLog(level)) {
            return;
        }

        switch (level) {
            case VERBOSE:
                Log.v(key, info);
                break;
            case DEBUG:
                Log.d(key, info);
                break;
            case INFO:
                Log.i(key, info);
                break;
            case WARN:
                Log.w(key, info);
                break;
            case ERROR:
                Log.e(key, info);
                break;
            default:
                break;
        }
    }

    /**
     * Generate log tag by cls
     *
     * @param cls method's class
     * @return
     */
    private static String asTag(Class<?> cls) {
        if (cls.isAnonymousClass()) {
            return asTag(cls.getEnclosingClass());
        }
        return TAG + cls.getSimpleName();
    }

    /**
     * Check current if log enable by {@link Loga}.
     *
     * @param loga {@link Loga}
     * @return
     */
    private static boolean enableLog(Loga loga) {
        if (!EasyLog.enable()) {
            return false;
        }

        if (loga == null) {
            return false;
        }

        return enableLog(loga.level());
    }

    /**
     * Check current if log enable by {@link Level}
     *
     * @param level log level
     * @return
     */
    private static boolean enableLog(Level level) {
        if (level.ordinal() < mLevel.ordinal()) {
            return false;
        }
        return true;
    }
}
