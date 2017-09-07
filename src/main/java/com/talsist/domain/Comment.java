package com.talsist.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_comment_user"))
	private User user;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_comment_board"))
	private Board board;
	
	@Lob
	private String content;

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
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
