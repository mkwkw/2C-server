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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void signup(SignupRequest signupResponse) {
        if (userRepository.existsByEmail(signupResponse.getEmail())) {
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }

        User newUser = User.builder()
                .nickname("익명")
                .email(signupResponse.getEmail())
                .password(signupResponse.getPassword())
                .createdAt(LocalDateTime.now())
                .build();// 패스워드 암호화
        userRepository.save(newUser);
    }

    public ResponseEntity<String> login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepository.findByEmail(email);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");

        if(user != null && user.getPassword().equals(password)){

            //회원인 경우에 jwt token 발급해서 헤더에 넣기
            String token = jwtService.encode(email, user.getId());
            headers.add("Authorization","Bearer "+token);
            String responseBody = "{ message: 로그인이 성공적으로 완료되었습니다.}";
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(responseBody);
        } else {
            String responseBody = "{ message: 아이디 또는 비밀번호가 일치하지 않습니다.}";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .headers(headers)
                    .body(responseBody);
        }
    }

    public ResponseEntity<Map<String,String>> logout(LogoutRequest logoutRequest) {
        String email = logoutRequest.getEmail();

        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("message","로그아웃 되었습니다.");

        return ResponseEntity.ok()
                .body(responseBody);
    }
}
