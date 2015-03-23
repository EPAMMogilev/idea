package com.epam.idea.core.repository;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.repository.config.PersistenceConfig;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static com.epam.idea.assertion.IdeaProjectAssertions.assertThatComment;
import static com.epam.idea.assertion.IdeaProjectAssertions.assertThatUser;
import static com.epam.idea.core.repository.config.support.DatabaseConfigProfile.INTEGRATION_TEST;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(INTEGRATION_TEST)
@ContextConfiguration(classes = {PersistenceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class})
@DatabaseSetup("repository-comment-entries.xml")
public class CommentRepositoryIntegTest {

	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void shouldFindAllCommentsByUserId() throws Exception {
		// Given:
		long userID = 1L;

		// When:
		List<Comment> comments = commentRepository.findCommentsByUserId(userID);

		// Then:
		assertThat(comments).hasSize(1);
		assertThatComment(comments.get(0))
				.hasId(1L)
				.hasBody("Awesome")
				.hasRating(5);
		assertThatUser(comments.get(0).getAuthor())
				.hasId(userID)
				.hasUsername("Jack")
				.hasEmail("Jack@test.com");
	}
}