package com.daily_exercise_routine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseHistoryResponse {
    private List<Integer> completedDays;
    private Integer totalDayInMonth;
}
