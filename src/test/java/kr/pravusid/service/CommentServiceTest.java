package kr.pravusid.service;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.comment.Comment;
import kr.pravusid.domain.comment.CommentRepository;
import kr.pravusid.domain.user.User;
import kr.pravusid.domain.user.UserRepository;
import kr.pravusid.dto.BoardDto;
import kr.pravusid.dto.CommentDto;
import kr.pravusid.dto.UserDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;

    private User user;
    private Board board;

    private boolean initialized;

    @Before
    public void 초기화_회원과_게시물을_정의한다() {
        if (!initialized) {
            user = userRepository.findByUsername("user");

            BoardDto boDto = new BoardDto();
            boDto.setTitle("테스트제목");
            boDto.setContent("테스트본문");

            board = boardRepository.save(boDto.toEntity(user));
            initialized = true;
        }
    }

    @After
    public void 반복_댓글을_삭제한다() {
        commentRepository.deleteAll();
    }

    @Test
    public void 특정_게시물의_모든_댓글을_가져온다() {
        // GIVEN
        댓글_3개를_저장하고_반환한다();

        // WHEN
        List<Comment> list = commentService.findAll(board.getId());

        // THEN
        assertThat(list).extracting(Comment::getContent).containsExactly("댓글1", "댓글2", "댓글3");
    }

    @Test
    public void 댓글을_저장한다() {
        // GIVEN
        CommentDto dto = new CommentDto();
        dto.setUser(UserDto.of(user));
        dto.setContent("테스트댓글");

        // WHEN
        commentService.save(user.getUsername(), board.getId(), dto);

        // THEN
        Comment result = commentRepository.findByBoardIdOrderByReplyOrderAsc(board.getId())
                .stream().findFirst().orElse(null);
        assertThat(result.getContent()).isEqualTo("테스트댓글");
    }

    @Test
    public void 대댓글을_저장한다() {
        // GIVEN
        List<Comment> list = 댓글_3개를_저장하고_반환한다();
        long order = list.get(1).getReplyOrder();
        long depth = list.get(1).getReplyDepth();
        CommentDto dto = new CommentDto();
        dto.setUser(UserDto.of(user));
        dto.setContent("테스트댓글");
        dto.setReplyOrder(order);
        dto.setReplyDepth(depth);

        // WHEN
        commentService.save(user.getUsername(), board.getId(), dto);

        // THEN
        List<Comment> result = commentRepository.findByBoardIdOrderByReplyOrderAsc(board.getId());
        assertThat(result).extracting(Comment::getReplyOrder).containsExactly(1L, 2L, 3L, 4L);
        assertThat(result).extracting(Comment::getReplyDepth).containsExactly(1L, 2L, 3L, 1L);
        assertThat(result.get(1).getContent()).isEqualTo("댓글2");
        assertThat(result.get(2).getContent()).isEqualTo("테스트댓글");
    }

    @Test
    public void 댓글을_수정한다() {
        // GIVEN
        List<Comment> list = 댓글_3개를_저장하고_반환한다();
        CommentDto dto = new CommentDto();
        dto.setContent("수정후");
        Comment comment = list.get(0);

        // WHEN
        commentService.update(user.getUsername(), comment.getId(), dto);

        // THEN
        Comment result = commentRepository.findOne(comment.getId());
        assertThat(comment.getContent()).isNotEqualTo("수정후");
        assertThat(result.getContent()).isEqualTo("수정후");
    }

    @Test
    public void 댓글을_삭제한다() {
        // GIVEN
        List<Comment> list = 댓글_3개를_저장하고_반환한다();
        long commentId = list.get(0).getId();

        // WHEN
        commentService.delete(user.getUsername(), board.getId(), commentId);

        // THEN
        Comment result = commentRepository.findOne(commentId);
        assertThat(result).isNull();
    }

    private List<Comment> 댓글_3개를_저장하고_반환한다() {
        List<Comment> data = Arrays.asList(
                new Comment(user, board, "댓글1", 1, 1),
                new Comment(user, board, "댓글2", 2, 2),
                new Comment(user, board, "댓글3", 1, 3)

        );
        return commentRepository.save(data);
    }

}
