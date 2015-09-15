package com.epam.idea.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.rest.resource.CommentResource;
import com.epam.idea.rest.resource.asm.CommentResourceAsm;

@Controller
@RequestMapping("/api/v1/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/{commentId}", method = RequestMethod.GET)
	public HttpEntity<CommentResource> show(@PathVariable final long commentId) {
		final Comment foundComment = this.commentService.findOne(commentId);
		return new ResponseEntity<>(new CommentResourceAsm().toResource(foundComment), HttpStatus.OK);
	}

	@RequestMapping(value = "/{commentId}/like", method = RequestMethod.POST)
	public HttpEntity<CommentResource> changeLike(@PathVariable final long commentId) {
		final Comment updatedComment = this.commentService.changeCommentLike(commentId);
		return new ResponseEntity<>(new CommentResourceAsm().toResource(updatedComment), HttpStatus.OK);
	}
}
