package com.daily_exercise_routine.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    // @Timed 어노테이션이 붙은 메서드의 실행 시간 측정
    @Around("@annotation(com.daily_exercise_routine.annotation.Timed)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            logger.info("⏱️ 성능 측정 - 메서드: {}, 실행시간: {}ms", 
                       joinPoint.getSignature().getName(), executionTime);
            
            // 실행시간이 1000ms 이상이면 경고
            if (executionTime > 1000) {
                logger.warn("⚠️ 느린 메서드 감지! 실행시간: {}ms", executionTime);
            }
        }
    }
}
