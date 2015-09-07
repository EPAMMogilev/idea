package com.epam.idea.rest.controller;

import com.epam.idea.builder.model.TestIdeaBuilder;
import com.epam.idea.builder.model.TestUserBuilder;
import com.epam.idea.builder.resource.TestIdeaResourceBuilder;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.exception.IdeaNotFoundException;
import com.epam.idea.rest.annotation.WebAppUnitTest;
import com.epam.idea.rest.resource.IdeaResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.Arrays;

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
import static org.mockito.Mockito.mock;
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
    private IdeaController ideaController;

    @Autowired
    private IdeaService ideaServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final Pageable defaultPageRequest = new PageRequest(0, 500, null);

    @Before
    public void setUp() throws Exception {
        Mockito.reset(this.ideaServiceMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(ideaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    public void shouldReturnFoundIdeaWithHttpCode200() throws Exception {
        final User foundLikedUser = TestUserBuilder.aUser().build();
        final Idea foundIdea = TestIdeaBuilder.anIdea().withLikedUser(foundLikedUser).build();

        when(this.ideaServiceMock.findOne(foundIdea.getId())).thenReturn(foundIdea);

        this.mockMvc.perform(get("/api/v1/ideas/{ideaId}", foundIdea.getId()).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title").value(foundIdea.getTitle()))
                .andExpect(jsonPath("$.description").value(foundIdea.getDescription()))
                .andExpect(jsonPath("$.rating").value(foundIdea.getRating()))
                .andExpect(jsonPath("$.likedUsers", hasSize(1)))
                .andExpect(jsonPath("$.likedUsers[0].username").value(foundLikedUser.getUsername()))
                .andExpect(jsonPath("$.links", hasSize(1))).andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
                .andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + foundIdea.getId())));

        verify(this.ideaServiceMock, times(1)).findOne(foundIdea.getId());
        verifyNoMoreInteractions(this.ideaServiceMock);
    }

    @Test(expected = NestedServletException.class)
    public void shouldReturnErrorWithHttpStatus404WhenUserNotFound() throws Exception {
        final String expectedErrorMsg = String.format(ERROR_MSG_PATTERN_IDEA_NOT_FOUND, DEFAULT_IDEA_ID);

        when(this.ideaServiceMock.findOne(DEFAULT_IDEA_ID)).thenThrow(new IdeaNotFoundException(DEFAULT_IDEA_ID));

        this.mockMvc.perform(get("/api/v1/ideas/{ideaId}", DEFAULT_IDEA_ID).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].logref").value(IDEA_NOT_FOUND_LOGREF))
                .andExpect(jsonPath("$[0].message").value(expectedErrorMsg)).andExpect(jsonPath("$[0].links", empty()));

        verify(this.ideaServiceMock, times(1)).findOne(DEFAULT_IDEA_ID);
        verifyNoMoreInteractions(this.ideaServiceMock);
    }

    @Test
    public void shouldReturnAllFoundIdeas() throws Exception {
        final Idea foundIdea = TestIdeaBuilder.anIdea().build();

        when(this.ideaServiceMock.findAllByUserIdQueryAndTagId(defaultPageRequest, null, null, null))
                .thenReturn(Arrays.asList(foundIdea));

        this.mockMvc.perform(get("/api/v1/ideas").accept(APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value(foundIdea.getTitle()))
                .andExpect(jsonPath("$[0].description").value(foundIdea.getDescription()))
                .andExpect(jsonPath("$[0].rating").value(foundIdea.getRating()))
                .andExpect(jsonPath("$[0].links", hasSize(1)))
                .andExpect(jsonPath("$[0].links[0].rel").value(Link.REL_SELF))
                .andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/ideas/" + foundIdea.getId())));

        verify(this.ideaServiceMock, times(1)).findAllByUserIdQueryAndTagId(defaultPageRequest, null, null, null);
        verifyNoMoreInteractions(this.ideaServiceMock);
    }

    @Test
	public void shouldCreateIdeaAndReturnItWithHttpCode201() throws Exception {
		final IdeaResource source = TestIdeaResourceBuilder.anIdeaResource().build();
		final Idea createdIdea = new TestIdeaBuilder()
				.withId(10L)
				.withTitle(source.getTitle())
				.withDescription(source.getDescription())
				.withState(source.getState())
				.withRating(source.getRating())
				.build();

		when(this.ideaServiceMock.create(any(Idea.class))).thenReturn(createdIdea);

		this.mockMvc.perform(post("/api/v1/ideas")
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(source)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title").value(createdIdea.getTitle()))
				.andExpect(jsonPath("$.description").value(createdIdea.getDescription()))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + createdIdea.getId())));

		final ArgumentCaptor<Idea> userCaptor = ArgumentCaptor.forClass(Idea.class);
		verify(this.ideaServiceMock, times(1)).create(userCaptor.capture());
		verifyNoMoreInteractions(this.ideaServiceMock);

		final Idea ideaArgument = userCaptor.getValue();
		assertThat(ideaArgument.getTitle()).isEqualTo(source.getTitle());
		assertThat(ideaArgument.getDescription()).isEqualTo(source.getDescription());
		assertThat(ideaArgument.getRating()).isEqualTo(source.getRating());
	}

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnValidationErrorsForTooLongTitleAndDescription() throws Exception {
        final String invalidTitle = createStringWithLength(Idea.MAX_LENGTH_TITLE + 1);
        final String invalidDescription = createStringWithLength(Idea.MAX_LENGTH_DESCRIPTION + 1);

        final IdeaResource ideaResource = TestIdeaResourceBuilder.anIdeaResource().withTitle(invalidTitle)
                .withDescription(invalidDescription).build();

        this.mockMvc
                .perform(post("/api/v1/ideas").contentType(APPLICATION_JSON_UTF8).accept(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(ideaResource)))
                .andDo(print()).andExpect(status().isBadRequest())
                // .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].logref").value(containsInAnyOrder("title", "description")))
                .andExpect(jsonPath("$[*].message").value(containsInAnyOrder(
                        String.format("size must be between %s and %s", Idea.MIN_LENGTH_TITLE, Idea.MAX_LENGTH_TITLE),
                        String.format("size must be between %s and %s", 0, Idea.MAX_LENGTH_DESCRIPTION))));

        verifyZeroInteractions(this.ideaServiceMock);
    }

    @Test
    public void shouldDeleteIdeaAndReturnHttpCode200() throws Exception {
        final Idea deleted = TestIdeaBuilder.anIdea().build();

        when(this.ideaServiceMock.deleteById(deleted.getId())).thenReturn(deleted);

        this.mockMvc
                .perform(delete("/api/v1/ideas/{ideaId}", deleted.getId()).contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(EMPTY));

        verify(this.ideaServiceMock, times(1)).deleteById(deleted.getId());
        verifyNoMoreInteractions(this.ideaServiceMock);
    }

    @Test(expected = NestedServletException.class)
    public void shouldReturnErrorWithHttpStatus404WhenDeleteIdeaWhichDoesNotExist() throws Exception {
        final long ideaId = 6L;

        when(this.ideaServiceMock.deleteById(ideaId)).thenThrow(new IdeaNotFoundException(ideaId));

        this.mockMvc
                .perform(delete("/api/v1/ideas/{ideaId}", ideaId).accept(APPLICATION_JSON_UTF8)
                        .contentType(APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isNotFound()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].logref").value(IDEA_NOT_FOUND_LOGREF))
                .andExpect(jsonPath("$[0].message").value("Could not find idea with id: " + ideaId + "."))
                .andExpect(jsonPath("$[0].links", hasSize(0)));

        verify(this.ideaServiceMock, times(1)).deleteById(ideaId);
        verifyNoMoreInteractions(this.ideaServiceMock);
    }

    @Test
    public void shouldUpdateIdeaAndReturnItWithHttpCode200() throws Exception {
        final long ideaId = 10L;
        final IdeaResource source = TestIdeaResourceBuilder.anIdeaResource().build();
        final Idea updatedIdea = new TestIdeaBuilder().withId(ideaId).withTitle(source.getTitle())
                .withDescription(source.getDescription()).build();

        when(this.ideaServiceMock.update(eq(ideaId), any(Idea.class))).thenReturn(updatedIdea);

        this.mockMvc
                .perform(put("/api/v1/ideas/{ideaId}", ideaId).accept(APPLICATION_JSON_UTF8)
                        .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(source)))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title").value(updatedIdea.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedIdea.getDescription()))
                .andExpect(jsonPath("$.links", hasSize(1))).andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
                .andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + updatedIdea.getId())));

        final ArgumentCaptor<Idea> ideaCaptor = ArgumentCaptor.forClass(Idea.class);
        verify(this.ideaServiceMock, times(1)).update(eq(ideaId), ideaCaptor.capture());
        verifyNoMoreInteractions(this.ideaServiceMock);

        final Idea ideaArgument = ideaCaptor.getValue();
        assertThat(ideaArgument.getTitle()).isEqualTo(source.getTitle());
        assertThat(ideaArgument.getDescription()).isEqualTo(source.getDescription());
    }

    @Test(expected = NestedServletException.class)
    public void shouldReturnErrorWithHttpStatus404WhenUpdateIdeaWhichDoesNotExist() throws Exception {
        final long ideaId = 10L;
        final IdeaResource source = TestIdeaResourceBuilder.anIdeaResource().build();

        when(this.ideaServiceMock.update(eq(ideaId), any(Idea.class))).thenThrow(new IdeaNotFoundException(ideaId));

        this.mockMvc
                .perform(put("/api/v1/ideas/{ideaId}", ideaId).contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(source)))
                .andExpect(status().isNotFound()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].logref").value(IDEA_NOT_FOUND_LOGREF))
                .andExpect(jsonPath("$[0].message").value("Could not find idea with id: " + ideaId + "."))
                .andExpect(jsonPath("$[0].links", hasSize(0)));

        final ArgumentCaptor<Idea> userCaptor = ArgumentCaptor.forClass(Idea.class);
        verify(this.ideaServiceMock, times(1)).update(eq(ideaId), userCaptor.capture());
        verifyNoMoreInteractions(this.ideaServiceMock);

        final Idea ideaArgument = userCaptor.getValue();
        assertThat(ideaArgument.getTitle()).isEqualTo(source.getTitle());
        assertThat(ideaArgument.getDescription()).isEqualTo(source.getDescription());
    }

    @Test
    public void shouldChangeIdeaLikeAndReturnItWithHttpCode200() throws Exception {
        final long ideaId = 10L;
        final IdeaResource source = TestIdeaResourceBuilder.anIdeaResource().build();
        final Idea changedLikeIdea = new TestIdeaBuilder().anIdea().build();
        final User likedUser = TestUserBuilder.aUser().build();

        source.setLiked(false);
        changedLikeIdea.setLiked(true);
        changedLikeIdea.setLikedUsers(Arrays.asList(likedUser));

        when(this.ideaServiceMock.changeIdeaLike(eq(ideaId))).thenReturn(changedLikeIdea);

        this.mockMvc
                .perform(post("/api/v1/ideas/{ideaId}/like", ideaId).accept(APPLICATION_JSON_UTF8)
                        .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(source)))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title").value(changedLikeIdea.getTitle()))
                .andExpect(jsonPath("$.description").value(changedLikeIdea.getDescription()))
                .andExpect(jsonPath("$.links", hasSize(1))).andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
                .andExpect(
                        jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + changedLikeIdea.getId())))
                .andExpect(jsonPath("$.liked").value(true));

        // ArgumentCaptor<Idea> ideaCaptor =
        // ArgumentCaptor.forClass(Idea.class);
        verify(this.ideaServiceMock, times(1)).changeIdeaLike(eq(ideaId));
        verifyNoMoreInteractions(this.ideaServiceMock);

        // Idea ideaArgument = ideaCaptor.getValue();
        // assertThat(ideaArgument.getTitle()).isEqualTo(source.getTitle());
        // assertThat(ideaArgument.getDescription()).isEqualTo(source.getDescription());
    }
}