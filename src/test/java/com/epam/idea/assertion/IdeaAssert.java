package com.epam.idea.assertion;

import java.time.ZonedDateTime;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.User;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Iterables;
import org.assertj.core.util.Objects;

/**
 * {@link Idea} specific assertions.
 */
public class IdeaAssert extends AbstractAssert<IdeaAssert, Idea> {

	/**
	 * Creates a new {@link IdeaAssert} to make assertions on actual Idea.
	 * @param actual the Idea we want to make assertions on.
	 */
	public IdeaAssert(Idea actual) {
		super(actual, IdeaAssert.class);
	}

	/**
	 * Verifies that the actual Idea's author is equal to the given one.
	 * @param author the given author to compare the actual Idea's author to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Idea's author is not equal to the given one.
	 */
	public IdeaAssert hasAuthor(User author) {
		// check that actual Idea we want to make assertions on is not null.
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
	 * Verifies that the actual Idea's comments contains the given Comment elements.
	 * @param comments the given elements that should be contained in actual Idea's comments.
	 * @return this assertion object.
	 * @throws AssertionError if the actual Idea's comments does not contain all given Comment elements.
	 */
	public IdeaAssert hasComments(Comment... comments) {
		// check that actual Idea we want to make assertions on is not null.
		isNotNull();

		// check that given Comment varargs is not null.
		if (comments == null) throw new AssertionError("Expecting comments parameter not to be null.");

		// check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
		Iterables.instance().assertContains(info, actual.getComments(), comments);

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Idea has no comments.
	 * @return this assertion object.
	 * @throws AssertionError if the actual Idea's comments is not empty.
	 */
	public IdeaAssert hasNoComments() {
		// check that actual Idea we want to make assertions on is not null.
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


	/**
	 * Verifies that the actual Idea's creationTime is equal to the given one.
	 * @param creationTime the given creationTime to compare the actual Idea's creationTime to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Idea's creationTime is not equal to the given one.
	 */
	public IdeaAssert hasCreationTime(ZonedDateTime creationTime) {
		// check that actual Idea we want to make assertions on is not null.
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
	 * Verifies that the actual Idea's description is equal to the given one.
	 * @param description the given description to compare the actual Idea's description to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Idea's description is not equal to the given one.
	 */
	public IdeaAssert hasDescription(String description) {
		// check that actual Idea we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected description of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		String actualDescription = actual.getDescription();
		if (!Objects.areEqual(actualDescription, description)) {
			failWithMessage(assertjErrorMessage, actual, description, actualDescription);
		}

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Idea's id is equal to the given one.
	 * @param id the given id to compare the actual Idea's id to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Idea's id is not equal to the given one.
	 */
	public IdeaAssert hasId(long id) {
		// check that actual Idea we want to make assertions on is not null.
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
	 * Verifies that the actual Idea's modificationTime is equal to the given one.
	 * @param modificationTime the given modificationTime to compare the actual Idea's modificationTime to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Idea's modificationTime is not equal to the given one.
	 */
	public IdeaAssert hasModificationTime(ZonedDateTime modificationTime) {
		// check that actual Idea we want to make assertions on is not null.
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
	 * Verifies that the actual Idea's rating is equal to the given one.
	 * @param rating the given rating to compare the actual Idea's rating to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Idea's rating is not equal to the given one.
	 */
	public IdeaAssert hasRating(int rating) {
		// check that actual Idea we want to make assertions on is not null.
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
	 * Verifies that the actual Idea's relatedTags contains the given Tag elements.
	 * @param relatedTags the given elements that should be contained in actual Idea's relatedTags.
	 * @return this assertion object.
	 * @throws AssertionError if the actual Idea's relatedTags does not contain all given Tag elements.
	 */
	public IdeaAssert hasRelatedTags(Tag... relatedTags) {
		// check that actual Idea we want to make assertions on is not null.
		isNotNull();

		// check that given Tag varargs is not null.
		if (relatedTags == null) throw new AssertionError("Expecting relatedTags parameter not to be null.");

		// check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
		Iterables.instance().assertContains(info, actual.getRelatedTags(), relatedTags);

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Idea has no relatedTags.
	 * @return this assertion object.
	 * @throws AssertionError if the actual Idea's relatedTags is not empty.
	 */
	public IdeaAssert hasNoRelatedTags() {
		// check that actual Idea we want to make assertions on is not null.
		isNotNull();

		// we override the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected :\n  <%s>\nnot to have relatedTags but had :\n  <%s>";

		// check
		if (actual.getRelatedTags().iterator().hasNext()) {
			failWithMessage(assertjErrorMessage, actual, actual.getRelatedTags());
		}

		// return the current assertion for method chaining
		return this;
	}


	/**
	 * Verifies that the actual Idea's title is equal to the given one.
	 * @param title the given title to compare the actual Idea's title to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Idea's title is not equal to the given one.
	 */
	public IdeaAssert hasTitle(String title) {
		// check that actual Idea we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected title of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		String actualTitle = actual.getTitle();
		if (!Objects.areEqual(actualTitle, title)) {
			failWithMessage(assertjErrorMessage, actual, title, actualTitle);
		}

		// return the current assertion for method chaining
		return this;
	}
}
