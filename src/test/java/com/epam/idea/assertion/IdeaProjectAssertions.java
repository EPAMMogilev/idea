package com.epam.idea.assertion;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.User;
import org.assertj.core.api.Assertions;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class IdeaProjectAssertions extends Assertions {

	/**
	 * Creates a new instance of {@link UserAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static UserAssert assertThatUser(User actual) {
		return new UserAssert(actual);
	}

	/**
	 * Creates a new instance of {@link TagAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static TagAssert assertThatTag(Tag actual) {
		return new TagAssert(actual);
	}

	/**
	 * Creates a new instance of {@link RoleAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static RoleAssert assertThatRole(Role actual) {
		return new RoleAssert(actual);
	}

	/**
	 * Creates a new instance of {@link IdeaAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static IdeaAssert assertThatIdea(Idea actual) {
		return new IdeaAssert(actual);
	}

	/**
	 * Creates a new instance of {@link CommentAssert}.
	 *
	 * @param actual the actual value.
	 * @return the created assertion object.
	 */
	public static CommentAssert assertThatComment(Comment actual) {
		return new CommentAssert(actual);
	}
}
