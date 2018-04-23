package com.talsist.domain.board;

import com.talsist.domain.BaseEntity;
import com.talsist.domain.comment.Comment;
import com.talsist.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.List;

@Entity
public class Board extends BaseEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_board_user"))
    private User user;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Lob
    private String content;

    @OneToMany(mappedBy = "board")
    @OrderBy("reply_root ASC, reply_order ASC")
    private List<Comment> comments;

    private int hit;

    public Board(User user, String title, String content) {
        this();
        this.user = user;
        this.title = title;
        this.content = content;
    }

    private Board() {
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getHit() {
        return hit;
    }

    public void increaseHit() {
        this.hit += 1;
    }

    public void update(Board reqBoard) {
        if (!user.equals(reqBoard.getUser())) {
            return;
        }
        this.title = reqBoard.title;
        this.content = reqBoard.content;
    }

    public boolean verifyUser(Long reqId) {
        return this.user.verifyId(reqId);
    }

}
