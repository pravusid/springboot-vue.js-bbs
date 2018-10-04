package kr.pravusid.domain.comment;

import kr.pravusid.domain.BaseEntity;
import kr.pravusid.domain.UserVerifiable;
import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.user.User;

import javax.persistence.*;

@Entity
public class Comment extends BaseEntity implements UserVerifiable {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_user"))
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_board"))
    private Board board;

    @Lob
    @Column(nullable = false)
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

    public void initializeRoot(long replyOrder) {
        this.replyDepth = 1;
        this.replyOrder = replyOrder;
    }

    public void initializeChild(long replyDepth, long replyOrder) {
        this.replyDepth = replyDepth + 1;
        this.replyOrder = replyOrder;
    }

    public void adjustReplyOrder(long... order) {
        if (order.length == 0) {
            replyOrder += 1;
        } else {
            replyOrder = order[0];
        }
    }

    public void update(String content) {
        this.content = content;
    }

    @Override
    public boolean verifyUser(String username) {
        return this.user.verifyUser(username);
    }

}
