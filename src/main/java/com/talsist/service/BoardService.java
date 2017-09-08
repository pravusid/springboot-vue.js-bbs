package com.talsist.service;

import com.talsist.domain.Board;
import com.talsist.domain.BoardRepository;
import com.talsist.domain.CommentRepository;
import com.talsist.domain.User;
import com.talsist.exception.NotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepo;
    @Autowired
    private CommentRepository commentRepo;

    public Page<Board> findAll(Pageable pageable) {
        return boardRepo.findAll(pageable);
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
