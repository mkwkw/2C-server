package home.todayhome.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    //@Value("${jwt.secret.key}")
    @Value("abc") //TODO. 나중에 위에 걸로 바꾸기 mysql.yml에 설정
    private String secretKey; //yaml에서 가져오기

    public static final String CLAIM_NAME_USER_EMAIL = "useremail";
    public static final String CLAIM_NAME_USER_ID = "userid";
    private Algorithm algorithm;
    private JWTVerifier jwtVerifier;

    @PostConstruct
    private  void init(){
        algorithm = Algorithm.HMAC256(secretKey);
        jwtVerifier = JWT.require(algorithm).build();
    }

    //memberId -> jwt 토큰
    public String encode(String userEmail, Integer userId){
        LocalDateTime expireAt = LocalDateTime.now().plusWeeks(4L);
        Date date = Timestamp.valueOf(expireAt);

        return JWT.create()
                .withClaim(CLAIM_NAME_USER_EMAIL, userEmail)
                .withClaim(CLAIM_NAME_USER_ID, userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    //jwt토큰 -> userEmail
    public Map<String, Object> decode(String token){
        try{
            DecodedJWT jwt = jwtVerifier.verify(token);
            return Map.of(
                    CLAIM_NAME_USER_EMAIL, jwt.getClaim(CLAIM_NAME_USER_EMAIL).asString(),
                    CLAIM_NAME_USER_ID, jwt.getClaim(CLAIM_NAME_USER_ID).asInt()
                );
        }
        catch (JWTVerificationException e){
            log.warn("Failed to decode jwt. token: {}", token, e);
            return null;
        }
    }
}
