package home.todayhome.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BoardResponse {
    private Integer id;
    private String writerName;
    private String title;
    private Long commentCount;
    private Integer heartCount;
    private Integer visitorCount;
    public static BoardResponse fromDto(BoardDto dto) {
        return BoardResponse.builder()
                .id(dto.getId())
                .writerName(dto.getWriterName())
                .title(dto.getTitle())
                .commentCount(dto.getCommentCount())
                .heartCount(dto.getHeartCount())
                .visitorCount(dto.getVisitorCount())
                .build();
    }
}