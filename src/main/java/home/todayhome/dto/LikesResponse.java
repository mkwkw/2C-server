package home.todayhome.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LikesResponse {

    @ApiModelProperty(name="likeId", value = "likes ID", example = "1")
    private Integer likesId;
    @ApiModelProperty(name="isLiked", value = "좋아요 여부", example = "true")
    private Boolean isLiked;
    @ApiModelProperty(name="likesAtContent", value = "좋아요가 적용된 댓글 혹은 게시글의 총 좋아요 수", example = "2")
    private Integer likesAtContent;
}
