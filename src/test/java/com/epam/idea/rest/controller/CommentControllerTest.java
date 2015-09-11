package com.epam.idea.rest.controller;

import com.epam.idea.builder.model.TestCommentBuilder;

import com.epam.idea.core.model.Comment;

import com.epam.idea.core.service.CommentService;
import com.epam.idea.rest.annotation.WebAppUnitTest;

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

import static org.hamcrest.Matchers.containsString;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}