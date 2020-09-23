package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.Tag;
import com.epam.idea.rest.controller.TagController;
import com.epam.idea.rest.resource.TagResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;


import static java.util.Objects.requireNonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TagResourceAsm extends RepresentationModelAssemblerSupport<Tag, TagResource> {

	public static final String IDEAS_REL = "ideas";

	public TagResourceAsm() {
		super(TagController.class, TagResource.class);
	}

	@Override
	public TagResource toModel(final Tag original) {
		requireNonNull(original, "Tag cannot be null");
		final TagResource tagResource = new TagResource();
		tagResource.setTagId(original.getId());
		tagResource.setName(original.getName());
		tagResource.add(linkTo(methodOn(TagController.class).getTag(original.getId())).withSelfRel());
		tagResource.add(linkTo(methodOn(TagController.class).getAllFoundIdeasForTag(original.getId())).withRel(IDEAS_REL));
		return tagResource;
	}
}
