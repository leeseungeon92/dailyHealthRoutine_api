package com.daily_exercise_routine.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // 모든 서비스 메서드 실행 전 로깅
    @Before("execution(* com.daily_exercise_routine.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("=== 메서드 실행 시작 ===");
        logger.info("메서드: {}", joinPoint.getSignature().getName());
        logger.info("클래스: {}", joinPoint.getTarget().getClass().getSimpleName());
        
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            logger.info("매개변수: {}", java.util.Arrays.toString(args));
        }
    }

    // 모든 서비스 메서드 실행 후 로깅
    @AfterReturning(pointcut = "execution(* com.daily_exercise_routine.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("=== 메서드 실행 완료 ===");
        logger.info("메서드: {}", joinPoint.getSignature().getName());
        logger.info("반환값: {}", result);
        logger.info("=========================");
    }
}
