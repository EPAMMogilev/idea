package com.epam.idea.rest.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.rest.resource.CommentResource;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.asm.CommentResourceAsm;
import com.epam.idea.rest.resource.asm.IdeaResourceAsm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/ideas")
public class IdeaController {

    @Autowired
    private IdeaService ideaService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/{ideaId}", method = RequestMethod.GET)
    public HttpEntity<IdeaResource> show(@PathVariable final long ideaId) {
        final Idea foundIdea = this.ideaService.findOne(ideaId);
        return new ResponseEntity<>(new IdeaResourceAsm().toResource(foundIdea), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<IdeaResource>> showAll(@PageableDefault(page = 0, size = 500) final Pageable pageable,
            @RequestParam(required = false) final Long userId, @RequestParam(required = false) final String query,
            @RequestParam(required = false) final Long tagId) {
        final List<Idea> foundIdeas = ideaService.findAllByUserIdQueryAndTagId(pageable, userId, query, tagId);
        return new ResponseEntity<>(new IdeaResourceAsm().toResources(foundIdeas), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<IdeaResource> create(@Valid @RequestBody final IdeaResource ideaRes) {
        final Idea createdIdea = this.ideaService.create(ideaRes.toIdea());
        return new ResponseEntity<>(new IdeaResourceAsm().toResource(createdIdea), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{ideaId}", method = RequestMethod.DELETE)
    public HttpEntity<IdeaResource> delete(@PathVariable final long ideaId) {
        this.ideaService.setDeletedStateById(ideaId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{ideaId}", method = RequestMethod.PUT)
    public HttpEntity<IdeaResource> update(@Valid @RequestBody final IdeaResource ideaResource,
            @PathVariable final long ideaId) {
        final Idea updatedIdea = this.ideaService.update(ideaId, ideaResource.toIdea());
        return new ResponseEntity<>(new IdeaResourceAsm().toResource(updatedIdea), HttpStatus.OK);
    }

    @RequestMapping(value = "/{ideaId}/like", method = RequestMethod.POST)
    public HttpEntity<IdeaResource> changeLike(@PathVariable final long ideaId) {
        final Idea updatedIdea = this.ideaService.changeIdeaLike(ideaId);
        return new ResponseEntity<>(new IdeaResourceAsm().toResource(updatedIdea), HttpStatus.OK);
    }

    @RequestMapping(value = "/{ideaId}/comments", method = RequestMethod.GET)
    public HttpEntity<List<CommentResource>> getIdeaComments(
            @PageableDefault(page = 0, size = 500) final Pageable pageable, @PathVariable final long ideaId) {
        final List<Comment> comments = this.commentService.findCommentsByIdeaId(pageable, ideaId);
        return new ResponseEntity<>(new CommentResourceAsm().toResources(comments), HttpStatus.OK);
    }

    @RequestMapping(value = "/{ideaId}/comments", method = RequestMethod.POST)
    public HttpEntity<CommentResource> create(@Valid @RequestBody final CommentResource commentRes,
            @PathVariable final long ideaId) {
        final Comment createdComment = this.commentService.create(commentRes.toComment(), ideaId);
        return new ResponseEntity<>(new CommentResourceAsm().toResource(createdComment), HttpStatus.CREATED);
    }

}