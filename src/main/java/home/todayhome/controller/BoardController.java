package home.todayhome.controller;

import home.todayhome.dto.board.*;
import home.todayhome.dto.Response;
import home.todayhome.security.AuthInfo;
import home.todayhome.security.TokenEmailAndId;
import home.todayhome.service.BoardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 페이지 조회
     * @param pageable
     * @return Page<BoardResponse>
     */
    @ApiOperation("게시글 목록 페이징")
    @GetMapping("/posts")
    public Response<?> showBoardPage(@PageableDefault(size = 10, page = 0, sort={"visitorCount"}) Pageable pageable) {
        Page<BoardResponse> boards = boardService.getBoardPage(pageable).map(BoardResponse::fromDto);
        return Response.success(boards);
    }

    /**
     * 인증 확인후 게시글 작성
     * @param authInfo 인증객체
     * @param requestBoard title, content
     * @return String
     */
    @ApiOperation("게시글 작성")
    @PostMapping("/posts")
    public Response<?> createBoard(@TokenEmailAndId AuthInfo authInfo, @RequestBody RequestBoard requestBoard) {
        boardService.createBoard(authInfo.getUserEmail(), requestBoard.getTitle(), requestBoard.getContent());
        return Response.success("게시글 작성 완료했습니다.");
    }

    /**
     * 게시글 단건 조회
     * @param id 게시글 ID
     * @return BoardDetailResponse
     */
    @ApiOperation("게시글 단건 조회")
    @GetMapping("/posts/{id}")
    public Response<?> showBoardPage(@PathVariable("id") Integer id) {
        BoardDto board = boardService.getBoard(id);
        return Response.success(BoardDetailResponse.fromDto(board));
    }

    /**
     * 인증권한 확인후 게시글 수정
     * @param authInfo 인증 객체
     * @param boardId 게시글 ID
     * @param requestBoard 수정 정보
     * @return String 성공 여부
     */
    @PutMapping("/posts/{id}")
    public Response<?> updateBoard(@TokenEmailAndId AuthInfo authInfo, @PathVariable("id") Integer boardId, @RequestBody RequestUpdateBoard requestBoard) {
        boardService.updateBoard(authInfo.getUserEmail(), boardId, requestBoard.getTitle(), requestBoard.getContent());
        return Response.success("게시글 수정 완료했습니다.");
    }

    /**
     * 인증권한 확인후 게시글 삭제
     * @param authInfo 인증객체
     * @param boardId 게시글 ID
     * @return String 성공 메시지
     */
    @DeleteMapping("/posts/{id}")
    public Response<?> deleteBoard(@TokenEmailAndId AuthInfo authInfo, @PathVariable("id") Integer boardId){
        boardService.deleteBoard(authInfo.getUserEmail(),boardId);
        return Response.success("게시글 삭제 완료했습니다.");
    }
}
