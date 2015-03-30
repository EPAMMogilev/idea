package com.epam.idea.rest.resource;


import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Size;

import com.epam.idea.core.model.Idea;
import com.epam.idea.rest.resource.support.JsonPropertyName;
import com.epam.idea.rest.resource.support.View;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.hateoas.ResourceSupport;

public class IdeaResource extends ResourceSupport {

	@JsonProperty(JsonPropertyName.ID)
	private long ideaId;

	@Size(min = Idea.MIN_LENGTH_TITLE, max = Idea.MAX_LENGTH_TITLE)
	private String title;

	@Size(max = Idea.MAX_LENGTH_DESCRIPTION)
	private String description;

	@JsonProperty(JsonPropertyName.CREATION_TIME)
	private ZonedDateTime creationTime;

	@JsonProperty(JsonPropertyName.MODIFICATION_TIME)
	private ZonedDateTime modificationTime;

	@JsonView({View.Basic.class})
	private int rating;

	private List<TagResource> tags;

	public IdeaResource() {
		this.tags = new ArrayList<>();
	}

	public long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(long ideaId) {
		this.ideaId = ideaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(ZonedDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public ZonedDateTime getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(ZonedDateTime modificationTime) {
		this.modificationTime = modificationTime;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public List<TagResource> getTags() {
		return tags;
	}

	public void setTags(List<TagResource> tags) {
		this.tags = tags;
	}

	public Idea toIdea() {
		final Idea idea = new Idea();
		idea.setTitle(title);
		idea.setDescription(description);
		idea.setRating(rating);
		idea.setRelatedTags(tags.parallelStream()
				.map(TagResource::toTag)
				.collect(Collectors.toList()));
		return idea;
	}
}
