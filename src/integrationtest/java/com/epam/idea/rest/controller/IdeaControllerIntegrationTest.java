package com.epam.idea.rest.controller;

import java.util.Collections;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.epam.idea.builder.resource.TestIdeaResourceBuilder;
import com.epam.idea.builder.resource.TestTagResourceBuilder;
import com.epam.idea.builder.resource.TestUserResourceBuilder;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.TagResource;
import com.epam.idea.rest.resource.UserResource;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.rest.controller.RestErrorHandler.IDEA_NOT_FOUND_LOGREF;
import static com.epam.idea.util.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.util.TestUtils.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class IdeaControllerIntegrationTest {

	public static final long IDEA_ID = 2L;
	public static final String IDEA_TITLE = "Test idea title";
	public static final String IDEA_DESCRIPTION = "Test idea description";
	public static final int IDEA_RATING = 5;
	public static final String TAG_NAME = "Technology";
	public static final long TAG_ID = 3L;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	@DatabaseSetup("ideaController-ideas.xml")
	public void shouldReturnInfoOfFoundIdeaAsJsonWithHttpCode200() throws Exception {
		this.mockMvc.perform(get("/api/v1/ideas/{ideaId}", IDEA_ID)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title").value(IDEA_TITLE))
				.andExpect(jsonPath("$.description").value(IDEA_DESCRIPTION))
				.andExpect(jsonPath("$.createdAt", notNullValue()))
				.andExpect(jsonPath("$.lastModifiedAt", notNullValue()))
				.andExpect(jsonPath("$.rating").value(IDEA_RATING))
//				.andExpect(jsonPath("$.author.username").value(is(USERNAME)))
//				.andExpect(jsonPath("$.author.email").value(is(EMAIL)))
//				.andExpect(jsonPath("$.author.password", nullValue()))
//				.andExpect(jsonPath("$.author.creationTime", notNullValue()))
//				.andExpect(jsonPath("$.author.links", hasSize(1)))
//				.andExpect(jsonPath("$.author.links[0].rel").value(is(Link.REL_SELF)))
//				.andExpect(jsonPath("$.author.links[0].href").value(containsString("/api/v1/users/" + USER_ID)))
				.andExpect(jsonPath("$.tags", hasSize(1)))
				.andExpect(jsonPath("$.tags[0].name").value(TAG_NAME))
				.andExpect(jsonPath("$.tags[0].links", hasSize(2)))
				.andExpect(jsonPath("$.tags[0].links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.tags[0].links[0].href").value(containsString("/api/v1/tags/" + TAG_ID)))
				.andExpect(jsonPath("$.links", hasSize(2)))
				.andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + IDEA_ID)));
	}

	@Test
	@DatabaseSetup("ideaController-no-ideas.xml")
	public void shouldReturnErrorMessageAsJsonAndHttpStatus404WhenIdeaNotFound() throws Exception {
		this.mockMvc.perform(get("/api/v1/ideas/{ideaId}", IDEA_ID)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(IDEA_NOT_FOUND_LOGREF))
				.andExpect(jsonPath("$[0].message").value("Could not find idea with id: " + IDEA_ID + "."))
				.andExpect(jsonPath("$[0].links", empty()));
	}

	@Ignore
	@Test
	@DatabaseSetup("ideaController-no-ideas.xml")
	@ExpectedDatabase(value = "ideaController-create-idea-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldCreatedIdeaAndReturnItAsJsonWithHttpStatus201() throws Exception {
		TagResource tag = TestTagResourceBuilder.aTagResource()
				.withName(TAG_NAME)
				.build();
		UserResource author = TestUserResourceBuilder.aUserResource()
				.withUsername("TEST")
				.withEmail("TEST")
				.withPassword("TEST")
				.build();
		IdeaResource idea = TestIdeaResourceBuilder.anIdeaResource()
				.withTitle(IDEA_TITLE)
				.withDescription(IDEA_DESCRIPTION)
				.withRating(IDEA_RATING)
				.withAuthor(author)
				.withRelatedTags(Collections.singletonList(tag))
				.build();

		this.mockMvc.perform(post("/api/v1/ideas")
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(idea)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title").value(IDEA_TITLE))
				.andExpect(jsonPath("$.description").value(IDEA_DESCRIPTION))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + IDEA_ID)));
	}
}
