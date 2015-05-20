package com.epam.idea.core.service.impl;

import java.util.List;
import java.util.Optional;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.IdeaRepository;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.exception.IdeaNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

	@Autowired
	private IdeaRepository ideaRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void delete(final Idea deleted) {
		ideaRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> findAll() {
		final List<Idea> allIdeas = ideaRepository.findAll();
		allIdeas.forEach(idea -> {
			Hibernate.initialize(idea.getAuthor());
			Hibernate.initialize(idea.getRelatedTags());
		});
		return allIdeas;
	}

	@Override
	@Transactional(readOnly = true)
	public Idea findOne(final Long ideaId) {
		final Optional<Idea> ideaOptional = ideaRepository.findOne(ideaId);
		return ideaOptional.map(idea -> {
					Hibernate.initialize(idea.getRelatedTags());
					return idea;
				}).orElseThrow(() -> new IdeaNotFoundException(ideaId));
	}

	@Override
	public Idea save(final Idea persisted) {
		User author = persisted.getAuthor();
		//userRepository.
//		List<Tag> collect = persisted.getTags().parallelStream()
//				.map(TagResource::toTag)
//				.collect(Collectors.toList());
//		//Tag.getBuilder().withName(persisted.)
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
		final
		Idea target = findOne(ideaId);
		target.updateWith(source);
		return target;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> findIdeasByUserId(final long userId) {
		return ideaRepository.findByUserId(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> findIdeasByTagId(final long tagId) {
		final List<Idea> ideas = ideaRepository.findByTagId(tagId);
		ideas.forEach(idea -> Hibernate.initialize(idea.getRelatedTags()));
		return ideas;
	}

	@Override
	public Idea saveForUser(final long userId, final Idea idea) {
		Optional<User> userOptional = userRepository.findOne(userId);
		User user = userOptional.get();
		List<Idea> ideas = user.getIdeas();
		ideas.add(idea);
		idea.setAuthor(user);
		Idea savedIdea = ideaRepository.save(idea);
		return savedIdea;
	}
}
