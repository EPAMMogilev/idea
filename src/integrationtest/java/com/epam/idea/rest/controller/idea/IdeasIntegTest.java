package com.epam.idea.rest.controller.idea;

import java.util.Collections;

import com.epam.idea.rest.config.RootConfig;
import com.epam.idea.rest.config.WebAppConfig;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.TagResource;
import com.epam.idea.rest.resource.UserResource;
import com.epam.idea.rest.resource.builders.TestIdeaResourceBuilder;
import com.epam.idea.rest.resource.builders.TestTagResourceBuilder;
import com.epam.idea.rest.resource.builders.TestUserResourceBuilder;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.core.repository.config.support.DatabaseConfigProfile.TEST;
import static com.epam.idea.rest.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.rest.TestUtils.convertObjectToJsonBytes;
import static com.epam.idea.rest.controller.RestErrorHandler.IDEA_NOT_FOUND_LOGREF;
import static com.epam.idea.rest.controller.idea.IdeaConstants.DESCRIPTION;
import static com.epam.idea.rest.controller.idea.IdeaConstants.EMAIL;
import static com.epam.idea.rest.controller.idea.IdeaConstants.IDEA_ID;
import static com.epam.idea.rest.controller.idea.IdeaConstants.NAME;
import static com.epam.idea.rest.controller.idea.IdeaConstants.PASSWORD;
import static com.epam.idea.rest.controller.idea.IdeaConstants.RATING;
import static com.epam.idea.rest.controller.idea.IdeaConstants.TITLE;
import static com.epam.idea.rest.controller.idea.IdeaConstants.USERNAME;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TEST)
@ContextConfiguration(classes = {RootConfig.class, WebAppConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@WebAppConfiguration
public class IdeasIntegTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	@DatabaseSetup("idea-entries.xml")
	public void shouldReturnInfoOfFoundIdeaAsJsonWithHttpCode200() throws Exception {
		mockMvc.perform(get("/api/v1/ideas/{ideaId}", IDEA_ID)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title").value(is(TITLE)))
				.andExpect(jsonPath("$.description").value(is(DESCRIPTION)))
				.andExpect(jsonPath("$.createdAt", notNullValue()))
				.andExpect(jsonPath("$.lastModifiedAt", notNullValue()))
				.andExpect(jsonPath("$.rating").value(is(RATING)))
//				.andExpect(jsonPath("$.author.username").value(is(USERNAME)))
//				.andExpect(jsonPath("$.author.email").value(is(EMAIL)))
//				.andExpect(jsonPath("$.author.password", nullValue()))
//				.andExpect(jsonPath("$.author.creationTime", notNullValue()))
//				.andExpect(jsonPath("$.author.links", hasSize(1)))
//				.andExpect(jsonPath("$.author.links[0].rel").value(is(Link.REL_SELF)))
//				.andExpect(jsonPath("$.author.links[0].href").value(containsString("/api/v1/users/" + USER_ID)))
				.andExpect(jsonPath("$.tags", hasSize(1)))
				.andExpect(jsonPath("$.tags[0].name").value(is(NAME)))
				.andExpect(jsonPath("$.tags[0].links", empty()))
				.andExpect(jsonPath("$.links", hasSize(2)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + IDEA_ID)));
	}

	@Test
	@DatabaseSetup("no-idea-entries.xml")
	public void shouldReturnErrorMessageAsJsonAndHttpStatus404WhenIdeaNotFound() throws Exception {
		mockMvc.perform(get("/api/v1/ideas/{ideaId}", IDEA_ID)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(is(IDEA_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is("Could not find idea with id: " + IDEA_ID + ".")))
				.andExpect(jsonPath("$[0].links", empty()));
	}

	@Ignore
	@Test
	@DatabaseSetup("no-idea-entries.xml")
	@ExpectedDatabase(value = "create-idea-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldCreatedIdeaAsReturnItAsJsonWithHttpStatus201() throws Exception {
		TagResource tag = TestTagResourceBuilder.aTagResource()
				.withName(NAME)
				.build();
		UserResource author = TestUserResourceBuilder.aUserResource()
				.withUsername(USERNAME)
				.withEmail(EMAIL)
				.withPassword(PASSWORD)
				.build();
		IdeaResource idea = TestIdeaResourceBuilder.anIdeaResource()
				.withTitle(TITLE)
				.withDescription(DESCRIPTION)
				.withRating(RATING)
				.withAuthor(author)
				.withRelatedTags(Collections.singletonList(tag))
				.build();

		mockMvc.perform(post("/api/v1/ideas")
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(idea)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title").value(is(TITLE)))
				.andExpect(jsonPath("$.description").value(is(DESCRIPTION)))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + IDEA_ID)));
	}
}
