package com.epam.idea.assertion;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.User;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Iterables;
import org.assertj.core.util.Objects;

import java.time.ZonedDateTime;

/**
 * {@link User} specific assertions.
 */
public class UserAssert extends AbstractAssert<UserAssert, User> {

	/**
	 * Creates a new {@link UserAssert} to make assertions on actual User.
	 *
	 * @param actual the User we want to make assertions on.
	 */
	public UserAssert(User actual) {
		super(actual, UserAssert.class);
	}

	/**
	 * Verifies that the actual User's email is equal to the given one.
	 *
	 * @param email the given email to compare the actual User's email to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual User's email is not equal to the given one.
	 */
	public UserAssert hasEmail(String email) {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected email of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		String actualEmail = actual.getEmail();
		if (!Objects.areEqual(actualEmail, email)) {
			failWithMessage(assertjErrorMessage, actual, email, actualEmail);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual User's id is equal to the given one.
	 *
	 * @param id the given id to compare the actual User's id to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual User's id is not equal to the given one.
	 */
	public UserAssert hasId(long id) {
		// check that actual User we want to make assertions on is not null.
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
	 * Verifies that the actual User's password is equal to the given one.
	 *
	 * @param password the given password to compare the actual User's password to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual User's password is not equal to the given one.
	 */
	public UserAssert hasPassword(String password) {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected password of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		String actualPassword = actual.getPassword();
		if (!Objects.areEqual(actualPassword, password)) {
			failWithMessage(assertjErrorMessage, actual, password, actualPassword);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual User's username is equal to the given one.
	 *
	 * @param username the given username to compare the actual User's username to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual User's username is not equal to the given one.
	 */
	public UserAssert hasUsername(String username) {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected username of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		String actualUsername = actual.getUsername();
		if (!Objects.areEqual(actualUsername, username)) {
			failWithMessage(assertjErrorMessage, actual, username, actualUsername);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual User's roles contains the given Role elements.
	 *
	 * @param roles the given elements that should be contained in actual User's roles.
	 * @return this assertion object.
	 * @throws AssertionError if the actual User's roles does not contain all given Role elements.
	 */
	public UserAssert hasRoles(Role... roles) {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// check that given Role varargs is not null.
		if (roles == null) throw new AssertionError("Expecting roles parameter not to be null.");

		// check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
		Iterables.instance().assertContains(info, actual.getRoles(), roles);

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual User has no roles.
	 *
	 * @return this assertion object.
	 * @throws AssertionError if the actual User's roles is not empty.
	 */
	public UserAssert hasNoRoles() {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// we override the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected :\n  <%s>\nnot to have roles but had :\n  <%s>";

		// check
		if (actual.getRoles().iterator().hasNext()) {
			failWithMessage(assertjErrorMessage, actual, actual.getRoles());
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual User's ideas contains the given Idea elements.
	 *
	 * @param ideas the given elements that should be contained in actual User's ideas.
	 * @return this assertion object.
	 * @throws AssertionError if the actual User's ideas does not contain all given Idea elements.
	 */
	public UserAssert hasIdeas(Idea... ideas) {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// check that given Idea varargs is not null.
		if (ideas == null) throw new AssertionError("Expecting ideas parameter not to be null.");

		// check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
		Iterables.instance().assertContains(info, actual.getIdeas(), ideas);

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual User has no ideas.
	 *
	 * @return this assertion object.
	 * @throws AssertionError if the actual User's ideas is not empty.
	 */
	public UserAssert hasNoIdeas() {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// we override the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected :\n  <%s>\nnot to have ideas but had :\n  <%s>";

		// check
		if (actual.getIdeas().iterator().hasNext()) {
			failWithMessage(assertjErrorMessage, actual, actual.getIdeas());
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual User's creationTime is equal to the given one.
	 *
	 * @param creationTime the given creationTime to compare the actual User's creationTime to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual User's creationTime is not equal to the given one.
	 */
	public UserAssert hasCreationTime(ZonedDateTime creationTime) {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected creationTime of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		ZonedDateTime actualCreationTime = actual.getCreationTime();
		if (!Objects.areEqual(actualCreationTime, creationTime)) {
			failWithMessage(assertjErrorMessage, actual, creationTime, actualCreationTime);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual User's comments contains the given Comment elements.
	 *
	 * @param comments the given elements that should be contained in actual User's comments.
	 * @return this assertion object.
	 * @throws AssertionError if the actual User's comments does not contain all given Comment elements.
	 */
	public UserAssert hasComments(Comment... comments) {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// check that given Comment varargs is not null.
		if (comments == null) throw new AssertionError("Expecting comments parameter not to be null.");

		// check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
		Iterables.instance().assertContains(info, actual.getComments(), comments);

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual User has no comments.
	 *
	 * @return this assertion object.
	 * @throws AssertionError if the actual User's comments is not empty.
	 */
	public UserAssert hasNoComments() {
		// check that actual User we want to make assertions on is not null.
		isNotNull();

		// we override the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected :\n  <%s>\nnot to have comments but had :\n  <%s>";

		// check
		if (actual.getComments().iterator().hasNext()) {
			failWithMessage(assertjErrorMessage, actual, actual.getComments());
		}

		// return the current assertion for method chaining
		return this;
	}
}
