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
public class UserControllerAspect {

    // Before Advice
    @Before("execution(* com.technoelevet.StudentManagmentSystem.controller.UserController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("🔷 [Before] Method called: {}", joinPoint.getSignature().getName());
    }

    // After Advice
    @After("execution(* com.technoelevet.StudentManagmentSystem.controller.UserController.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("🔶 [After] Method finished: {}", joinPoint.getSignature().getName());
    }

    // After Returning Advice
    @AfterReturning(pointcut = "execution(* com.technoelevet.StudentManagmentSystem.controller.UserController.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("✅ [AfterReturning] Method {} returned: {}", joinPoint.getSignature().getName(), result);
    }

    // After Throwing Advice
    @AfterThrowing(pointcut = "execution(* com.technoelevet.StudentManagmentSystem.controller.UserController.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("❌ [AfterThrowing] Exception in {}: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    // Around Advice (optional, use only if you want full control of method execution)
    /*
    @Around("execution(* com.technoelevet.StudentManagmentSystem.controller.UserController.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("🔁 [Around] Before method: {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        log.info("🔁 [Around] After method: {}", joinPoint.getSignature().getName());
        return result;
    }
    */
}
