package home.todayhome.repository;


import home.todayhome.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByBoardId(Integer boardId);

    List<Comment> findAllByBoardIdAndIsDeletedOrIsDeletedIsNull(Integer boardId, boolean isDeleted);
}
