package kr.pravusid.dto;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.user.User;
import kr.pravusid.helper.StringHelper;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDto extends BaseDto {

    private UserDto user;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<CommentDto> comments;

    private int hit;

    public BoardDto() {
    }

    private BoardDto(Board board) {
        super(board);
        this.user = UserDto.of(board.getUser());
        this.title = board.getTitle();
        this.content = board.getContent();
        this.comments = board.getComments().stream().map(CommentDto::of).collect(Collectors.toList());
        this.hit = board.getHit();
    }

    public static BoardDto of(Board it) {
        return new BoardDto(it);
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return StringHelper.escapeCharacters(content);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getNumberOfComments() {
        return comments.size();
    }

    public Board toEntity(User user) {
        return new Board(user, title, content);
    }

}
