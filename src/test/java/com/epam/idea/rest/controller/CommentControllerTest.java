package com.epam.idea.rest.controller;

import java.util.Arrays;

import com.epam.idea.builder.model.TestCommentBuilder;
import com.epam.idea.builder.model.TestIdeaBuilder;
import com.epam.idea.builder.model.TestUserBuilder;
import com.epam.idea.builder.resource.TestCommentResourceBuilder;
import com.epam.idea.builder.resource.TestIdeaResourceBuilder;
import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.rest.annotation.WebAppUnitTest;
import com.epam.idea.rest.resource.CommentResource;
import com.epam.idea.rest.resource.IdeaResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.util.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.util.TestUtils.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppUnitTest
public class CommentControllerTest {

	@Autowired
	private CommentController commentController;

	@Autowired
	private CommentService commentServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		Mockito.reset(this.commentServiceMock);
		this.mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
	}

	@Test
	public void shouldReturnFoundCommentWithHttpCode200() throws Exception {
		Comment foundComment = TestCommentBuilder.aComment().build();

		when(this.commentServiceMock.findOne(foundComment.getId())).thenReturn(foundComment);

		this.mockMvc.perform(get("/api/v1/comments/{commentId}", foundComment.getId())
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.body").value(foundComment.getBody()))
				.andExpect(jsonPath("$.subject").value(foundComment.getSubject()))
				.andExpect(jsonPath("$.rating").value(foundComment.getRating()))
				.andExpect(jsonPath("$.author").value(foundComment.getAuthor()))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/comments/" + foundComment.getId())));

		verify(this.commentServiceMock, times(1)).findOne(foundComment.getId());
		verifyNoMoreInteractions(this.commentServiceMock);
	}

    @Test
    public void shouldChangeCommentLikeAndReturnItWithHttpCode200() throws Exception {
        final long commentId = 10L;
        final CommentResource source = TestCommentResourceBuilder.aCommentResource().build();
        final Comment changedLikeComment = TestCommentBuilder.aComment().build();
        final User likedUser = TestUserBuilder.aUser().build();

        source.setLiked(false);
        changedLikeComment.setLiked(true);
        changedLikeComment.setLikedUsers(Arrays.asList(likedUser));

        when(this.commentServiceMock.changeCommentLike(eq(commentId))).thenReturn(changedLikeComment);

        this.mockMvc
                .perform(post("/api/v1/comments/{commentId}/like", commentId).accept(APPLICATION_JSON_UTF8)
                        .contentType(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(source)))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.body").value(changedLikeComment.getBody()))
                .andExpect(jsonPath("$.author").value(changedLikeComment.getAuthor()))
                .andExpect(jsonPath("$.subject").value(changedLikeComment.getSubject()))
                .andExpect(jsonPath("$.liked").value(true))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF));

        verify(this.commentServiceMock, times(1)).changeCommentLike(eq(commentId));
        verifyNoMoreInteractions(this.commentServiceMock);
    }
}