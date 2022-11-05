package com.book.my.show.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {
    @Before("execution(* com.book.my.show.service..*(..)))")
    public void logBeforeAllMethods(JoinPoint joinPoint) {
        log.info("Method {}.{} execution starts", joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
    }

    @Around("execution(* com.book.my.show.service..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        log.info("Execution time of " + className + "." + methodName + " :: "
                + stopWatch.getTotalTimeMillis() + " ms");

        return result;
    }

    @After("execution(* com.book.my.show.service..*(..)))")
    public void logAfterAllMethods(JoinPoint joinPoint) {
        log.info("Method {}.{} execution ends", joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
    }
}
