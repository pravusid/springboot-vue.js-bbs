package kr.pravusid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pravusid.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
