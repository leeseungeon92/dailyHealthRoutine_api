package com.daily_exercise_routine.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<RoutineEntity, Long> {

    List<RoutineEntity> findByPart(String part);
}
