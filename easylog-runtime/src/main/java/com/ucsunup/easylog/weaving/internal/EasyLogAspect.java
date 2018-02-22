package com.ucsunup.easylog.weaving.internal;


import com.ucsunup.easylog.annotations.Loga;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.concurrent.TimeUnit;

/**
 * Created by ucsunup on 2017/12/2.
 */

@Aspect
public class EasyLogAspect {

    @Pointcut("@annotation(loga)")
    public void easyLogAnnotated(Loga loga) {
    }

    @Pointcut("execution(* *(..))")
    public void atExecution() {
    }

    @Around("easyLogAnnotated(loga) && atExecution()")
    public Object logAndExecute(ProceedingJoinPoint joinPoint, Loga loga) throws Throwable {
        LogHelper.enterMethod(joinPoint, loga);

        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        long costTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        LogHelper.exitMethod(joinPoint, loga, result, costTime);
        return result;
    }
}
