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

    @GetMapping("/")
    public List<CommentDto.CommentResponse> getComments(
            @Valid @RequestParam Integer boardId
    ){
        return commentService.getComments(boardId);
    }

    @PutMapping("/{commentId}")
    public CommentDto.CommentResponse updateComment(
            @TokenEmailAndId AuthInfo authInfo,
            @PathVariable Integer commentId,
            @RequestBody @Validated CommentDto.PatchCommentRequest patchCommentRequest
    ){

        return commentService.updateComment(authInfo.getUserEmail(), commentId, patchCommentRequest);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @TokenEmailAndId AuthInfo authInfo,
            @PathVariable Integer commentId,
            @RequestBody @Validated CommentDto.DeleteCommentRequest deleteCommentRequest
    ){

        commentService.deleteComment(authInfo.getUserEmail(), commentId, deleteCommentRequest);
        return ResponseEntity.ok("삭제 완료");
    }
}