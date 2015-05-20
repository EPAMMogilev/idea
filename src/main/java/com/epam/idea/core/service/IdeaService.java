package com.epam.idea.core.service;

import java.util.List;

import com.epam.idea.core.model.Idea;

public interface IdeaService extends BaseService<Idea, Long> {

	/**
	 * Deletes an idea.
	 *
	 * @param ideaId The id of the deleted idea.
	 * @return The deleted idea.
	 * @throws com.epam.idea.core.service.exception.IdeaNotFoundException if no idea was found with the given id.
	 */
	Idea deleteById(long ideaId);

	/**
	 * Updates the information of an idea.
	 *
	 * @param ideaId The id of the idea to update.
	 * @param source The information of the updated idea.
	 * @return The updated idea.
	 * @throws com.epam.idea.core.service.exception.IdeaNotFoundException If no idea was found with the given id.
	 */
	Idea update(long ideaId, Idea source);

	List<Idea> findIdeasByUserId(long userId);

	List<Idea> findIdeasByTagId(long tagId);

	Idea saveForUser(long userId, Idea idea);
}
