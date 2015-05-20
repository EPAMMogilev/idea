package com.epam.idea.assertion;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Iterables;
import org.assertj.core.util.Objects;

/**
 * {@link com.epam.idea.core.model.Tag} specific assertions.
 */
public class TagAssert extends AbstractAssert<TagAssert, Tag> {

	/**
	 * Creates a new {@link TagAssert} to make assertions on actual Tag.
	 *
	 * @param actual the Tag we want to make assertions on.
	 */
	public TagAssert(Tag actual) {
		super(actual, TagAssert.class);
	}

	/**
	 * Verifies that the actual Tag's id is equal to the given one.
	 * @param id the given id to compare the actual Tag's id to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Tag's id is not equal to the given one.
	 */
	public TagAssert hasId(long id) {
		// check that actual Tag we want to make assertions on is not null.
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
	 * Verifies that the actual Tag's ideasWithTag contains the given Idea elements.
	 * @param ideas the given elements that should be contained in actual Tag's ideasWithTag.
	 * @return this assertion object.
	 * @throws AssertionError if the actual Tag's ideasWithTag does not contain all given Idea elements.
	 */
	public TagAssert hasIdeas(Idea... ideas) {
		// check that actual Tag we want to make assertions on is not null.
		isNotNull();

		// check that given Idea varargs is not null.
		if (ideas == null) throw new AssertionError("Expecting ideasWithTag parameter not to be null.");

		// check with standard error message, to set another message call: info.overridingErrorMessage("my error message");
		Iterables.instance().assertContains(info, actual.getIdeasWithTag(), ideas);

		// return the current assertion for method chaining
		return this;
	}

	/**
	 * Verifies that the actual Tag has no ideas.
	 * @return this assertion object.
	 * @throws AssertionError if the actual Tag's ideasWithTag is not empty.
	 */
	public TagAssert hasNoIdeas() {
		// check that actual Tag we want to make assertions on is not null.
		isNotNull();

		// we override the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected :\n  <%s>\nnot to have ideasWithTag but had :\n  <%s>";

		// check
		if (actual.getIdeasWithTag().iterator().hasNext()) {
			failWithMessage(assertjErrorMessage, actual, actual.getIdeasWithTag());
		}

		// return the current assertion for method chaining
		return this;
	}


	/**
	 * Verifies that the actual Tag's name is equal to the given one.
	 * @param name the given name to compare the actual Tag's name to.
	 * @return this assertion object.
	 * @throws AssertionError - if the actual Tag's name is not equal to the given one.
	 */
	public TagAssert hasName(String name) {
		// check that actual Tag we want to make assertions on is not null.
		isNotNull();

		// overrides the default error message with a more explicit one
		String assertjErrorMessage = "\nExpected name of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

		// null safe check
		String actualName = actual.getName();
		if (!Objects.areEqual(actualName, name)) {
			failWithMessage(assertjErrorMessage, actual, name, actualName);
		}

		// return the current assertion for method chaining
		return this;
	}
}
