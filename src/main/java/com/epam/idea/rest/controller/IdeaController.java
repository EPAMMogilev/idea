package com.epam.idea.rest.controller;

import java.util.List;
import javax.validation.Valid;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.asm.IdeaResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/v1/ideas")
public class IdeaController {

	@Autowired
	private IdeaService ideaService;

	@RequestMapping(value = "/{ideaId}", method = RequestMethod.GET)
	public HttpEntity<IdeaResource> show(@PathVariable final long ideaId) {
		final Idea foundIdea = this.ideaService.findOne(ideaId);
		return new ResponseEntity<>(new IdeaResourceAsm().toResource(foundIdea), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<IdeaResource>> showAll() {
		final List<Idea> foundIdeas = ideaService.findAll();
		return new ResponseEntity<>(new IdeaResourceAsm().toResources(foundIdeas), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<IdeaResource> create(@Valid @RequestBody final IdeaResource ideaRes) {
		final Idea createdIdea = this.ideaService.save(ideaRes.toIdea());
		return new ResponseEntity<>(new IdeaResourceAsm().toResource(createdIdea), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{ideaId}", method = RequestMethod.DELETE)
	public HttpEntity<IdeaResource> delete(@PathVariable final long ideaId) {
		this.ideaService.deleteById(ideaId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{ideaId}", method = RequestMethod.PUT)
	public HttpEntity<IdeaResource> update(@Valid @RequestBody final IdeaResource ideaResource, @PathVariable final long ideaId) {
		final Idea updatedIdea = this.ideaService.update(ideaId, ideaResource.toIdea());
		return new ResponseEntity<>(new IdeaResourceAsm().toResource(updatedIdea), HttpStatus.OK);
	}
}
