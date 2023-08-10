package home.todayhome.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class BoardDetailResponse {
    private Integer id;
    private String writerName;
    private String title;
    private String content;
    private Long commentCount;
    private Integer heartCount;
    private Integer visitorCount;
    private LocalDateTime createdAt;
    public static BoardDetailResponse fromDto(BoardDto dto) {
        return BoardDetailResponse.builder()
                .id(dto.getId())
                .writerName(dto.getWriterName())
                .title(dto.getTitle())
                .content(dto.getContent())
                .commentCount(dto.getCommentCount())
                .heartCount(dto.getHeartCount())
                .visitorCount(dto.getVisitorCount())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
