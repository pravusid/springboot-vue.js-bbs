package com.talsist.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import com.talsist.util.HttpSessionUtils;
import com.talsist.util.Pagination;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardSvc;

	@GetMapping("/board")
	public String list(@PageableDefault(size = 5, sort = "id", direction = Direction.DESC) Pageable pageable,
			Pagination pagination, Model model) {
		Page<Board> list = boardSvc.findAll(pageable);
		model.addAttribute("pagination", pagination.calcPage(list, 5));
		model.addAttribute("list", list);
		return "board/list";
	}

	@PostMapping("/board")
	public String write(Board board, HttpServletRequest request, HttpSession session) {
		try {
			HttpSessionUtils.loginCheck(session);
			boardSvc.save(board, HttpSessionUtils.getSessionUser(session));
			return "redirect:/board";

		} catch (Exception e) {
			return HttpSessionUtils.redirctToLoginPage(request, session);
		}
	}

	@PutMapping("/board")
	public String modify(Long id, Board reqBoard, int page, HttpServletRequest request, HttpSession session) {
		try {
			boardSvc.update(id, HttpSessionUtils.getSessionUser(session).getId(), reqBoard);
			return "redirect:/board/" + id + "?page=" + page;

		} catch (Exception e) {
			return HttpSessionUtils.redirctToLoginPage(request, session);
		}
	}

	@DeleteMapping("/board")
	public @ResponseBody String delete(@RequestBody Board board, HttpSession session) {
		try {
			System.out.println("아이디는"+board.getId());
			boardSvc.delete(board.getId(), HttpSessionUtils.getSessionUser(session).getId());
			
		} catch (Exception e) {
			System.out.println("삭제 중 오류발생");
		}
		return "/board";
	}

	@GetMapping("/board/{id}")
	public String detail(@PathVariable Long id, int page, Model model) {
		model.addAttribute("page", page);
		model.addAttribute("detail", boardSvc.findOneAndHit(id));
		return "board/detail";
	}

	@GetMapping("/board/{id}/modify")
	public String modify(@PathVariable Long id, int page, HttpServletRequest request, HttpSession session, Model model) {
		try {
			model.addAttribute("page", page);
			model.addAttribute("detail",
					boardSvc.findOneForMod(id, HttpSessionUtils.getSessionUser(session).getId()));
			return "board/modify";

		} catch (Exception e) {
			return HttpSessionUtils.redirctToLoginPage(request, session);
		}
	}

	@GetMapping("/board/write")
	public String write(HttpServletRequest request, HttpSession session) {
		try {
			HttpSessionUtils.loginCheck(session);
			return "board/write";
		} catch (Exception e) {
			return HttpSessionUtils.redirctToLoginPage(request, session);
		}
	}
	
}
