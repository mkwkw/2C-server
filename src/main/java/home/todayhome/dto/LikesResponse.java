package home.todayhome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LikesResponse {
    private Integer likesId;
    private Boolean isLiked;
    private Integer likesAtContent;
}
