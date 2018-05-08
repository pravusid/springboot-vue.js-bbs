package kr.pravusid.domain.comment;

import kr.pravusid.domain.BaseEntity;
import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.user.User;

import javax.persistence.*;

@Entity
public class Comment extends BaseEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_user"))
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_board"))
    private Board board;

    @Lob
    private String content;

    private long replyDepth;
    private long replyOrder;

    public Comment(User user, Board board, String content, long replyDepth, long replyOrder) {
        this.user = user;
        this.board = board;
        this.content = content;
        this.replyDepth = replyDepth;
        this.replyOrder = replyOrder;
    }

    private Comment() {
    }

    public User getUser() {
        return user;
    }

    public Board getBoard() {
        return board;
    }

    public String getContent() {
        return content;
    }

    public long getReplyDepth() {
        return replyDepth;
    }

    public long getReplyOrder() {
        return replyOrder;
    }

    public void adjustReplyDepth() {
        replyDepth += 1;
    }

    public void adjustReplyOrder(long... order) {
        if (order.length == 0) {
            replyOrder += 1;
        } else {
            replyOrder = order[0];
        }
    }

    public void update(Comment reqComment) {
        this.content = reqComment.content;
    }

    public boolean verifyUser(String username) {
        return this.user.verifyUser(username);
    }

}
