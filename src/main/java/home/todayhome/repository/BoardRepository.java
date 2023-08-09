package home.todayhome.repository;

import home.todayhome.dto.BoardDto;
import home.todayhome.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT new home.todayhome.dto.BoardDto(b.id, u.nickname, b.title, b.content, b.heartCount, b.visitorCount, b.createdAt) " +
            "FROM Board b " +
            "INNER JOIN User u on u = b.user "

    List<BoardDto> findBoardPage(Pageable pageable);

    @Query("SELECT b From Board b INNER JOIN User u on u = b.user where b.id = :boardId")
    Board findByBoardId(@Param("boardId") Integer boardId);
}
