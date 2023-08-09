package home.todayhome.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(staticName = "of")
public class AuthInfo {
    private String userEmail; //멤버 이메일 식별자를 담는 역할
    private Integer userId; //DB에서 조회할 식별자를 담는 역할
}
