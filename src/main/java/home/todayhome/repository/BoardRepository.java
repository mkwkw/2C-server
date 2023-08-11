package home.todayhome.repository;

import home.todayhome.dto.board.BoardDto;
import home.todayhome.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("SELECT new home.todayhome.dto.board.BoardDto(b.id, u.nickname, b.title, b.content, count(c.id), b.heartCount, b.visitorCount, b.createdAt) " +
            "FROM Board b " +
            "LEFT JOIN b.user u " +
            "LEFT JOIN Comment c on c.board.id = b.id " +
            "GROUP BY b.id "
    )
    Page<BoardDto> findBoardDtoPage(Pageable pageable);

    @Query("SELECT new home.todayhome.dto.board.BoardDto(b.id, u.nickname, b.title, b.content, count(c.id), b.heartCount, b.visitorCount, b.createdAt) " +
            "FROM Board b " +
            "INNER JOIN User u on u.id = b.user.id " +
            "INNER JOIN Comment c on c.board.id = b.id "
    )
    List<BoardDto> findBoardList();

    @Query("SELECT new home.todayhome.dto.board.BoardDto(b.id, u.nickname, b.title, b.content, count(c.id), b.heartCount, b.visitorCount, b.createdAt) " +
            "FROM Board b " +
            "LEFT JOIN Comment c on c.board.id = b.id " +
            "LEFT JOIN b.user u " +
            "WHERE b.id = :boardId "
    )
    Optional<BoardDto> findByBoardId(@Param("boardId") Integer boardId);

    @Modifying
    @Query("UPDATE Board b " +
            "SET b.visitorCount = b.visitorCount + 1" +
            "WHERE b.id = :boardId")
    void addBoardVisitorCount(@Param("boardId") Integer boardId);

    @Query("SELECT b FROM Board b ")
    Page<Board> findBoardPage(Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.id = :boardId")
    Optional<Board> findBoardByBoardId(@Param("boardId") Integer boardId);

    @Query("SELECT count(c.id) " +
            "FROM Comment c " +
            "WHERE c.board.id = :boardId " +
            "GROUP BY c.board.id ")
    Optional<Long> findCommentCountByBoardId(@Param("boardId") Integer boardId);
}
