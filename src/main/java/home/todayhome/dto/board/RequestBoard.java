package home.todayhome.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class RequestBoard {
    private String title;
    private String content;
}
