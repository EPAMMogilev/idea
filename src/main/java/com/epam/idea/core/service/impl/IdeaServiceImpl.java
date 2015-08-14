package com.epam.idea.core.service.impl;

import java.util.ArrayList;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
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
	public void delete(@Param("idea") Idea deleted) {
		ideaRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> findAll() {
		final List<Idea> allIdeas = ideaRepository.findAll();

		allIdeas.forEach(idea -> {
			Hibernate.initialize(idea.getAuthor());
			Hibernate.initialize(idea.getRelatedTags());
			idea.setLiked(isCurrentUserLikedIdea(idea.getId()));
		});
		return allIdeas;
	}

	@Override
	@Transactional(readOnly = true)
	public Idea findOne(final Long ideaId) {
		final Optional<Idea> ideaOptional = ideaRepository.findOne(ideaId);
		return ideaOptional.map(idea -> {
			Hibernate.initialize(idea.getRelatedTags());
			Hibernate.initialize(idea.getLikedUsers());
			idea.setLiked(isCurrentUserLikedIdea(ideaId));
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
	@PostAuthorize("hasRole('ADMIN') or returnObject.author.id == principal.id")
	public Idea update(final long ideaId, final Idea source) {
		final Idea target = findOne(ideaId);
		target.updateWith(source);
		return target;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> findAllByUserId(final long userId) {
		List<Idea> ideas = ideaRepository.findByUserId(userId);
		ideas.forEach(idea -> {
			Hibernate.initialize(idea.getRelatedTags());
			idea.setLiked(isCurrentUserLikedIdea(idea.getId()));
		});
		return ideas;
	}

	@Override
	public List<Idea> findAllByTagId(Pageable pageable, Long tagId) {
		return findAllByQueryAndTagId(pageable, null, tagId);
	}

	@Override
	public List<Idea> findAllByQueryAndTagId(Pageable pageable, String query, Long tagId) {
		List<Idea> ideas;
		if (tagId != null && query != null) {
			ideas = ideaRepository.findAllByTagIdAndByQuery(pageable, tagId, query.toUpperCase());
		} else {
			if (tagId != null) {
				ideas = ideaRepository.findAllByTagId(pageable, tagId);
			} else {
				if (query != null) {
					ideas = ideaRepository.findAllByQuery(pageable, query.toUpperCase());
				} else {
					ideas = ideaRepository.findAll(pageable);
				}
			}
		}
		ideas.forEach(idea -> {
			Hibernate.initialize(idea.getRelatedTags());
			idea.setLiked(isCurrentUserLikedIdea(idea.getId()));
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
		Idea idea = ideaRepository.findByIdAndLikedByCurrentUser(ideaId);
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
		boolean isCurrentUserLikedIdea = false;
		boolean isAnonymous = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
		if (!isAnonymous) {
			Idea idea = ideaRepository.findByIdAndLikedByCurrentUser(ideaId);
			isCurrentUserLikedIdea = idea != null;
		}
		return isCurrentUserLikedIdea;
	}

	@Override
	public List<Idea> findAll(Pageable pageable) {
		List<Idea> allIdeas = ideaRepository.findAll(pageable);
		allIdeas.forEach(idea -> {
			Hibernate.initialize(idea.getRelatedTags());
			idea.setLiked(isCurrentUserLikedIdea(idea.getId()));
		});
		return allIdeas;
	}
}