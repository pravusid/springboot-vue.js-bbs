package com.talsist.web;

import com.talsist.domain.Board;
import com.talsist.service.BoardService;
import com.talsist.util.HttpUtils;
import com.talsist.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardSvc;

    @GetMapping("/board")
    public String list(@PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable,
                       Pagination pagination, Model model) {
        Page<Board> list = boardSvc.findAll(pageable, pagination);
        model.addAttribute("pagination", pagination.calcPage(list, 5));
        model.addAttribute("list", list);
        return "board/list";
    }

    @PostMapping("/board")
    public String write(Board board, HttpServletRequest request) {
        try {
            HttpUtils.loginCheck(request.getSession());
            boardSvc.save(board, HttpUtils.getSessionUser(request.getSession()));
            return "redirect:/board";

        } catch (Exception e) {
            return HttpUtils.redirctToLoginPage(request);
        }
    }

    @PutMapping("/board")
    public String modify(Long id, Board reqBoard, String query, HttpServletRequest request) {
        try {
            boardSvc.update(id, HttpUtils.getSessionUser(request.getSession()).getId(), reqBoard);
            return "redirect:/board/" + id + "?" + query;

        } catch (Exception e) {
            return HttpUtils.redirctToLoginPage(request);
        }
    }

    @DeleteMapping("/board")
    public @ResponseBody
    String delete(@RequestBody Board board, HttpSession session) {
        try {
            boardSvc.delete(board.getId(), HttpUtils.getSessionUser(session).getId());

        } catch (Exception e) {
            System.out.println("삭제 중 오류발생");
        }
        return "/board";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Long id, HttpServletRequest request, Model model) {
        model.addAttribute("query", request.getQueryString());
        model.addAttribute("detail", boardSvc.findOneAndHit(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/modify")
    public String modify(@PathVariable Long id, HttpServletRequest request, Model model) {
        try {
            model.addAttribute("query", request.getQueryString());
            model.addAttribute("detail",
                    boardSvc.findOneForMod(id, HttpUtils.getSessionUser(request.getSession()).getId()));
            return "board/modify";

        } catch (Exception e) {
            return HttpUtils.redirctToLoginPage(request);
        }
    }

    @GetMapping("/board/write")
    public String write(HttpServletRequest request) {
        try {
            HttpUtils.loginCheck(request.getSession());
            return "board/write";

        } catch (Exception e) {
            return HttpUtils.redirctToLoginPage(request);
        }
    }

}
