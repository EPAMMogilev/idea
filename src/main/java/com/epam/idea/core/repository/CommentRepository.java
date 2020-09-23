package com.epam.idea.core.repository;

import com.epam.idea.core.model.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
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

	@Query("select c from Comment c where c.subject.id = ?1")
	List<Comment> findByIdeaId(Pageable pageable, Long ideaId);

	@Query("select c from Comment c left join c.likedUsers u where c.id = ?1 and u.id = ?#{ principal?.id }")
	Comment findByIdAndLikedByCurrentUser(long commentId);
}
