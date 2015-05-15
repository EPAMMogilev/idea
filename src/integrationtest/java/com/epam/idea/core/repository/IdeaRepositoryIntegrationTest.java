package com.epam.idea.core.repository;

import java.util.List;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.epam.idea.core.model.Idea;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.epam.idea.assertion.IdeaProjectAssertions.assertThatIdea;
import static com.epam.idea.assertion.IdeaProjectAssertions.assertThatTag;
import static com.epam.idea.assertion.IdeaProjectAssertions.assertThatUser;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class IdeaRepositoryIntegrationTest {

	public static final long USER_ID = 1L;
	public static final String USERNAME = "potato";
	public static final String USER_EMAIL = "potato@test.com";
	public static final long IDEA_ID = 2L;
	public static final String IDEA_TITLE = "Test title";
	public static final String IDEA_DESCRIPTION = "Test description";
	public static final int IDEA_RATING = -1;
	public static final long TAG_ID = 3L;
	public static final String TAG_NAME = "Testing";

	@Autowired
	private IdeaRepository ideaRepository;

	@Test
	@DatabaseSetup(value = "ideaRepository-ideas.xml")
	public void shouldFindAllIdeasByUserId() throws Exception {
		// When:
		List<Idea> ideas = this.ideaRepository.findByUserId(USER_ID);

		// Then:
		assertThat(ideas).hasSize(1);
		assertThatIdea(ideas.get(0))
				.hasId(IDEA_ID)
				.hasTitle(IDEA_TITLE)
				.hasDescription(IDEA_DESCRIPTION)
				.hasRating(IDEA_RATING);
		assertThatUser(ideas.get(0).getAuthor())
				.hasId(USER_ID)
				.hasUsername(USERNAME)
				.hasEmail(USER_EMAIL);
	}

	@Test
	@DatabaseSetup(value = "ideaRepository-ideas.xml")
	public void shouldFindAllIdeasByTagId() throws Exception {
		// When:
		List<Idea> ideas = this.ideaRepository.findByTagId(TAG_ID);

		// Then:
		assertThat(ideas).hasSize(1);
		assertThatIdea(ideas.get(0))
				.hasId(IDEA_ID)
				.hasTitle(IDEA_TITLE)
				.hasDescription(IDEA_DESCRIPTION)
				.hasRating(IDEA_RATING);
		assertThatTag(ideas.get(0).getRelatedTags().get(0))
				.hasId(TAG_ID)
				.hasName(TAG_NAME);
	}
}
