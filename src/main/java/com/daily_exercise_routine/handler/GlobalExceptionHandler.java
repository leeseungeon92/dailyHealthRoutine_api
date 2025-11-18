package com.daily_exercise_routine.handler;

import com.daily_exercise_routine.common.ErrorCode;
import com.daily_exercise_routine.common.Response;
import com.daily_exercise_routine.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response> handleAuthenticationException(BadCredentialsException e) {
        ErrorCode errorCode = ErrorCode.AUTH_INVALID_CREDENTIALS;
        return ResponseEntity.status(errorCode.getStatus())
                .body(Response.fail(errorCode));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(Response.fail(errorCode));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> exception(Exception e) {
        e.printStackTrace();

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getStatus())
                .body(Response.fail(errorCode));
    }
}
