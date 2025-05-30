
package com.technoelevet.StudentManagmentSystem.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class UserServiceAspect {

    // Before Advice
    @Before("execution(* com.technoelevet.StudentManagmentSystem.Service.UserServiceImpl.*(..))")
    public void logBeforeService(JoinPoint joinPoint) {
        log.info("📌 [Before - Service] Method: {}", joinPoint.getSignature().getName());
    }

    // After Advice
    @After("execution(* com.technoelevet.StudentManagmentSystem.Service.UserServiceImpl.*(..))")
    public void logAfterService(JoinPoint joinPoint) {
        log.info("✅ [After - Service] Method completed: {}", joinPoint.getSignature().getName());
    }

    // After Returning Advice
    @AfterReturning(
        pointcut = "execution(* com.technoelevet.StudentManagmentSystem.Service.UserServiceImpl.*(..))",
        returning = "result")
    public void logAfterReturningService(JoinPoint joinPoint, Object result) {
        log.info("🎯 [AfterReturning - Service] Method: {} returned: {}", joinPoint.getSignature().getName(), result);
    }

    // After Throwing Advice
    @AfterThrowing(
        pointcut = "execution(* com.technoelevet.StudentManagmentSystem.Service.UserServiceImpl.*(..))",
        throwing = "ex")
    public void logAfterThrowingService(JoinPoint joinPoint, Throwable ex) {
        log.error("💥 [AfterThrowing - Service] Exception in {}: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    // Around Advice (optional)
    /*
    @Around("execution(* com.technoelevet.StudentManagmentSystem.Service.UserServiceImpl.*(..))")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("🔄 [Around - Service] Before: {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        log.info("🔄 [Around - Service] After: {}", joinPoint.getSignature().getName());
        return result;
    }
    */
}
