package home.todayhome.service;

import home.todayhome.dto.LikesResponse;
import home.todayhome.entity.*;
import home.todayhome.exception.NotFoundException;
import home.todayhome.repository.LikeBoardRepository;
import home.todayhome.repository.BoardRepository;
import home.todayhome.repository.LikeCommentRepository;
import home.todayhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final BoardRepository boardRepository;

//    TODO :
//     private final CommentRepository commentRepository

    private final UserRepository userRepository;

    private final LikeBoardRepository likeBoardRepository;
    private final LikeCommentRepository likeCommentRepository;

    public LikesResponse likePlus(Integer id, Integer userId, String likeWhere) {
        LikesResponse likesResponse;
        if (likeWhere.equals("Board")) {
            LikesBoard like = likePlusAtBoard(id, userId);

            likesResponse = LikesResponse.builder()
                    .likesId(like.getId())
                    .likesAtContent(like.getBoard().getHeartCount())
                    .isLiked(like.getIsLiked())
                    .build();
        } else {
            LikesComment like  = likePlusAtComment(id, userId);

            likesResponse = LikesResponse.builder()
                    .likesId(like.getId())
                    .likesAtContent(like.getComment().getHeartCount())
                    .isLiked(like.getIsLiked())
                    .build();
        }

        return likesResponse;
    }

    @Transactional
    private LikesBoard likePlusAtBoard(Integer boardId, Integer userId) {
//        Board 객체 가져오기
        Board board = boardRepository
                .findById(boardId)
                .orElseThrow(() -> new NotFoundException("작성한 게시글이 없습니다."));
        log.info(board.toString());


//      User 객체 가져오기
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("접속하신 유저 정보가 없습니다."));


        // 해당 유저 좋아요 해당 게시글 좋아요 좋아요 객체 존재하는지
        // (있다면 그 객체 가져오고 없으면 isLiked 가 false 인 객체 생성)
        LikesBoard likeBoard = likeBoardRepository.findLikesBoardByUserIdAndBoardId(userId, boardId)
                .orElse(LikesBoard.builder()
                        .board(board)
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .isDeleted(false)
                        .isLiked(false)
                        .build()
                );
        log.info("유저와 보드로 라이크 가져오기");

        likeBoardRepository.save(likeBoard);
        //BoardLike 올리기
        // 객체가 좋아요 false 면 좋아요 true 로
        if (!likeBoard.getIsLiked()) {
            likeBoard.setIsLiked(true);
            likeBoard.setModifiedAt(LocalDateTime.now());
            likeBoardRepository.save(likeBoard);
            // 게시글의 좋아요 수 변경(+1).
            Board boardToChanged = boardRepository.findById(likeBoard.getBoard().getId())
                    .orElseThrow(() -> new NotFoundException("해당 게시글이 없습니다."));
            Integer heartCount = boardToChanged.getHeartCount();
            boardToChanged.setHeartCount(heartCount + 1);
            boardRepository.save(boardToChanged);
        } else {
            //좋아요 true 면, likeCancel 메서드 실행 (좋아요 취소)
            Integer likeId = likeBoard.getId();
            likeBoard = likeCancelAtBoard(likeId, likeBoard);
        }
        log.info(likeBoard.getBoard() + "");
        return likeBoard;
    }

    @Transactional
    private LikesComment likePlusAtComment(Integer commentId, Integer userId) {
        //TODO : Comment 객체 가져오기
//            Comment comment = commentRepository
//                    .findById(id)
//                    .orElseThrow(()->new NotFoundException("작성한 게시글이 없습니다."));
        //TODO: User 객체 가져오기
//            User user = userRepository
//                    .findById(userId)
//                    .orElseThrow(()->new NotFoundException("접속하신 유저 정보가 없습니다."));
//  TODO : 커멘트 레포지토리 완성되면 아래 거 없애기
        User user = User.builder()
                .id(userId)
                .nickname("지원")
                .email("way_2005@naver.com")
                .password("1234")
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
        Comment comment = Comment.builder()
                .id(commentId)
                .board(new Board())
                .user(user)
                .contents("content")
                .heartCount(20)
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();


        // 해당 유저 좋아요 해당 게시글 좋아요 좋아요 객체 존재하는지
        // (있다면 그 객체 가져오고 없으면 isLiked 가 false 인 객체 생성)
        LikesComment likeComment = likeCommentRepository.findLikesCommentByUserIdAndCommentId(user.getId(), comment.getId())
                .orElse(LikesComment.builder()
                        .comment(comment)
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .isDeleted(false)
                        .isLiked(false)
                        .build()
                );

        likeCommentRepository.save(likeComment);
        //CommentLike 올리기
        // 객체가 좋아요 누른 적 없다면, 게시판 좋아요 생성

        if (!likeComment.getIsLiked()) {
            likeComment.setIsLiked(true);
            likeComment.setModifiedAt(LocalDateTime.now());
            likeCommentRepository.save(likeComment);
            // TODO : CommentRepository 생성되면 살리기
            // 댓글의 좋아요 수 변경(+1).
//            Comment commentToChanged = commentRepository.findById(likeComment.getComment().getId())
//                    .orElseThrow(()->new NotFoundException("해당 댓글이 없습니다."));
//            Integer heartCount = commentToChanged.getHeartCount();
//            commentToChanged.setHeartCount(heartCount+1);
//            commentRepository.save(commentToChanged);
        } else {
//          좋아요 누른 적 있다면, likeCancel 메서드 실행  (좋아요 취소)
            Integer likeId = likeComment.getId();
            likeComment = likeCancelAtComment(likeId);
        }
        return likeComment;
    }

    @Transactional
    private LikesBoard likeCancelAtBoard(Integer likeId, LikesBoard likeBoard) {
        LikesBoard boardLikeUpdated = likeBoardRepository.findById(likeId)
                .orElseThrow(() -> new NotFoundException("해당 id의 유저가 좋아요를 누른 적이 없습니다."));
        boardLikeUpdated.setIsLiked(false);
        boardLikeUpdated.setModifiedAt(LocalDateTime.now());

        likeBoardRepository.save(boardLikeUpdated);
        //게시글 좋아요 변경 (-1)
        Board boardToChanged = boardRepository.findById(likeBoard.getBoard().getId())
                .orElseThrow(() -> new NotFoundException("해당 게시글이 없습니다."));
        Integer heartCount = boardToChanged.getHeartCount();
        boardToChanged.setHeartCount(heartCount - 1);
        boardRepository.save(boardToChanged);

        return boardLikeUpdated;
    }

    @Transactional
    private LikesComment likeCancelAtComment(Integer likeId) {
        LikesComment commentLikeUpdated = likeCommentRepository.findById(likeId)
                .orElseThrow(() -> new NotFoundException("해당 id의 유저가 좋아요를 누른 적이 없습니다."));
        commentLikeUpdated.setIsLiked(false);
        commentLikeUpdated.setModifiedAt(LocalDateTime.now());

        likeCommentRepository.save(commentLikeUpdated);

        // TODO : CommentRepository 생성되면 살리기
        //  댓글의 좋아요 수 변경(-1).
//            Comment commentToChanged = commentRepository.findById(likeComment.getComment().getId())
//                    .orElseThrow(()->new NotFoundException("해당 댓글이 없습니다."));
//            Integer heartCount = commentToChanged.getHeartCount();
//            commentToChanged.setHeartCount(heartCount-1);
//            commentRepository.save(commentToChanged);

        return commentLikeUpdated;

    }

}
