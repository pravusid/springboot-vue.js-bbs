package kr.pravusid.api.v1.board;

import kr.pravusid.dto.BoardDto;
import kr.pravusid.dto.Pagination;
import kr.pravusid.service.BoardService;
import kr.pravusid.service.JwtUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/board")
public class BoardApi {

    private JwtUserService jwtUserService;
    private BoardService boardService;

    public BoardApi(JwtUserService jwtUserService, BoardService boardService) {
        this.jwtUserService = jwtUserService;
        this.boardService = boardService;
    }

    @GetMapping("")
    public Page<BoardDto> list(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                               Pagination pagination) {
        return boardService.findAll(pageable, pagination).map(BoardDto::of);
    }

    @PostMapping("")
    public void write(@RequestHeader("Authorization") String authorization,
                      @RequestBody BoardDto boardDto) {
        boardService.save(jwtUserService.getTokenUsername(authorization), boardDto);
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
