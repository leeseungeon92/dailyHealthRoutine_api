package com.daily_exercise_routine.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;
import org.thymeleaf.extras.springsecurity6.auth.AuthUtils;

import java.time.LocalDateTime;

@Data
public class CommonEntity {

    @Column
    public LocalDateTime createdAt;

    @Column
    public LocalDateTime updatedAt;

    @PrePersist
    public void onPersist() {
        this.createdAt = this.updatedAt;
    }
}
