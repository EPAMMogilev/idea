package com.epam.idea.core.repository;

import java.util.List;

import com.epam.idea.core.model.Comment;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends BaseRepository<Comment, Long> {

	/**
	 * Return a list of comments which belongs to the user with given id,
	 * or an empty list if the user has no comments.
	 *
	 * @param userId The id of the user.
	 * @return All the comments of the user.
	 */
	@Query("select c from Comment c where c.author.id = ?1")
	List<Comment> findByUserId(Long userId);
}
