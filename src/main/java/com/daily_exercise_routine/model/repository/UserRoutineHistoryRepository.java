package com.daily_exercise_routine.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRoutineHistoryRepository extends JpaRepository<UserRoutineHistory, Long> {
    List<UserRoutineHistory> findByUsernameAndWeekStartDate(String username, LocalDate weekStartDate);
}
