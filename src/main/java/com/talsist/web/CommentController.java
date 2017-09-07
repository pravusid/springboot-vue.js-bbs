package com.talsist.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.talsist.domain.BoardRepository;
import com.talsist.domain.Comment;
import com.talsist.domain.CommentRepository;
import com.talsist.domain.User;
import com.talsist.exception.NotLoggedInException;
import com.talsist.service.CommentService;
import com.talsist.util.HttpSessionUtils;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentSvc;
	
	@PostMapping("/board/{boardId}/comment")
	public String insert(@PathVariable Long boardId, int page, Comment comment, HttpSession session) {
		try {
			User user = HttpSessionUtils.getSessionUser(session);
			commentSvc.save(comment, user, boardId);
			return "redirect:/board/" + boardId + "?page=" + page;
			
		} catch (NotLoggedInException e) {
			session.setAttribute("prevPage", "/board/" + boardId + "?page=" + page);
			return "redirect:/login";
		}
	}

}
