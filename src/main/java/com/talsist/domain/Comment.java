package com.talsist.domain;

import javax.persistence.*;

@Entity
public class Comment extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_user"))
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_board"))
    private Board board;

    @Lob
    private String content;

    @Column(columnDefinition = "bigint DEFAULT 0")
    private Long replyRoot = Long.valueOf(0);
    @Column(columnDefinition = "bigint DEFAULT 0")
    private Long replyDepth = Long.valueOf(0);
    @Column(columnDefinition = "bigint DEFAULT 0")
    private Long replyOrder = Long.valueOf(0);

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getContent() {
        return content.replace("\n", "<br>");
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReplyRoot() {
        return replyRoot;
    }

    public void setReplyRoot(Long replyRoot) {
        this.replyRoot = replyRoot;
    }

    public Long getReplyDepth() {
        return replyDepth;
    }

    public void setReplyDepth(Long replyDepth) {
        this.replyDepth = replyDepth;
    }

    public Long getReplyOrder() {
        return replyOrder;
    }

    public void setReplyOrder(Long replyOrder) {
        this.replyOrder = replyOrder;
    }

    public void initReplyRoot() {
        this.replyRoot = getId();
    }

    public void adjustDepthAndOrder() {
        this.replyDepth += 1;
        this.replyOrder += 1;
    }

    public void update(Comment reqComment) {
        this.content = reqComment.content;
    }

    public boolean verifyUser(Long reqId) {
        return this.user.verifyId(reqId);
    }

}
