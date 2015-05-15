package com.epam.idea.builder.resource;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.TagResource;
import com.epam.idea.rest.resource.UserResource;

public class TestIdeaResourceBuilder {

	private String title;
	private String description;
	private ZonedDateTime creationTime;
	private ZonedDateTime modificationTime;
	private int rating;
	private UserResource author;
	private List<TagResource> tags = new ArrayList<>();

	private TestIdeaResourceBuilder() {
	}

	public static TestIdeaResourceBuilder anIdeaResource() {
		return new TestIdeaResourceBuilder();
	}

	public TestIdeaResourceBuilder withTitle(final String title) {
		this.title = title;
		return this;
	}

	public TestIdeaResourceBuilder withDescription(final String description) {
		this.description = description;
		return this;
	}

	public TestIdeaResourceBuilder withCreationTime(final ZonedDateTime creationTime) {
		this.creationTime = creationTime;
		return this;
	}

	public TestIdeaResourceBuilder withModificationTime(final ZonedDateTime modificationTime) {
		this.modificationTime = modificationTime;
		return this;
	}

	public TestIdeaResourceBuilder withRating(final int rating) {
		this.rating = rating;
		return this;
	}

	public TestIdeaResourceBuilder withAuthor(final UserResource author) {
		this.author = author;
		return this;
	}

	public TestIdeaResourceBuilder withRelatedTags(final List<TagResource> relatedTags) {
		this.tags = relatedTags;
		return this;
	}

	public TestIdeaResourceBuilder but() {
		return anIdeaResource()
				.withTitle(title)
				.withDescription(description)
				.withCreationTime(creationTime)
				.withModificationTime(modificationTime)
				.withRating(rating)
				.withAuthor(author)
				.withRelatedTags(tags);
	}

	public IdeaResource build() {
		IdeaResource ideaResource = new IdeaResource();
		ideaResource.setTitle(title);
		ideaResource.setDescription(description);
		ideaResource.setCreationTime(creationTime);
		ideaResource.setModificationTime(modificationTime);
		ideaResource.setRating(rating);
//		if (author != null) {
//			ideaResource.setAuthor(author);
//		}
		ideaResource.setTags(tags);
		return ideaResource;
	}
}
