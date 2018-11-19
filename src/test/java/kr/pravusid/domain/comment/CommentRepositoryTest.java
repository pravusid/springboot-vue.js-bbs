package kr.pravusid.domain.comment;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.user.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @After
    public void 반복_댓글을_모두_삭제한다() {
        commentRepository.deleteAll();
    }

    @Test
    public void 댓글이_2개인_특정게시물의_댓글_전체를_가져온다() {
        // GIVEN
        Board board = 댓글_2개를_입력한_게시물_반환();

        // WHEN
        List<Comment> list = commentRepository.findByBoardIdOrderByReplyOrderAsc(board.getId());

        // THEN

        assertThat(list).hasSize(2);
        assertThat(list).extracting(Comment::getContent).containsExactly("댓글내용1", "댓글내용2");
    }

    @Test
    public void 댓글이_0개인_특정게시물의_댓글_전체를_가져온다() {
        // GIVEN
        Board saved = 댓글이_없는_게시물_반환();

        // WHEN
        List<Comment> list = commentRepository.findByBoardIdOrderByReplyOrderAsc(saved.getId());

        // THEN
        assertThat(list).hasSize(0);
    }

    @Test
    public void 특정_게시물_댓글_Order의_최고값을_가지고_온다() {
        // GIVEN
        Board board = 댓글_2개를_입력한_게시물_반환();

        // WHEN
        long max = commentRepository.getMaximumReplyOrder(board.getId());

        // THEN
        assertThat((max)).isEqualTo(2);
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
        assertThat(max1).isEqualTo(2);
        assertThat(max2).isEqualTo(2);
    }

    @Test
    public void 댓글_Order는_댓글이없으면_0을_반환함() {
        // GIVEN
        Board saved = 댓글이_없는_게시물_반환();

        // WHEN
        long max = commentRepository.getMaximumReplyOrder(saved.getId());

        // THEN
        assertThat(max).isEqualTo(0);
    }

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

}
