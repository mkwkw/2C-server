package home.todayhome.controller;

import home.todayhome.dto.LikesResponse;
import home.todayhome.dto.Response;
import home.todayhome.entity.Likes;
import home.todayhome.entity.LikesBoard;
import home.todayhome.entity.LikesComment;
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
@RequestMapping("/api")
@Slf4j
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/post/{id}/like")
    public Response<LikesResponse> BoardLikePlus(
            @PathVariable String id,
            @TokenEmailAndId AuthInfo authInfo
    ) {
        Integer intBoardId = Integer.valueOf(id);
        Integer userId = authInfo.getUserId();
        String likeWhere = "Board";
        LikesResponse likesResponse = likeService.likePlus(intBoardId, userId, likeWhere);
        return Response.success(likesResponse);
    }

    @PostMapping("/comments/{id}/like")
    public Response<LikesResponse> CommentLikePlus(
            @PathVariable String id,
            @TokenEmailAndId AuthInfo authInfo
    ) {
        Integer intCommentId = Integer.valueOf(id);
//        TODO : 회원가입 로그인 구현 되면 살리기
//        Integer userId = authInfo.getUserId();
        //임시 사용자 mock id
        Integer userId = 1;
        String likeWhere = "Comment";
        LikesResponse likesResponse = likeService.likePlus(intCommentId, userId, likeWhere);
        return Response.success(likesResponse);
    }
}