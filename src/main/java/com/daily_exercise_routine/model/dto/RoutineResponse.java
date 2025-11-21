package com.daily_exercise_routine.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoutineResponse {
    private String dayOfWeek;
    private String date;
    private List<Exercise> exercises;
    private boolean completed;

    @Setter
    @Getter
    public static class Exercise {
        private String name;
        private String part;
        private String sets;
        private String raps;
        private String memo;
        private String image;
    }
}
