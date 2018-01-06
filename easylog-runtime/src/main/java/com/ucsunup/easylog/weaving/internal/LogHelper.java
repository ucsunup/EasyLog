package com.ucsunup.easylog.weaving.internal;

import android.os.Build;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;

import com.ucsunup.easylog.annotations.Logit;
import com.ucsunup.easylog.weaving.EasyLog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by ucsunup on 2017/12/2.
 */

public class LogHelper {

    public static volatile Logit.Type mType = Logit.Type.Debug;
    private static final String TAG = "EasyLog-";

    public static void enterMethod(ProceedingJoinPoint joinPoint) {
        enterMethod(joinPoint, null);
    }

    /**
     * Enter method advice
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @param logit     {@link Logit}
     */
    public static void enterMethod(ProceedingJoinPoint joinPoint, Logit logit) {
        if (!EasyLog.enable) {
            return;
        }

        if (logit != null) {
            Log.d("heiheihei", "easyLog type = " + logit.type() + ", append = " + logit.append());
        }

        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Class<?> cls = codeSignature.getDeclaringType();
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();

        StringBuilder builder = new StringBuilder("\u21e2 ");
        builder.append(methodName).append('(');
        for (int i = 0; i < parameterNames.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }

            builder.append(parameterNames[i])
                    .append("=")
                    .append(Strings.toString(parameterValues[i]));
        }
        builder.append(')');

        if (Looper.myLooper() != Looper.getMainLooper()) {
            builder.append(" [Thread:\"").append(Thread.currentThread().getName()).append("\"]");
        }

        Log.v(asTag(cls), builder.toString());

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
     * @param logit     {@link Logit}
     * @param result    method return object
     * @param costTime  method cost time
     */
    public static void exitMethod(ProceedingJoinPoint joinPoint, Logit logit,
                                  Object result, long costTime) {
        if (!EasyLog.enable) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        }

        if (logit != null) {
            Log.d("heiheihei", "easyLog exit type = " + logit.type() + ", append = " + logit.append());
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

        Log.v(asTag(cls), builder.toString());
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
}
