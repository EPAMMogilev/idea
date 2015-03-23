package com.epam.idea.core.repository;

import com.epam.idea.core.model.Idea;
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

import static com.epam.idea.assertion.IdeaProjectAssertions.assertThatIdea;
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
@DatabaseSetup("repository-idea-entries.xml")
public class IdeaRepositoryIntegTest {

	@Autowired
	private IdeaRepository ideaRepository;

	@Test
	public void shouldFindAllIdeasByUserId() throws Exception {
		// Given:
		long userID = 1L;

		// When:
		List<Idea> ideas = ideaRepository.findByUserId(userID);

		// Then:
		assertThat(ideas).hasSize(1);
		assertThatIdea(ideas.get(0))
				.hasId(1L)
				.hasTitle("Test title")
				.hasDescription("Test description")
				.hasRating(5);
		assertThatUser(ideas.get(0).getAuthor())
				.hasId(userID)
				.hasUsername("Jack")
				.hasEmail("Jack@test.com");
	}
}