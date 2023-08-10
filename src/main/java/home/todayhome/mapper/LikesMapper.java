package home.todayhome.mapper;

import home.todayhome.dto.LikesResponse;
import home.todayhome.entity.LikesBoard;
import home.todayhome.entity.LikesComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LikesMapper {
    LikesMapper INSTANCE = Mappers.getMapper(LikesMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "likesId"),
            @Mapping(source = "board.heartCount", target = "likesAtContent")
    })
    LikesResponse LikesBoardToLikesResponse(LikesBoard likesBoard);

    @Mappings({
            @Mapping(source = "id", target = "likesId"),
            @Mapping(source = "comment.heartCount", target = "likesAtContent")
    })
    LikesResponse LikesCommentToLikesResponse(LikesComment likesBoard);
}
