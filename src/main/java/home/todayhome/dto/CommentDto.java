package home.todayhome.dto;

import home.todayhome.entity.Comment;
import lombok.*;

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
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponse{

        private String userEmail;

        private Integer boardId;

        private Integer commentId;

        private String comments;

        private Integer heartCount;

        private LocalDateTime createdAt;

//        private

        public static CommentResponse toResponse(Comment comment){
            return CommentResponse.builder()
                    .userEmail(comment.getUser().getEmail())
                    .boardId(comment.getBoard().getId())
                    .commentId(comment.getId())
                    .comments(comment.getContents())
                    .heartCount(comment.getHeartCount())
                    .createdAt(comment.getCreatedAt())
                    .build();
        }


        public static List<CommentResponse> toResponse(List<Comment> list){
            return list.stream().map(CommentResponse::toResponse).collect(Collectors.toList());
        }

    }

}
