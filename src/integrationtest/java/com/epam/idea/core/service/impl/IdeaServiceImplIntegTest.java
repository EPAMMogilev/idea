package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.builders.TestIdeaBuilder;
import com.epam.idea.core.model.builders.TestTagBuilder;
import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.rest.config.RootConfig;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Ignore;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(DatabaseConfigProfile.TEST)
@ContextConfiguration(classes = {RootConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class})
@DatabaseSetup("repository-idea-entries.xml")
@Ignore
public class IdeaServiceImplIntegTest {

	@Autowired
	private IdeaService ideaService;

	@Test
	@ExpectedDatabase(value = "create-idea.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testName() throws Exception {
		Tag tag = new TestTagBuilder().withName("Dota").build();
		Idea idea = new TestIdeaBuilder()
				.withDescription("new description")
				.withTitle("new title")
				.addTag(tag)
				.build();
		//tag.setIdeasWithTag(Lists.newArrayList(idea));
		Idea saveForUser = ideaService.saveForUser(1L, idea);
		assertThat(saveForUser.getId()).isEqualTo(2L);
	}
}