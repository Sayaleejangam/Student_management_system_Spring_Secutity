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
public class StudentControllerAspect {

	// Run before every method in StudentController
	@Before("execution(* com.technoelevet.StudentManagmentSystem.controller.StudentController.*(..))")
	public void beforeMethod(JoinPoint joinPoint) {
		log.info("📌 [Before - Controller] Method: {}", joinPoint.getSignature().getName());
	}

	// Run after every method in StudentController
	@After("execution(* com.technoelevet.StudentManagmentSystem.controller.StudentController.*(..))")
	public void afterMethod(JoinPoint joinPoint) {
		log.info("✅ [After - Controller] Method completed: {}", joinPoint.getSignature().getName());
	}

	// Run when the method returns successfully
	@AfterReturning(pointcut = "execution(* com.technoelevet.StudentManagmentSystem.controller.StudentController.*(..))", returning = "result")
	public void afterReturningMethod(JoinPoint joinPoint, Object result) {
		log.info("🎯 [AfterReturning - Controller] Method: {} returned: {}", joinPoint.getSignature().getName(),
				result);
	}

	// Run when the method throws an exception
	@AfterThrowing(pointcut = "execution(* com.technoelevet.StudentManagmentSystem.controller.StudentController.*(..))", throwing = "ex")
	public void afterThrowingMethod(JoinPoint joinPoint, Throwable ex) {
		log.error("💥 [AfterThrowing - Controller] Method: {} threw exception: {}", joinPoint.getSignature().getName(),
				ex.getMessage());
	}

	// Optional: Around advice if you want to track execution time
	/*
	 * @Around("execution(* com.technoelevet.StudentManagmentSystem.controller.StudentController.*(..))"
	 * ) public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable
	 * { long start = System.currentTimeMillis();
	 * log.info("🔁 [Around - Controller] Starting method: {}",
	 * joinPoint.getSignature().getName()); Object result = joinPoint.proceed();
	 * long end = System.currentTimeMillis();
	 * log.info("⏱️ [Around - Controller] Completed method: {} in {} ms",
	 * joinPoint.getSignature().getName(), (end - start)); return result; }
	 */
}
