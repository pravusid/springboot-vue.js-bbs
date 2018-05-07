package kr.pravusid.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.comment.Comment;
import kr.pravusid.domain.comment.CommentRepository;
import kr.pravusid.domain.user.User;
import kr.pravusid.dto.CommentDto;
import kr.pravusid.util.SecurityContextUtils;

@Service
public class CommentService {

    private BoardRepository boardRepo;
    private CommentRepository commentRepo;

    public CommentService(BoardRepository boardRepo, CommentRepository commentRepo) {
        this.boardRepo = boardRepo;
        this.commentRepo = commentRepo;
    }

    public void save(CommentDto commentDto, Long boardId) {
        User user = SecurityContextUtils.getAuthenticatedUser();

        if (commentDto.getReplyDepth() == 0) {
            saveParentComment(user, commentDto, boardId);
        } else {
            saveChildComment(user, commentDto, boardId);
        }
    }

    @Transactional
    private void saveParentComment(User user, CommentDto commentDto, Long boardId) {
        commentDto.setReplyOrder(commentRepo.getMaximumReplyOrder() + 1);
        Comment comment = commentRepo.save(commentDto.toEntity(user, boardRepo.findOne(boardId)));
        comment.adjustReplyDepth();
        commentRepo.save(comment);
    }

    @Transactional
    private void saveChildComment(User user, CommentDto commentDto, Long boardId) {
        Board board = boardRepo.findOne(boardId);
        Comment comment = commentDto.toEntity(user, board);

        List<Comment> targets = findTargets(board.getComments(), comment);
        long replyOrder = findReplyOrder(targets, comment);

        targets.stream()
                .filter(c -> c.getReplyOrder() >= replyOrder)
                .forEach(c -> c.adjustReplyOrder());
        commentRepo.save(targets);

        comment.adjustReplyDepth();
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
        long replyOrder = findReplyOrder(targets, comment);

        List<Comment> delList = targets.stream()
                .filter(c -> c.getReplyOrder() < replyOrder)
                .collect(Collectors.toList());
        targets.removeAll(delList);

        delList.add(comment);
        commentRepo.delete(delList);

        targets.forEach(c -> c.adjustReplyOrder(c.getReplyOrder() - delList.size()));
        commentRepo.save(targets);
    }

    // 요청 댓글 이후의 댓글만
    private List<Comment> findTargets(List<Comment> comments, Comment comment) {
        return comments.stream()
                .filter(c -> c.getReplyOrder() > comment.getReplyOrder())
                .collect(Collectors.toList());
    }

    // 새로운 댓글이 들어갈 위치
    private long findReplyOrder(List<Comment> targets, Comment comment) {
        return targets.stream()
                .filter(c -> c.getReplyDepth() <= comment.getReplyDepth())
                .findFirst().map(Comment::getReplyOrder)
                .orElse(targets.stream()
                        .mapToLong(c -> c.getReplyOrder() + 1).max()
                        .orElse(comment.getReplyOrder() + 1)
                        );
    }

    private void permissionCheck(Comment comment) throws AuthenticationException {
        if (!comment.verifyUser(SecurityContextUtils.getAuthenticatedUser().getId())) {
            throw new AuthenticationCredentialsNotFoundException("권한이 없습니다");
        }
    }

}
