package com.daily_exercise_routine.model.controller;

import com.daily_exercise_routine.common.Response;
import com.daily_exercise_routine.model.dto.ExerciseHistoryResponse;
import com.daily_exercise_routine.model.dto.RoutineResponse;
import com.daily_exercise_routine.model.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/complete")
    public ResponseEntity<Void> completeExercise(@AuthenticationPrincipal UserDetails userDetails) {
        routineService.completeExercise(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ExerciseHistoryResponse getHistory(@RequestParam int year,
                                              @RequestParam int month,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return routineService.getHistory(username, year, month);
    }
}
