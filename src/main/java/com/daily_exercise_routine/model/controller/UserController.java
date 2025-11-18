package com.daily_exercise_routine.model.controller;

import com.daily_exercise_routine.common.ErrorCode;
import com.daily_exercise_routine.common.Response;
import com.daily_exercise_routine.exception.BusinessException;
import com.daily_exercise_routine.model.dto.UserResponse;
import com.daily_exercise_routine.model.repository.UserEntity;
import com.daily_exercise_routine.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/myinfo")
    public Response getUserInfo(Authentication authentication) {
        String username = authentication.getName();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS));

        UserResponse response = new UserResponse(user.getUsername(), user.getRole());

        return Response.success(response);
    }
}
