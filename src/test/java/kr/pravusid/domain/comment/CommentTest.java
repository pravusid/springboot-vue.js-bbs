package kr.pravusid.domain.comment;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommentTest {

    private User user;
    private Board board;

    @Before
    public void 회원과_게시물을_설정한다() {
        user = new User(1L);
        board = new Board(user, "테스트제목", "테스트내용");
    }

    @Test
    public void 상위_댓글을_초기화하면_Depth_1과_지정한_Order가_된다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.initializeRoot(5);

        // THEN
        assertEquals(1, comment.getReplyDepth());
        assertEquals(5, comment.getReplyOrder());
    }

    @Test
    public void 하위_댓글을_초기화하면_Depth_1추가_지정한_Order가_된다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.initializeChild(3, 12);

        // THEN
        assertEquals(4, comment.getReplyDepth());
        assertEquals(12, comment.getReplyOrder());
    }

    @Test
    public void 댓글_Order를_지정하지_않으면_1로_설정된다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.adjustReplyOrder();

        // THEN
        assertEquals(1, comment.getReplyOrder());
    }

    @Test
    public void 댓글_Order를_설정한다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.adjustReplyOrder(5);

        // THEN
        assertEquals(5, comment.getReplyOrder());
    }

    @Test
    public void 댓글_내용을_수정한다() {
        // GIVEN
        Comment comment = new Comment(user, board, "댓글내용", 0, 0);

        // WHEN
        comment.update("수정된내용");

        // THEN
        assertEquals("수정된내용", comment.getContent());
    }

    @Test
    public void 동일회원의_댓글인지_확인한다() {
        // GIVEN
        User tester = new User("tester", "1234", "테스터", "test@kr");

        // WHEN
        Comment comment = new Comment(tester, board, "댓글내용", 0, 0);

        // THEN
        assertTrue(comment.verifyUser("tester"));
    }

}
