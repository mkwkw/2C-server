package home.todayhome.controller;

import home.todayhome.dto.LikesResponse;
import home.todayhome.dto.Response;
import home.todayhome.security.AuthInfo;
import home.todayhome.security.TokenEmailAndId;
import home.todayhome.service.LikeService;
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