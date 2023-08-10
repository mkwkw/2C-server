package home.todayhome.controller;

import home.todayhome.dto.CommentDto;
import home.todayhome.security.AuthInfo;
import home.todayhome.security.TokenEmailAndId;
import home.todayhome.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor()
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping ("/")
    public ResponseEntity<?> createComment(
            @TokenEmailAndId AuthInfo authInfo,
            @RequestBody @Validated CommentDto.CreateCommentRequest createCommentRequest
    ){
        return ResponseEntity.ok(commentService.createComment(createCommentRequest, authInfo.getUserEmail()));
    }

}
