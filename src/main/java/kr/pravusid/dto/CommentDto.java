package kr.pravusid.dto;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.comment.Comment;
import kr.pravusid.domain.user.User;

public class CommentDto extends BaseDto {

    private User user;
    private BoardDto board;
    private String content;
    private long replyDepth;
    private long replyOrder;

    public CommentDto(Comment comment, BoardDto boardDto) {
        assignBaseDtoVariable(comment);
        this.user = comment.getUser();
        this.board = boardDto;
        this.content = comment.getContent();
        this.replyDepth = comment.getReplyDepth();
        this.replyOrder = comment.getReplyOrder();
    }

    private CommentDto() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BoardDto getBoard() {
        return board;
    }

    public void setBoard(BoardDto board) {
        this.board = board;
    }

    public String getContent() {
        return content.replace("\n", "<br>").replace("<script>", "&lt;script&gt;").replace("</script>",
                "&lt;/script&gt;");
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getReplyDepth() {
        return replyDepth;
    }

    public void setReplyDepth(long replyDepth) {
        this.replyDepth = replyDepth;
    }

    public long getReplyOrder() {
        return replyOrder;
    }

    public void setReplyOrder(long replyOrder) {
        this.replyOrder = replyOrder;
    }

    public Comment toEntity(User user, Board board) {
        return new Comment(user, board, content, replyDepth, replyOrder);
    }

}
