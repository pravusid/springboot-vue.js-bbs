package kr.pravusid.service;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.board.BoardSpecification;
import kr.pravusid.domain.comment.CommentRepository;
import kr.pravusid.domain.user.User;
import kr.pravusid.domain.user.UserRepository;
import kr.pravusid.dto.BoardDto;
import kr.pravusid.dto.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BoardService {

    private UserService userService;

    private UserRepository userRepository;
    private BoardRepository boardRepository;
    private CommentRepository commentRepository;

    public BoardService(UserService userService, UserRepository userRepository,
                        BoardRepository boardRepository, CommentRepository commentRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    public Page<Board> findAll(Pageable pageable, Pagination pagination) {
        if (pagination.getKeyword() == null) {
            return boardRepository.findAll(pageable);
        }

        String keyword = pagination.getKeyword();
        return (pagination.filterMatcher(Pagination.FilterType.ALL))?
                boardRepository.findAll(Specifications.where(BoardSpecification.findByAll(keyword)), pageable):
                boardRepository.findAll(Specifications.where(BoardSpecification.findByFilter(pagination)), pageable);
    }

    public void save(String username, BoardDto dto) {
        User user = userRepository.findByUsername(username);
        boardRepository.save(dto.toEntity(user));
    }

    @Transactional
    public void update(String username, Long id, BoardDto dto) {
        Board board = boardRepository.findOne(id);
        userService.permissionCheck(username, board);
        board.update(dto.getTitle(), dto.getContent());
    }

    public Board findOneAndHit(Long id) {
        Board board = boardRepository.findOne(id);
        board.increaseHit();
        boardRepository.save(board);
        return board;
    }

    public Board findOneForMod(String username, Long id) {
        Board board = boardRepository.findOne(id);
        userService.permissionCheck(username, board);
        return board;
    }

    @Transactional
    public void delete(String username, Long id) {
        Board board = boardRepository.findOne(id);
        userService.permissionCheck(username, board);
        commentRepository.delete(board.getComments());
        boardRepository.delete(id);
    }

}
