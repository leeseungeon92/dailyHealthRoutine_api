package com.daily_exercise_routine.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    // @Secured 어노테이션이 붙은 메서드의 보안 검증
    @Around("@annotation(com.daily_exercise_routine.annotation.Secured)")
    public Object checkSecurity(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("🔒 보안 검증 시작 - 메서드: {}", joinPoint.getSignature().getName());
        
        // 간단한 보안 검증 로직 (실제로는 더 복잡한 검증이 필요)
        String methodName = joinPoint.getSignature().getName();
        
        if (methodName.contains("delete") || methodName.contains("remove")) {
            logger.warn("⚠️ 위험한 작업 감지: {}", methodName);
            // 실제 환경에서는 예외를 던지거나 추가 검증을 수행
        }
        
        if (methodName.contains("admin")) {
            logger.info("🔐 관리자 권한 필요 작업: {}", methodName);
            // 실제 환경에서는 사용자 권한을 확인
        }
        
        Object result = joinPoint.proceed();
        logger.info("✅ 보안 검증 완료 - 메서드: {}", methodName);
        
        return result;
    }
}
