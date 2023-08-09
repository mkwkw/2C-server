package home.todayhome.service;

import home.todayhome.entity.*;
import home.todayhome.exception.NotFoundException;
import home.todayhome.repository.LikeBoardRepository;
import home.todayhome.repository.BoardRepository;
import home.todayhome.repository.LikeCommentRepository;
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
//    private final CommentRepository commentRepository;
//    private final UserRepository userRepository;

    private final LikeBoardRepository likeBoardRepository;
    private final LikeCommentRepository likeCommentRepository;

    public Integer likePlus(Integer id, Integer userId, String likeWhere) {
        Integer likeId;
        if (likeWhere.equals("Board")) likeId = likePlusAtBoard(id, userId);
        else likeId = likePlusAtComment(id, userId);

        return likeId;
    }

    @Transactional
    public Integer likePlusAtBoard(Integer boardId, Integer userId) {
        //Board 객체 가져오기
//        Board board = boardRepository
//                .findById(boardId)
//                .orElseThrow(() -> new NotFoundException("작성한 게시글이 없습니다."));


        //TODO: User 객체 가져오기
//            User user = userRepository
//                    .findById(userId)
//                    .orElseThrow(()->new NotFoundException("접속하신 유저 정보가 없습니다."));

        User user = User.builder()
                .id(userId)
                .nickname("지원")
                .email("way_2005@naver.com")
                .password("1234")
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
        Board board = Board.builder()
                .id(boardId)
                .user(user)
                .title("title")
                .content("content")
                .heartCount(20)
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();

        // 해당 유저 좋아요 해당 게시글 좋아요 좋아요 객체 존재하는지
        // (있다면 그 객체 가져오고 없으면 isLiked 가 false 인 객체 생성)
        LikesBoard likeBoard = likeBoardRepository.findLikesBoardByUserAndBoard(user, board)
                .orElse(LikesBoard.builder()
                        .board(board)
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .isDeleted(false)
                        .isLiked(false)
                        .build()
                );

        likeBoardRepository.save(likeBoard);
        //BoardLike 올리기
        // 객체가 좋아요 false 면 좋아요 true 로
        if (!likeBoard.getIsLiked()) {
            likeBoard.setIsLiked(true);
            likeBoard.setModifiedAt(LocalDateTime.now());
            likeBoardRepository.save(likeBoard);
            // 게시글의 좋아요 수 변경(+1).
            Board boardToChanged = boardRepository.findById(likeBoard.getBoard().getId())
                    .orElseThrow(()->new NotFoundException("해당 게시글이 없습니다."));
            Integer heartCount = boardToChanged.getHeartCount();
            boardToChanged.setHeartCount(heartCount+1);
            boardRepository.save(boardToChanged);
        } else {
            //좋아요 true 면, likeCancel 메서드 실행 (좋아요 취소)
            Integer likeId = likeBoard.getId();
            likeBoard = likeCancelAtBoard(likeId, likeBoard);
        }
        return likeBoard.getId();
    }
    @Transactional
    public Integer likePlusAtComment(Integer commentId, Integer userId) {
        //TODO : Comment 객체 가져오기
//            Comment comment = commentRepository
//                    .findById(id)
//                    .orElseThrow(()->new NotFoundException("작성한 게시글이 없습니다."));
        //TODO: User 객체 가져오기
//            User user = userRepository
//                    .findById(userId)
//                    .orElseThrow(()->new NotFoundException("접속하신 유저 정보가 없습니다."));

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
                .content("content")
                .heartCount(20)
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();


        // 해당 유저 좋아요 해당 게시글 좋아요 좋아요 객체 존재하는지
        // (있다면 그 객체 가져오고 없으면 isLiked 가 false 인 객체 생성)
        LikesComment likeComment = likeCommentRepository.findLikesCommentByUserAndComment(user, comment)
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
        return likeComment.getId();
    }

    @Transactional
    public LikesBoard likeCancelAtBoard(Integer likeId , LikesBoard likeBoard) {
        LikesBoard boardLikeUpdated = likeBoardRepository.findById(likeId)
                .orElseThrow(()->new NotFoundException("해당 id의 유저가 좋아요를 누른 적이 없습니다."));
        boardLikeUpdated.setIsLiked(false);
        boardLikeUpdated.setModifiedAt(LocalDateTime.now());

        likeBoardRepository.save(boardLikeUpdated);
        //게시글 좋아요 변경 (-1)
        Board boardToChanged = boardRepository.findById(likeBoard.getBoard().getId())
                .orElseThrow(()->new NotFoundException("해당 게시글이 없습니다."));
        Integer heartCount = boardToChanged.getHeartCount();
        boardToChanged.setHeartCount(heartCount-1);
        boardRepository.save(boardToChanged);

        return boardLikeUpdated;
    }
    @Transactional
    public LikesComment likeCancelAtComment(Integer likeId) {
        LikesComment commentLikeUpdated = likeCommentRepository.findById(likeId)
                .orElseThrow(()->new NotFoundException("해당 id의 유저가 좋아요를 누른 적이 없습니다."));
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

