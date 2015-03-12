package com.epam.idea.rest.controller;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.User;
import com.epam.idea.core.model.builders.TestCommentBuilder;
import com.epam.idea.core.model.builders.TestIdeaBuilder;
import com.epam.idea.core.model.builders.TestUserBuilder;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.UserNotFoundException;
import com.epam.idea.rest.config.TestConfig;
import com.epam.idea.rest.config.WebAppConfig;
import com.epam.idea.rest.resource.UserResource;
import com.epam.idea.rest.resource.asm.IdeaResourceAsm;
import com.epam.idea.rest.resource.asm.UserResourceAsm;
import com.epam.idea.rest.resource.builders.TestUserResourceBuilder;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.core.model.User.MAX_LENGTH_EMAIL;
import static com.epam.idea.core.model.User.MAX_LENGTH_PASSWORD;
import static com.epam.idea.core.model.User.MAX_LENGTH_USERNAME;
import static com.epam.idea.core.model.builders.TestTagBuilder.aTag;
import static com.epam.idea.core.model.builders.TestUserBuilder.DEFAULT_CREATION_TIME;
import static com.epam.idea.core.model.builders.TestUserBuilder.DEFAULT_USER_ID;
import static com.epam.idea.core.model.builders.TestUserBuilder.aUser;
import static com.epam.idea.rest.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.rest.TestUtils.EMPTY;
import static com.epam.idea.rest.TestUtils.convertObjectToJsonBytes;
import static com.epam.idea.rest.TestUtils.createEmailWithLength;
import static com.epam.idea.rest.TestUtils.createStringWithLength;
import static com.epam.idea.rest.controller.RestErrorHandler.USER_NOT_FOUND_LOGREF;
import static com.epam.idea.rest.resource.support.JsonPropertyName.CREATION_TIME;
import static com.epam.idea.rest.resource.support.JsonPropertyName.ID;
import static com.epam.idea.rest.resource.support.JsonPropertyName.MODIFICATION_TIME;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
@ContextConfiguration(classes = {TestConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class UserControllerTest {

	public static final String EXPECTED_USER_CREATION_TIME = "2015-01-12T00:00Z";
	public static final String EXPECTED_IDEA_CREATION_TIME = "2014-02-12T10:00Z";
	public static final String EXPECTED_IDEA_MODIFICATION_TIME = "2014-10-05T00:00Z";
	public static final String EXPECTED_COMMENT_CREATION_TIME = "2014-05-05T00:00Z";
	public static final String EXPECTED_COMMENT_MODIFICATION_TIME = "2014-07-08T00:00Z";

	@Autowired
	private UserService userServiceMock;

	@Autowired
	private IdeaService ideaServiceMock;

	@Autowired
	private CommentService commentServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		Mockito.reset(userServiceMock);

		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void shouldReturnAllFoundUsers() throws Exception {
		User first = aUser().build();

		when(userServiceMock.findAll()).thenReturn(asList(first));

		mockMvc.perform(get("/api/v1/users")
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0]." + ID).value(is(((int) first.getId()))))
				.andExpect(jsonPath("$[0].username").value(is(first.getUsername())))
				.andExpect(jsonPath("$[0].email").value(is(first.getEmail())))
				.andExpect(jsonPath("$[0].password").doesNotExist())
				.andExpect(jsonPath("$[0]." + CREATION_TIME).value(is(EXPECTED_USER_CREATION_TIME)))
				.andExpect(jsonPath("$[0].links", hasSize(3)))
				.andExpect(jsonPath("$[0].links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/users/" + first.getId())))
				.andExpect(jsonPath("$[0].links[1].rel").value(is(UserResourceAsm.IDEAS_REL)))
				.andExpect(jsonPath("$[0].links[1].href").value(containsString("/api/v1/users/" + first.getId() + "/ideas")))
				.andExpect(jsonPath("$[0].links[2].rel").value(is(UserResourceAsm.COMMENTS_REL)))
				.andExpect(jsonPath("$[0].links[2].href").value(containsString("/api/v1/users/" + first.getId() + "/comments")));

		verify(userServiceMock, times(1)).findAll();
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldReturnFoundUserWithHttpCode200() throws Exception {
		User user = aUser().build();

		when(userServiceMock.findOne(user.getId())).thenReturn(user);

		mockMvc.perform(get("/api/v1/users/{userId}", DEFAULT_USER_ID)
				.accept(APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$." + ID).value(is(((int) user.getId()))))
				.andExpect(jsonPath("$.username").value(is(user.getUsername())))
				.andExpect(jsonPath("$.email").value(is(user.getEmail())))
				.andExpect(jsonPath("$.password").doesNotExist())
				.andExpect(jsonPath("$." + CREATION_TIME).value(is(EXPECTED_USER_CREATION_TIME)))
				.andExpect(jsonPath("$.links", hasSize(3)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + user.getId())))
				.andExpect(jsonPath("$.links[1].rel").value(is(UserResourceAsm.IDEAS_REL)))
				.andExpect(jsonPath("$.links[1].href").value(containsString("/api/v1/users/" + user.getId() + "/ideas")))
				.andExpect(jsonPath("$.links[2].rel").value(is(UserResourceAsm.COMMENTS_REL)))
				.andExpect(jsonPath("$.links[2].href").value(containsString("/api/v1/users/" + user.getId() + "/comments")));

		verify(userServiceMock, times(1)).findOne(user.getId());
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenUserNotFound() throws Exception {
		when(userServiceMock.findOne(DEFAULT_USER_ID)).thenThrow(new UserNotFoundException(DEFAULT_USER_ID));

		mockMvc.perform(get("/api/v1/users/{userId}", DEFAULT_USER_ID)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(is(USER_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is("Could not find user with id: 1.")));

		verify(userServiceMock, times(1)).findOne(DEFAULT_USER_ID);
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldReturnValidationErrorsForTooLongEmailAndPassword() throws Exception {
		String invalidUsername = createStringWithLength(MAX_LENGTH_USERNAME + 1);
		String invalidEmail = createEmailWithLength(MAX_LENGTH_EMAIL + 1);
		String invalidPassword = createStringWithLength(MAX_LENGTH_PASSWORD + 1);

		UserResource userResource = TestUserResourceBuilder.aUserResource()
				.withUsername(invalidUsername)
				.withEmail(invalidEmail)
				.withPassword(invalidPassword)
				.build();

		mockMvc.perform(post("/api/v1/users")
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(userResource)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(3)))

						//Spring MVC does not guarantee the ordering of the field errors,
						//i.e. the field errors are returned in random order.
				.andExpect(jsonPath("$[*].logref").value(containsInAnyOrder("password", "email", "username")))
				.andExpect(jsonPath("$[*].message").value(containsInAnyOrder(
						String.format("size must be between %s and %s", User.MIN_LENGTH_EMAIL, User.MAX_LENGTH_EMAIL),
						String.format("size must be between %s and %s", User.MIN_LENGTH_PASSWORD, User.MAX_LENGTH_PASSWORD),
						String.format("size must be between %s and %s", User.MIN_LENGTH_USERNAME, User.MAX_LENGTH_USERNAME)
				)));

		verifyZeroInteractions(userServiceMock);
	}

	@Test
	public void shouldCreateUserAndReturnItWithHttpCode201() throws Exception {
		String username = "username";
		String email = "email@test.com";
		String password = "password";
		UserResource userResource = TestUserResourceBuilder.aUserResource()
				.withUsername(username)
				.withEmail(email)
				.withPassword(password)
				.build();
		User saved = new TestUserBuilder()
				.withId(DEFAULT_USER_ID)
				.withUsername(username)
				.withEmail(email)
				.withPassword(password)
				.withCreationTime(DEFAULT_CREATION_TIME)
				.build();

		when(userServiceMock.save(any(User.class))).thenReturn(saved);

		mockMvc.perform(post("/api/v1/users")
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(userResource))).andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$." + ID).value(is((int) saved.getId())))
				.andExpect(jsonPath("$.username").value(is(saved.getUsername())))
				.andExpect(jsonPath("$.email").value(is(saved.getEmail())))
				.andExpect(jsonPath("$.password").doesNotExist())
				.andExpect(jsonPath("$." + CREATION_TIME).value(is(EXPECTED_USER_CREATION_TIME)))
				.andExpect(jsonPath("$.links", hasSize(3)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + saved.getId())))
				.andExpect(jsonPath("$.links[1].rel").value(is(UserResourceAsm.IDEAS_REL)))
				.andExpect(jsonPath("$.links[1].href").value(containsString("/api/v1/users/" + saved.getId() + "/ideas")))
				.andExpect(jsonPath("$.links[2].rel").value(is(UserResourceAsm.COMMENTS_REL)))
				.andExpect(jsonPath("$.links[2].href").value(containsString("/api/v1/users/" + saved.getId() + "/comments")));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userServiceMock, times(1)).save(userCaptor.capture());
		verifyNoMoreInteractions(userServiceMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(userResource.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(userResource.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(userResource.getPassword());
	}

	@Test
	public void shouldDeleteUserAndReturnHttpCode200() throws Exception {
		User deleted = aUser().build();

		when(userServiceMock.deleteById(deleted.getId())).thenReturn(deleted);

		mockMvc.perform(delete("/api/v1/users/{userId}", deleted.getId())
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().string(is(EMPTY)));

		verify(userServiceMock, times(1)).deleteById(deleted.getId());
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenDeleteUserWhichDoesNotExist() throws Exception {
		long userId = 3L;
		when(userServiceMock.deleteById(userId)).thenThrow(new UserNotFoundException(userId));

		mockMvc.perform(delete("/api/v1/users/{userId}", userId)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(is(USER_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is("Could not find user with id: " + userId + ".")))
				.andExpect(jsonPath("$[0].links", hasSize(0)));

		verify(userServiceMock, times(1)).deleteById(userId);
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldUpdateUserAndReturnItWithHttpCode200() throws Exception {
		long userId = 2L;
		String newUsername = "new_username";
		String newEmail = "new_email@test.com";
		String newPassword = "new_password";
		UserResource source = TestUserResourceBuilder.aUserResource()
				.withUsername(newUsername)
				.withEmail(newEmail)
				.withPassword(newPassword)
				.build();
		User updated = new TestUserBuilder()
				.withId(userId)
				.withUsername(newUsername)
				.withEmail(newEmail)
				.withPassword(newPassword)
				.withCreationTime(DEFAULT_CREATION_TIME)
				.build();

		when(userServiceMock.update(eq(userId), any(User.class))).thenReturn(updated);

		mockMvc.perform(put("/api/v1/users/{userId}", userId)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(source)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$." + ID).value(is((int) updated.getId())))
				.andExpect(jsonPath("$.username").value(is(updated.getUsername())))
				.andExpect(jsonPath("$.email").value(is(updated.getEmail())))
				.andExpect(jsonPath("$.password").doesNotExist())
				.andExpect(jsonPath("$." + CREATION_TIME).value(is(EXPECTED_USER_CREATION_TIME)))
				.andExpect(jsonPath("$.links", hasSize(3)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + updated.getId())))
				.andExpect(jsonPath("$.links[1].rel").value(is(UserResourceAsm.IDEAS_REL)))
				.andExpect(jsonPath("$.links[1].href").value(containsString("/api/v1/users/" + updated.getId() + "/ideas")))
				.andExpect(jsonPath("$.links[2].rel").value(is(UserResourceAsm.COMMENTS_REL)))
				.andExpect(jsonPath("$.links[2].href").value(containsString("/api/v1/users/" + updated.getId() + "/comments")));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userServiceMock, times(1)).update(eq(userId), userCaptor.capture());
		verifyNoMoreInteractions(userServiceMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(source.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(source.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(source.getPassword());
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenUpdateUserWhichDoesNotExist() throws Exception {
		long userId = 3L;
		UserResource source = TestUserResourceBuilder.aUserResource()
				.withUsername("new_username")
				.withEmail("new_email@test.com")
				.withPassword("new_password")
				.build();

		when(userServiceMock.update(eq(userId), any(User.class))).thenThrow(new UserNotFoundException(userId));

		mockMvc.perform(put("/api/v1/users/{userId}", userId)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(source)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(is(USER_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is("Could not find user with id: " + userId + ".")))
				.andExpect(jsonPath("$[0].links", hasSize(0)));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userServiceMock, times(1)).update(eq(userId), userCaptor.capture());
		verifyNoMoreInteractions(userServiceMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(source.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(source.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(source.getPassword());
	}

	@Test
	public void shouldReturnAllFoundIdeasForGivenUser() throws Exception {
		long userId = 1L;
		User author = aUser().build();
		Tag tag = aTag().build();
		Idea idea = TestIdeaBuilder.anIdea()
				.withAuthor(author)
				.withTags(Lists.newArrayList(tag))
				.build();

		when(ideaServiceMock.findIdeasByUserId(userId)).thenReturn(asList(idea));

		mockMvc.perform(get("/api/v1/users/{userId}/ideas", userId)
				.accept(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0]" + ID).value(is((int) idea.getId())))
				.andExpect(jsonPath("$[0].title").value(is(idea.getTitle())))
				.andExpect(jsonPath("$[0].description").value(is(idea.getDescription())))
				.andExpect(jsonPath("$[0].rating").value(is(idea.getRating())))
				.andExpect(jsonPath("$[0]." + CREATION_TIME).value(is(EXPECTED_IDEA_CREATION_TIME)))
				.andExpect(jsonPath("$[0]." + MODIFICATION_TIME).value(is(EXPECTED_IDEA_MODIFICATION_TIME)))
				.andExpect(jsonPath("$[0].author").doesNotExist())
				.andExpect(jsonPath("$[0].links", hasSize(2)))
				.andExpect(jsonPath("$[0].links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/ideas/" + idea.getId())))
				.andExpect(jsonPath("$[0].links[1].rel").value(is(IdeaResourceAsm.REL_AUTHOR)))
				.andExpect(jsonPath("$[0].links[1].href").value(containsString("/api/v1/users/" + author.getId())))
				.andExpect(jsonPath("$[0].tags", hasSize(1)))
				.andExpect(jsonPath("$[0].tags[0]." + ID).value(is((int) idea.getRelatedTags().get(0).getId())))
				.andExpect(jsonPath("$[0].tags[0].name").value(is(idea.getRelatedTags().get(0).getName())))
				.andExpect(jsonPath("$[0].tags[0].links", empty()));

		verify(ideaServiceMock, times(1)).findIdeasByUserId(userId);
		verifyNoMoreInteractions(ideaServiceMock);
	}

	@Test
	public void shouldReturnAllFoundCommentsForGivenUser() throws Exception {
		long userId = 1L;
		Comment comment = TestCommentBuilder.aComment().build();
		User user = TestUserBuilder.aUser().build();
		Idea idea = TestIdeaBuilder.anIdea().build();
		comment.setAuthor(user);
		comment.setSubject(idea);
		user.addComment(comment);
		idea.addComment(comment);

		when(commentServiceMock.findCommentsByUserId(userId)).thenReturn(Lists.newArrayList(comment));

		mockMvc.perform(get("/api/v1/users/{userId}/comments", userId)
				.accept(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0]." + ID).value(is((int) comment.getId())))
				.andExpect(jsonPath("$[0].body").value(is(comment.getBody())))
				.andExpect(jsonPath("$[0].rating").value(is(comment.getRating())))
				.andExpect(jsonPath("$[0]." + CREATION_TIME).value(is(EXPECTED_COMMENT_CREATION_TIME)))
				.andExpect(jsonPath("$[0]." + MODIFICATION_TIME).value(is(EXPECTED_COMMENT_MODIFICATION_TIME)))
				.andExpect(jsonPath("$[0].links", empty()));

		verify(commentServiceMock, times(1)).findCommentsByUserId(userId);
		verifyNoMoreInteractions(commentServiceMock);
	}
}
