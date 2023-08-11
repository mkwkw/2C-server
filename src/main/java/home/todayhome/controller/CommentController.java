package home.todayhome.controller;

import home.todayhome.dto.CommentDto;
import home.todayhome.security.AuthInfo;
import home.todayhome.security.TokenEmailAndId;
import home.todayhome.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Api(tags = "댓글 API")
@RequestMapping("/comments")
public class CommentController {


    private final CommentService commentService;

    @ApiOperation(
            value = "댓글 작성",
            notes = "회원만 댓글 작성 가능"
    )
    @PostMapping ("/")
    public ResponseEntity<?> createComment(
            @TokenEmailAndId AuthInfo authInfo,
            @RequestBody @Validated CommentDto.CreateCommentRequest createCommentRequest
    ){
        return ResponseEntity.ok(commentService.createComment(createCommentRequest, authInfo.getUserEmail()));
    }

    @ApiOperation(
            value = "댓글 조회",
            notes = "특정 게시글의 해당되는 댓글을 조회, 조회는 비회원도 가능"
    )
    @GetMapping("/")
    public List<CommentDto.CommentResponse> getComments(
            @Valid @RequestParam Integer boardId
    ){
        return commentService.getComments(boardId);
    }

    @ApiOperation(
            value = "댓글 수정",
            notes = "댓글 수정되면 수정시간이 업데이트" +
                    "본인이 쓴 댓글만 수정가능"
    )
    @PutMapping("/{commentId}")
    public CommentDto.CommentResponse updateComment(
            @TokenEmailAndId AuthInfo authInfo,
            @PathVariable Integer commentId,
            @RequestBody @Validated CommentDto.PatchCommentRequest patchCommentRequest
    ){

        return commentService.updateComment(authInfo.getUserEmail(), commentId, patchCommentRequest);
    }

    @ApiOperation(
            value = "댓글 삭제",
            notes = "댓글이 삭제되면 isDeleted(soft delete) = true 로 변경" +
                    "삭제된 게시글은 조회되지 않음"
    )
    @PatchMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @TokenEmailAndId AuthInfo authInfo,
            @PathVariable Integer commentId
    ){

        commentService.deleteComment(authInfo.getUserEmail(), commentId);
        return ResponseEntity.ok("삭제 완료");
    }
}
