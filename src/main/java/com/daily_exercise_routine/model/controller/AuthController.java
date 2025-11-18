package com.daily_exercise_routine.model.controller;

import com.daily_exercise_routine.Jwt.JwtTokenProvider;
import com.daily_exercise_routine.common.ErrorCode;
import com.daily_exercise_routine.common.Response;
import com.daily_exercise_routine.exception.BusinessException;
import com.daily_exercise_routine.model.dto.LoginRequest;
import com.daily_exercise_routine.model.dto.LoginResponse;
import com.daily_exercise_routine.model.dto.SignUpRequest;
import com.daily_exercise_routine.model.repository.UserEntity;
import com.daily_exercise_routine.model.repository.UserRepository;
import com.daily_exercise_routine.model.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        String encodedPw = passwordEncoder.encode(signUpRequest.getPassword());
        UserEntity user = new UserEntity(signUpRequest.getUsername(), encodedPw, "ROLE_USER");
        userRepository.save(user);

        Response body = Response.success(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        String token = jwtTokenProvider.createToken(authentication.getName());

        LoginResponse loginResponse = new LoginResponse(token, request.getUsername());

        Response body = Response.success(loginResponse);

        return ResponseEntity.ok(body);
    }

    // 로그인 처리
    @GetMapping("/auth/login/{username}/{password}")
    @ResponseBody
    public String login(@PathVariable String username, @PathVariable String password) {
        try {
            return authService.login(username, password);
        } catch (Exception e) {
            return "로그인 실패: " + e.getMessage();
        }
    }

    // 로그아웃 처리
    @GetMapping("/auth/logout/{sessionId}")
    @ResponseBody
    public String logout(@PathVariable String sessionId) {
        try {
            return authService.logout(sessionId);
        } catch (Exception e) {
            return "로그아웃 실패: " + e.getMessage();
        }
    }

    // 사용자 정보 조회
    @GetMapping("/auth/userinfo/{sessionId}")
    @ResponseBody
    public String getUserInfo(@PathVariable String sessionId) {
        try {
            return authService.getUserInfo(sessionId);
        } catch (Exception e) {
            return "사용자 정보 조회 실패: " + e.getMessage();
        }
    }

    // 관리자 기능
    @GetMapping("/auth/admin/{sessionId}")
    @ResponseBody
    public String adminFunction(@PathVariable String sessionId) {
        try {
            return authService.adminFunction(sessionId);
        } catch (Exception e) {
            return "관리자 기능 실행 실패: " + e.getMessage();
        }
    }

    // 세션 상태 확인
    @GetMapping("/auth/sessions")
    @ResponseBody
    public String getSessionCount() {
        return authService.getSessionCount();
    }

    // 등록된 사용자 목록
    @GetMapping("/auth/users")
    @ResponseBody
    public String getAllUsers() {
        return authService.getAllUsers();
    }

    // 로그인 상태 확인
    @GetMapping("/auth/check/{sessionId}")
    @ResponseBody
    public String checkLogin(@PathVariable String sessionId) {
        boolean isLoggedIn = authService.isLoggedIn(sessionId);
        return "로그인 상태: " + (isLoggedIn ? "로그인됨" : "로그아웃됨");
    }
}
