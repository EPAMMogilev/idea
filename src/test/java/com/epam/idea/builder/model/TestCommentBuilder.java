package com.epam.idea.builder.model;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import org.springframework.test.util.ReflectionTestUtils;

public class TestCommentBuilder {

	public static final String DEFAULT_BODY = "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet";
	public static final int DEFAULT_RATING = 3;
	public static final long DEFAULT_ID = 1L;
	public static final ZonedDateTime DEFAULT_CREATION_TIME = ZonedDateTime.of(2014, 5, 5, 0, 0, 0, 0, ZoneOffset.UTC);
	public static final ZonedDateTime DEFAULT_MODIFICATION_TIME = ZonedDateTime.of(2014, 7, 8, 0, 0, 0, 0, ZoneOffset.UTC);

	private long id;
	private String body;
	private int rating;
	private ZonedDateTime creationTime;
	private ZonedDateTime modificationTime;
	private User author;
	private Idea subject;

	private TestCommentBuilder() {
		//empty
	}

	public static TestCommentBuilder aComment() {
		return new TestCommentBuilder()
				.withId(DEFAULT_ID)
				.withModificationTime(DEFAULT_MODIFICATION_TIME)
				.withCreationTime(DEFAULT_CREATION_TIME)
				.withBody(DEFAULT_BODY)
				.withRating(DEFAULT_RATING);
	}

	public TestCommentBuilder withId(final long id) {
		this.id = id;
		return this;
	}

	public TestCommentBuilder withBody(final String body) {
		this.body = body;
		return this;
	}

	public TestCommentBuilder withRating(final int rating) {
		this.rating = rating;
		return this;
	}

	public TestCommentBuilder withCreationTime(final ZonedDateTime creationTime) {
		this.creationTime = creationTime;
		return this;
	}

	public TestCommentBuilder withModificationTime(final ZonedDateTime modificationTime) {
		this.modificationTime = modificationTime;
		return this;
	}

	public TestCommentBuilder withAuthor(final User author) {
		this.author = author;
		return this;
	}

	public TestCommentBuilder withSubject(final Idea subject) {
		this.subject = subject;
		return this;
	}

	public TestCommentBuilder but() {
		return aComment()
				.withBody(body)
				.withRating(rating)
				.withAuthor(author)
				.withSubject(subject);
	}

	public Comment build() {
		final Comment comment = new Comment();
		ReflectionTestUtils.setField(comment, "id", id);
		ReflectionTestUtils.setField(comment, "creationTime", creationTime);
		ReflectionTestUtils.setField(comment, "modificationTime", modificationTime);
		comment.setBody(body);
		comment.setSubject(subject);
		comment.setAuthor(author);
		comment.setRating(rating);
		return comment;
	}
}
