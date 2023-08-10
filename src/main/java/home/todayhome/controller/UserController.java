package home.todayhome.controller;

import home.todayhome.dto.user.LoginRequest;
import home.todayhome.dto.user.LogoutRequest;
import home.todayhome.dto.user.SignupRequest;
import home.todayhome.dto.user.SignupResponse;
import home.todayhome.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return ResponseEntity.ok(new SignupResponse("회원가입이 완료되었습니다."));
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
    @PostMapping("/logout")
    public ResponseEntity<Map<String,String>> logout(@RequestBody LogoutRequest logoutRequest) {
        return userService.logout(logoutRequest);
    }

}
