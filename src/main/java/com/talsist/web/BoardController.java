package com.talsist.web;

import com.talsist.domain.Board;
import com.talsist.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardController {
    @Autowired
    private BoardRepository boardRepo;

    @GetMapping("/board")
    public Page<Board> findAll(Pageable pageable) {
        Page<Board> list = null;
        list = boardRepo.findAll(pageable);
        return list;
    }

    @GetMapping("/board/{id}")
    public Board findOne(@PathVariable long id) {
        Board board = null;
        board = boardRepo.findOne(id);
        return board;
    }

    @PostMapping("/board")
    public String insert(Board board) {
        boardRepo.save(board);
        return "";
    }

    @PutMapping("/board/{id}")
    public String update(@PathVariable long id) {

        return "";
    }

    @DeleteMapping("/board/{id}")
    public String delete(@PathVariable long id) {

        return "";
    }

}
