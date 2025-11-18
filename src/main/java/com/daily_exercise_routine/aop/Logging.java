package com.daily_exercise_routine.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class Logging {

    @Before("execution(* com.daily_exercise_routine.controller..*(..))")
    public void logRequest(JoinPoint joinPoint) {

        log.info("Request = {}", joinPoint.getSignature());
        log.info("Args= {}", joinPoint.getArgs());
    }

    @AfterThrowing(
            pointcut = "execution(* com.daily_exercise_routine.controller..*(..)) || execution(* com.daily_exercise_routine.model.service..*(..))",
            throwing = "e"
    )    public void logException(JoinPoint joinPoint, Exception e) {
        log.error("Exception = {} - {}", joinPoint.getSignature(), e.getMessage());

    }
}
