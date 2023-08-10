package home.todayhome.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RequestUpdateBoard {
    private String title;
    private String content;
}
