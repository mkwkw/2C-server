package home.todayhome.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AuthInfo {
    private String userEmail; //멤버 이메일 식별자를 담는 역할
}
