package com.epam.idea.rest.resource.asm;

import java.util.Optional;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.rest.controller.CommentController;
import com.epam.idea.rest.controller.IdeaController;
import com.epam.idea.rest.controller.UserController;
import com.epam.idea.rest.resource.CommentResource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static java.util.Objects.requireNonNull;
import static org.hibernate.Hibernate.isInitialized;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CommentResourceAsm extends ResourceAssemblerSupport<Comment, CommentResource> {

	public static final String REL_AUTHOR = "author";
	public static final String REL_SUBJECT = "subject";

	public CommentResourceAsm() {
		super(CommentController.class, CommentResource.class);
	}

	@Override
	public CommentResource toResource(final Comment original) {
		requireNonNull(original, "Comment cannot be null");
		final CommentResource commentResource = new CommentResource();
		commentResource.setCommentId(original.getId());
		commentResource.setBody(original.getBody());
		commentResource.setCreationTime(original.getCreationTime());
		commentResource.setModificationTime(original.getModificationTime());
		commentResource.setRating(original.getRating());
		final User author = original.getAuthor();
		if (author != null && isInitialized(author)) {
			commentResource.setAuthor(new UserResourceAsm().toResource(author));
		}
		final Idea subject = original.getSubject();
		if (subject != null && isInitialized(subject)) {
			commentResource.setSubject(new IdeaResourceAsm().toResource(subject));
		}
		commentResource.add(linkTo(methodOn(CommentController.class).show(original.getId())).withSelfRel());
		Optional.ofNullable(original.getAuthor()).ifPresent(commentAuthor ->
				commentResource.add(linkTo(methodOn(UserController.class).getUser(commentAuthor.getId())).withRel(REL_AUTHOR)));
		Optional.ofNullable(original.getSubject()).ifPresent(idea ->
				commentResource.add(linkTo(methodOn(IdeaController.class).show(idea.getId())).withRel(REL_SUBJECT)));
		return commentResource;
	}
}
