package com.example.LibraryManagementSystem.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect

public class LoggingAspect {
    @Around("execution(* com.example.LibraryManagementSystem..*(..))")
    public Object LogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis();
        System.out.println("Method [" + joinPoint.getSignature() + "] executed in " + elapsedTime + " ms");
        return result;
    }

}
