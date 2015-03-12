package com.epam.idea.core.repository;

import com.epam.idea.core.model.Idea;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdeaRepository extends BaseRepository<Idea, Long> {

	/**
	 * Return a list of ideas which belongs to the user with given id,
	 * or an empty list if the user has no ideas.
	 *
	 * @param userId The id of the user.
	 * @return All the ideas of the user.
	 */
	@Query("select i from Idea i where i.author.id = ?1")
	List<Idea> findByUserId(Long userId);
}
