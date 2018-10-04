package kr.pravusid.service;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.comment.Comment;
import kr.pravusid.domain.comment.CommentRepository;
import kr.pravusid.domain.user.User;
import kr.pravusid.domain.user.UserRepository;
import kr.pravusid.dto.CommentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private UserService userService;

    private UserRepository userRepository;
    private BoardRepository boardRepository;
    private CommentRepository commentRepository;

    public CommentService(UserService userService, UserRepository userRepository,
                          BoardRepository boardRepository, CommentRepository commentRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll(Long boardId) {
        return commentRepository.findByBoardIdOrderByReplyOrderAsc(boardId);
    }

    @Transactional
    public void save(String username, Long boardId, CommentDto dto) {
        User user = userRepository.findByUsername(username);

        if (dto.getReplyDepth() == 0) {
            saveParentComment(user, boardId, dto);
        } else {
            saveChildComment(user, boardId, dto);
        }
    }

    private void saveParentComment(User user, Long boardId, CommentDto dto) {
        dto.setReplyOrder(commentRepository.getMaximumReplyOrder(boardId) + 1);
        Comment comment = commentRepository.save(dto.toEntity(user, boardRepository.findOne(boardId)));
        comment.adjustReplyDepth();
        commentRepository.save(comment);
    }

    private void saveChildComment(User user, Long boardId, CommentDto dto) {
        Board board = boardRepository.findOne(boardId);
        Comment comment = dto.toEntity(user, board);

        List<Comment> targets = findTargets(board.getComments(), comment);
        long replyOrder = findReplyOrder(targets, comment);

        targets.stream()
                .filter(c -> c.getReplyOrder() >= replyOrder)
                .forEach(Comment::adjustReplyOrder);
        commentRepository.save(targets);

        comment.adjustReplyDepth();
        comment.adjustReplyOrder(replyOrder);
        commentRepository.save(comment);
    }

    @Transactional
    public void update(String username, Long id, CommentDto dto) {
        Comment comment = commentRepository.findOne(id);
        userService.permissionCheck(username, comment);
        comment.update(dto.getContent());
    }

    @Transactional
    public void delete(String username, Long boardId, Long id) {
        Comment comment = commentRepository.findOne(id);
        userService.permissionCheck(username, comment);

        List<Comment> targets = findTargets(boardRepository.findOne(boardId).getComments(), comment);
        long replyOrder = findReplyOrder(targets, comment);

        List<Comment> delList = targets.stream()
                .filter(c -> c.getReplyOrder() < replyOrder)
                .collect(Collectors.toList());
        targets.removeAll(delList);

        delList.add(comment);
        commentRepository.delete(delList);

        targets.forEach(c -> c.adjustReplyOrder(c.getReplyOrder() - delList.size()));
        commentRepository.save(targets);
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

}
