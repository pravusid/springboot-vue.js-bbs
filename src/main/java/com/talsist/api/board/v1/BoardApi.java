package com.talsist.api.board.v1;

import com.talsist.domain.Board;
import com.talsist.service.BoardService;
import com.talsist.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board/v1")
public class BoardApi {

    private BoardService boardSvc;

    @Autowired
    public BoardApi(BoardService boardSvc) {
        this.boardSvc = boardSvc;
    }

    @GetMapping("/")
    public Page<Board> list(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
    Pagination pagination) {
        Page<Board> list = boardSvc.findAll(pageable, pagination);
        pagination.calcPage(list, 5);
        return list;
    }

    @PostMapping("/")
    public String write() {
        return null;
    }

    @GetMapping("/{id}")
    public String readOne() {
        return "hello";
    }

    @PutMapping("/{id}")
    public String modifyOne() {
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteOne() {
        return null;
    }

}
