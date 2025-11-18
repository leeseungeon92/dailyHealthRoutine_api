package com.daily_exercise_routine.model.controller;

import com.daily_exercise_routine.model.service.AuthService;
import com.daily_exercise_routine.model.service.ExerciseService;
import com.daily_exercise_routine.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class HomeController {

    @Autowired
    private ExerciseService exerciseService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthService authService;

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return """
            <h1>🚀 Spring AOP 실습 프로젝트</h1>
            <h2>📋 기본 기능</h2>
            <ul>
                <li><a href="/test">테스트 페이지</a></li>
                <li><a href="/login">로그인 페이지</a></li>
            </ul>
            
            <h2>🔐 로그인 AOP 테스트</h2>
            <ul>
                <li><a href="/auth/login/admin/admin123">admin 로그인</a></li>
                <li><a href="/auth/login/user1/password123">user1 로그인</a></li>
                <li><a href="/auth/login/wrong/password">잘못된 로그인</a></li>
                <li><a href="/auth/sessions">세션 상태</a></li>
                <li><a href="/auth/users">사용자 목록</a></li>
            </ul>
            
            <h2>⚡ 일반 AOP 테스트</h2>
            <ul>
                <li><a href="/exercise/create">운동 계획 생성</a></li>
                <li><a href="/exercise/slow">느린 작업</a></li>
                <li><a href="/user/register">사용자 등록</a></li>
            </ul>
            """;
    }
    
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "hello spring test";
    }

    // AOP 테스트 엔드포인트들
    @GetMapping("/exercise/create")
    @ResponseBody
    public String createExercise() {
        return exerciseService.createExercisePlan("유산소");
    }

    @GetMapping("/exercise/history")
    @ResponseBody
    public String getHistory() {
        return exerciseService.getExerciseHistory("user123");
    }

    @GetMapping("/exercise/delete")
    @ResponseBody
    public String deleteExercise() {
        return exerciseService.deleteExerciseData("data456");
    }

    @GetMapping("/exercise/slow")
    @ResponseBody
    public String slowOperation() {
        return exerciseService.slowOperation();
    }

    @GetMapping("/user/register")
    @ResponseBody
    public String registerUser() {
        return userService.registerUser("홍길동", "hong@example.com");
    }

    @GetMapping("/user/info")
    @ResponseBody
    public String getUserInfo() {
        return userService.getUserInfo("user789");
    }

    @GetMapping("/user/delete")
    @ResponseBody
    public String deleteUser() {
        return userService.deleteUser("user789");
    }
}
