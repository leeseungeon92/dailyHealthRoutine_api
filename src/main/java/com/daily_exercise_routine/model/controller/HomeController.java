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

    @GetMapping("/aop-test")
    @ResponseBody
    public String home() {
        return "";
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
