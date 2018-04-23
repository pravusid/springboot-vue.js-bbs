package com.talsist.service;

import com.talsist.domain.board.Board;
import com.talsist.domain.board.BoardRepository;
import com.talsist.domain.comment.Comment;
import com.talsist.domain.comment.CommentRepository;
import com.talsist.domain.user.User;
import com.talsist.dto.CommentDto;
import com.talsist.util.SecurityContextUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private BoardRepository boardRepo;
    private CommentRepository commentRepo;

    public CommentService(BoardRepository boardRepo, CommentRepository commentRepo) {
        this.boardRepo = boardRepo;
        this.commentRepo = commentRepo;
    }

    @Transactional
    public void save(CommentDto commentDto, Long boardId) {
        User user = SecurityContextUtils.getAuthenticatedUser();

        if (commentDto.getReplyRoot() == 0) {
            saveParentComment(user, commentDto, boardId);
        } else {
            saveChildComment(user, commentDto, boardId);
        }
    }
    
    private void saveParentComment(User user, CommentDto commentDto, Long boardId) {
        Comment saved = commentRepo.save(commentDto.toEntity(user, boardRepo.findOne(boardId)));
        saved.initReplyRoot();
        commentRepo.save(saved);
    }
    
    private void saveChildComment(User user, CommentDto commentDto, Long boardId) {
        Board board = boardRepo.findOne(boardId);
        Comment comment = commentDto.toEntity(user, board);
        List<Comment> targets = findTargets(board.getComments(), comment);
        Long replyOrder = findReplyOrder(targets, comment);

        // comment가 들어갈 자리 이후의 comment order 값 증가
        targets.stream()
                .filter(c -> c.getReplyOrder() >= replyOrder).forEach(c -> c.adjustReplyOrder());
        commentRepo.save(targets);

        comment.increaseDepth();
        comment.adjustReplyOrder(replyOrder);
        commentRepo.save(comment);
    }

    public void update(CommentDto commentDto, Long commentId) {
        User user = SecurityContextUtils.getAuthenticatedUser();
        Comment comment = commentRepo.findOne(commentId);
        permissionCheck(comment);
        comment.update(commentDto.toEntity(user, comment.getBoard()));
        commentRepo.save(comment);
    }

    @Transactional
    public void delete(Long boardId, Long commentId) {
        Comment comment = commentRepo.findOne(commentId);
        permissionCheck(comment);

        List<Comment> targets = findTargets(boardRepo.findOne(boardId).getComments(), comment);
        Long replyOrder = findReplyOrder(targets, comment);

        List<Comment> delList = targets.stream()
                .filter(c -> c.getReplyOrder() < replyOrder).collect(Collectors.toList());

        commentRepo.delete(comment);
        commentRepo.delete(delList);

        targets.removeAll(delList);
        targets.forEach(c -> c.adjustReplyOrder(c.getReplyOrder() - replyOrder + comment.getReplyOrder()));
        commentRepo.save(targets);
    }

    private List<Comment> findTargets(List<Comment> comments, Comment comment) {
        // 요청 댓글 다음 요소부터 대상을 찾는다
        return comments.stream()
                .filter(c -> c.getReplyRoot() == comment.getReplyRoot() && c.getReplyOrder() > comment.getReplyOrder())
                .collect(Collectors.toList());
    }

    private Long findReplyOrder(List<Comment> targets, Comment comment) {
        return (comment.getReplyDepth() == 0) ?
                // 최상위 depth 처리
                targets.stream().mapToLong(c -> c.getReplyOrder() + 1).max().orElse(comment.getReplyOrder() + 1) :
                // 같은 depth인 바로 다음 댓글과 depth + 1인 마지막 댓글 또는 현재 댓글 + 1의 순서중 선택 됨
                targets.stream().filter(c -> c.getReplyDepth() == comment.getReplyDepth())
                        .mapToLong(c -> c.getReplyOrder()).min().orElse(targets.stream()
                                .filter(c -> c.getReplyDepth() == comment.getReplyDepth() + 1)
                                .mapToLong(c -> c.getReplyOrder() + 1).max().orElse(comment.getReplyOrder() + 1));
    }

    private void permissionCheck(Comment comment) throws AuthenticationException {
        if (!comment.verifyUser(SecurityContextUtils.getAuthenticatedUser().getId())) {
            throw new AuthenticationCredentialsNotFoundException("권한이 없습니다");
        }
    }

}
