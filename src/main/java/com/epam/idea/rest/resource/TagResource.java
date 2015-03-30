package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Tag;
import com.epam.idea.rest.resource.support.JsonPropertyName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class TagResource extends ResourceSupport {

	@JsonProperty(JsonPropertyName.ID)
	private long tagId;

	private String name;

	public TagResource() {
		//empty
	}

	public TagResource(String name) {
		this.name = name;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Tag toTag() {
		final Tag tag = new Tag();
		tag.setName(name);
		return tag;
	}
}
