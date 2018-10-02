package kr.pravusid.domain.board;

import kr.pravusid.domain.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

    private User user;

    @Before
    public void 회원을_생성한다() {
        user = new User(1L);
    }

    @Test
    public void 조회수를_증가시킨다() {
        // GIVEN
        Board board = new Board(user, "테스트제목", "테스트내용");
        int hitAnte = board.getHit();

        // WHEN
        board.increaseHit();

        // THEN
        Assert.assertEquals(hitAnte + 1, board.getHit());
    }

    @Test
    public void 게시물_내용을_수정한다() {
        // GIVEN
        Board boardAnte = new Board(user, "이전제목", "이전내용");

        // WHEN
        boardAnte.update("수정제목", "수정내용");

        // THEN
        Assert.assertEquals("수정제목", boardAnte.getTitle());
        Assert.assertEquals("수정내용", boardAnte.getContent());
    }

    @Test
    public void 동일회원의_게시물인지_확인한다() {
        // GIVEN
        User tester = new User("tester", "1234", "테스터", "test@test.kr");

        // WHEN
        Board board = new Board(tester, "테스트제목", "테스트내용");

        // THEN
        Assert.assertFalse(board.verifyUser("guest"));
        Assert.assertTrue(board.verifyUser("tester"));
    }

}
