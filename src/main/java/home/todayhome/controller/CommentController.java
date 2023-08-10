package home.todayhome.controller;

import home.todayhome.dto.CommentDto;
import home.todayhome.security.AuthInfo;
import home.todayhome.security.TokenEmailAndId;
import home.todayhome.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor()
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping ("/comments")
    public ResponseEntity<?> createComment(
            @TokenEmailAndId AuthInfo authInfo,
            @RequestBody CommentDto.CreateCommentRequest createCommentRequest
    ){
        return ResponseEntity.ok(commentService.createComment(createCommentRequest, authInfo.getUserEmail()));
    }





}
