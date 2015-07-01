package com.epam.idea.core.service.impl;

import java.util.List;
import java.util.Optional;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.IdeaRepository;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.exception.IdeaNotFoundException;
import com.epam.idea.logger.Log;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

	private Log log = new Log(IdeaServiceImpl.class);

	@Autowired
	private IdeaRepository ideaRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@PreAuthorize("hasRole('ADMIN') or #idea.author.id == principal.id")
	public void delete(@Param("idea")Idea deleted) {
		ideaRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> findAll() {
		final List<Idea> allIdeas = ideaRepository.findAll();
		boolean isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;

		allIdeas.forEach(idea -> {
			Hibernate.initialize(idea.getAuthor());
			Hibernate.initialize(idea.getRelatedTags());
			if (!isAnonymous) {
				idea.setLiked(isCurrentUserLikedIdea(idea.getId()));
			}
		});
		return allIdeas;
	}

	@Override
	@Transactional(readOnly = true)
	public Idea findOne(final Long ideaId) {
		boolean isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
		final Optional<Idea> ideaOptional = ideaRepository.findOne(ideaId);
		return ideaOptional.map(idea -> {
					Hibernate.initialize(idea.getRelatedTags());
					if (isAnonymous) {
						idea.setLiked(isCurrentUserLikedIdea(ideaId));
					}
					return idea;
				}).orElseThrow(() -> new IdeaNotFoundException(ideaId));
	}

	@Override
	@PreAuthorize("isFullyAuthenticated()")
	public Idea save(final Idea persisted) {
		User author = userRepository.findCurrentUser();
		persisted.setAuthor(author);
		return ideaRepository.save(persisted);
	}

	@Override
	public Idea deleteById(final long ideaId) {
		final Idea deleted = findOne(ideaId);
		ideaRepository.delete(deleted);
		return deleted;
	}

	@Override
	public Idea update(final long ideaId, final Idea source) {
		final Idea target = findOne(ideaId);
		target.updateWith(source);
		return target;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> findIdeasByUserId(final long userId) {
		boolean isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
		List<Idea> ideas = ideaRepository.findByUserId(userId);
		ideas.forEach(idea -> {
			Hibernate.initialize(idea.getRelatedTags());
			if (isAnonymous) {
				idea.setLiked(isCurrentUserLikedIdea(idea.getId()));
			}
		});
		return ideas;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> findIdeasByTagId(final long tagId) {
		boolean isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
		final List<Idea> ideas = ideaRepository.findByTagId(tagId);
		ideas.forEach(idea -> {
			Hibernate.initialize(idea.getRelatedTags());
			if (isAnonymous) {
				idea.setLiked(isCurrentUserLikedIdea(idea.getId()));
			}
		});
		return ideas;
	}

	@Override
	public Idea saveForUser(final long userId, final Idea idea) {
		User user = userRepository.findOne(userId).get();
		List<Idea> ideas = user.getIdeas();
		ideas.add(idea);
		idea.setAuthor(user);
		Idea savedIdea = ideaRepository.save(idea);
		return savedIdea;
	}

	@Override
	public Idea changeIdeaLike(long ideaId) {
		Idea idea = ideaRepository.findIdeaByIdThatLikedCurrentUser(ideaId);
		User currentUser = userRepository.findCurrentUser();

		if (idea == null) {
			idea = ideaRepository.findOne(ideaId).orElseThrow(() -> new IdeaNotFoundException(ideaId));
			idea.getLikedUsers().add(currentUser);
			idea.setRating(idea.getRating() + 1);
		} else {
			idea.getLikedUsers().remove(currentUser);
			idea.setRating(idea.getRating() - 1);
		}

		return ideaRepository.save(idea);
	}

	@Override
	public boolean isCurrentUserLikedIdea(long ideaId) {
		Idea idea = ideaRepository.findIdeaByIdThatLikedCurrentUser(ideaId);
		return idea != null;
	}
}
