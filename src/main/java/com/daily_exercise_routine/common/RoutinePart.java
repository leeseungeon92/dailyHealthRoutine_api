package com.daily_exercise_routine.common;

import lombok.Getter;

@Getter
public enum RoutinePart {

    CHEST("가슴"),
    BACK("등"),
    ARM("팔"),
    SHOULDER("어깨"),
    AEROBIC("유산소"),
    LOWER("하체");

    private final String part;

    RoutinePart(String part) {
        this.part = part;
    }
}
