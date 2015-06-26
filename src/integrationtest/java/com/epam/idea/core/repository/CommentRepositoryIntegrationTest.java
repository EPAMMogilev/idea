package com.epam.idea.core.repository;

import java.util.List;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.epam.idea.core.model.Comment;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.epam.idea.assertion.IdeaProjectAssertions.assertThatComment;
import static com.epam.idea.assertion.IdeaProjectAssertions.assertThatUser;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class CommentRepositoryIntegrationTest {

	public static final long USER_ID = 1L;
	public static final String COMMENT_BODY = "Awesome";
	public static final String USERNAME = "user";
	public static final String USER_EMAIL = "user@test.com";
	public static final long COMMENT_ID = 3L;
	public static final int COMMENT_RATING = 10;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	@DatabaseSetup(value = "commentRepository-comments.xml")
	public void shouldFindAllCommentsByUserId() throws Exception {
		// When:
		List<Comment> comments = this.commentRepository.findByUserId(USER_ID);

		// Then:
		assertThat(comments).hasSize(1);
		assertThatComment(comments.get(0))
				.hasId(COMMENT_ID)
				.hasBody(COMMENT_BODY)
				.hasRating(COMMENT_RATING);
		assertThatUser(comments.get(0).getAuthor())
				.hasId(USER_ID)
				.hasUsername(USERNAME)
				.hasEmail(USER_EMAIL);
	}
}
