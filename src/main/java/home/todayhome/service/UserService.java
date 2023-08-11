package home.todayhome.service;

import home.todayhome.dto.user.LoginRequest;
import home.todayhome.dto.user.LogoutRequest;
import home.todayhome.dto.user.SignupRequest;
import home.todayhome.entity.User;
import home.todayhome.repository.UserRepository;
import home.todayhome.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(SignupRequest signupResponse) {
        if (userRepository.existsByEmail(signupResponse.getEmail())) {
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupResponse.getPassword());

        User newUser = User.builder()
                .nickname("익명")
                .email(signupResponse.getEmail())
                .password(encodedPassword)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(newUser);
    }

    public ResponseEntity<String> login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepository.findByEmail(email);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            // 이메일에 해당하는 사용자를 찾을 수 없거나 비밀번호가 일치하지 않는 경우 처리
            String responseBody = "{ \"message\": \"이메일 또는 비밀번호가 일치하지 않습니다.\" }";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .headers(headers)
                    .body(responseBody);
        } else {
            // 로그인 성공 처리
            String responseBody = "{ \"message\": \"로그인이 성공적으로 완료되었습니다.\" }";
            //회원인 경우에 jwt token 발급해서 헤더에 넣기
            String token = jwtService.encode(email, user.getId());
            headers.add("Authorization","Bearer "+token);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(responseBody);
        }

    }

    public ResponseEntity<Map<String,String>> logout(LogoutRequest logoutRequest) {
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("message","로그아웃 되었습니다.");

        return ResponseEntity.ok()
                .body(responseBody);
    }
}
