package kr.pravusid.domain.board;

import kr.pravusid.domain.BaseEntity;
import kr.pravusid.domain.UserVerifiable;
import kr.pravusid.domain.comment.Comment;
import kr.pravusid.domain.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Board extends BaseEntity implements UserVerifiable {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_board_user"))
    private User user;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
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

    public Board update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    @Override
    public boolean verifyUser(String username) {
        return this.user.verifyUser(username);
    }

}
