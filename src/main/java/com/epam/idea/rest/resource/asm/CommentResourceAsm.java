package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.Comment;
import com.epam.idea.rest.controller.CommentController;
import com.epam.idea.rest.resource.CommentResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static java.util.Objects.requireNonNull;

public class CommentResourceAsm extends ResourceAssemblerSupport<Comment, CommentResource> {

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
//		final User author = original.getAuthor();
//		if (author != null && isInitialized(author)) {
//			commentResource.setAuthor(new UserResourceAsm().toResource(author));
//		}
//		final Idea subject = original.getSubject();
//		if (subject != null && isInitialized(subject)) {
//			commentResource.setSubject(new IdeaResourceAsm().toResource(subject));
//		}
		//todo add links
		return commentResource;
	}
}
