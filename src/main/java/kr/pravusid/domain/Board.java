package kr.pravusid.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Board extends AbstractEntity {

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
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getNumberOfComments() {
        return comments.size();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getHit() {
        return hit;
    }

    public void increaseHit() {
        this.hit += 1;
    }

    public void Update(Board reqBoard) {
        this.title = reqBoard.title;
        this.content = reqBoard.content;
    }

    public boolean verifyUser(Long reqId) {
        return this.user.verifyId(reqId);
    }

}
