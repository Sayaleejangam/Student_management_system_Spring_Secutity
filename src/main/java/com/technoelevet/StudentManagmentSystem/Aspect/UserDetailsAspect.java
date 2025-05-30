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
public class UserDetailsAspect {

    @Before("execution(* com.technoelevet.StudentManagmentSystem.securityService.CustomUserDetailsService.loadUserByUsername(..))")
    public void beforeLoadUser(JoinPoint joinPoint) {
        log.info("🔐 [Before] Invoking method: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            log.info("   ➤ Username passed: {}", args[0]);
        }
    }

    @AfterReturning(
        pointcut = "execution(* com.technoelevet.StudentManagmentSystem.securityService.CustomUserDetailsService.loadUserByUsername(..))",
        returning = "userDetails"
    )
    public void afterReturningLoadUser(JoinPoint joinPoint, Object userDetails) {
        log.info("✅ [Success] Method: {} returned user details: {}", joinPoint.getSignature().getName(), userDetails);
    }

    @AfterThrowing(
        pointcut = "execution(* com.technoelevet.StudentManagmentSystem.securityService.CustomUserDetailsService.loadUserByUsername(..))",
        throwing = "ex"
    )
    public void afterThrowingLoadUser(JoinPoint joinPoint, Throwable ex) {
        log.error("❌ [Error] Method: {} threw exception: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    @After("execution(* com.technoelevet.StudentManagmentSystem.securityService.CustomUserDetailsService.loadUserByUsername(..))")
    public void afterLoadUser(JoinPoint joinPoint) {
        log.info("🔚 [After] Method completed: {}", joinPoint.getSignature().getName());
    }
}
