package home.todayhome.security;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation("사용자 email과 id를 이용해서 jwt token 생성")
    @GetMapping("/encode")
    public String jwtEncode(@ApiParam(name = "email", value = "user email", example = "abc@de.com") @RequestParam("email") String userEmail, @ApiParam(name = "user-id", value = "user id", example = "1") @RequestParam("user-id") Integer userId){
        return jwtService.encode(userEmail, userId);
    }

    @ApiOperation("Jwt token 복호화해서 user email과 id 얻기")
    @GetMapping("/decode")
    public Map<String, Object> jwtDecode(@ApiParam(name = "token", value = "jwt token(by email and id)", example = "euKdfkj~~") @RequestParam("token") String jwtToken){
        return jwtService.decode(jwtToken);
    }

    //TODO. swagger에서 테스트해보려면 따로 헤더 설정 필요
    @ApiOperation("Request Header에서 얻은 Jwt token으로 user email과 id 얻기")
    @GetMapping
    public void getMemberGeneratedId(@TokenEmailAndId AuthInfo authInfo){
        log.info("authinfo={}", authInfo);
        log.info("userEmail={}", authInfo.getUserEmail());
        log.info("userId={}", authInfo.getUserId());
        //로그는 잘 뜨는데 왜 리턴은 안될까
        //return authInfo.getMemberGenerationId().toString();
    }
}
