package com.ucsunup.easylog.weaving.internal;

import com.ucsunup.easylog.annotations.Logit;

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

    @Pointcut("@annotation(logit)")
    public void easyLogAnnotated(Logit logit) {
    }

    @Pointcut("execution(* *(..))")
    public void atExecution() {
    }

//    静态编译可以选择如下匹配:
//    pointcut txRequiredMethod(Tx transactionAnnotation) :
//    execution(* *(..)) && @this(transactionAnnotation)
//            && if(transactionAnnotation.policy() == TxPolicy.REQUIRED);

//    @Pointcut("within(@com.ucsunup.easylog.annotations.EasyLogAspect *)")
//    public void withinAnnotatedClass() {
//    }
//
//    @Pointcut("execution(!synthetic * *(..)) && easyLogAnnotated(easyLog)")
//    public void methodInsideAnnotatedType(EasyLog easyLog) {
//    }
//
//    @Pointcut("execution(!synthetic *.new(..)) && easyLogAnnotated(easyLog)")
//    public void constructorInsideAnnotatedType(EasyLog easyLog) {
//    }
//
//    @Pointcut("execution(@com.ucsunup.easylog.annotations.EasyLog * *(..)) || methodInsideAnnotatedType(easyLog)")
//    public void method(EasyLog easyLog) {
//    }
//
//    @Pointcut("execution(@com.ucsunup.easylog.annotations.EasyLog *.new(..)) || constructorInsideAnnotatedType(easyLog)")
//    public void constructor(EasyLog easyLog) {
//    }

    @Around("easyLogAnnotated(logit) && atExecution()")
    public Object logAndExecute(ProceedingJoinPoint joinPoint, Logit logit) throws Throwable {
        LogHelper.enterMethod(joinPoint, logit);

        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        long costTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        LogHelper.exitMethod(joinPoint, logit, result, costTime);
        return result;
    }
}
