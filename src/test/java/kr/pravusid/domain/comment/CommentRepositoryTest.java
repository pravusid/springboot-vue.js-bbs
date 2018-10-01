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
        User user = new User(1L);
        Board board = new Board(user, "제목1", "내용2");
        Board saved = boardRepository.save(board);

        // WHEN
        List<Comment> list = commentRepository.findByBoardIdOrderByReplyOrderAsc(saved.getId());

        // THEN
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void 댓글_Order의_최고값을_가지고_온다() {
        // GIVEN
        댓글_2개를_입력한_게시물_반환();

        // WHEN
        long max = commentRepository.getMaximumReplyOrder();

        // THEN
        Assert.assertEquals(2, max);
    }

    @Test
    public void 댓글_Order는_댓글이없으면_0을_반환함() {
        // GIVEN
        // WHEN
        long max = commentRepository.getMaximumReplyOrder();

        // THEN
        Assert.assertEquals(0, max);
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

}
