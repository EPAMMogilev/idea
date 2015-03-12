package com.epam.idea.rest.resource.builders;

import com.epam.idea.rest.resource.TagResource;

public class TestTagResourceBuilder {

	private String name;

	private TestTagResourceBuilder() {
		//empty
	}

	public static TestTagResourceBuilder aTagResource() {
		return new TestTagResourceBuilder();
	}

	public TestTagResourceBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public TestTagResourceBuilder but() {
		return aTagResource().withName(name);
	}

	public TagResource build() {
		TagResource tagResource = new TagResource();
		tagResource.setName(name);
		return tagResource;
	}
}
