package kr.pravusid.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoardId(Long boardId);

    @Query("SELECT coalesce(max(cmt.replyOrder), 0) FROM Comment cmt")
    Long getMaximumReplyOrder();

}
