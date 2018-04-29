package kr.pravusid.service;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.board.BoardSpecification;
import kr.pravusid.domain.comment.CommentRepository;
import kr.pravusid.dto.BoardDto;
import kr.pravusid.dto.Pagination;
import kr.pravusid.util.SecurityContextUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BoardService {

    private BoardRepository boardRepo;
    private CommentRepository commentRepo;

    public BoardService(BoardRepository boardRepo, CommentRepository commentRepo) {
        this.boardRepo = boardRepo;
        this.commentRepo = commentRepo;
    }

    public Page<BoardDto> findAll(Pageable pageable, Pagination pagination) {
        if (pagination.getKeyword() == null) {
            return boardRepo.findAll(pageable).map(BoardDto::new);
        }

        String keyword = pagination.getKeyword();
        Page<Board> list = (pagination.filterMatcher(Pagination.FilterType.ALL))?
                boardRepo.findAll(Specifications.where(BoardSpecification.findByAll(keyword)), pageable):
                boardRepo.findAll(Specifications.where(BoardSpecification.findByFilter(pagination)), pageable);
        return list.map(BoardDto::new);
    }

    public void save(BoardDto boardDto) {
        boardRepo.save(boardDto.toEntity(SecurityContextUtils.getAuthenticatedUser()));
    }

    public void update(Long boardId, BoardDto reqBoard) {
        Board board = boardRepo.findOne(boardId);
        permissionCheck(board);
        board.update(reqBoard.toEntity(SecurityContextUtils.getAuthenticatedUser()));
        boardRepo.save(board);
    }

    public BoardDto findOneAndHit(Long id) {
        Board board = boardRepo.findOne(id);
        board.increaseHit();
        boardRepo.save(board);
        return new BoardDto(board);
    }

    public BoardDto findOneForMod(Long id) {
        Board board = boardRepo.findOne(id);
        permissionCheck(board);
        return new BoardDto(board);
    }

    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepo.findOne(boardId);
        permissionCheck(board);
        commentRepo.delete(board.getComments());
        boardRepo.delete(boardId);
    }

    private void permissionCheck(Board board) {
        if (!board.verifyUser(SecurityContextUtils.getAuthenticatedUser().getId())) {
            throw new AuthenticationCredentialsNotFoundException("권한이 없습니다");
        }
    }

}
