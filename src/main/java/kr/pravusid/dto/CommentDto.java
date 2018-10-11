package kr.pravusid.dto;

import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.comment.Comment;
import kr.pravusid.domain.user.User;
import kr.pravusid.helper.StringHelper;

public class CommentDto extends BaseDto {

    private UserDto user;

    private String content;

    private long replyDepth;

    private long replyOrder;

    public CommentDto() {
    }

    private CommentDto(Comment comment) {
        super(comment);
        this.user = UserDto.of(comment.getUser());
        this.content = comment.getContent();
        this.replyDepth = comment.getReplyDepth();
        this.replyOrder = comment.getReplyOrder();
    }

    public static CommentDto of(Comment it) {
        return new CommentDto(it);
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getContent() {
        return StringHelper.escapeCharacters(content);
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
