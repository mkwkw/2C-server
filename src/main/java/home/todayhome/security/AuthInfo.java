package home.todayhome.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AuthInfo {

    @Builder.Default
    @ApiModelProperty(name = "userEmail", value = "user email", example = "abc@de.com", hidden=true)
    private String userEmail = ""; //멤버 이메일 식별자를 담는 역할

    @Builder.Default
    @ApiModelProperty(name = "userId", value = "user id", example = "1", hidden = true)
    private Integer userId = -1; //DB에서 조회할 식별자를 담는 역할

}
