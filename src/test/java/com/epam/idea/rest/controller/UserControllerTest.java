package com.epam.idea.rest.controller;

import com.epam.idea.builder.model.TestCommentBuilder;
import com.epam.idea.builder.model.TestIdeaBuilder;
import com.epam.idea.builder.model.TestUserBuilder;
import com.epam.idea.builder.resource.TestIdeaResourceBuilder;
import com.epam.idea.builder.resource.TestUserResourceBuilder;
import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.User;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.UserNotFoundException;
import com.epam.idea.core.util.State;
import com.epam.idea.rest.annotation.WebAppUnitTest;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.UserResource;
import com.epam.idea.rest.resource.asm.IdeaResourceAsm;
import com.epam.idea.rest.resource.asm.TagResourceAsm;
import com.epam.idea.rest.resource.asm.UserResourceAsm;
//import com.google.common.base.Service.State;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.epam.idea.builder.model.TestTagBuilder.aTag;
import static com.epam.idea.builder.model.TestUserBuilder.DEFAULT_CREATION_TIME;
import static com.epam.idea.builder.model.TestUserBuilder.DEFAULT_EMAIL;
import static com.epam.idea.builder.model.TestUserBuilder.DEFAULT_USER_ID;
import static com.epam.idea.builder.model.TestUserBuilder.aUser;
import static com.epam.idea.core.model.User.MAX_LENGTH_EMAIL;
import static com.epam.idea.core.model.User.MAX_LENGTH_PASSWORD;
import static com.epam.idea.core.model.User.MAX_LENGTH_USERNAME;
import static com.epam.idea.rest.controller.RestErrorHandler.USER_NOT_FOUND_LOGREF;
import static com.epam.idea.rest.resource.support.JsonPropertyName.CREATION_TIME;
import static com.epam.idea.rest.resource.support.JsonPropertyName.ID;
import static com.epam.idea.rest.resource.support.JsonPropertyName.MODIFICATION_TIME;
import static com.epam.idea.util.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.util.TestUtils.EMPTY;
import static com.epam.idea.util.TestUtils.convertObjectToJsonBytes;
import static com.epam.idea.util.TestUtils.createEmailWithLength;
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
public class UserControllerTest {

    public static final String EXPECTED_USER_CREATION_TIME = "2015-01-12T00:00Z";
    public static final String EXPECTED_IDEA_CREATION_TIME = "2014-02-12T10:00Z";
    public static final String EXPECTED_IDEA_MODIFICATION_TIME = "2014-10-05T00:00Z";
    public static final String EXPECTED_COMMENT_CREATION_TIME = "2014-05-05T00:00:00.000+0000";
    public static final String EXPECTED_COMMENT_MODIFICATION_TIME = "2014-07-08T00:00:00.000+0000";

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userServiceMock;

    @Autowired
    private IdeaService ideaServiceMock;

    @Autowired
    private CommentService commentServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final Pageable defaultPageRequest = PageRequest.of(0, 500);

