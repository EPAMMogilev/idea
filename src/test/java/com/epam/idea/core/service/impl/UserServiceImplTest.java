package com.epam.idea.core.service.impl;

import java.util.List;
import java.util.Optional;

import com.epam.idea.core.model.User;
import com.epam.idea.core.model.builders.TestUserBuilder;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepositoryMock;

	@InjectMocks
	private UserService sut = new UserServiceImpl();

	@Before
	public void setUp() throws Exception {
		Mockito.reset(userRepositoryMock);
	}

	@Test
	public void shouldSaveNewUser() throws Exception {
		//Given:
		User userToSave = TestUserBuilder.aUser().build();

		//When:
		sut.save(userToSave);

		//Then:
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepositoryMock, times(1)).save(userCaptor.capture());
		verifyNoMoreInteractions(userRepositoryMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(userToSave.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(userToSave.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(userToSave.getPassword());
	}

	@Test
	public void shouldReturnFoundUser() throws Exception {
		//Given:
		User found = TestUserBuilder.aUser().build();
		when(userRepositoryMock.findOne(eq(found.getId()))).thenReturn(Optional.of(found));

		//When:
		User actual = sut.findOne(found.getId());

		//Then:
		assertThat(actual).isEqualTo(found);
		verify(userRepositoryMock, times(1)).findOne(found.getId());
		verifyNoMoreInteractions(userRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryFindUserWhichDoesNotExist() throws Exception {
		//Given:
		long fakeUserId = 4L;
		when(userRepositoryMock.findOne(eq(fakeUserId))).thenReturn(Optional.empty());

		//When:
		try {
			sut.findOne(fakeUserId);
			fail("UserNotFoundException expected because we try to find the user which does not exist");
		} catch (UserNotFoundException e) {

			//Then:
			verify(userRepositoryMock, times(1)).findOne(fakeUserId);
			verifyNoMoreInteractions(userRepositoryMock);
		}
	}

	@Test
	public void shouldDeleteUserAndReturnIt() throws Exception {
		//Given:
		User deletedUser = TestUserBuilder.aUser().build();
		when(userRepositoryMock.findOne(anyLong())).thenReturn(Optional.of(deletedUser));

		//When:
		User actual = sut.deleteById(deletedUser.getId());

		//Then:
		assertThat(actual).isEqualTo(deletedUser);

		verify(userRepositoryMock, times(1)).findOne(deletedUser.getId());
		verify(userRepositoryMock, times(1)).delete(deletedUser);
		verifyNoMoreInteractions(userRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryDeleteUserWhichDoesNotExist() throws Exception {
		//Given:
		long fakeUserId = 2L;
		when(userRepositoryMock.findOne(eq(fakeUserId))).thenReturn(Optional.empty());

		//When:
		try {
			sut.deleteById(fakeUserId);
			fail("UserNotFoundException expected because we try to delete the user which does not exist");
		} catch (UserNotFoundException e) {

			//Then:
			verify(userRepositoryMock, times(1)).findOne(fakeUserId);
			verifyNoMoreInteractions(userRepositoryMock);
		}
	}

	@Test
	public void shouldUpdateUserAndReturnIt() throws Exception {
		//Given:
		User source = new TestUserBuilder()
				.withUsername("new_username")
				.withEmail("new_email@test.com")
				.withPassword("new_password")
				.build();
		User target = new TestUserBuilder()
				.withId(1L)
				.withUsername("username")
				.withEmail("email@test.com")
				.withPassword("password")
				.build();
		when(userRepositoryMock.findOne(eq(target.getId()))).thenReturn(Optional.of(target));

		//When:
		User actual = sut.update(target.getId(), source);

		//Then:
		assertThat(actual.getId()).isEqualTo(target.getId());
		assertThat(actual.getUsername()).isEqualTo(source.getUsername());
		assertThat(actual.getEmail()).isEqualTo(source.getEmail());
		assertThat(actual.getPassword()).isEqualTo(source.getPassword());
		verify(userRepositoryMock, times(1)).findOne(target.getId());
		verifyNoMoreInteractions(userRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryUpdateUserWhichDoesNotExist() throws Exception {
		//Given:
		long fakeUserId = 3L;
		User newUser = new TestUserBuilder()
				.withUsername("new_username")
				.withEmail("new_email@test.com")
				.withPassword("new_password")
				.build();
		when(userRepositoryMock.findOne(eq(fakeUserId))).thenReturn(Optional.empty());

		//When
		try {
			sut.update(fakeUserId, newUser);
			fail("UserNotFoundException expected because we try to update the user which does not exist");
		} catch (UserNotFoundException ex) {

			//Then:
			verify(userRepositoryMock, times(1)).findOne(fakeUserId);
			verifyNoMoreInteractions(userRepositoryMock);
		}
	}

	@Test
	public void shouldReturnListOfAllUsers() throws Exception {
		//Given:
		List<User> users = asList(TestUserBuilder.aUser().build(), TestUserBuilder.anAdmin().build());
		when(userRepositoryMock.findAll()).thenReturn(users);

		//When:
		List<User> actual = sut.findAll();

		//Then:
		assertThat(actual).isEqualTo(users);
		verify(userRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(userRepositoryMock);
	}
}
