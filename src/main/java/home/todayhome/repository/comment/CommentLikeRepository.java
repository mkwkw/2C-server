package home.todayhome.repository.comment;

import home.todayhome.entity.LikesComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<LikesComment, Integer> {
}