    @Before
    public void setUp() throws Exception {
        Mockito.reset(this.userServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void shouldReturnAllFoundUsers() throws Exception {
        final User first = aUser().build();

        when(this.userServiceMock.findAll()).thenReturn(Lists.newArrayList(first));

        this.mockMvc.perform(get("/api/v1/users").accept(APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andDo(print()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0]." + ID).value((first.getId())))
                .andExpect(jsonPath("$[0].username").value(first.getUsername()))
                .andExpect(jsonPath("$[0].email").value(first.getEmail()))
                .andExpect(jsonPath("$[0].password").doesNotExist())
                .andExpect(jsonPath("$[0]." + CREATION_TIME).value(EXPECTED_USER_CREATION_TIME))
                .andExpect(jsonPath("$[0].links", hasSize(3)))
                .andExpect(jsonPath("$[0].links[0].rel").value("self"))
                .andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/users/" + first.getId())))
                .andExpect(jsonPath("$[0].links[1].rel").value(UserResourceAsm.IDEAS_REL))
                .andExpect(jsonPath("$[0].links[1].href")
                        .value(containsString("/api/v1/users/" + first.getId() + "/ideas")))
                .andExpect(jsonPath("$[0].links[2].rel").value(UserResourceAsm.COMMENTS_REL))
                .andExpect(jsonPath("$[0].links[2].href")
                        .value(containsString("/api/v1/users/" + first.getId() + "/comments")));

        verify(this.userServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(this.userServiceMock);
    }

    @Test
    public void shouldReturnFoundUserWithHttpCode200() throws Exception {
        final User user = aUser().build();

        when(this.userServiceMock.findOne(user.getId())).thenReturn(user);

        this.mockMvc.perform(get("/api/v1/users/{userId}", DEFAULT_USER_ID).accept(APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$." + ID).value((user.getId())))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail())).andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$." + CREATION_TIME).value(EXPECTED_USER_CREATION_TIME))
                .andExpect(jsonPath("$.links", hasSize(3))).andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + user.getId())))
                .andExpect(jsonPath("$.links[1].rel").value(UserResourceAsm.IDEAS_REL))
                .andExpect(
                        jsonPath("$.links[1].href").value(containsString("/api/v1/users/" + user.getId() + "/ideas")))
                .andExpect(jsonPath("$.links[2].rel").value(UserResourceAsm.COMMENTS_REL))
                .andExpect(jsonPath("$.links[2].href")
                        .value(containsString("/api/v1/users/" + user.getId() + "/comments")));

        verify(this.userServiceMock, times(1)).findOne(user.getId());
        verifyNoMoreInteractions(this.userServiceMock);
    }

    @Test
    public void shouldReturnErrorWithHttpStatus404WhenUserNotFound() throws Exception {
        when(this.userServiceMock.findOne(DEFAULT_USER_ID)).thenThrow(new UserNotFoundException(DEFAULT_USER_ID));

        this.mockMvc.perform(get("/api/v1/users/{userId}", DEFAULT_USER_ID).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content[0].logref").value(USER_NOT_FOUND_LOGREF))
                .andExpect(jsonPath("$.content[0].message").value("Could not find user with id: 1."));

        verify(this.userServiceMock, times(1)).findOne(DEFAULT_USER_ID);
        verifyNoMoreInteractions(this.userServiceMock);
    }

    @Test
    public void shouldReturnValidationErrorsForTooLongEmailAndPassword() throws Exception {
        final String invalidUsername = createStringWithLength(MAX_LENGTH_USERNAME + 1);
        final String invalidEmail = createEmailWithLength(MAX_LENGTH_EMAIL + 1);
        final String invalidPassword = createStringWithLength(MAX_LENGTH_PASSWORD + 1);

        final UserResource userResource = TestUserResourceBuilder.aUserResource().withUsername(invalidUsername)
                .withEmail(invalidEmail).withPassword(invalidPassword).build();

        this.mockMvc
                .perform(post("/api/v1/users").contentType(APPLICATION_JSON_UTF8).accept(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(userResource)))
                .andExpect(status().isBadRequest()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                //.andExpect(jsonPath("$", hasSize(3)))

                // Spring MVC does not guarantee the ordering of the field
                // errors,
                // i.e. the field errors are returned in random order.
                //.andExpect(jsonPath("$.content[*].logref").value(containsInAnyOrder("password", "email", "username")))
                .andExpect(jsonPath("$.content[*].message").value(containsInAnyOrder(
                        String.format("size must be between %s and %s", User.MIN_LENGTH_EMAIL, User.MAX_LENGTH_EMAIL),
                        String.format("size must be between %s and %s", User.MIN_LENGTH_PASSWORD,
                                User.MAX_LENGTH_PASSWORD),
                        String.format("size must be between %s and %s", User.MIN_LENGTH_USERNAME,
                                User.MAX_LENGTH_USERNAME))));

        verifyZeroInteractions(this.userServiceMock);
    }

    @Test
    public void shouldCreateUserAndReturnItWithHttpCode201() throws Exception {
        final String username = "username";
        final String email = "email@test.com";
        final String password = "password";
        final UserResource userResource = TestUserResourceBuilder.aUserResource().withUsername(username)
                .withEmail(email).withPassword(password).build();
        final User saved = new TestUserBuilder().withId(DEFAULT_USER_ID).withUsername(username).withEmail(email)
                .withPassword(password).withCreationTime(DEFAULT_CREATION_TIME).build();

        when(this.userServiceMock.save(any(User.class))).thenReturn(saved);

        this.mockMvc
                .perform(post("/api/v1/users").contentType(APPLICATION_JSON_UTF8).accept(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(userResource)))
                .andDo(print()).andExpect(status().isCreated()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$." + ID).value(saved.getId()))
                .andExpect(jsonPath("$.username").value(saved.getUsername()))
                .andExpect(jsonPath("$.email").value(saved.getEmail())).andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$." + CREATION_TIME).value(EXPECTED_USER_CREATION_TIME))
                .andExpect(jsonPath("$.links", hasSize(3))).andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + saved.getId())))
                .andExpect(jsonPath("$.links[1].rel").value(UserResourceAsm.IDEAS_REL))
                .andExpect(
                        jsonPath("$.links[1].href").value(containsString("/api/v1/users/" + saved.getId() + "/ideas")))
                .andExpect(jsonPath("$.links[2].rel").value(UserResourceAsm.COMMENTS_REL))
                .andExpect(jsonPath("$.links[2].href")
                        .value(containsString("/api/v1/users/" + saved.getId() + "/comments")));

        final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(this.userServiceMock, times(1)).save(userCaptor.capture());
        verifyNoMoreInteractions(this.userServiceMock);

        final User userArgument = userCaptor.getValue();
        assertThat(userArgument.getUsername()).isEqualTo(userResource.getUsername());
        assertThat(userArgument.getEmail()).isEqualTo(userResource.getEmail());
        assertThat(userArgument.getPassword()).isEqualTo(userResource.getPassword());
    }

    @Test
    public void shouldDeleteUserAndReturnHttpCode200() throws Exception {
        final User deleted = aUser().build();

        when(this.userServiceMock.deleteById(deleted.getId())).thenReturn(deleted);

        this.mockMvc.perform(delete("/api/v1/users/{userId}", deleted.getId()).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(content().string(EMPTY));

        verify(this.userServiceMock, times(1)).deleteById(deleted.getId());
        verifyNoMoreInteractions(this.userServiceMock);
    }

    @Test
    public void shouldReturnErrorWithHttpStatus404WhenDeleteUserWhichDoesNotExist() throws Exception {
        final long userId = 3L;
        when(this.userServiceMock.deleteById(userId)).thenThrow(new UserNotFoundException(userId));

        this.mockMvc
                .perform(delete("/api/v1/users/{userId}", userId).contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content[0].logref").value(USER_NOT_FOUND_LOGREF))
                .andExpect(jsonPath("$.content[0]..message").value("Could not find user with id: " + userId + "."))
                .andExpect(jsonPath("$.content[0].links", empty()));

        verify(this.userServiceMock, times(1)).deleteById(userId);
        verifyNoMoreInteractions(this.userServiceMock);
    }

    @Test
    public void shouldUpdateUserAndReturnItWithHttpCode200() throws Exception {
        final long userId = 2L;
        final String newUsername = "new_username";
        final String newEmail = "new_email@test.com";
        final String newPassword = "new_password";
        final UserResource source = TestUserResourceBuilder.aUserResource().withUsername(newUsername)
                .withEmail(newEmail).withPassword(newPassword).build();
        final User updated = new TestUserBuilder().withId(userId).withUsername(newUsername).withEmail(newEmail)
                .withPassword(newPassword).withCreationTime(DEFAULT_CREATION_TIME).build();

        when(this.userServiceMock.update(eq(userId), any(User.class))).thenReturn(updated);

        this.mockMvc
                .perform(put("/api/v1/users/{userId}", userId).contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(source)))
                .andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$." + ID).value(updated.getId()))
                .andExpect(jsonPath("$.username").value(updated.getUsername()))
                .andExpect(jsonPath("$.email").value(updated.getEmail()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$." + CREATION_TIME).value(EXPECTED_USER_CREATION_TIME))
                .andExpect(jsonPath("$.links", hasSize(3))).andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + updated.getId())))
                .andExpect(jsonPath("$.links[1].rel").value(UserResourceAsm.IDEAS_REL))
                .andExpect(jsonPath("$.links[1].href")
                        .value(containsString("/api/v1/users/" + updated.getId() + "/ideas")))
                .andExpect(jsonPath("$.links[2].rel").value(UserResourceAsm.COMMENTS_REL))
                .andExpect(jsonPath("$.links[2].href")
                        .value(containsString("/api/v1/users/" + updated.getId() + "/comments")));

        final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(this.userServiceMock, times(1)).update(eq(userId), userCaptor.capture());
        verifyNoMoreInteractions(this.userServiceMock);

        final User userArgument = userCaptor.getValue();
        assertThat(userArgument.getUsername()).isEqualTo(source.getUsername());
        assertThat(userArgument.getEmail()).isEqualTo(source.getEmail());
        assertThat(userArgument.getPassword()).isEqualTo(source.getPassword());
    }

    @Test
    public void shouldReturnErrorWithHttpStatus404WhenUpdateUserWhichDoesNotExist() throws Exception {
        final long userId = 3L;
        final UserResource source = TestUserResourceBuilder.aUserResource().withUsername("new_username")
                .withEmail("new_email@test.com").withPassword("new_password").build();

        when(this.userServiceMock.update(eq(userId), any(User.class))).thenThrow(new UserNotFoundException(userId));

        this.mockMvc
                .perform(put("/api/v1/users/{userId}", userId).contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(source)))
                .andExpect(status().isNotFound()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content[0].logref").value(USER_NOT_FOUND_LOGREF))
                .andExpect(jsonPath("$.content[0].message").value("Could not find user with id: " + userId + "."))
                .andExpect(jsonPath("$.content[0].links", hasSize(0)));

        final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(this.userServiceMock, times(1)).update(eq(userId), userCaptor.capture());
        verifyNoMoreInteractions(this.userServiceMock);

        final User userArgument = userCaptor.getValue();
        assertThat(userArgument.getUsername()).isEqualTo(source.getUsername());
        assertThat(userArgument.getEmail()).isEqualTo(source.getEmail());
        assertThat(userArgument.getPassword()).isEqualTo(source.getPassword());
    }

    @Test
    public void shouldReturnAllFoundIdeasForGivenUser() throws Exception {
        final long userId = 1L;
        final User author = aUser().build();
        final Tag tag = aTag().build();
        final Idea idea = TestIdeaBuilder.anIdea().withAuthor(author).withTags(Lists.newArrayList(tag)).build();

        when(this.ideaServiceMock.findAllByUserId(defaultPageRequest, userId)).thenReturn(Lists.newArrayList(idea));

        this.mockMvc.perform(get("/api/v1/users/{userId}/ideas", userId).accept(APPLICATION_JSON_UTF8)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0]" + ID).value(idea.getId()))
                .andExpect(jsonPath("$[0].title").value(idea.getTitle()))
                .andExpect(jsonPath("$[0].description").value(idea.getDescription()))
                .andExpect(jsonPath("$[0].rating").value(idea.getRating()))
                .andExpect(jsonPath("$[0]." + CREATION_TIME).value(EXPECTED_IDEA_CREATION_TIME))
                .andExpect(jsonPath("$[0]." + MODIFICATION_TIME).value(EXPECTED_IDEA_MODIFICATION_TIME))
                // .andExpect(jsonPath("$[0].author").doesNotExist())
                .andExpect(jsonPath("$[0].links", hasSize(3)))
                .andExpect(jsonPath("$[0].links[0].rel").value("self"))
                .andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/ideas/" + idea.getId())))
                .andExpect(jsonPath("$[0].links[1].rel").value(IdeaResourceAsm.REL_AUTHOR))
                .andExpect(jsonPath("$[0].links[1].href").value(containsString("/api/v1/users/" + author.getId())))
                .andExpect(jsonPath("$[0].tags", hasSize(1)))
                .andExpect(jsonPath("$[0].tags[0]." + ID).value(idea.getRelatedTags().get(0).getId()))
                .andExpect(jsonPath("$[0].tags[0].name").value(idea.getRelatedTags().get(0).getName()))
                .andExpect(jsonPath("$[0].tags[0].links", hasSize(2)))
                .andExpect(jsonPath("$[0].tags[0].links[0].rel").value("self"))
                .andExpect(jsonPath("$[0].tags[0].links[0].href").value(containsString("/api/v1/tags/" + tag.getId())))
                .andExpect(jsonPath("$[0].tags[0].links[1].rel").value(TagResourceAsm.IDEAS_REL))
                .andExpect(jsonPath("$[0].tags[0].links[1].href")
                        .value(containsString("/api/v1/tags/" + idea.getRelatedTags().get(0).getId() + "/ideas")));

        verify(this.ideaServiceMock, times(1)).findAllByUserId(defaultPageRequest, userId);
        verifyNoMoreInteractions(this.ideaServiceMock);
    }

    @Test
    public void shouldReturnAllFoundCommentsForGivenUser() throws Exception {
        final long userId = 1L;
        final Comment comment = TestCommentBuilder.aComment().build();
        final User user = TestUserBuilder.aUser().build();
        final Idea idea = TestIdeaBuilder.anIdea().build();
        comment.setAuthor(user);
        comment.setSubject(idea);
        user.addComment(comment);
        idea.addComment(comment);

        when(this.commentServiceMock.findCommentsByUserId(userId)).thenReturn(Lists.newArrayList(comment));

        this.mockMvc.perform(get("/api/v1/users/{userId}/comments", userId).accept(APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0]." + ID).value(comment.getId()))
                .andExpect(jsonPath("$[0].body").value(comment.getBody()))
                .andExpect(jsonPath("$[0].rating").value(comment.getRating()))
                .andExpect(jsonPath("$[0]." + CREATION_TIME).value(EXPECTED_COMMENT_CREATION_TIME))
                .andExpect(jsonPath("$[0]." + MODIFICATION_TIME).value(EXPECTED_COMMENT_MODIFICATION_TIME))
                .andExpect(jsonPath("$[0].links", hasSize(3)));

        verify(this.commentServiceMock, times(1)).findCommentsByUserId(userId);
    }

    @Test
    public void shouldCreateIdeaWithAuthorUserAndReturnItWithHttpCode201() throws Exception {
        final long userId = 1L;
        final String username = "username";
        final String email = "email@test.com";
        final String password = "password";
        final UserResource userResource = TestUserResourceBuilder.aUserResource().withUsername(username)
                .withEmail(email).withPassword(password).build();
        final User author = new TestUserBuilder().withId(userId).withUsername(username).withEmail(email)
                .withPassword(password).withCreationTime(DEFAULT_CREATION_TIME).build();

        final long ideaId = 2L;
        final int rating = 3;
        final String title = "title";
        final String description = "description";
        final State state = State.New;
        final IdeaResource ideaResource = TestIdeaResourceBuilder.anIdeaResource().withTitle(title)
                .withDescription(description).withRating(rating).withAuthor(userResource).build();
        final Idea createdIdea = new TestIdeaBuilder().withId(ideaId).withTitle(title).withDescription(description)
                .withAuthor(author).withRating(rating).withState(state).build();

        when(this.ideaServiceMock.saveForUser(any(Long.class), any(Idea.class))).thenReturn(createdIdea);

        this.mockMvc
                .perform(post("/api/v1/users/{userId}/ideas", userId).contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(ideaResource)))
                .andDo(print()).andExpect(status().isCreated()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$." + ID).value(createdIdea.getId()))
                .andExpect(jsonPath("$.title").value(createdIdea.getTitle()))
                .andExpect(jsonPath("$.description").value(createdIdea.getDescription()))
                .andExpect(jsonPath("$.rating").value(createdIdea.getRating()))
                .andExpect(jsonPath("$.links", hasSize(3))).andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/" + createdIdea.getId())))
                .andExpect(jsonPath("$.links[1].rel").value(IdeaResourceAsm.REL_AUTHOR))
                .andExpect(jsonPath("$.links[1].href").value(containsString("/api/v1/users/" + author.getId())))
                .andExpect(jsonPath("$.author." + ID).value(author.getId()))
                .andExpect(jsonPath("$.author.username").value(author.getUsername()))
                .andExpect(jsonPath("$.author.email").value(author.getEmail()))
                .andExpect(jsonPath("$.author.links", hasSize(3)))
                .andExpect(jsonPath("$.author.links[0].rel").value("self"))
                .andExpect(jsonPath("$.author.links[0].href").value(containsString("/api/v1/users/" + author.getId())))
                .andExpect(jsonPath("$.author.links[1].rel").value(UserResourceAsm.IDEAS_REL))
                .andExpect(jsonPath("$.author.links[1].href")
                        .value(containsString("/api/v1/users/" + author.getId() + "/ideas")))
                .andExpect(jsonPath("$.author.links[2].rel").value(UserResourceAsm.COMMENTS_REL))
                .andExpect(jsonPath("$.author.links[2].href")
                        .value(containsString("/api/v1/users/" + author.getId() + "/comments")));

        final ArgumentCaptor<Idea> ideaCaptor = ArgumentCaptor.forClass(Idea.class);
        verify(this.ideaServiceMock, times(1)).saveForUser(eq(userId), ideaCaptor.capture());
        verifyNoMoreInteractions(this.userServiceMock);

        final Idea ideaArgument = ideaCaptor.getValue();
        assertThat(ideaArgument.getDescription()).isEqualTo(ideaResource.getDescription());
        assertThat(ideaArgument.getTitle()).isEqualTo(ideaResource.getTitle());
        assertThat(ideaArgument.getRating()).isEqualTo(ideaResource.getRating());
    }

    @Test
    public void shouldReturnFoundUserByEmailWithHttpCode200() throws Exception {
        final User user = aUser().build();

        when(this.userServiceMock.findRegisteredUserByEmail(user.getEmail())).thenReturn(user);

        this.mockMvc.perform(get("/api/v1/users/{userEmail}/email", DEFAULT_EMAIL).accept(APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$." + ID).value((user.getId())))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail())).andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$." + CREATION_TIME).value(EXPECTED_USER_CREATION_TIME))
                .andExpect(jsonPath("$.links", hasSize(3))).andExpect(jsonPath("$.links[0].rel").value("self"))
                .andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + user.getId())))
                .andExpect(jsonPath("$.links[1].rel").value(UserResourceAsm.IDEAS_REL))
                .andExpect(
                        jsonPath("$.links[1].href").value(containsString("/api/v1/users/" + user.getId() + "/ideas")))
                .andExpect(jsonPath("$.links[2].rel").value(UserResourceAsm.COMMENTS_REL))
                .andExpect(jsonPath("$.links[2].href")
                        .value(containsString("/api/v1/users/" + user.getId() + "/comments")));

        verify(this.userServiceMock, times(1)).findRegisteredUserByEmail(user.getEmail());
        verifyNoMoreInteractions(this.userServiceMock);
    }

