package com.talsist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Board extends AbstractEntity {
    
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="fk_board_user"))
    private User user;
    
    @Column(nullable = false)
    private String title;
    
    @Lob
    private String content;
    
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
	
	public void Update(Board reqBoard) {
		this.title = reqBoard.title;
		this.content = reqBoard.content;
	}
	
	public boolean verifyUser(User reqUser) {
		return this.user.equals(reqUser);
	}
	
}
