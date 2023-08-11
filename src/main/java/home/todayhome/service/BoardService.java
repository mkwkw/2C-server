package home.todayhome.service;

import home.todayhome.dto.board.BoardDto;
import home.todayhome.entity.Board;
import home.todayhome.entity.User;
import home.todayhome.exception.CustomException;
import home.todayhome.exception.Errorcode;
import home.todayhome.repository.BoardRepository;
import home.todayhome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    /**
     * 게시글 목록 페이징 메소드
     * @param pageable 현재 페이지, 페이지 크기, 페이지 정렬
     * @return Page<BoardDto> 페이지 목록
     */
    @Transactional(readOnly = true)
    public Page<BoardDto> getBoardPage(Pageable pageable) {
        return boardRepository.findBoardPage(pageable).map(BoardDto::fromEntity);
    }

    /**
     * 상세 게시글 메소드
     * @param boardId 찾고 싶은 게시글 ID
     * @return BoardDto 게시글 반환
     */
    @Transactional
    public BoardDto getBoard(Integer boardId) {
        return boardRepository.findBoardByBoardId(boardId).map((Board board) -> {
            if (board.getId() == null) {
                log.error("Board: {} ", board);
                throw new CustomException(Errorcode.NOT_FOUND_BOARD, String.format("게시글Id: %d가 존재하지 않습니다", boardId));
            }
            updateVisitorCount(boardId);
            Long commentCount = boardRepository.findCommentCountByBoardId(boardId).orElseGet(()->0L);
            BoardDto boardDto = BoardDto.fromEntity(board);
            log.info("{} 댓글 갯수", commentCount);
            boardDto.setCommentCount(commentCount);
            return boardDto;
        }).get();
    }

    /**
     * 게시글 작성 인증 권한 확인후 입력으로 받아온 게시물 작성
     * @param userEmail 찾을 유저 이메일
     * @param title 제목
     * @param content 본문
     */

    @Transactional
    public void createBoard(String userEmail, String title, String content) {
        log.info("이메일: {}", userEmail);
        User findUser = getFindUser(userEmail);
        Board board = Board.builder()
                .user(findUser)
                .title(title)
                .content(content)
                .visitorCount(0)
                .heartCount(0)
                .createdAt(LocalDateTime.now())
                .build();
        boardRepository.save(board);
    }

    /**
     * 게시글 조회시 조회수증가
     * @param boardId 조회할 게시글 ID
     */
    @Transactional
    public void updateVisitorCount(Integer boardId){
        boardRepository.addBoardVisitorCount(boardId);
    }

    /**
     * 게시글 수정 인증 권한 확인 후 수정할 값으로 변경
     * @param userEmail 
     * @param boardId
     * @param title
     * @param content
     */
    @Transactional
    @Modifying
    public void updateBoard(String userEmail, Integer boardId, String title, String content) {
        getFindUser(userEmail);

        Board board = boardRepository.findBoardByBoardId(boardId).orElseThrow(() ->
                new CustomException(Errorcode.NOT_FOUND_BOARD, String.format("boardId: %d 가 존재하지 않았습니다", boardId))
        );
        if(title != null) board.setTitle(title); //title null이 아닐 때 수정
        if(content != null) board.setContent(content); //content null이 아닐 때 수정

        boardRepository.saveAndFlush(board); // 수정된 Board update
    }

    /**
     * 특정 게시글 삭제 인증 권한 확인후 해당 게시글 삭제
     * @param userEmail
     * @param boardId
     */
    @Transactional
    public void deleteBoard(String userEmail, Integer boardId) {
        getFindUser(userEmail);
        Board board = boardRepository.findBoardByBoardId(boardId).orElseThrow(() ->
                new CustomException(Errorcode.NOT_FOUND_BOARD, String.format("boardId: %d 가 존재하지 않았습니다", boardId))
        );
        board.setIsDeleted(true);
        boardRepository.save(board);
    }

    /**
     * 유저 검색 인증 실피시 오류 반환 
     * @param userEmail 유정
     * @return User 반환
     */
    private User getFindUser(String userEmail) {
        return Optional.ofNullable(userRepository.findByEmail(userEmail)).orElseThrow(() ->
                new CustomException(Errorcode.NOT_AUTHORIZATION, String.format("%s email이 존재하지 않습니다.", userEmail))
        );
    }


    @Transactional(readOnly = true)
    public Page<BoardDto> getBoardDtoPage(Pageable pageable) {
        return boardRepository.findBoardDtoPage(pageable);
    }

    @Transactional
    public BoardDto getBoardDto(Integer boardId) {
        return boardRepository.findByBoardId(boardId).filter((board) -> {
            log.info("boardId: {}", boardId);
            if (board.getId() == null) {
                log.info("board: {}", board);
                throw new CustomException(Errorcode.NOT_FOUND_BOARD, String.format("게시글Id: %d가 존재하지 않습니다", boardId));
            }
            updateVisitorCount(boardId);
            return true;
        }).get();
    }

}
