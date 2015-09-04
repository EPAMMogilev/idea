package com.epam.idea.rest.controller;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.epam.idea.rest.resource.asm.TagResourceAsm;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.util.TestUtils.APPLICATION_JSON_UTF8;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class TagControllerIntegrationTest {

	public static final long TAG_ID = 3L;
	public static final String TAG_NAME = "Programming";

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	@DatabaseSetup("tagController-tags.xml")
	public void shouldReturnInfoOfFoundTagAsJsonWithHttpCode200() throws Exception {
		this.mockMvc.perform(get("/api/v1/tags/{tagId}", TAG_ID)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name").value(TAG_NAME))
				.andExpect(jsonPath("$.links", hasSize(2)))
				.andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/tags/" + TAG_ID)))
				.andExpect(jsonPath("$.links[1].rel").value(TagResourceAsm.IDEAS_REL))
				.andExpect(jsonPath("$.links[1].href").value(containsString("/api/v1/tags/" + TAG_ID + "/ideas")));
	}

	@Test
	@DatabaseSetup("tagController-no-tags.xml")
	public void shouldReturnErrorMessageAsJsonAndHttpStatus404WhenTagNotFound() throws Exception {
		this.mockMvc.perform(get("/api/v1/tags/{tagId}", TAG_ID)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andDo(print())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(RestErrorHandler.TAG_NOT_FOUND_LOGREF))
				.andExpect(jsonPath("$[0].message").value("Could not find tag"))
				.andExpect(jsonPath("$[0].links", empty()));
	}
}

