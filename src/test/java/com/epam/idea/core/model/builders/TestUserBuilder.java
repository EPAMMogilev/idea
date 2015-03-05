package com.epam.idea.core.model.builders;

import com.epam.idea.core.model.Authority;
import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class TestUserBuilder {

	public static final String DEFAULT_USERNAME = "Super user";
	public static final String DEFAULT_EMAIL = "test@email.com";
	public static final String DEFAULT_PASSWORD = "password";
	public static final long DEFAULT_USER_ID = 1L;
	public static final ZonedDateTime DEFAULT_CREATION_TIME = ZonedDateTime.of(2015, 1, 12, 0, 0, 0, 0, ZoneOffset.UTC);

	private User.Builder userBuilder;
	private String username;
	private String email;
	private String password;
	private List<Idea> ideas = new ArrayList<>(1);
	private List<Comment> comments = new ArrayList<>(1);
	private List<Role> roles = new ArrayList<>(1);

	public TestUserBuilder() {
		this.userBuilder = User.getBuilder();
	}

	private static TestUserBuilder aDefaultUser() {
		return new TestUserBuilder()
				.withId(DEFAULT_USER_ID)
				.withUsername(DEFAULT_USERNAME)
				.withEmail(DEFAULT_EMAIL)
				.withPassword(DEFAULT_PASSWORD)
				.withCreationTime(DEFAULT_CREATION_TIME);
	}

	public static TestUserBuilder aUser() {
		return aDefaultUser()
				.inUserRole();
	}

	public static TestUserBuilder anAdmin() {
		return aDefaultUser()
				.inAdminRole();
	}

	public TestUserBuilder withId(final long id) {
		ReflectionTestUtils.setField(userBuilder, "id", id);
		return this;
	}

	public TestUserBuilder withUsername(final String username) {
		this.username = username;
		return this;
	}

	public TestUserBuilder withEmail(final String email) {
		this.email = email;
		return this;
	}

	public TestUserBuilder withPassword(final String password) {
		this.password = password;
		return this;
	}

	public TestUserBuilder withCreationTime(final ZonedDateTime creationTime) {
		ReflectionTestUtils.setField(userBuilder, "creationTime", creationTime);
		return this;
	}

	public TestUserBuilder withIdeas(final List<Idea> ideas) {
		this.ideas = ideas;
		return this;
	}

	public TestUserBuilder addIdea(final Idea idea) {
		this.ideas.add(idea);
		return this;
	}

	public TestUserBuilder withComments(final List<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public TestUserBuilder addComment(final Comment comment) {
		this.comments.add(comment);
		return this;
	}

	public TestUserBuilder inRoles(final List<Role> roles) {
		this.roles = roles;
		return this;
	}

	public TestUserBuilder addRole(final Role role) {
		this.roles.add(role);
		return this;
	}

	public TestUserBuilder inAdminRole() {
		List<Role> roles = new ArrayList<>(1);
		roles.add(Role.getBuilder().withName(Authority.ADMIN).build());
		this.roles = roles;
		return this;
	}

	public TestUserBuilder inUserRole() {
		List<Role> roles = new ArrayList<>(1);
		roles.add(Role.getBuilder().withName(Authority.USER).build());
		this.roles = roles;
		return this;
	}

	public TestUserBuilder but() {
		return aDefaultUser()
				.withUsername(username)
				.withEmail(email)
				.withPassword(password)
				.withIdeas(ideas)
				.withComments(comments)
				.inRoles(roles);
	}

	public User build() {
		return userBuilder
				.withUsername(username)
				.withEmail(email)
				.withPassword(password)
				.withComments(comments)
				.withIdeas(ideas)
				.withRoles(roles)
				.build();
	}
}
