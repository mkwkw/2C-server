package home.todayhome.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {
    //AuthInfo에 값을 어떻게 바인딩 해줄 것인가
    private final JwtService jwtService;

    //어떤 대상에 바인딩할 것인가
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthInfo.class.isAssignableFrom(parameter.getParameterType()) && parameter.hasParameterAnnotation(TokenEmailAndId.class);
    }

    //어떤 argument를 넣어줄 것인가
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String authorization = webRequest.getHeader("Authorization"); //request에서 Header에서 JWT 토큰 가져오기
        if(authorization == null || !authorization.startsWith("Bearer")){
            throw new RuntimeException("UnauthorizedException");
        }
        String token = authorization.substring(7); //"Bearer" 뒤에 있는 토큰만 가져오기
        Map<String, Object> decodedToken = jwtService.decode(token); //Map 형태의 decoded 토큰 얻기
        String userEmail = (String) decodedToken.get(JwtService.CLAIM_NAME_USER_EMAIL);
        Integer userId = (Integer) decodedToken.get(JwtService.CLAIM_NAME_USER_ID);
        if(userEmail==null || userId == null){
            throw new RuntimeException("UnauthorizedException");
        }

        return AuthInfo.of(userEmail, userId); //AuthInfo에 값 바인딩
    }
}
