package com.epam.idea.core.service.impl;

import java.util.List;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.repository.CommentRepository;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.core.service.exception.CommentDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void delete(Comment deleted) {
		commentRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Comment findOne(Long id) {
		return commentRepository.findOne(id).orElseThrow(CommentDoesNotExistException::new);
	}

	@Override
	public Comment save(Comment persisted) {
		return commentRepository.save(persisted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> findCommentsByUserId(Long userId) {
		return commentRepository.findByUserId(userId);
	}
}
