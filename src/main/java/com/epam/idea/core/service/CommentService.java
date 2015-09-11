package com.epam.idea.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.epam.idea.core.model.Comment;

public interface CommentService extends BaseService<Comment, Long> {
	List<Comment> findCommentsByUserId(Long userId);
	List<Comment> findCommentsByIdeaId(Pageable pageable, Long ideaId);
}
