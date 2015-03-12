package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.Tag;
import com.epam.idea.rest.controller.TagController;
import com.epam.idea.rest.resource.TagResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static java.util.Objects.requireNonNull;

public class TagResourceAsm extends ResourceAssemblerSupport<Tag, TagResource> {

	public TagResourceAsm() {
		super(TagController.class, TagResource.class);
	}

	@Override
	public TagResource toResource(final Tag original) {
		requireNonNull(original, "Tag cannot be null");
		final TagResource tagResource = new TagResource();
		tagResource.setTagId(original.getId());
		tagResource.setName(original.getName());
		//todo add self link
		return tagResource;
	}
}
