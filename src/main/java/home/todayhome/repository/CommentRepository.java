package home.todayhome.repository;


import home.todayhome.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByBoardId(Integer boardId);

    List<Comment> findAllByBoardIdAndIsDeletedFalse(Integer boardId);

    @Query(value = "update Comment as c set c.isDeleted = true where c.id = :commentId")
    @Modifying
    void deleteByComment(@Param("commentId") Integer commentId);
}
