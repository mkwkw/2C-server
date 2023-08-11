package home.todayhome.controller;

import home.todayhome.dto.user.LoginRequest;
import home.todayhome.dto.user.LogoutRequest;
import home.todayhome.dto.user.SignupRequest;
import home.todayhome.dto.user.SignupResponse;
import home.todayhome.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Api(tags = "회원 API")
public class UserController {
    private final UserService userService;
    @ApiOperation("회원가입")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return ResponseEntity.ok(new SignupResponse("회원가입이 완료되었습니다."));
    }

    @ApiOperation("로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
    @ApiOperation("로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<Map<String,String>> logout(@RequestBody LogoutRequest logoutRequest) {
        return userService.logout(logoutRequest);
    }

}
