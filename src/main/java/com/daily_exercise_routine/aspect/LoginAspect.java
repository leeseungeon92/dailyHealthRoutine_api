package com.daily_exercise_routine.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoginAspect.class);

    // 로그인 시도 전
    @Before("execution(* com.daily_exercise_routine.service.AuthService.login(..))")
    public void beforeLogin(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String username = args.length > 0 ? (String) args[0] : "unknown";
        
        logger.info("🔐 로그인 시도 시작");
        logger.info("사용자명: {}", username);
        logger.info("IP 주소: {}", getClientIP()); // 실제로는 HttpServletRequest에서 가져옴
        logger.info("시도 시간: {}", java.time.LocalDateTime.now());
    }

    // 로그인 성공 후
    @AfterReturning(pointcut = "execution(* com.daily_exercise_routine.service.AuthService.login(..))", returning = "result")
    public void afterLoginSuccess(JoinPoint joinPoint, Object result) {
        logger.info("✅ 로그인 성공!");
        logger.info("사용자 정보: {}", result);
        logger.info("세션 생성 시간: {}", java.time.LocalDateTime.now());
        logger.info("=========================");
    }

    // 로그인 실패 후
    @AfterThrowing(pointcut = "execution(* com.daily_exercise_routine.service.AuthService.login(..))", throwing = "exception")
    public void afterLoginFailure(JoinPoint joinPoint, Exception exception) {
        Object[] args = joinPoint.getArgs();
        String username = args.length > 0 ? (String) args[0] : "unknown";
        
        logger.warn("❌ 로그인 실패!");
        logger.warn("사용자명: {}", username);
        logger.warn("실패 사유: {}", exception.getMessage());
        logger.warn("실패 시간: {}", java.time.LocalDateTime.now());
        logger.warn("=========================");
    }

    // 로그아웃 시도 전
    @Before("execution(* com.daily_exercise_routine.service.AuthService.logout(..))")
    public void beforeLogout(JoinPoint joinPoint) {
        logger.info("🚪 로그아웃 시도 시작");
        logger.info("로그아웃 시간: {}", java.time.LocalDateTime.now());
    }

    // 로그아웃 성공 후
    @AfterReturning("execution(* com.daily_exercise_routine.service.AuthService.logout(..))")
    public void afterLogoutSuccess(JoinPoint joinPoint) {
        logger.info("✅ 로그아웃 성공!");
        logger.info("세션 종료 시간: {}", java.time.LocalDateTime.now());
        logger.info("=========================");
    }

    // 보호된 리소스 접근 시도 전
    @Before("execution(* com.daily_exercise_routine.service.*.get*(..)) || " +
            "execution(* com.daily_exercise_routine.service.*.create*(..)) || " +
            "execution(* com.daily_exercise_routine.service.*.update*(..))")
    public void beforeProtectedResourceAccess(JoinPoint joinPoint) {
        logger.info("🔒 보호된 리소스 접근 시도");
        logger.info("메서드: {}", joinPoint.getSignature().getName());
        logger.info("접근 시간: {}", java.time.LocalDateTime.now());
        
        // 실제로는 세션에서 사용자 정보를 확인
        if (isUserLoggedIn()) {
            logger.info("✅ 인증된 사용자 접근");
        } else {
            logger.warn("⚠️ 미인증 사용자 접근 시도");
        }
    }

    private String getClientIP() {
        // 실제로는 HttpServletRequest에서 가져옴
        return "127.0.0.1";
    }

    private boolean isUserLoggedIn() {
        // 실제로는 세션에서 로그인 상태 확인
        return true; // 테스트용
    }
}
