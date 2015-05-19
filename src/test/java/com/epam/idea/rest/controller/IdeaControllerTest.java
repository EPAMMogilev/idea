package com.epam.idea.rest.controller;

import com.epam.idea.builder.model.TestIdeaBuilder;
import com.epam.idea.builder.resource.TestIdeaResourceBuilder;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.exception.IdeaNotFoundException;
import com.epam.idea.rest.annotation.WebAppUnitTest;
import com.epam.idea.rest.resource.IdeaResource;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.builder.model.TestIdeaBuilder.DEFAULT_IDEA_ID;
import static com.epam.idea.core.service.exception.IdeaNotFoundException.ERROR_MSG_PATTERN_IDEA_NOT_FOUND;
import static com.epam.idea.rest.controller.RestErrorHandler.IDEA_NOT_FOUND_LOGREF;
import static com.epam.idea.util.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.util.TestUtils.EMPTY;
import static com.epam.idea.util.TestUtils.convertObjectToJsonBytes;
import static com.epam.idea.util.TestUtils.createStringWithLength;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppUnitTest
public class IdeaControllerTest {

	@Autowired
	private IdeaService ideaServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		Mockito.reset(this.ideaServiceMock);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void shouldReturnFoundIdeaWithHttpCode200() throws Exception {
		Idea foundIdea = TestIdeaBuilder.anIdea().build();

		when(this.ideaServiceMock.findOne(foundIdea.getId())).thenReturn(foundIdea);

		this.mockMvc.perform(get("/api/v1/ideas/{ideaId}", foundIdea.getId())
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title").value(foundIdea.getTitle()))
				.andExpect(jsonPath("$.description").value(foundIdea.getDescription()))
				.andExpect(jsonPath("$.rating").value(foundIdea.getRating()))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + foundIdea.getId())));

		verify(this.ideaServiceMock, times(1)).findOne(foundIdea.getId());
		verifyNoMoreInteractions(this.ideaServiceMock);
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenUserNotFound() throws Exception {
		String expectedErrorMsg = String.format(ERROR_MSG_PATTERN_IDEA_NOT_FOUND, DEFAULT_IDEA_ID);

		when(this.ideaServiceMock.findOne(DEFAULT_IDEA_ID)).thenThrow(new IdeaNotFoundException(DEFAULT_IDEA_ID));

		this.mockMvc.perform(get("/api/v1/ideas/{ideaId}", DEFAULT_IDEA_ID)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].logref").value(IDEA_NOT_FOUND_LOGREF))
				.andExpect(jsonPath("$[0].message").value(expectedErrorMsg))
				.andExpect(jsonPath("$[0].links", empty()));

		verify(this.ideaServiceMock, times(1)).findOne(DEFAULT_IDEA_ID);
		verifyNoMoreInteractions(this.ideaServiceMock);
	}

	@Test
	public void shouldReturnAllFoundIdeas() throws Exception {
		Idea foundIdea = TestIdeaBuilder.anIdea().build();

		when(this.ideaServiceMock.findAll()).thenReturn(Lists.newArrayList(foundIdea));

		this.mockMvc.perform(get("/api/v1/ideas")
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].title").value(foundIdea.getTitle()))
				.andExpect(jsonPath("$[0].description").value(foundIdea.getDescription()))
				.andExpect(jsonPath("$[0].rating").value(foundIdea.getRating()))
				.andExpect(jsonPath("$[0].links", hasSize(1)))
				.andExpect(jsonPath("$[0].links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/ideas/" + foundIdea.getId())));

		verify(this.ideaServiceMock, times(1)).findAll();
		verifyNoMoreInteractions(this.ideaServiceMock);
	}

	@Test
	public void shouldCreateIdeaAndReturnItWithHttpCode201() throws Exception {
		long ideaId = 2L;
		int rating = 3;
		String title = "title";
		String description = "description";
		IdeaResource ideaResource = TestIdeaResourceBuilder.anIdeaResource()
				.withTitle(title)
				.withDescription(description)
				.withRating(rating)
				.build();
		Idea createdIdea = new TestIdeaBuilder()
				.withId(ideaId)
				.withTitle(title)
				.withDescription(description)
				.withRating(rating)
				.build();

		when(this.ideaServiceMock.save(any(Idea.class))).thenReturn(createdIdea);

		this.mockMvc.perform(post("/api/v1/ideas")
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(ideaResource)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title").value(createdIdea.getTitle()))
				.andExpect(jsonPath("$.description").value(createdIdea.getDescription()))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + createdIdea.getId())));

		ArgumentCaptor<Idea> userCaptor = ArgumentCaptor.forClass(Idea.class);
		verify(this.ideaServiceMock, times(1)).save(userCaptor.capture());
		verifyNoMoreInteractions(this.ideaServiceMock);

		Idea ideaArgument = userCaptor.getValue();
		assertThat(ideaArgument.getTitle()).isEqualTo(ideaResource.getTitle());
		assertThat(ideaArgument.getDescription()).isEqualTo(ideaResource.getDescription());
		assertThat(ideaArgument.getRating()).isEqualTo(ideaResource.getRating());
	}

	@Test
	public void shouldReturnValidationErrorsForTooLongTitleAndDescription() throws Exception {
		String invalidTitle = createStringWithLength(Idea.MAX_LENGTH_TITLE + 1);
		String invalidDescription = createStringWithLength(Idea.MAX_LENGTH_DESCRIPTION + 1);

		IdeaResource ideaResource = TestIdeaResourceBuilder.anIdeaResource()
				.withTitle(invalidTitle)
				.withDescription(invalidDescription)
				.build();

		this.mockMvc.perform(post("/api/v1/ideas")
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(ideaResource)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[*].logref").value(containsInAnyOrder("title", "description")))
				.andExpect(jsonPath("$[*].message").value(containsInAnyOrder(
						String.format("size must be between %s and %s", Idea.MIN_LENGTH_TITLE, Idea.MAX_LENGTH_TITLE),
						String.format("size must be between %s and %s", 0, Idea.MAX_LENGTH_DESCRIPTION)
				)));

		verifyZeroInteractions(this.ideaServiceMock);
	}

	@Test
	public void shouldDeleteIdeaAndReturnHttpCode200() throws Exception {
		Idea deleted = TestIdeaBuilder.anIdea().build();

		when(this.ideaServiceMock.deleteById(deleted.getId())).thenReturn(deleted);

		this.mockMvc.perform(delete("/api/v1/ideas/{ideaId}", deleted.getId())
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(EMPTY));

		verify(this.ideaServiceMock, times(1)).deleteById(deleted.getId());
		verifyNoMoreInteractions(this.ideaServiceMock);
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenDeleteIdeaWhichDoesNotExist() throws Exception {
		long ideaId = 6L;

		when(this.ideaServiceMock.deleteById(ideaId)).thenThrow(new IdeaNotFoundException(ideaId));

		this.mockMvc.perform(delete("/api/v1/ideas/{ideaId}", ideaId)
				.accept(APPLICATION_JSON_UTF8)
				.contentType(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(IDEA_NOT_FOUND_LOGREF))
				.andExpect(jsonPath("$[0].message").value("Could not find idea with id: " + ideaId + "."))
				.andExpect(jsonPath("$[0].links", hasSize(0)));

		verify(this.ideaServiceMock, times(1)).deleteById(ideaId);
		verifyNoMoreInteractions(this.ideaServiceMock);
	}

	@Test
	public void shouldUpdateIdeaAndReturnItWithHttpCode200() throws Exception {
		long ideaId = 10L;
		String newTitle = "New title";
		String newDescription = "New description";
		IdeaResource source = TestIdeaResourceBuilder.anIdeaResource()
				.withTitle(newTitle)
				.withDescription(newDescription)
				.build();
		Idea updatedIdea = new TestIdeaBuilder()
				.withId(ideaId)
				.withTitle(newTitle)
				.withDescription(newDescription)
				.build();

		when(this.ideaServiceMock.update(eq(ideaId), any(Idea.class))).thenReturn(updatedIdea);

		this.mockMvc.perform(put("/api/v1/ideas/{ideaId}", ideaId)
				.accept(APPLICATION_JSON_UTF8)
				.contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(source)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title").value(updatedIdea.getTitle()))
				.andExpect(jsonPath("$.description").value(updatedIdea.getDescription()))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + updatedIdea.getId())));

		ArgumentCaptor<Idea> ideaCaptor = ArgumentCaptor.forClass(Idea.class);
		verify(this.ideaServiceMock, times(1)).update(eq(ideaId), ideaCaptor.capture());
		verifyNoMoreInteractions(this.ideaServiceMock);

		Idea ideaArgument = ideaCaptor.getValue();
		assertThat(ideaArgument.getTitle()).isEqualTo(source.getTitle());
		assertThat(ideaArgument.getDescription()).isEqualTo(source.getDescription());
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenUpdateIdeaWhichDoesNotExist() throws Exception {
		long ideaId = 10L;
		String newTitle = "New title";
		String newDescription = "New description";
		IdeaResource source = TestIdeaResourceBuilder.anIdeaResource()
				.withTitle(newTitle)
				.withDescription(newDescription)
				.build();

		when(this.ideaServiceMock.update(eq(ideaId), any(Idea.class))).thenThrow(new IdeaNotFoundException(ideaId));

		this.mockMvc.perform(put("/api/v1/ideas/{ideaId}", ideaId)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(source)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(IDEA_NOT_FOUND_LOGREF))
				.andExpect(jsonPath("$[0].message").value("Could not find idea with id: " + ideaId + "."))
				.andExpect(jsonPath("$[0].links", hasSize(0)));

		ArgumentCaptor<Idea> userCaptor = ArgumentCaptor.forClass(Idea.class);
		verify(this.ideaServiceMock, times(1)).update(eq(ideaId), userCaptor.capture());
		verifyNoMoreInteractions(this.ideaServiceMock);

		Idea ideaArgument = userCaptor.getValue();
		assertThat(ideaArgument.getTitle()).isEqualTo(source.getTitle());
		assertThat(ideaArgument.getDescription()).isEqualTo(source.getDescription());
	}
}
