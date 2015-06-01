package com.epam.idea.core.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.epam.idea.builder.model.TestUserBuilder;
import com.epam.idea.builder.model.TestUserSessionBuilder;
import com.epam.idea.core.model.User;
import com.epam.idea.core.model.UserSession;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.repository.UserSessionRepository;
import com.epam.idea.core.security.LoginRequest;
import com.epam.idea.core.security.PasswordHasher;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.UserSessionService;
import com.epam.idea.core.service.exception.UserNotFoundException;
import com.epam.idea.core.service.exception.UserSessionNotFoundException;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static com.epam.idea.builder.model.TestUserBuilder.DEFAULT_EMAIL;
import static com.epam.idea.builder.model.TestUserBuilder.DEFAULT_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UserSessionServiceImplTest {

	@Mock
	private UserSessionRepository userSessionRepositoryMock;

	@Mock
	private UserRepository userRepositoryMock;

	@InjectMocks
	private UserSessionService sut = new UserSessionServiceImpl();

	@Before
	public void setUp() throws Exception {
		Mockito.reset(this.userSessionRepositoryMock);
	}

	@Test
	public void shouldSaveNewSession() throws Exception {
		//Given:
		User user = TestUserBuilder.aUser().build();
		UserSession userSession = TestUserSessionBuilder.aUserSession().withAuthor(user).build();

		userSession.prePersist();
		userSession.setSessionId(userSession.getSessionId());
		userSession.setCreationTime(userSession.getCreationTime());

		//When:
		this.sut.save(userSession);

		//Then:
		ArgumentCaptor<UserSession> userSessionCaptor = ArgumentCaptor.forClass(UserSession.class);
		verify(this.userSessionRepositoryMock, times(1)).save(userSessionCaptor.capture());
		verifyNoMoreInteractions(this.userSessionRepositoryMock);

		UserSession userArgument = userSessionCaptor.getValue();
		assertThat(userArgument.getCreationTime()).isEqualTo(userSession.getCreationTime());
		assertThat(userArgument.getSessionId()).isEqualTo(userSession.getSessionId());
		assertThat(userArgument.getUser()).isEqualTo(userSession.getUser());
	}

	@Test
	public void shouldReturnFoundSession() throws Exception {
		//Given:
		User user = TestUserBuilder.aUser().build();
		UserSession found = TestUserSessionBuilder.aUserSession().withAuthor(user).build();

		given(this.userSessionRepositoryMock.findOne(eq(found.getSessionId()))).willReturn(Optional.of(found));

		//When:
		UserSession actual = this.sut.findOne(found.getSessionId());

		//Then:
		assertThat(actual).isEqualTo(found);
		verify(this.userSessionRepositoryMock, times(1)).findOne(found.getSessionId());
		verifyNoMoreInteractions(this.userSessionRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryFindUserWhichDoesNotExist() throws Exception {
		//Given:
		String fakeSessionId = "13";
		given(this.userSessionRepositoryMock.findOne(eq(fakeSessionId))).willReturn(Optional.empty());

		//When:
		try {
			this.sut.findOne(fakeSessionId);
			fail("UserSessionNotFoundException expected because we try to find the session which does not exist");

			//Then:
		} catch (UserSessionNotFoundException e) {
			verify(this.userSessionRepositoryMock, times(1)).findOne(fakeSessionId);
			verifyNoMoreInteractions(this.userSessionRepositoryMock);
		}
	}

	@Test
	public void shouldDeleteUserAndReturnIt() throws Exception {
		//Given:
		User user = TestUserBuilder.aUser().build();
		UserSession deletedUserSession = TestUserSessionBuilder.aUserSession().withAuthor(user).build();
		given(this.userSessionRepositoryMock.findOne(anyString())).willReturn(Optional.of(deletedUserSession));

		//When:
		UserSession actual = this.sut.deleteById(deletedUserSession.getSessionId());

		//Then:
		assertThat(actual).isEqualTo(deletedUserSession);
		verify(this.userSessionRepositoryMock, times(1)).findOne(deletedUserSession.getSessionId());
		verify(this.userSessionRepositoryMock, times(1)).delete(deletedUserSession);
		verifyNoMoreInteractions(this.userSessionRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryDeleteUserWhichDoesNotExist() throws Exception {
		//Given:
		String fakeSessionId = "13";
		given(this.userSessionRepositoryMock.findOne(eq(fakeSessionId))).willReturn(Optional.empty());

		//When:
		try {
			this.sut.deleteById(fakeSessionId);
			fail("UserSessionNotFoundException expected because we try to delete the session which does not exist");

			//Then:
		} catch (UserSessionNotFoundException e) {
			verify(this.userSessionRepositoryMock, times(1)).findOne(fakeSessionId);
			verifyNoMoreInteractions(this.userSessionRepositoryMock);
		}
	}

	@Test
	public void shouldSaveNewSessionByLoginRequest() throws Exception {
		//Given:
		User user = TestUserBuilder.aUser().build();
		UserSession userSession = TestUserSessionBuilder.aUserSession().withAuthor(user).build();
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(DEFAULT_EMAIL);
		loginRequest.setPassword(DEFAULT_PASSWORD);
		given(this.userRepositoryMock.findUserByEmailAndPassword(eq(loginRequest.getEmail()), eq(PasswordHasher.md5(loginRequest.getPassword())))).willReturn(Optional.of(user));

		//When:
		this.sut.save(loginRequest);

		//Then:
		ArgumentCaptor<UserSession> userSessionCaptor = ArgumentCaptor.forClass(UserSession.class);
		verify(this.userSessionRepositoryMock, times(1)).save(userSessionCaptor.capture());
		verifyNoMoreInteractions(this.userSessionRepositoryMock);

		UserSession userArgument = userSessionCaptor.getValue();
		assertThat(userArgument.getUser()).isEqualTo(userSession.getUser());
	}
}
