package kr.pravusid.service;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.user.User;
import kr.pravusid.dto.BoardDto;
import kr.pravusid.dto.Pagination;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    private BoardDto dto;

    @Before
    public void 게시물과_요청을_생성한다() {
        User user = new User(1L);
        boardRepository.save(new Board(user, "제목1", "내용1"));
        boardRepository.save(new Board(user, "제목2", "내용2"));
        boardRepository.save(new Board(user, "제목3", "내용3"));

        dto = new BoardDto();
        dto.setTitle("테스트제목");
        dto.setContent("테스트내용");
    }

    @Test
    public void 전체_게시물을_조회한다() {
        // WHEN
        Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC,"id");
        // pagination은 검색 필터, 키워드 추출에 사용됨
        Pagination pagination = new Pagination();
        Page<Board> list = boardService.findAll(pageable, pagination);

        // THEN
        int total = boardRepository.findAll().size();
        assertEquals(0, list.getNumber());
        assertEquals(total, list.getTotalElements());
        assertEquals("내용3", list.getContent().get(0).getContent() );
        assertEquals("내용1", list.getContent().get(2).getContent() );
    }

    @Test
    public void 게시물을_저장한다() {
        // WHEN
        Board saved = boardService.save("user", dto);
        // THEN
        Board board = boardRepository.findOne(saved.getId());
        assertTrue(board.verifyUser("user"));
        assertEquals("테스트제목", board.getTitle());
        assertEquals("테스트내용", board.getContent());
    }

    @Test
    public void 게시물을_수정한다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board board = boardRepository.findOne(id);

        // WHEN
        Board updated = boardService.update(board.getUser().getUsername(), board.getId(), dto);

        // THEN
        Board result = boardRepository.findOne(updated.getId());
        assertEquals(board.getId(), result.getId());
        assertNotEquals(board.getTitle(), result.getTitle());
        assertEquals("테스트제목", result.getTitle());
        assertEquals("테스트내용", result.getContent());
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
        assertEquals(target.getId(), board.getId());
        assertEquals(hitAnte + 1, board.getHit());
    }

    @Test
    public void 요청한_회원의_게시물이_맞으면_내용을_반환한다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board target = boardRepository.findOne(id);

        // WHEN
        Board board = boardService.findOneForMod(target.getUser().getUsername(), target.getId());

        // THEN
        assertEquals(target.getId(), board.getId());
    }

    @Test
    public void 요청한_회원의_게시물이_아니면_예외를_발생시킨다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board target = boardRepository.findOne(id);

        // WHEN THEN
        try {
            boardService.findOneForMod("guest", target.getId());
        } catch (Exception e) {
            assertTrue(e instanceof AuthenticationCredentialsNotFoundException);
        }
    }

    @Test
    public void 요청한_회원의_삭제요청이_맞으면_삭제한다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board target = boardRepository.findOne(id);

        // WHEN
        boardService.delete(target.getUser().getUsername(), target.getId());

        // THEN
        Board board = boardRepository.findOne(target.getId());
        assertNull(board);
    }

    @Test
    public void 요청한_회원의_삭제요청이_아니면_예외를_발생시킨다() {
        // GIVEN
        long id = boardRepository.findAll().get(0).getId();
        Board target = boardRepository.findOne(id);

        // WHEN THEN
        try {
            boardService.delete("guest", target.getId());
        } catch (Exception e) {
            assertTrue(e instanceof AuthenticationCredentialsNotFoundException);
        }
    }

}
