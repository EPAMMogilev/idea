package com.epam.idea.core.service;

import com.epam.idea.core.model.Comment;

import java.util.List;

public interface CommentService extends BaseService<Comment, Long> {
	List<Comment> findCommentsByUserId(Long userId);
}
