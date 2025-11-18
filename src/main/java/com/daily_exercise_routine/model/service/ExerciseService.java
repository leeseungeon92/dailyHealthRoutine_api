package com.daily_exercise_routine.model.service;

import com.daily_exercise_routine.annotation.Secured;
import com.daily_exercise_routine.annotation.Timed;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

    @Timed("운동 계획 생성")
    public String createExercisePlan(String type) {
        // 실제로는 데이터베이스에 저장하는 로직
        simulateDelay(500); // 500ms 지연 시뮬레이션
        return type + " 운동 계획이 생성되었습니다.";
    }

    @Timed("운동 기록 조회")
    public String getExerciseHistory(String userId) {
        simulateDelay(200);
        return userId + "님의 운동 기록을 조회했습니다.";
    }

    @Secured(roles = {"ADMIN"})
    @Timed("운동 데이터 삭제")
    public String deleteExerciseData(String dataId) {
        simulateDelay(100);
        return "운동 데이터 " + dataId + "가 삭제되었습니다.";
    }

    @Secured(roles = {"USER", "ADMIN"})
    @Timed("운동 통계 조회")
    public String getExerciseStatistics(String userId) {
        simulateDelay(800);
        return userId + "님의 운동 통계를 조회했습니다.";
    }

    @Timed("느린 작업 시뮬레이션")
    public String slowOperation() {
        simulateDelay(1500); // 1.5초 지연 - 경고가 발생할 것
        return "느린 작업이 완료되었습니다.";
    }

    private void simulateDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
