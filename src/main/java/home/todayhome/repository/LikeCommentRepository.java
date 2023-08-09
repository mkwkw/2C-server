package home.todayhome.repository;


import home.todayhome.entity.Comment;
import home.todayhome.entity.LikesComment;
import home.todayhome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikesComment, Integer> {
    Optional<LikesComment> findLikesCommentByUserAndComment(User user, Comment comment);

}
