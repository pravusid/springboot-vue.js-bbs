package kr.pravusid.domain.comment;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.user.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentTest {

    private User user = new User(1L);
    private Board board = new Board(user, "테스트제목", "테스트내용");

    @Test
    public void 상위_댓글을_초기화하면_Depth_1과_지정한_Order가_된다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.initializeRoot(5);

        // THEN
        assertThat(comment.getReplyDepth()).isEqualTo(1);
        assertThat(comment.getReplyOrder()).isEqualTo(5);
    }

    @Test
    public void 하위_댓글을_초기화하면_Depth_1추가_지정한_Order가_된다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.initializeChild(3, 12);

        // THEN
        assertThat(comment.getReplyDepth()).isEqualTo(4);
        assertThat(comment.getReplyOrder()).isEqualTo(12);
    }

    @Test
    public void 댓글_Order를_지정하지_않으면_1로_설정된다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.adjustReplyOrder();

        // THEN
        assertThat(comment.getReplyOrder()).isEqualTo(1);
    }

    @Test
    public void 댓글_Order를_설정한다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.adjustReplyOrder(5);

        // THEN
        assertThat(comment.getReplyOrder()).isEqualTo(5);
    }

    @Test
    public void 댓글_내용을_수정한다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.update("수정된내용");

        // THEN
        assertThat(comment.getContent()).isEqualTo("수정된내용");
    }

    @Test
    public void 동일회원의_댓글인지_확인한다() {
        // GIVEN
        User tester = new User("tester", "1234", "테스터", "test@kr");

        // WHEN
        Comment comment = new Comment(tester, board, "댓글내용", 0, 0);

        // THEN
        assertThat(comment.verifyUser("tester")).isTrue();
    }

}
