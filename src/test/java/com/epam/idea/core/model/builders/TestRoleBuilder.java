package com.epam.idea.core.model.builders;

import java.util.ArrayList;
import java.util.List;

import com.epam.idea.core.model.Authority;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.User;
import org.springframework.test.util.ReflectionTestUtils;


public class TestRoleBuilder {

	public static final long DEFAULT_ID = 1L;

	private long id;
	private Authority name;
	private List<User> users;

	private TestRoleBuilder() {
		this.users = new ArrayList<>(1);
	}

	public static TestRoleBuilder aRole() {
		return new TestRoleBuilder()
				.withId(DEFAULT_ID)
				.withName(Authority.USER);
	}

	public static TestRoleBuilder anAdminRole() {
		return aRole()
				.withName(Authority.ADMIN);
	}

	public TestRoleBuilder withId(final long id) {
		this.id = id;
		return this;
	}

	public TestRoleBuilder withName(final Authority name) {
		this.name = name;
		return this;
	}

	public TestRoleBuilder withUsers(final List<User> users) {
		this.users = users;
		return this;
	}

	public TestRoleBuilder addUser(final User user) {
		this.users.add(user);
		return this;
	}

	public TestRoleBuilder but() {
		return aRole()
				.withId(id)
				.withName(name)
				.withUsers(users);
	}

	public Role build() {
		final Role role = new Role();
		ReflectionTestUtils.setField(role, "id", id);
		role.setName(name);
		role.setUsersWithRole(users);
		return role;
	}
}
