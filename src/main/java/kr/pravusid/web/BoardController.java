package kr.pravusid.web;

import kr.pravusid.dto.BoardDto;
import kr.pravusid.dto.Pagination;
import kr.pravusid.service.BoardService;
import kr.pravusid.service.SessionUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/board")
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private SessionUserService sessionUserService;
    private BoardService boardService;

    public BoardController(SessionUserService sessionUserService, BoardService boardService) {
        this.sessionUserService = sessionUserService;
        this.boardService = boardService;
    }

    @GetMapping("")
    public String list(@PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable,
                       Pagination pagination, Model model) {
        Page<BoardDto> list = boardService.findAll(pageable, pagination).map(BoardDto::of);
        logger.info("검색요청: 필터-{}, 검색어-{}", pagination.getFilter(), pagination.getKeyword());

        model.addAttribute("pagination", pagination.calcPage(list, 5));
        model.addAttribute("list", list);
        return "board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public String write(BoardDto boardDto) {
        boardService.save(sessionUserService.getAuthUsername(), boardDto);
        return "redirect:/board";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, HttpServletRequest request, Authentication auth, Model model) {
        model.addAttribute("auth", auth);
        model.addAttribute("query", request.getQueryString());
        model.addAttribute("detail", BoardDto.of(boardService.findOneAndHit(id)));
        return "board/detail";
    }

    @PutMapping("/{id}")
    public String modify(@PathVariable Long id, BoardDto boardDto, String query) {
        boardService.update(sessionUserService.getAuthUsername(), id, boardDto);
        return "redirect:/board/" + id + "?" + query;
    }

    @DeleteMapping("")
    public @ResponseBody String delete(@RequestBody BoardDto boardDto) {
        try {
            boardService.delete(sessionUserService.getAuthUsername(), boardDto.getId());

        } catch (Exception e) {
            logger.error("게시물 {} 삭제 중 오류 발생", boardDto.getId());
        }
        return "/board";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String modify(@PathVariable Long id, HttpServletRequest request, Model model) {
        model.addAttribute("query", request.getQueryString());
        model.addAttribute("detail",
                BoardDto.of(boardService.findOneForMod(sessionUserService.getAuthUsername(), id)));
        return "board/modify";
    }

}
