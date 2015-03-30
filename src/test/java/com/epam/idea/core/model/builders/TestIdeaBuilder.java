package com.epam.idea.core.model.builders;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.User;
import org.springframework.test.util.ReflectionTestUtils;

public class TestIdeaBuilder {

	public static final String DEFAULT_TITLE = "Bar";
	public static final String DEFAULT_DESCRIPTION = "Lorem ipsum";
	public static final int DEFAULT_RATING = 5;
	public static final long DEFAULT_IDEA_ID = 1L;
	public static final ZonedDateTime DEFAULT_CREATION_TIME = ZonedDateTime.of(2014, 2, 12, 10, 0, 0, 0, ZoneOffset.UTC);
	public static final ZonedDateTime DEFAULT_MODIFICATION_TIME = ZonedDateTime.of(2014, 10, 5, 0, 0, 0, 0, ZoneOffset.UTC);

	private long id;
	private int rating;
	private String title;
	private String description;
	private ZonedDateTime creationTime;
	private ZonedDateTime modificationTime;
	private User author;
	private List<Tag> tags;
	private List<Comment> comments;

	public TestIdeaBuilder() {
		this.tags = new ArrayList<>(1);
		this.comments = new ArrayList<>(1);
	}

	public static TestIdeaBuilder anIdea() {
		return new TestIdeaBuilder()
				.withId(DEFAULT_IDEA_ID)
				.withTitle(DEFAULT_TITLE)
				.withDescription(DEFAULT_DESCRIPTION)
				.withRating(DEFAULT_RATING)
				.withCreationTime(DEFAULT_CREATION_TIME)
				.withModificationTime(DEFAULT_MODIFICATION_TIME);
	}

	public TestIdeaBuilder withId(final long id) {
		this.id = id;
		return this;
	}

	public TestIdeaBuilder withTitle(final String title) {
		this.title = title;
		return this;
	}

	public TestIdeaBuilder withDescription(final String description) {
		this.description = description;
		return this;
	}

	public TestIdeaBuilder withCreationTime(final ZonedDateTime creationTime) {
		this.creationTime = creationTime;
		return this;
	}

	public TestIdeaBuilder withModificationTime(final ZonedDateTime modificationTime) {
		this.modificationTime = modificationTime;
		return this;
	}

	public TestIdeaBuilder withRating(final int rating) {
		this.rating = rating;
		return this;
	}

	public TestIdeaBuilder withAuthor(final User author) {
		this.author = author;
		return this;
	}

	public TestIdeaBuilder withTags(final List<Tag> tags) {
		this.tags = tags;
		return this;
	}

	public TestIdeaBuilder addTag(final Tag tag) {
		this.tags.add(tag);
		return this;
	}

	public TestIdeaBuilder withComments(final List<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public TestIdeaBuilder addComment(final Comment comment) {
		this.comments.add(comment);
		return this;
	}

	public TestIdeaBuilder but() {
		return anIdea()
				.withId(id)
				.withTitle(title)
				.withDescription(description)
				.withCreationTime(creationTime)
				.withModificationTime(modificationTime)
				.withRating(rating)
				.withAuthor(author)
				.withTags(tags)
				.withComments(comments);
	}

	public Idea build() {
		final Idea idea = new Idea();
		ReflectionTestUtils.setField(idea, "id", id);
		ReflectionTestUtils.setField(idea, "creationTime", creationTime);
		ReflectionTestUtils.setField(idea, "modificationTime", modificationTime);
		idea.setTitle(title);
		idea.setRelatedTags(tags);
		idea.setAuthor(author);
		idea.setRating(rating);
		idea.setDescription(description);
		idea.setComments(comments);
		return idea;
	}
}
