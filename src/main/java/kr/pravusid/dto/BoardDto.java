package kr.pravusid.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.user.User;

public class BoardDto extends BaseDto {

    private User user;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<CommentDto> comments;

    private int hit;

    public BoardDto(Board board) {
        assignBaseDtoVariable(board);
        this.user = board.getUser();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.comments = board.getComments().stream().map(c -> new CommentDto(c, this)).collect(Collectors.toList());
        this.hit = board.getHit();
    }

    private BoardDto() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content.replace("\n", "<br>").replace("<script>", "&lt;script&gt;").replace("</script>",
                "&lt;/script&gt;");
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
