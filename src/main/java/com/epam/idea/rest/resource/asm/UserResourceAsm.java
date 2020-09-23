package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.User;
import com.epam.idea.rest.controller.UserController;
import com.epam.idea.rest.resource.UserResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;


import static java.util.Objects.requireNonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class UserResourceAsm extends RepresentationModelAssemblerSupport<User, UserResource> {

	public static final String IDEAS_REL = "ideas";
	public static final String COMMENTS_REL = "comments";

	public UserResourceAsm() {
		super(UserController.class, UserResource.class);
	}

	@Override
	public UserResource toModel(final User original) {
		requireNonNull(original, "User cannot be null");
		final UserResource userResource = new UserResource();
		userResource.setUserId(original.getId());
		userResource.setUsername(original.getUsername());
		userResource.setEmail(original.getEmail());
		userResource.setCreationTime(original.getCreationTime());
		userResource.setImageUrl(original.getImageUrl());
		userResource.add(linkTo(methodOn(UserController.class).getUser(original.getId())).withSelfRel());
		userResource.add(linkTo(methodOn(UserController.class).getUserIdeas(original.getId())).withRel(IDEAS_REL));
		userResource.add(linkTo(methodOn(UserController.class).getUserComments(original.getId())).withRel(COMMENTS_REL));
		return userResource;
	}
}
