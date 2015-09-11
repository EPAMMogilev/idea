package com.epam.idea.core.service.impl;

import java.util.List;
import java.util.Optional;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.repository.CommentRepository;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.core.service.exception.CommentDoesNotExistException;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
		List <Comment> comments = commentRepository.findAll();
		comments.forEach(comment -> {
			comment.setLiked(isCurrentUserLikedComment(comment.getId()));
		});
		return comments;
	}

	@Override
	@Transactional(readOnly = true)
	public Comment findOne(Long id) {
		final Optional<Comment> commentOptional = commentRepository.findOne(id);
		return commentOptional.map(comment -> {
			comment.setLiked(isCurrentUserLikedComment(id));
			return comment;
		}).orElseThrow(() -> new CommentDoesNotExistException());
	}

	@Override
	public Comment save(Comment persisted) {
		return commentRepository.save(persisted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> findCommentsByUserId(Long userId) {
		List <Comment> comments =  commentRepository.findByUserId(userId);
		comments.forEach(comment -> {
			comment.setLiked(isCurrentUserLikedComment(comment.getId()));
		});
		return comments;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> findCommentsByIdeaId(Pageable pageable, Long ideaId) {
		List <Comment> comments =  commentRepository.findByIdeaId(pageable, ideaId);
		comments.forEach(comment -> {
			Hibernate.initialize(comment.getLikedUsers());
			comment.setLiked(isCurrentUserLikedComment(comment.getId()));
		});
		return comments;
	}

	@Override
	public boolean isCurrentUserLikedComment(long commentId) {
		boolean isCurrentUserLikedComment = false;
		boolean isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
		if (!isAnonymous) {
			Comment comment = commentRepository.findByIdAndLikedByCurrentUser(commentId);
			isCurrentUserLikedComment = comment != null;
		}
		return isCurrentUserLikedComment;
	}
}
