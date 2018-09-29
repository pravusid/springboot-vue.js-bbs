package kr.pravusid.domain.comment;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void 특정게시물의_댓글_전체를_가져온다() {
        // GIVEN
        User user = new User(1L);
        Board board1 = new Board(user, "제목1", "내용2");
        Board board2 = new Board(user, "제목1", "내용2");
        Board saved1 = boardRepository.save(board1);
        Board saved2 = boardRepository.save(board2);
        Comment comment1 = new Comment(user, saved1, "댓글내용1", 0, 0);
        Comment comment2 = new Comment(user, saved1, "댓글내용2", 0, 0);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        // WHEN
        List<Comment> list1 = commentRepository.findByBoardId(saved1.getId());
        List<Comment> list2 = commentRepository.findByBoardId(saved2.getId());

        // THEN
        Assert.assertEquals(2, list1.size());
        Assert.assertEquals("댓글내용1", list1.get(0).getContent());
        Assert.assertEquals("댓글내용2", list1.get(1).getContent());
        Assert.assertEquals(0, list2.size());
    }

}
