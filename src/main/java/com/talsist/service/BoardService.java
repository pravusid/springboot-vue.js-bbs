package com.talsist.service;

import com.talsist.domain.board.Board;
import com.talsist.domain.board.BoardRepository;
import com.talsist.domain.board.BoardSpecification;
import com.talsist.domain.comment.CommentRepository;
import com.talsist.dto.PaginationDto;
import com.talsist.util.SecurityContextUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
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

    public Page<Board> findAll(Pageable pageable, PaginationDto pagination) {
        if (pagination.getKeyword() == null) {
            return boardRepo.findAll(pageable);
        }

        Page<Board> list = null;
        String keyword = pagination.getKeyword();
        if (pagination.filterMatcher(PaginationDto.FilterType.ALL)) {
            list = boardRepo.findAll(Specifications.where(BoardSpecification.findByAll(keyword)), pageable);
        } else {
            list = boardRepo.findAll(Specifications.where(BoardSpecification.findByFilter(pagination)), pageable);
        }
        return list;
    }

    public void save(Board board) {
        board.setUser(SecurityContextUtils.getAuthenticatedUser());
        boardRepo.save(board);
    }

    public void update(Long boardId, Board reqBoard) throws AuthenticationException {
        Board board = boardRepo.findOne(boardId);
        permissionCheck(board);
        board.Update(reqBoard);
        boardRepo.save(board);
    }

    public Board findOneAndHit(Long id) {
        Board board = boardRepo.findOne(id);
        board.increaseHit();
        boardRepo.save(board);
        return board;
    }

    public Board findOneForMod(Long id) throws AuthenticationException {
        Board board = boardRepo.findOne(id);
        permissionCheck(board);
        return board;
    }

    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepo.findOne(boardId);
        permissionCheck(board);
        commentRepo.delete(board.getComments());
        boardRepo.delete(boardId);
    }

    private void permissionCheck(Board board) throws AuthenticationException {
        if (!board.verifyUser(SecurityContextUtils.getAuthenticatedUser().getId())) {
            throw new AuthenticationCredentialsNotFoundException("권한이 없습니다");
        }
    }

}
