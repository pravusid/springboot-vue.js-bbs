package kr.pravusid.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoardIdOrderByReplyOrderAsc(Long boardId);

    @Query("SELECT coalesce(max(cmt.replyOrder), 0) " +
            "FROM Comment cmt WHERE cmt.board.id = :#{#id}")
    long getMaximumReplyOrder(@Param("id") Long boardId);

}
