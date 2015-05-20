package com.epam.idea.assertion;

import com.epam.idea.core.model.Authority;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.User;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Iterables;
import org.assertj.core.util.Objects;

/**
 * {@link Role} specific assertions.
 */
public class RoleAssert extends AbstractAssert<RoleAssert, Role> {

	/**
	 * Creates a new {@link RoleAssert} to make assertions on actual Role.
	 * @param actual the Role we want to make assertions on.
	 */
	public RoleAssert(Role actual) {
		super(actual, RoleAssert.class);
	}

	/**
	 * Verifies that the actual Role's id is equal to the given one.
	 * @param id the given id to compare the actual Role's id to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Role's id is not equal to the given one.
	 */
	public RoleAssert hasId(long id) {
		// check that actual Role we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected id of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// check
		long actualId = actual.getId();
		if (actualId != id) {
			failWithMessage(assertjErrorMessage, actual, id, actualId);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Role's name is equal to the given one.
	 * @param name the given name to compare the actual Role's name to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Role's name is not equal to the given one.
	 */
	public RoleAssert hasName(Authority name) {
		// check that actual Role we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected name of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		Authority actualName = actual.getName();
		if (!Objects.areEqual(actualName, name)) {
			failWithMessage(assertjErrorMessage, actual, name, actualName);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Role's usersWithRole contains the given User elements.
	 * @param usersWithRole the given elements that should be contained in actual Role's usersWithRole.
	 * @return this assertion object.
	 * @throws AssertionError if the actual Role's usersWithRole does not contain all given User elements.
	 */
	public RoleAssert hasUsersWithRole(User... usersWithRole) {
		// check that actual Role we want to make assertions on is not null.
		isNotNull();

		// check that given User varargs is not null.
		if (usersWithRole == null) throw new AssertionError("Expecting usersWithRole parameter not to be null.");

		// check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
		Iterables.instance().assertContains(info, actual.getUsersWithRole(), usersWithRole);

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Role has no usersWithRole.
	 * @return this assertion object.
	 * @throws AssertionError if the actual Role's usersWithRole is not empty.
	 */
	public RoleAssert hasNoUsersWithRole() {
		// check that actual Role we want to make assertions on is not null.
		isNotNull();

		// we override the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected :\n  <%s>\nnot to have usersWithRole but had :\n  <%s>";

		// check
		if (actual.getUsersWithRole().iterator().hasNext()) {
			failWithMessage(assertjErrorMessage, actual, actual.getUsersWithRole());
		}

		// return the current assertion for method chaining
		return this;
	}
}
