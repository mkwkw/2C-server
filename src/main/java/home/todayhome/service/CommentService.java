package home.todayhome.service;

import home.todayhome.dto.CommentDto;
import home.todayhome.entity.Board;
import home.todayhome.entity.Comment;
import home.todayhome.entity.User;
import home.todayhome.exception.NotFoundException;
import home.todayhome.repository.BoardRepository;
import home.todayhome.repository.CommentRepository;
import home.todayhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        LocalDateTime createDateTime = LocalDateTime.now();
        Comment comment = new Comment();

        comment.setBoard(boardId);
        comment.setContents(createCommentRequest.getContents());
        comment.setHeartCount(0);
        comment.setCreatedAt(createDateTime);
        comment.setUser(writer);

        commentRepository.save(comment);
        return CommentDto.CommentResponse.toResponse(comment);
    }

    public List<CommentDto.CommentResponse> getComments(Integer boardId){

        log.info("commentRepository : {}" , commentRepository.findAllByBoardId(boardId));
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return CommentDto.CommentResponse.toResponse(comments);
    }

    public CommentDto.CommentResponse updateComment(
            String userEmail,
            Integer commentId,
            CommentDto.PatchCommentRequest patchCommentRequest
    ){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글입니다."));

        String writer = comment.getUser().getEmail();

        if (!writer.equals(userEmail)){
            throw new NotFoundException("잘못된 접근입니다.");
        }

        boolean isContentUpdated = false;

        if(patchCommentRequest.getContents() !=null){
            comment.setContents(patchCommentRequest.getContents());
            isContentUpdated = true;
        }

        if (isContentUpdated){
            LocalDateTime modifiedTime = LocalDateTime.now();
            comment.setModifiedAt(modifiedTime);
        }

        commentRepository.save(comment);
        return CommentDto.CommentResponse.toResponse(comment);
    }

}
