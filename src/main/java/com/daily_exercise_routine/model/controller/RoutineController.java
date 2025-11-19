package com.daily_exercise_routine.model.controller;

import com.daily_exercise_routine.common.Response;
import com.daily_exercise_routine.model.dto.RoutineResponse;
import com.daily_exercise_routine.model.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercise")
public class RoutineController {

    private final RoutineService routineService;

    @GetMapping("/getRoutine")
    public RoutineResponse getRoutine(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return routineService.getRoutine(username);
    }
}
