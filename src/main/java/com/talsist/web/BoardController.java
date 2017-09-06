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

import com.talsist.domain.Board;
import com.talsist.domain.BoardRepository;
import com.talsist.domain.User;
import com.talsist.exception.NotAllowedException;
import com.talsist.exception.NotLoggedInException;
import com.talsist.util.HttpSessionUtils;
import com.talsist.util.Pagination;

@Controller
public class BoardController {

	@Autowired
	private BoardRepository boardRepo;

	@GetMapping("/board")
	public String findAll(
			@PageableDefault(size = 5, sort = "id", direction = Direction.DESC) Pageable pageable,
			Pagination pagination, Model model) {
		Page<Board> list = boardRepo.findAll(pageable);
		model.addAttribute("pagination", pagination.calcPage(list, 5));
		model.addAttribute("list", list);
		return "board/list";
	}

	@PostMapping("/board")
	public String insert(Board board, HttpServletRequest request, HttpSession session) {
		try {
			permissionCheck(session);
			board.setUser(HttpSessionUtils.getSessionUser(session));
			boardRepo.save(board);
			return "redirect:/board";

		} catch (Exception e) {
			return HttpSessionUtils.redirctToLoginPage(request, session);
		}
	}

	@PutMapping("/board")
	public String update(Long id, Board reqBoard, int page, HttpServletRequest request, HttpSession session) {
		try {
			Board board = boardRepo.findOne(id);
			permissionCheck(session, board);
			board.Update(reqBoard);
			boardRepo.save(board);
			return "redirect:/board/" + id + "?page=" + page;

		} catch (Exception e) {
			return HttpSessionUtils.redirctToLoginPage(request, session);
		}
	}

	@DeleteMapping("/board")
	public String delete(Board board, HttpSession session) {
		return "redirect:/board";
	}

	@GetMapping("/board/{id}")
	public String findOne(@PathVariable long id, int page, Model model) {
		model.addAttribute("page", page);
		model.addAttribute("detail", boardRepo.findOne(id));
		return "board/detail";
	}

	@GetMapping("/board/{id}/modify")
	public String modify(@PathVariable Long id, int page, HttpServletRequest request, HttpSession session, Model model) {
		try {
			Board board = boardRepo.findOne(id);
			permissionCheck(session, board);
			model.addAttribute("page", page);
			model.addAttribute("detail", board);
			return "board/modify";

		} catch (Exception e) {
			return HttpSessionUtils.redirctToLoginPage(request, session);
		}
	}

	@GetMapping("/board/write")
	public String write(HttpServletRequest request, HttpSession session) {
		try {
			permissionCheck(session);
			return "board/write";
		} catch (Exception e) {
			return HttpSessionUtils.redirctToLoginPage(request, session);
		}
	}
	
	private void permissionCheck(HttpSession session) throws NotLoggedInException {
		if (!HttpSessionUtils.isLogin(session)) {
			throw new NotLoggedInException();
		}
	}

	private void permissionCheck(HttpSession session, Board board) throws NotAllowedException {
		User sessionUser = HttpSessionUtils.getSessionUser(session);
		if (sessionUser == null || !board.verifyUser(sessionUser)) {
			throw new NotAllowedException();
		}
	}

}
