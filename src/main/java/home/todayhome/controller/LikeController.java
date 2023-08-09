package home.todayhome.controller;

import home.todayhome.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeService likeService;
    @PostMapping("/posts/{id}/like")
    //TODO : 사용자 ID 어떻게 받아올 것인가. 받아온 것 어디에서 가져올 것인가.
    public String BoardLikePlus(@PathVariable String boardId){
        Integer intBoardId = Integer.valueOf(boardId);
        //임시 사용자 mock id
        Integer userId = 3;
        String likeWhere = "Board";
        Integer likeId = likeService.likePlus(intBoardId, userId, likeWhere);
        return userId + "가 좋아요 눌렸습니다."+likeId;
    }
}