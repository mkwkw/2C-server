package home.todayhome.repository;

import home.todayhome.entity.Board;
import home.todayhome.entity.LikesBoard;
import home.todayhome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeBoardRepository extends JpaRepository<LikesBoard, Integer> {
    Optional<LikesBoard> findLikesBoardByUserAndBoard(User user, Board board);

}
