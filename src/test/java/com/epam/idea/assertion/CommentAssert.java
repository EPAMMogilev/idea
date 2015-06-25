package com.epam.idea.assertion;

import java.time.ZonedDateTime;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.util.Objects;

/**
 * {@link com.epam.idea.core.model.Comment} specific assertions.
 */
public class CommentAssert extends AbstractAssert<CommentAssert, Comment> {

	/**
	 * Creates a new {@link CommentAssert} to make assertions on actual Comment.
	 * @param actual the Comment we want to make assertions on.
	 */
	public CommentAssert(Comment actual) {
		super(actual, CommentAssert.class);
	}

	/**
	 * Verifies that the actual Comment's author is equal to the given one.
	 * @param author the given author to compare the actual Comment's author to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Comment's author is not equal to the given one.
	 */
	public CommentAssert hasAuthor(User author) {
		// check that actual Comment we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected author of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		User actualAuthor = actual.getAuthor();
		if (!Objects.areEqual(actualAuthor, author)) {
			failWithMessage(assertjErrorMessage, actual, author, actualAuthor);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Comment's body is equal to the given one.
	 * @param body the given body to compare the actual Comment's body to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Comment's body is not equal to the given one.
	 */
	public CommentAssert hasBody(String body) {
		// check that actual Comment we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected body of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		String actualBody = actual.getBody();
		if (!Objects.areEqual(actualBody, body)) {
			failWithMessage(assertjErrorMessage, actual, body, actualBody);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Comment's creationTime is equal to the given one.
	 * @param creationTime the given creationTime to compare the actual Comment's creationTime to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Comment's creationTime is not equal to the given one.
	 */
	public CommentAssert hasCreationTime(ZonedDateTime creationTime) {
		// check that actual Comment we want to make assertions on is not null.
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
	 * Verifies that the actual Comment's id is equal to the given one.
	 * @param id the given id to compare the actual Comment's id to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Comment's id is not equal to the given one.
	 */
	public CommentAssert hasId(long id) {
		// check that actual Comment we want to make assertions on is not null.
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
	 * Verifies that the actual Comment's modificationTime is equal to the given one.
	 * @param modificationTime the given modificationTime to compare the actual Comment's modificationTime to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Comment's modificationTime is not equal to the given one.
	 */
	public CommentAssert hasModificationTime(ZonedDateTime modificationTime) {
		// check that actual Comment we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected modificationTime of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		ZonedDateTime actualModificationTime = actual.getModificationTime();
		if (!Objects.areEqual(actualModificationTime, modificationTime)) {
			failWithMessage(assertjErrorMessage, actual, modificationTime, actualModificationTime);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Comment's rating is equal to the given one.
	 * @param rating the given rating to compare the actual Comment's rating to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Comment's rating is not equal to the given one.
	 */
	public CommentAssert hasRating(int rating) {
		// check that actual Comment we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected rating of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// check
		int actualRating = actual.getRating();
		if (actualRating != rating) {
			failWithMessage(assertjErrorMessage, actual, rating, actualRating);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Comment's subject is equal to the given one.
	 * @param subject the given subject to compare the actual Comment's subject to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Comment's subject is not equal to the given one.
	 */
	public CommentAssert hasSubject(Idea subject) {
		// check that actual Comment we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected subject of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		Idea actualSubject = actual.getSubject();
		if (!Objects.areEqual(actualSubject, subject)) {
			failWithMessage(assertjErrorMessage, actual, subject, actualSubject);
		}

		// return the current assertion for method chaining
		return this;
	}
}
