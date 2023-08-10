package home.todayhome.service;

import home.todayhome.dto.CommentDto;
import home.todayhome.entity.Board;
import home.todayhome.entity.Comment;
import home.todayhome.entity.User;
import home.todayhome.exception.NotFoundException;
import home.todayhome.repository.BoardRepository;
import home.todayhome.repository.CommentRepository;
import home.todayhome.repository.UserRepository;
import home.todayhome.security.AuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {


    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentDto.CommentResponse createComment(
            CommentDto.CreateCommentRequest createCommentRequest,
            String userEmail
    ){
        User writer = userRepository.findByEmail(userEmail);
        Board boardId = boardRepository.findById(createCommentRequest.getBoardId()).orElse(null);

        log.info("User email :{} ",userEmail);

        if(boardId == null || writer == null){
            throw new NotFoundException(("잘못된 접근입니다."));
        }

//        log.info("User email :{} ",userEmail);
        LocalDateTime createDateTime = LocalDateTime.now();
        Comment comment = new Comment();

        comment.setBoard(boardId);
        comment.setContents(createCommentRequest.getContents());
//        comment.setHeartCount(createCommentRequest.getHeartCount());
        comment.setHeartCount(0);
        comment.setCreatedAt(createDateTime);
        comment.setUser(writer);

        commentRepository.save(comment);
        return CommentDto.CommentResponse.toResponse(comment);

    }

}
