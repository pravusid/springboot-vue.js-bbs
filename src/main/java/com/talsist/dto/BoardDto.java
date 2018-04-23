package com.talsist.dto;

import java.util.List;

import com.talsist.domain.board.Board;
import com.talsist.domain.comment.Comment;
import com.talsist.domain.user.User;

public class BoardDto extends BaseDto {

    private User user;
    private String title;
    private String content;
    private List<Comment> comments;
    private int hit;

    private BoardDto() {
    }

    public BoardDto(Board board) {
        this();
        this.id = board.getId();
        this.regdate = board.getRegdate();
        this.moddate = board.getModdate();
        this.user = board.getUser();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.comments = board.getComments();
        this.hit = board.getHit();
    }

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

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getNumberOfComments() {
        return comments.size();
    }

    public Board toEntity(User user) {
        return new Board(user, title, content);
    }

}
