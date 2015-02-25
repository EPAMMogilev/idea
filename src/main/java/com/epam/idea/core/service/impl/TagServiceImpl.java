package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.Tag;
import com.epam.idea.core.repository.TagRepository;
import com.epam.idea.core.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
	public Optional<Tag> findOne(Long id) {
		return tagRepository.findOne(id);
	}

	@Override
	public Tag save(Tag persisted) {
		return tagRepository.save(persisted);
	}
}
