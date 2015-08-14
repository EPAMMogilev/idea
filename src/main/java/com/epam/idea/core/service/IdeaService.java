package com.epam.idea.core.service;

import java.util.List;

import com.epam.idea.core.model.Authority;
import com.epam.idea.core.model.Idea;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IdeaService extends BaseService<Idea, Long> {

	/**
	 * Deletes an idea.
	 *
	 * @param ideaId The id of the deleted idea.
	 * @return The deleted idea.
	 * @throws com.epam.idea.core.service.exception.IdeaNotFoundException if no idea was found with the given id.
	 */
	@PostAuthorize("hasRole('ADMIN') or returnObject.author.id == principal.id")
	Idea deleteById(long ideaId);

	/**
	 * Updates the information of an idea.
	 *
	 * @param ideaId The id of the idea to update.
	 * @param source The information of the updated idea.
	 * @return The updated idea.
	 * @throws com.epam.idea.core.service.exception.IdeaNotFoundException If no idea was found with the given id.
	 */
	//@PreAuthorize("hasRole('ADMIN') or #idea.author.id == principal.id")
	Idea update(long ideaId, @Param("idea") Idea source);

	@PreAuthorize("hasRole('ADMIN') or userId == principal.id")
	Idea saveForUser(@Param("userId") long userId, Idea idea);

	@PreAuthorize("isFullyAuthenticated()")
	Idea changeIdeaLike(long ideaId);

	@PreAuthorize("isFullyAuthenticated()")
	boolean isCurrentUserLikedIdea(long ideaId);

	List<Idea> findAll(Pageable pageable);

	List<Idea> findAllByUserId(long userId);

	List<Idea> findAllByTagId(Pageable pageable, Long tag);

	List<Idea> findAllByQueryAndTagId(Pageable pageable, String query, Long tag);
}