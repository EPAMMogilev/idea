package com.epam.idea.rest.resource.asm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.idea.core.model.Idea;
import com.epam.idea.rest.controller.IdeaController;
import com.epam.idea.rest.controller.UserController;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.TagResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static org.hibernate.Hibernate.isInitialized;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class IdeaResourceAsm extends ResourceAssemblerSupport<Idea, IdeaResource> {

	public static final String REL_AUTHOR = "author";

	public IdeaResourceAsm() {
		super(IdeaController.class, IdeaResource.class);
	}

	@Override
	public IdeaResource toResource(final Idea original) {
		requireNonNull(original, "Idea cannot be null");
		final IdeaResource ideaResource = new IdeaResource();
		ideaResource.setIdeaId(original.getId());
		ideaResource.setTitle(original.getTitle());
		ideaResource.setDescription(original.getDescription());
		ideaResource.setCreationTime(original.getCreationTime());
		ideaResource.setModificationTime(original.getModificationTime());
		ideaResource.setRating(original.getRating());
		if (isInitialized(original.getRelatedTags())) {
			List<TagResource> tagResources = original.getRelatedTags().parallelStream()
					.map(tag -> new TagResourceAsm().toResource(tag))
					.collect(Collectors.toList());
			ideaResource.setTags(tagResources);
		} else {
			ideaResource.setTags(emptyList());
		}
		ideaResource.add(linkTo(methodOn(IdeaController.class).show(original.getId())).withSelfRel());
		Optional.ofNullable(original.getAuthor()).ifPresent(author ->
				ideaResource.add(linkTo(methodOn(UserController.class).getUser(author.getId())).withRel(REL_AUTHOR)));
		//todo add link to comments
		return ideaResource;
	}
}
