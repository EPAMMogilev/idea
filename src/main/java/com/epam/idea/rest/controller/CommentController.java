package com.epam.idea.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.rest.resource.CommentResource;
import com.epam.idea.rest.resource.asm.CommentResourceAsm;

@Controller
@RequestMapping("/api/v1/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<CommentResource>> showAll(@RequestParam(required = true) Long ideaId) {
		final List<Comment> comments = commentService.findCommentsByIdeaId(ideaId);
		return new ResponseEntity<>(new CommentResourceAsm().toResources(comments), HttpStatus.OK);
	}
}
