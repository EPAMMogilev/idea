package com.epam.idea.rest.resource.asm;

import java.util.Optional;

import com.epam.idea.core.model.UserSession;
import com.epam.idea.rest.controller.UserController;
import com.epam.idea.rest.resource.UserSessionResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static java.util.Objects.requireNonNull;
import static org.hibernate.Hibernate.isInitialized;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Ihar_Niakhlebau on 26-May-15.
 */
public class UserSessionResourceAsm extends ResourceAssemblerSupport<UserSession, UserSessionResource> {

	public static final String REL_USER = "user";

	public UserSessionResourceAsm() {
		super(UserController.class, UserSessionResource.class);
	}

	@Override
	public UserSessionResource toResource(UserSession original) {
		requireNonNull(original, "Email or password is incorrect");
		final UserSessionResource userSessionResource = new UserSessionResource();
		userSessionResource.setSessionId(original.getSessionId());
		userSessionResource.setCreationTime(original.getCreationTime());
		if (original.getUser() != null && isInitialized(original.getUser())) {
			userSessionResource.setUserResource(new UserResourceAsm().toResource(original.getUser()));
		}
		Optional.ofNullable(original.getUser()).ifPresent(user ->
				userSessionResource.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withRel(REL_USER)));
		return userSessionResource;
	}
}
