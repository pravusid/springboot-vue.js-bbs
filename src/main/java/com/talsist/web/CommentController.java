package com.talsist.web;

import com.talsist.domain.Comment;
import com.talsist.domain.User;
import com.talsist.exception.NotLoggedInException;
import com.talsist.service.CommentService;
import com.talsist.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentSvc;

    @PostMapping("/board/{boardId}/comment")
    public String reply(@PathVariable Long boardId, String query, Comment comment, HttpServletRequest request) {
        try {
            User user = HttpUtils.getSessionUser(request.getSession());
            commentSvc.save(comment, user, boardId);
            return "redirect:/board/" + boardId + "?" + query;

        } catch (NotLoggedInException e) {
            return HttpUtils.redirctToLoginPage(request);
        }
    }

    @PutMapping("/board/{boardId}/comment")
    public String modify(@PathVariable Long boardId, Long id, String query, Comment reqComment,
                         HttpServletRequest request) {
        try {
            commentSvc.update(reqComment, id, HttpUtils.getSessionUser(request.getSession()).getId());
            return "redirect:/board/" + boardId + "?" + query;

        } catch (NotLoggedInException e) {
            return HttpUtils.redirctToLoginPage(request);
        }
    }

    @DeleteMapping("/board/{boardId}/comment")
    public String delete(@PathVariable Long boardId, Long id, String query, HttpServletRequest request) {
        try {
            commentSvc.delete(boardId, id, HttpUtils.getSessionUser(request.getSession()).getId());
        } catch (Exception e) {
            System.out.println("댓글 삭제 중 오류 발생");
        }
        return "redirect:/board/" + boardId + "?" + query;
    }

}
