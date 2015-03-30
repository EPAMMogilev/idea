package com.epam.idea.rest.resource.builders;

import java.time.ZonedDateTime;

import com.epam.idea.rest.resource.UserResource;

public class TestUserResourceBuilder {
	private String username;
	private String email;
	private String password;
	private ZonedDateTime creationTime;

	private TestUserResourceBuilder() {
	}

	public static TestUserResourceBuilder aUserResource() {
		return new TestUserResourceBuilder();
	}

	public TestUserResourceBuilder withUsername(final String username) {
		this.username = username;
		return this;
	}

	public TestUserResourceBuilder withEmail(final String email) {
		this.email = email;
		return this;
	}

	public TestUserResourceBuilder withPassword(final String password) {
		this.password = password;
		return this;
	}

	public TestUserResourceBuilder withCreationTime(final ZonedDateTime creationTime) {
		this.creationTime = creationTime;
		return this;
	}

	public TestUserResourceBuilder but() {
		return aUserResource()
				.withUsername(username)
				.withEmail(email)
				.withPassword(password)
				.withCreationTime(creationTime);
	}

	public UserResource build() {
		UserResource userResource = new UserResource();
		userResource.setUsername(username);
		userResource.setEmail(email);
		userResource.setPassword(password);
		userResource.setCreationTime(creationTime);
		return userResource;
	}
}
