package kr.pravusid.domain.comment;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    private Board 댓글이_없는_게시물_반환() {
        User user = new User(1L);
        Board board = new Board(user, "제목1", "내용2");
        return boardRepository.save(board);
    }

    private Board 댓글_2개를_입력한_게시물_반환() {
        User user = new User(1L);
        Board board = boardRepository.save(new Board(user, "제목1", "내용2"));

        Comment comment1 = new Comment(user, board, "댓글내용1", 0, 1);
        Comment comment2 = new Comment(user, board, "댓글내용2", 0, 2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);

        return board;
    }

    @Test
    public void 댓글이_2개인_특정게시물의_댓글_전체를_가져온다() {
        // GIVEN
        Board board = 댓글_2개를_입력한_게시물_반환();

        // WHEN
        List<Comment> list = commentRepository.findByBoardIdOrderByReplyOrderAsc(board.getId());

        // THEN

        Assert.assertEquals(2, list.size());
        Assert.assertEquals("댓글내용1", list.get(0).getContent());
        Assert.assertEquals("댓글내용2", list.get(1).getContent());
    }

    @Test
    public void 댓글이_0개인_특정게시물의_댓글_전체를_가져온다() {
        // GIVEN
        Board saved = 댓글이_없는_게시물_반환();

        // WHEN
        List<Comment> list = commentRepository.findByBoardIdOrderByReplyOrderAsc(saved.getId());

        // THEN
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void 특정_게시물_댓글_Order의_최고값을_가지고_온다() {
        // GIVEN
        Board board = 댓글_2개를_입력한_게시물_반환();

        // WHEN
        long max = commentRepository.getMaximumReplyOrder(board.getId());

        // THEN
        Assert.assertEquals(2, max);
    }

    @Test
    public void 댓글_2개가있는_게시물을_연속입력시_Order의_최고값은_둘다_2이다() {
        // GIVEN
        Board board1 = 댓글_2개를_입력한_게시물_반환();
        Board board2 = 댓글_2개를_입력한_게시물_반환();

        // WHEN
        long max1 = commentRepository.getMaximumReplyOrder(board1.getId());
        long max2 = commentRepository.getMaximumReplyOrder(board2.getId());

        // THEN
        Assert.assertEquals(2, max1);
        Assert.assertEquals(2, max2);
    }

    @Test
    public void 댓글_Order는_댓글이없으면_0을_반환함() {
        // GIVEN
        Board saved = 댓글이_없는_게시물_반환();

        // WHEN
        long max = commentRepository.getMaximumReplyOrder(saved.getId());

        // THEN
        Assert.assertEquals(0, max);
    }

}
