package com.epam.idea.builder.model;

import java.util.ArrayList;
import java.util.List;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import org.springframework.test.util.ReflectionTestUtils;

public class TestTagBuilder {

	public static final String DEFAULT_NAME = "Foo";
	public static final long DEFAULT_ID = 1L;

	private long id;
	private String name;
	private List<Idea> ideas;

	public TestTagBuilder() {
		this.ideas = new ArrayList<>(1);
	}

	public static TestTagBuilder aTag() {
		return new TestTagBuilder()
				.withId(DEFAULT_ID)
				.withName(DEFAULT_NAME);
	}

	public TestTagBuilder withId(final long id) {
		this.id = id;
		return this;
	}

	public TestTagBuilder withName(final String name) {
		this.name = name;
		return this;
	}

	public TestTagBuilder withIdeas(final List<Idea> ideas) {
		this.ideas = ideas;
		return this;
	}

	public TestTagBuilder addIdea(final Idea idea) {
		this.ideas.add(idea);
		return this;
	}

	public TestTagBuilder but() {
		return aTag()
				.withId(id)
				.withName(name)
				.withIdeas(ideas);
	}

	public Tag build() {
		final Tag tag = new Tag();
		ReflectionTestUtils.setField(tag, "id", id);
		tag.setName(name);
		tag.setIdeasWithTag(ideas);
		return tag;
	}
}
