package com.talsist.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talsist.domain.Board;
import com.talsist.service.BoardService;
import com.talsist.util.Pagination;

@Controller
public class BoardController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private BoardService boardSvc;

    @Autowired
    public BoardController(BoardService boardSvc) {
        this.boardSvc = boardSvc;
    }

    @GetMapping("/board")
    public String list(@PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable,
                       Pagination pagination, Model model) {
        Page<Board> list = boardSvc.findAll(pageable, pagination);
        logger.info("검색요청: 필터-{}, 검색어-{}", pagination.getFilter(), pagination.getKeyword());
        model.addAttribute("pagination", pagination.calcPage(list, 5));
        model.addAttribute("list", list);
        return "board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/board")
    public String write(Board board) {
        boardSvc.save(board);
        return "redirect:/board";
    }
    
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Long id, HttpServletRequest request, Authentication auth, Model model) {
        model.addAttribute("auth", auth);
        model.addAttribute("query", request.getQueryString());
        model.addAttribute("detail", boardSvc.findOneAndHit(id));
        return "board/detail";
    }

    @PutMapping("/board/{id}")
    public String modify(@PathVariable Long id, Board board, String query) {
        boardSvc.update(id, board);
        return "redirect:/board/" + id + "?" + query;
    }

    @DeleteMapping("/board")
    public @ResponseBody
    String delete(@RequestBody Board board) {
        try {
            boardSvc.delete(board.getId());

        } catch (Exception e) {
            logger.info("게시물 {} 삭제 중 오류 발생", board.getId());
        }
        return "/board";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/write")
    public String write(HttpServletRequest request) {
        return "board/write";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board/{id}/modify")
    public String modify(@PathVariable Long id, HttpServletRequest request, Model model) {
        model.addAttribute("query", request.getQueryString());
        model.addAttribute("detail", boardSvc.findOneForMod(id));
        return "board/modify";
    }

}
