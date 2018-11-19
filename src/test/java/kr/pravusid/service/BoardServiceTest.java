package kr.pravusid.service;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.user.User;
import kr.pravusid.dto.BoardDto;
import kr.pravusid.dto.Pagination;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Before
    public void 게시물을_생성한다() {
        User user = new User(1L);
        boardRepository.save(new Board(user, "제목1", "내용1"));
        boardRepository.save(new Board(user, "제목2", "내용2"));
        boardRepository.save(new Board(user, "제목3", "내용3"));
    }

    @After
    public void 게시물을_삭제한다() {
        boardRepository.deleteAll();
    }

    @Test
    public void 전체_게시물을_조회한다() {
        // WHEN
        Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC, "id");
        Pagination pagination = new Pagination();
        Page<Board> list = boardService.findAll(pageable, pagination);

        // THEN
        int total = boardRepository.findAll().size();

        assertThat(list.getTotalElements()).isEqualTo(total);
        assertThat(list.getContent()).extracting(Board::getContent).containsExactly("내용3", "내용2", "내용1");
    }

    @Test
    public void 게시물을_저장한다() {
        // WHEN
        BoardDto dto = 요청을_생성한다();
        Board saved = boardService.save("user", dto);

        // THEN
        Board board = boardRepository.findOne(saved.getId());
        assertThat(board.verifyUser("user")).isTrue();
        assertThat(board.getTitle()).isEqualTo("테스트제목");
        assertThat(board.getContent()).isEqualTo("테스트내용");
    }

    @Test
    public void 게시물을_수정한다() {
        // GIVEN
        BoardDto dto = 요청을_생성한다();
        long id = boardRepository.findAll().get(0).getId();
        Board board = boardRepository.findOne(id);

        // WHEN
        Board updated = boardService.update(board.getUser().getUsername(), board.getId(), dto);

        // THEN
        Board result = boardRepository.findOne(updated.getId());
        assertThat(result.getId()).isEqualTo(board.getId());
        assertThat(result.getTitle()).isNotEqualTo(board.getTitle());
        assertThat(result.getTitle()).isEqualTo("테스트제목");
        assertThat(result.getContent()).isEqualTo("테스트내용");
    }

    @Test
    public void 게시물을_조회하고_조회수를_증가시킨다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board target = boardRepository.findOne(id);
        int hitAnte = target.getHit();

        // WHEN
        Board board = boardService.findOneAndHit(target.getId());

        // THEN
        assertThat(board.getId()).isEqualTo(target.getId());
        assertThat(board.getHit()).isEqualTo(hitAnte + 1);
    }

    @Test
    public void 요청한_회원의_게시물이_맞으면_내용을_반환한다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board target = boardRepository.findOne(id);

        // WHEN
        Board board = boardService.findOneForMod(target.getUser().getUsername(), target.getId());

        // THEN
        assertThat(board.getId()).isEqualTo(target.getId());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void 요청한_회원의_게시물이_아니면_예외를_발생시킨다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board target = boardRepository.findOne(id);

        // WHEN THEN
        boardService.findOneForMod("guest", target.getId());
    }

    @Test
    public void 요청한_회원의_삭제요청이_맞으면_삭제한다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board target = boardRepository.findOne(id);

        // WHEN
        boardService.delete(target.getUser().getUsername(), target.getId());

        // THEN
        assertThat(boardRepository.findOne(target.getId())).isNull();
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void 요청한_회원의_삭제요청이_아니면_예외를_발생시킨다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board target = boardRepository.findOne(id);

        // WHEN THEN
        boardService.delete("guest", target.getId());
    }

    private BoardDto 요청을_생성한다() {
        BoardDto dto = new BoardDto();
        dto.setTitle("테스트제목");
        dto.setContent("테스트내용");
        return dto;
    }

}
