package com.daily_exercise_routine.model.dto;

import com.daily_exercise_routine.common.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
