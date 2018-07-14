package kr.pravusid.web;

import kr.pravusid.service.CommentService;
import kr.pravusid.service.SessionUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import kr.pravusid.dto.CommentDto;

@Controller
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private SessionUserService sessionUserService;
    private CommentService commentService;

    public CommentController(SessionUserService sessionUserService, CommentService commentService) {
        this.sessionUserService = sessionUserService;
        this.commentService = commentService;
    }

    @PostMapping("/board/{boardId}/comment")
    public String reply(@PathVariable Long boardId, String query, CommentDto commentDto) {
        commentService.save(sessionUserService.getAuthUsername(), boardId, commentDto);
        return "redirect:/board/" + boardId + "?" + query;
    }

    @PutMapping("/board/{boardId}/comment/{id}")
    public String modify(@PathVariable Long boardId, @PathVariable Long id, String query, CommentDto commentDto) {
        commentService.update(sessionUserService.getAuthUsername(), id, commentDto);
        return "redirect:/board/" + boardId + "?" + query;
    }

    @DeleteMapping("/board/{boardId}/comment/{id}")
    public String delete(@PathVariable Long boardId, @PathVariable Long id, String query) {
        try {
            commentService.delete(sessionUserService.getAuthUsername(), boardId, id);

        } catch (Exception e) {
            logger.info("댓글삭제오류: board-{}, comment-{}", boardId, id);
        }
        return "redirect:/board/" + boardId + "?" + query;
    }

}
