package kr.pravusid.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import kr.pravusid.domain.Board;
import kr.pravusid.domain.User;
import kr.pravusid.exception.NotAllowedException;
import kr.pravusid.repository.BoardRepository;
import kr.pravusid.repository.BoardSpecification;
import kr.pravusid.repository.CommentRepository;
import kr.pravusid.util.Pagination;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepo;
    @Autowired
    private CommentRepository commentRepo;

    public Page<Board> findAll(Pageable pageable, Pagination pagination) {
        if (pagination.getKeyword() == null) {
            return boardRepo.findAll(pageable);
        }

        Page<Board> list = null;
        String keyword = pagination.getKeyword();
        if (pagination.filterMatcher(Pagination.FilterType.ALL)) {
            list = boardRepo.findAll(Specifications.where(BoardSpecification.findByAll(keyword)), pageable);
        } else {
            list = boardRepo.findAll(Specifications.where(BoardSpecification.findByFilter(pagination)), pageable);
        }
        return list;
    }

    public void save(Board board, User user) {
        board.setUser(user);
        boardRepo.save(board);
    }

    public void update(Long boardId, Long userId, Board reqBoard) throws NotAllowedException {
        Board board = boardRepo.findOne(boardId);
        permissionCheck(board, userId);
        board.Update(reqBoard);
        boardRepo.save(board);
    }

    public Board findOneAndHit(Long id) {
        Board board = boardRepo.findOne(id);
        board.increaseHit();
        boardRepo.save(board);
        return board;
    }

    public Board findOneForMod(Long boardId, Long userId) {
        Board board = boardRepo.findOne(boardId);
        permissionCheck(board, userId);
        return board;
    }

    @Transactional
    public void delete(Long boardId, Long userId) {
        Board board = boardRepo.findOne(boardId);
        permissionCheck(board, userId);
        commentRepo.delete(board.getComments());
        boardRepo.delete(boardId);
    }

    private void permissionCheck(Board board, Long userId) throws NotAllowedException {
        if (!board.verifyUser(userId)) {
            throw new NotAllowedException();
        }
    }

}
