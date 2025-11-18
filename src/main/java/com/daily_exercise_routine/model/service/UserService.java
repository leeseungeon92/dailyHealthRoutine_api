package com.daily_exercise_routine.model.service;

import com.daily_exercise_routine.annotation.Secured;
import com.daily_exercise_routine.annotation.Timed;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Timed("사용자 등록")
    public String registerUser(String username, String email) {
        simulateDelay(300);
        return username + " 사용자가 등록되었습니다. 이메일: " + email;
    }

    @Timed("사용자 정보 조회")
    public String getUserInfo(String userId) {
        simulateDelay(150);
        return "사용자 ID: " + userId + "의 정보를 조회했습니다.";
    }

    @Secured(roles = {"ADMIN"})
    @Timed("사용자 삭제")
    public String deleteUser(String userId) {
        simulateDelay(200);
        return "사용자 " + userId + "가 삭제되었습니다.";
    }

    @Secured(roles = {"USER", "ADMIN"})
    @Timed("사용자 프로필 업데이트")
    public String updateUserProfile(String userId, String profileData) {
        simulateDelay(400);
        return "사용자 " + userId + "의 프로필이 업데이트되었습니다.";
    }

    private void simulateDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
