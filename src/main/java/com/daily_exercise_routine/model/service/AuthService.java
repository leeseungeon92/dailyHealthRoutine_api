package com.daily_exercise_routine.model.service;

import com.daily_exercise_routine.annotation.Secured;
import com.daily_exercise_routine.annotation.Timed;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    // 간단한 사용자 저장소 (실제로는 데이터베이스 사용)
    private Map<String, String> users = new HashMap<>();
    private Map<String, String> sessions = new HashMap<>();

    public AuthService() {
        // 테스트용 사용자 데이터
        users.put("admin", "admin123");
        users.put("user1", "password123");
        users.put("test", "test123");
    }

    @Timed("사용자 로그인")
    public String login(String username, String password) {
        // 로그인 로직
        if (users.containsKey(username) && users.get(username).equals(password)) {
            String sessionId = UUID.randomUUID().toString();
            sessions.put(sessionId, username);
            
            return "로그인 성공! 사용자: " + username + ", 세션ID: " + sessionId;
        } else {
            throw new RuntimeException("로그인 실패: 잘못된 사용자명 또는 비밀번호");
        }
    }

    @Timed("사용자 로그아웃")
    public String logout(String sessionId) {
        if (sessions.containsKey(sessionId)) {
            String username = sessions.remove(sessionId);
            return "로그아웃 성공! 사용자: " + username;
        } else {
            throw new RuntimeException("로그아웃 실패: 유효하지 않은 세션");
        }
    }

    @Timed("세션 검증")
    public boolean isLoggedIn(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @Timed("사용자 정보 조회")
    @Secured(roles = {"USER", "ADMIN"})
    public String getUserInfo(String sessionId) {
        if (sessions.containsKey(sessionId)) {
            String username = sessions.get(sessionId);
            return "사용자 정보 - 이름: " + username + ", 세션: " + sessionId;
        } else {
            throw new RuntimeException("인증되지 않은 사용자");
        }
    }

    @Timed("관리자 기능")
    @Secured(roles = {"ADMIN"})
    public String adminFunction(String sessionId) {
        if (sessions.containsKey(sessionId)) {
            String username = sessions.get(sessionId);
            return "관리자 기능 실행 - 사용자: " + username;
        } else {
            throw new RuntimeException("관리자 권한이 필요합니다");
        }
    }

    // 테스트용 메서드들
    public String getSessionCount() {
        return "현재 활성 세션 수: " + sessions.size();
    }

    public String getAllUsers() {
        return "등록된 사용자: " + users.keySet().toString();
    }
}
