package home.todayhome.repository;

import home.todayhome.entity.LikesBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<LikesBoard, Integer> {
}
