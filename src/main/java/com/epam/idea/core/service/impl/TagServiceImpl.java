package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.Tag;
import com.epam.idea.core.repository.TagRepository;
import com.epam.idea.core.service.TagService;
import com.epam.idea.core.service.exception.TagDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;

	@Override
	public void delete(Tag deleted) {
		tagRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tag> findAll() {
		return tagRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Tag findOne(final Long id) {
		return tagRepository.findOne(id).orElseThrow(TagDoesNotExistException::new);
	}

	@Override
	public Tag save(final Tag persisted) {
		return tagRepository.save(persisted);
	}
}
