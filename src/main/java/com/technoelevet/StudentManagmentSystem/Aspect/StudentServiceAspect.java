package com.technoelevet.StudentManagmentSystem.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class StudentServiceAspect {

    // Executed before any method in StudentServiceImpl
    @Before("execution(* com.technoelevet.StudentManagmentSystem.Service.StudentServiceImpl.*(..))")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        log.info("🔔 [Before - Service] Method Called: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("   ➤ Argument: {}", arg);
        }
    }

    // Executed after any method in StudentServiceImpl completes
    @After("execution(* com.technoelevet.StudentManagmentSystem.Service.StudentServiceImpl.*(..))")
    public void afterServiceMethod(JoinPoint joinPoint) {
        log.info("✅ [After - Service] Method Completed: {}", joinPoint.getSignature().getName());
    }

    // Executed when method returns successfully
    @AfterReturning(pointcut = "execution(* com.technoelevet.StudentManagmentSystem.Service.StudentServiceImpl.*(..))",
                    returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("🎯 [AfterReturning - Service] Method: {} Returned: {}", joinPoint.getSignature().getName(), result);
    }

    // Executed when method throws an exception
    @AfterThrowing(pointcut = "execution(* com.technoelevet.StudentManagmentSystem.Service.StudentServiceImpl.*(..))",
                   throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("💥 [AfterThrowing - Service] Method: {} Threw: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    // Optional: Around advice if you want to track execution time
    /*
    @Around("execution(* com.technoelevet.StudentManagmentSystem.Service.StudentServiceImpl.*(..))")
    public Object aroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("🔁 [Around - Service] Starting: {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("⏱️ [Around - Service] Method {} executed in {} ms", joinPoint.getSignature().getName(), (end - start));
        return result;
    }
    */
}
