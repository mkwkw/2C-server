package home.todayhome.dto;

import home.todayhome.entity.Comment;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDto {

    @ToString
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCommentRequest {

        private Integer boardId;

        private String contents;

        private Integer heartCount;

        private LocalDateTime createAt;

    }

    @ToString
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatchCommentRequest {

        private Integer boardId;

        private Integer commentId;

        private String contents;

        private LocalDateTime modifiedAt;

    }



    @ToString
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponse{

        private String userEmail;

        private Integer boardId;

        private Integer commentId;

        private String contents;

        private Integer heartCount;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

//        private

        public static CommentResponse toResponse(Comment comment){
            return CommentResponse.builder()
                    .userEmail(comment.getUser().getEmail())
                    .boardId(comment.getBoard().getId())
                    .commentId(comment.getId())
                    .contents(comment.getContents())
                    .heartCount(comment.getHeartCount())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build();
        }


        public static List<CommentResponse> toResponse(List<Comment> list){
            return list.stream().map(CommentResponse::toResponse).collect(Collectors.toList());
        }

    }

}
