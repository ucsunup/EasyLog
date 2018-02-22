package com.ucsunup.easylog.weaving.internal;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.concurrent.TimeUnit;

/**
 * Created by ucsunup on 2017/11/26.
 */

@Aspect
public class DebugLogAspect {

//    @Pointcut("within(@com.ucsunup.easylog.annotations.Logit *)")
//    public void withinAnnotatedClass() {
//    }
//
//    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
//    public void methodInsideAnnotatedType() {
//    }
//
//    @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
//    public void constructorInsideAnnotatedType() {
//    }
//
//    @Pointcut("execution(@com.ucsunup.easylog.annotations.Logit * *(..)) || methodInsideAnnotatedType()")
//    public void method() {
//    }
//
//    @Pointcut("execution(@com.ucsunup.easylog.annotations.Logit *.new(..)) || constructorInsideAnnotatedType()")
//    public void constructor() {
//    }
//
//    public static void setEnable(boolean enable) {
//        EasyLog.mEnable = mEnable;
//    }
//
//    @Around("method() || constructor()")
//    public Object logAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
//        LogHelper.enterMethod(joinPoint);
//
//        long startTime = System.nanoTime();
//        Object result = joinPoint.proceed();
//        long endTime = System.nanoTime();
//        long costTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
//
//        LogHelper.exitMethod(joinPoint, result, costTime);
//        return result;
//    }
}
