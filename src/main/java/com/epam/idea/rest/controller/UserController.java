package com.epam.idea.rest.controller;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.UserService;
import com.epam.idea.rest.resource.CommentResource;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.UserResource;
import com.epam.idea.rest.resource.asm.CommentResourceAsm;
import com.epam.idea.rest.resource.asm.IdeaResourceAsm;
import com.epam.idea.rest.resource.asm.UserResourceAsm;
import com.epam.idea.rest.resource.support.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private IdeaService ideaService;

	@Autowired
	private CommentService commentService;

	@JsonView(View.Basic.class)
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<UserResource> createUser(@Valid @RequestBody final UserResource userRes) {
		User savedUser = userService.save(userRes.toUser());
		return new ResponseEntity<>(new UserResourceAsm().toResource(savedUser), HttpStatus.CREATED);
	}

	@JsonView(View.Basic.class)
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<UserResource>> showAllUsers() {
		List<User> userList = userService.findAll();
		return new ResponseEntity<>(new UserResourceAsm().toResources(userList), HttpStatus.OK);
	}

	@JsonView(View.Basic.class)
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public HttpEntity<UserResource> getUser(@PathVariable final long userId) {
		User user = userService.findOne(userId);
		UserResource userResource = new UserResourceAsm().toResource(user);
		return new ResponseEntity<>(userResource, HttpStatus.OK);
	}

	@JsonView(View.Basic.class)
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public HttpEntity<UserResource> updateUser(@Valid @RequestBody final UserResource userRes, @PathVariable final long userId) {
		User updated = userService.update(userId, userRes.toUser());
		return new ResponseEntity<>(new UserResourceAsm().toResource(updated), HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public HttpEntity<UserResource> deleteUser(@PathVariable final long userId) {
		userService.deleteById(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}/ideas", method = RequestMethod.GET)
	public HttpEntity<List<IdeaResource>> getUserIdeas(@PathVariable final long userId) {
		List<Idea> userIdeas = ideaService.findIdeasByUserId(userId);
		return new ResponseEntity<>(new IdeaResourceAsm().toResources(userIdeas), HttpStatus.OK);
	}

	@JsonView(View.Basic.class)
	@RequestMapping(value = "/{userId}/ideas", method = RequestMethod.POST)
	public HttpEntity<IdeaResource> createIdeaOfUser(@PathVariable final long userId, @Valid @RequestBody final IdeaResource ideaRes) {
		Idea savedIdea = ideaService.saveForUser(userId, ideaRes.toIdea());
		return new ResponseEntity<>(new IdeaResourceAsm().toResource(savedIdea), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{userId}/comments", method = RequestMethod.GET)
	public HttpEntity<List<CommentResource>> getUserComments(@PathVariable final long userId) {
		List<Comment> comments = commentService.findCommentsByUserId(userId);
		return new ResponseEntity<>(new CommentResourceAsm().toResources(comments), HttpStatus.OK);
	}
}
