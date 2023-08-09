package home.todayhome.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/jwt")
public class JwtTestController {

    private final JwtService jwtService;

    @GetMapping("/encode")
    public String jwtEncode(@RequestParam("email") String userEmail, @RequestParam("user-id") Integer userId){
        return jwtService.encode(userEmail, userId);
    }

    @GetMapping("/decode")
    public Map<String, Object> jwtDecode(@RequestParam("token") String jwtToken){
        return jwtService.decode(jwtToken);
    }

    @GetMapping
    public void getMemberGeneratedId(@TokenEmailAndId AuthInfo authInfo){
        log.info("authinfo={}", authInfo);
        log.info("userEmail={}", authInfo.getUserEmail());
        log.info("userId={}", authInfo.getUserId());
        //로그는 잘 뜨는데 왜 리턴은 안될까
        //return authInfo.getMemberGenerationId().toString();
    }
}
