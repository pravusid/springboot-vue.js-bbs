package com.talsist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talsist.domain.BoardRepository;
import com.talsist.domain.Comment;
import com.talsist.domain.CommentRepository;
import com.talsist.domain.User;

@Service
public class CommentService {

	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private CommentRepository commentRepo;
	
	public void save(Comment comment, User user, Long boardId) {
		comment.setUser(user);
		comment.setBoard(boardRepo.findOne(boardId));
		commentRepo.save(comment);
	}
	
	private void permissionCheck() {
		
	}
	
}
