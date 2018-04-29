package kr.pravusid.domain.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import kr.pravusid.domain.BaseEntity;
import kr.pravusid.domain.board.Board;
import kr.pravusid.domain.user.User;

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

    @Column(columnDefinition = "bigint DEFAULT 0")
    private long replyRoot;
    @Column(columnDefinition = "bigint DEFAULT 0")
    private long replyDepth;
    @Column(columnDefinition = "bigint DEFAULT 0")
    private long replyOrder;

    public Comment(User user, Board board, String content, long replyRoot, long replyDepth, long replyOrder) {
        this.user = user;
        this.board = board;
        this.content = content;
        this.replyRoot = replyRoot;
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

    public long getReplyRoot() {
        return replyRoot;
    }

    public long getReplyDepth() {
        return replyDepth;
    }

    public long getReplyOrder() {
        return replyOrder;
    }

    public void initReplyRoot() {
        replyRoot = getId();
    }

    public void increaseReplyDepth() {
        this.replyDepth += 1;
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

    public boolean verifyUser(Long reqId) {
        return this.user.verifyId(reqId);
    }

}
