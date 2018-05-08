package kr.pravusid.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.board.BoardRepository;
import kr.pravusid.domain.comment.Comment;
import kr.pravusid.domain.comment.CommentRepository;
import kr.pravusid.domain.user.User;
import kr.pravusid.domain.user.UserRepository;
import kr.pravusid.dto.BoardDto;
import kr.pravusid.dto.CommentDto;
import kr.pravusid.domain.user.customuserdetail.CustomUserDetailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @WithMockUser
    public void Save_A_Comment_Test() {
        // given user
        User user = userRepository.findByUsername("user");
        // given board
        BoardDto boDto = new BoardDto();
        boDto.setTitle("테스트제목");
        boDto.setContent("테스트본문");
        Board board = boardRepository.save(boDto.toEntity(user));
        boDto.setId(board.getId());
        // given comment
        CommentDto dto = new CommentDto();
        dto.setUser(user);
        dto.setBoard(boDto);
        dto.setContent("테스트댓글");

        // when
        commentService.save(dto, board.getId());

        // then
        Comment result = commentRepository.findOne(1L);
        Assert.assertEquals("테스트댓글", result.getContent());
    }

}
