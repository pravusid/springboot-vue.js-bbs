package kr.pravusid.api.v1;

import kr.pravusid.dto.CommentDto;
import kr.pravusid.service.CommentService;
import kr.pravusid.service.JwtUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/board")
public class CommentApi {

    private static final Logger logger = LoggerFactory.getLogger(CommentApi.class);

    private JwtUserService jwtUserService;
    private CommentService commentService;

    public CommentApi(JwtUserService jwtUserService, CommentService commentService) {
        this.jwtUserService = jwtUserService;
        this.commentService = commentService;
    }

    @GetMapping("/{boardId}/comment")
    public List<CommentDto> comments(@PathVariable Long boardId) {
        return commentService.findAll(boardId)
                .stream().map(CommentDto::of).collect(Collectors.toCollection(ArrayList::new));
    }

    @PostMapping("/{boardId}/comment")
    public void reply(@PathVariable Long boardId,
                      @RequestHeader("Authorization") String authorization,
                      @RequestBody CommentDto commentDto) {
        commentService.save(jwtUserService.getTokenUsername(authorization), boardId, commentDto);
    }

    @PutMapping("/{boardId}/comment/{id}")
    public void modify(@PathVariable Long boardId, @PathVariable Long id,
                       @RequestHeader("Authorization") String authorization,
                       @RequestBody CommentDto commentDto) {
        commentService.update(jwtUserService.getTokenUsername(authorization), id, commentDto);
    }

    @DeleteMapping("/{boardId}/comment/{id}")
    public void delete(@PathVariable Long boardId, @PathVariable Long id,
                       @RequestHeader("Authorization") String authorization) {
        try {
            commentService.delete(jwtUserService.getTokenUsername(authorization), boardId, id);
        } catch (Exception e) {
            logger.info("댓글삭제오류: board-{}, comment-{}", boardId, id);
        }
    }

}
