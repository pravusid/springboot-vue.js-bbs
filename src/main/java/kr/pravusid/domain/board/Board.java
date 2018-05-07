package kr.pravusid.domain.board;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import kr.pravusid.domain.BaseEntity;
import kr.pravusid.domain.comment.Comment;
import kr.pravusid.domain.user.User;

@Entity
public class Board extends BaseEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_board_user"))
    private User user;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @OneToMany(mappedBy = "board")
    @OrderBy("reply_order ASC")
    private List<Comment> comments;

    private int hit;

    public Board(User user, String title, String content) {
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
