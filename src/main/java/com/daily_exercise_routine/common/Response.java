package com.daily_exercise_routine.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Response {
    private String code;
    private String message;
    private int status;
    private Object data;

    public static Response success(Object data) {
        return new Response(
                "SUCCESS",
                "성공(success)",
                HttpStatus.OK.value(),
                data
        );
    }

    public static Response fail(ErrorCode errorCode) {
        return new Response(
                errorCode.getCode(),
                errorCode.getMessage(),
                errorCode.getStatus().value(),
                null);
    }

}
