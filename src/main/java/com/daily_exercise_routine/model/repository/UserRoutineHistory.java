package com.daily_exercise_routine.model.repository;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_routine_history")
public class UserRoutineHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PART")
    private String part;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "WEEK_START_DATE")
    private LocalDate weekStartDate;

    @Column(name = "COMPLETED")
    private Boolean completed = false;
}
