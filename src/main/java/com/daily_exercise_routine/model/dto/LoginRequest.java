package com.daily_exercise_routine.model.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
