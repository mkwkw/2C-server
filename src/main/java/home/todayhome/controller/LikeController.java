package home.todayhome.controller;

import home.todayhome.dto.LikesResponse;
import home.todayhome.dto.Response;
import home.todayhome.security.AuthInfo;
import home.todayhome.security.TokenEmailAndId;
import home.todayhome.service.LikeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class LikeController {
    private final LikeService likeService;

    @ApiOperation(
            value = "게시글 좋아요 / 좋아요 취소",
            notes= "게시글의 ID를 통해 해당 게시글의 좋아요 갯수 늘리거나(좋아요를 누른 적이 없는 경우), 줄인다(좋아요를 눌렀던 경우)."
    )
    @ApiImplicitParam(
            name = "id"
            ,value="게시글 id"
            ,required = true
            ,paramType = "path"
            ,defaultValue = "None"
    )
    @PostMapping("/posts/{id}/likes")
    public Response<LikesResponse> BoardLikePlus(
            @PathVariable String id,
            @TokenEmailAndId AuthInfo authInfo
    ) {
        Integer intBoardId = Integer.valueOf(id);
        Integer userId = authInfo.getUserId();
        log.info("" + userId);
        String likeWhere = "Board";
        LikesResponse likesResponse = likeService.likePlus(intBoardId, userId, likeWhere);
        return Response.success(likesResponse);
    }

    @ApiOperation(
            value = "댓글 좋아요 / 좋아요 취소",
            notes= "댓글의 ID를 통해 해당 게시글의 좋아요 갯수 늘리거나(좋아요를 누른 적이 없는 경우), 줄인다(좋아요를 눌렀던 경우)."
    )
    @PostMapping("/comments/{id}/likes")
    public Response<LikesResponse> CommentLikePlus(
            @PathVariable String id,
            @TokenEmailAndId AuthInfo authInfo
    ) {
        Integer intCommentId = Integer.valueOf(id);
        Integer userId = authInfo.getUserId();

        String likeWhere = "Comment";
        LikesResponse likesResponse = likeService.likePlus(intCommentId, userId, likeWhere);
        return Response.success(likesResponse);
    }
}