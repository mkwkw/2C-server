package home.todayhome.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Errorcode {
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    NOT_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
    private final HttpStatus status;
    private final String message;
}
