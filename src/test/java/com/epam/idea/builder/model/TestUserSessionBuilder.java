package com.epam.idea.builder.model;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.epam.idea.core.model.Authority;
import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.User;
import com.epam.idea.core.model.UserSession;
import com.google.common.collect.Lists;
import org.springframework.test.util.ReflectionTestUtils;


public class TestUserSessionBuilder {

	public static final String DEFAULT_USER_SESSION_ID = "1";
	public static final ZonedDateTime DEFAULT_CREATION_TIME = ZonedDateTime.of(2015, 1, 18, 0, 0, 0, 0, ZoneOffset.UTC);

	private String sessionId;
	private User user;
	private ZonedDateTime creationTime;

	public TestUserSessionBuilder() {
	}

	public static TestUserSessionBuilder aUserSession() {
		return new TestUserSessionBuilder()
				.withId(DEFAULT_USER_SESSION_ID)
				.withCreationTime(DEFAULT_CREATION_TIME);
	}



	public TestUserSessionBuilder withId(final String sessionId) {
		this.sessionId = sessionId;
		return this;
	}


	public TestUserSessionBuilder withCreationTime(final ZonedDateTime creationTime) {
		this.creationTime = creationTime;
		return this;
	}

	public TestUserSessionBuilder withAuthor(final User user) {
		this.user = user;
		return this;
	}

	public TestUserSessionBuilder but() {
		return aUserSession()
				.withId(sessionId)
				.withAuthor(user)
				.withCreationTime(creationTime);
	}

	public UserSession build() {
		final UserSession userSession = new UserSession();
		ReflectionTestUtils.setField(userSession, "sessionId", sessionId);
		ReflectionTestUtils.setField(userSession, "creationTime", creationTime);
		userSession.setUser(user);
		return userSession;
	}
}
