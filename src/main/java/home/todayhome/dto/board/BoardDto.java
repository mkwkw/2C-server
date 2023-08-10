package home.todayhome.dto.board;

import home.todayhome.entity.Board;
import lombok.*;

import java.time.LocalDateTime;


@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
public class BoardDto {
    private Integer id;
    private String writerName;
    private String title;
    private String content;
    private Long commentCount;
    private Integer heartCount;
    private Integer visitorCount;
    private LocalDateTime createdAt;

    public static BoardDto fromEntity(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .writerName(board.getUser().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .heartCount(board.getHeartCount())
                .visitorCount(board.getVisitorCount())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
