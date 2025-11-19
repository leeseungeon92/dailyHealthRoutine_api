package com.daily_exercise_routine.model.repository;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "routine")
@Entity
public class RoutineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PART")
    private String part;

    @Column(name = "SETS")
    private String sets;

    @Column(name = "NAME")
    private String name;

    @Column(name = "RAPS")
    private String raps;

    @Column(name = "MEMO")
    private String memo;
}
