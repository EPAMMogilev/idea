package com.epam.idea.rest.controller;

import java.util.List;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.TagService;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.TagResource;
import com.epam.idea.rest.resource.asm.IdeaResourceAsm;
import com.epam.idea.rest.resource.asm.TagResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/v1/tags")
public class TagController {

	@Autowired
	private TagService tagService;

	@Autowired
	private IdeaService ideaService;

	@RequestMapping(value = "/{tagId}", method = RequestMethod.GET)
	public HttpEntity<TagResource> getTag(@PathVariable final long tagId) {
		final Tag foundTag = this.tagService.findOne(tagId);
		final TagResource tagResource = new TagResourceAsm().toResource(foundTag);
		return new ResponseEntity<>(tagResource, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<TagResource>> getAllTags() {
		final List<Tag> foundTags = this.tagService.findAll();
		final List<TagResource> tagResources = new TagResourceAsm().toResources(foundTags);
		return new ResponseEntity<>(tagResources, HttpStatus.OK);
	}

	@RequestMapping(value = "/{tagId}/ideas", method = RequestMethod.GET)
	public HttpEntity<List<IdeaResource>> getAllFoundIdeasForTag(@PathVariable final long tagId) {
		final List<Idea> foundIdeas = this.ideaService.findIdeasByTagId(tagId);
		final List<IdeaResource> ideaResources = new IdeaResourceAsm().toResources(foundIdeas);
		return new ResponseEntity<>(ideaResources, HttpStatus.OK);
	}
}
