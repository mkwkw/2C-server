package home.todayhome.service;

import home.todayhome.entity.LikesBoard;
import home.todayhome.entity.LikesComment;
import home.todayhome.repository.board.BoardLikeRepository;
import home.todayhome.repository.comment.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    public Integer likePlus(Integer id, Integer userId, String likeWhere) {
        if (likeWhere.equals("Board")) {
            //BoardLike 올리기
            LikesBoard createdLike;
            LikesBoard like = LikesBoard.builder()
                    .boardId(id)
                    .userId(userId)
                    .createdAt(LocalDateTime.now())
                    .isDeleted(false)
                    .isLiked(true)
                    .build();
            createdLike = boardLikeRepository.save(like);
            return createdLike.getId();
        } else {
            //CommentLike 올리기
            LikesComment createdLike;
            LikesComment like = LikesComment.builder()
                    .commentId(id)
                    .userId(userId)
                    .createdAt(LocalDateTime.now())
                    .isDeleted(false)
                    .isLiked(true)
                    .build();
            createdLike = commentLikeRepository.save(like);
            return createdLike.getId();
        }
    }
}
