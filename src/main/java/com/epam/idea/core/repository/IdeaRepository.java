package com.epam.idea.core.repository;

import java.util.List;

import com.epam.idea.core.model.Idea;
import org.springframework.data.jpa.repository.Query;

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

	/**
	 * Return a list of ideas which marked by the tag with given id,
	 * or an empty list if the tag has no ideas.
	 *
	 * @param tagId The id of the tag.
	 * @return All the ideas of the tag.
	 */
	@Query("select t.ideasWithTag from Tag t where t.id = ?1")
	List<Idea> findByTagId(Long tagId);
}