    @Test
    public void shouldReturnErrorWithHttpStatus404WhenUserNotFoundByEmail() throws Exception {
        when(this.userServiceMock.findRegisteredUserByEmail(DEFAULT_EMAIL))
                .thenThrow(new UserNotFoundException("This user does not exist"));

        this.mockMvc.perform(get("/api/v1/users/{userEmail}/email", DEFAULT_EMAIL).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content[0].logref").value(USER_NOT_FOUND_LOGREF))
                .andExpect(jsonPath("$.content[0].message").value("This user does not exist"));

        verify(this.userServiceMock, times(1)).findRegisteredUserByEmail(DEFAULT_EMAIL);
        verifyNoMoreInteractions(this.userServiceMock);
    }

    // @Test
    // public void
    // shouldReturnErrorWithHttpStatus404WhenUserNotFoundByEmailAndPassword()
    // throws Exception {
    // LoginRequest loginRequest = new LoginRequest();
    // loginRequest.setEmail(DEFAULT_EMAIL);
    // loginRequest.setPassword(DEFAULT_PASSWORD);
    // when(this.userSessionServiceMock.save(any(LoginRequest.class))).thenThrow(new
    // UserNotFoundException("This user does not exist"));
    //
    // this.mockMvc.perform(post("/api/v1/users/authenticate/")
    // .accept(APPLICATION_JSON_UTF8)
    // .contentType(APPLICATION_JSON_UTF8)
    // .content(convertObjectToJsonBytes(loginRequest)))
    // .andExpect(status().isNotFound())
    // .andExpect(content().contentType(APPLICATION_JSON_UTF8))
    // .andExpect(jsonPath("$", hasSize(1)))
    // .andExpect(jsonPath("$[0].logref").value(USER_NOT_FOUND_LOGREF))
    // .andExpect(jsonPath("$[0].message").value("This user does not exist"));
    //
    // verify(this.userSessionServiceMock,
    // times(1)).save(any(LoginRequest.class));
    // verifyNoMoreInteractions(this.userSessionServiceMock);
    // }
    //
    // @Test
    // public void shouldCreateSessionAndReturnItWithHttpCode201() throws
    // Exception {
    // User user = aUser().build();
    // LoginRequest loginRequest = new LoginRequest();
    // loginRequest.setEmail(DEFAULT_EMAIL);
    // loginRequest.setPassword(DEFAULT_PASSWORD);
    // UserSession userSession = aUserSession().build();
    // userSession.setUser(user);
    // when(this.userSessionServiceMock.save(any(LoginRequest.class))).thenReturn(userSession);
    //
    // this.mockMvc.perform(post("/api/v1/users/authenticate/")
    // .contentType(APPLICATION_JSON_UTF8)
    // .accept(APPLICATION_JSON_UTF8)
    // .content(convertObjectToJsonBytes(loginRequest)))
    // .andDo(print())
    // .andExpect(status().isCreated())
    // .andExpect(content().contentType(APPLICATION_JSON_UTF8))
    // .andExpect(jsonPath("$." + ID).value(DEFAULT_USER_SESSION_ID))
    // .andExpect(jsonPath("$." +
    // CREATION_TIME).value(EXPECTED_USER_SESSION_CREATION_TIME))
    // .andExpect(jsonPath("$.links", hasSize(1)));
    //
    // verify(this.userSessionServiceMock,
    // times(1)).save(any(LoginRequest.class));
    // verifyNoMoreInteractions(this.userSessionServiceMock);
    // }
    //
    // @Test
    // public void shouldDeleteSessionAndReturnHttpCode200() throws Exception {
    // User user = aUser().build();
    // UserSession deleted = aUserSession().build();
    // deleted.setUser(user);
    //
    // when(this.userSessionServiceMock.deleteById(deleted.getSessionId())).thenReturn(deleted);
    //
    // this.mockMvc.perform(delete("/api/v1/users/authenticate/{userId}",
    // deleted.getSessionId())
    // .contentType(APPLICATION_JSON_UTF8))
    // .andExpect(status().isOk())
    // .andExpect(content().string(EMPTY));
    //
    // verify(this.userSessionServiceMock,
    // times(1)).deleteById(deleted.getSessionId());
    // verifyNoMoreInteractions(this.userSessionServiceMock);
    // }
    //
    // @Test
    // public void
    // shouldReturnErrorWithHttpStatus404WhenDeleteSessionWhichDoesNotExist()
    // throws Exception {
    // when(this.userSessionServiceMock.deleteById(DEFAULT_USER_SESSION_ID)).thenThrow(new
    // UserSessionNotFoundException(DEFAULT_USER_SESSION_ID));
    //
    // this.mockMvc.perform(delete("/api/v1/users/authenticate/{userId}",
    // DEFAULT_USER_SESSION_ID)
    // .contentType(APPLICATION_JSON_UTF8)
    // .accept(APPLICATION_JSON_UTF8))
    // .andExpect(status().isNotFound())
    // .andExpect(content().contentType(APPLICATION_JSON_UTF8))
    // .andExpect(jsonPath("$", hasSize(1)))
    // .andExpect(jsonPath("$[0].logref").value(USER_NOT_FOUND_LOGREF))
    // .andExpect(jsonPath("$[0].message").value("Could not find session with
    // id: " + DEFAULT_USER_SESSION_ID + "."))
    // .andExpect(jsonPath("$[0].links", empty()));
    //
    // verify(this.userSessionServiceMock,
    // times(1)).deleteById(DEFAULT_USER_SESSION_ID);
    // verifyNoMoreInteractions(this.userSessionServiceMock);
    // }
    //
    // @Test
    // public void shouldReturnFoundSessionWithHttpCode200() throws Exception {
    // User user = aUser().build();
    // LoginRequest loginRequest = new LoginRequest();
    // loginRequest.setEmail(DEFAULT_EMAIL);
    // loginRequest.setPassword(DEFAULT_PASSWORD);
    // UserSession userSession = aUserSession().build();
    // userSession.setUser(user);
    //
    // when(this.userSessionServiceMock.findOne(userSession.getSessionId())).thenReturn(userSession);
    //
    // this.mockMvc.perform(get("/api/v1/users/authenticate/{sessionId}",
    // DEFAULT_USER_ID)
    // .accept(APPLICATION_JSON_UTF8)).andDo(print())
    // .andExpect(status().isOk())
    // .andExpect(content().contentType(APPLICATION_JSON_UTF8))
    // .andExpect(jsonPath("$." + ID).value(userSession.getSessionId()))
    // .andExpect(jsonPath("$." +
    // CREATION_TIME).value(EXPECTED_USER_SESSION_CREATION_TIME))
    // .andExpect(jsonPath("$.links", hasSize(1)));
    //
    // verify(this.userSessionServiceMock,
    // times(1)).findOne(userSession.getSessionId());
    // verifyNoMoreInteractions(this.userSessionServiceMock);
    // }
    //
    // @Test
    // public void shouldReturnErrorWithHttpStatus404WhenUserSessionNotFound()
    // throws Exception {
    // when(this.userSessionServiceMock.findOne(DEFAULT_USER_SESSION_ID)).thenThrow(new
    // UserSessionNotFoundException(DEFAULT_USER_SESSION_ID));
    //
    // this.mockMvc.perform(get("/api/v1/users/authenticate/{sessionId}",
    // DEFAULT_USER_SESSION_ID)
    // .accept(APPLICATION_JSON_UTF8))
    // .andExpect(status().isNotFound())
    // .andExpect(content().contentType(APPLICATION_JSON_UTF8))
    // .andExpect(jsonPath("$", hasSize(1)))
    // .andExpect(jsonPath("$[0].logref").value(USER_NOT_FOUND_LOGREF))
    // .andExpect(jsonPath("$[0].message").value("Could not find session with
    // id: 1."));
    //
    // verify(this.userSessionServiceMock,
    // times(1)).findOne(DEFAULT_USER_SESSION_ID);
    // verifyNoMoreInteractions(this.userSessionServiceMock);
    // }
}