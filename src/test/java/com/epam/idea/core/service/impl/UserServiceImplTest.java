package com.epam.idea.core.service.impl;

import java.util.List;
import java.util.Optional;

import com.epam.idea.builder.model.TestUserBuilder;
import com.epam.idea.core.model.SocialMediaService;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.DuplicateUserException;
import com.epam.idea.core.service.exception.UserNotFoundException;
import com.epam.idea.core.social.util.PasswordHasher;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepositoryMock;

	@InjectMocks
	private UserService sut = new UserServiceImpl();

	@Before
	public void setUp() throws Exception {
		Mockito.reset(this.userRepositoryMock);
	}

	@Test
	public void shouldSaveNewUser() throws Exception {
		//Given:
		User userToSave = TestUserBuilder.aUser().build();

		//When:
		this.sut.save(userToSave);

		//Then:
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(this.userRepositoryMock, times(1)).save(userCaptor.capture());
		verifyNoMoreInteractions(this.userRepositoryMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(userToSave.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(userToSave.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(userToSave.getPassword());
	}

	@Test
	public void shouldReturnFoundUser() throws Exception {
		//Given:
		User found = TestUserBuilder.aUser().build();
		given(this.userRepositoryMock.findOne(eq(found.getId()))).willReturn(Optional.of(found));

		//When:
		User actual = this.sut.findOne(found.getId());

		//Then:
		assertThat(actual).isEqualTo(found);
		verify(this.userRepositoryMock, times(1)).findOne(found.getId());
		verifyNoMoreInteractions(this.userRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryFindUserWhichDoesNotExist() throws Exception {
		//Given:
		long fakeUserId = 4L;
		given(this.userRepositoryMock.findOne(eq(fakeUserId))).willReturn(Optional.empty());

		//When:
		try {
			this.sut.findOne(fakeUserId);
			fail("UserNotFoundException expected because we try to find the user which does not exist");

			//Then:
		} catch (UserNotFoundException e) {
			verify(this.userRepositoryMock, times(1)).findOne(fakeUserId);
			verifyNoMoreInteractions(this.userRepositoryMock);
		}
	}

	@Test
	public void shouldDeleteUseByIdrAndReturnIt() throws Exception {
		//Given:
		User deletedUser = TestUserBuilder.aUser().build();
		given(this.userRepositoryMock.findOne(anyLong())).willReturn(Optional.of(deletedUser));

		//When:
		User actual = this.sut.deleteById(deletedUser.getId());

		//Then:
		assertThat(actual).isEqualTo(deletedUser);
		verify(this.userRepositoryMock, times(1)).findOne(deletedUser.getId());
		verify(this.userRepositoryMock, times(1)).delete(deletedUser);
		verifyNoMoreInteractions(this.userRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryDeleteUserByIdWhichDoesNotExist() throws Exception {
		//Given:
		long fakeUserId = 2L;
		given(this.userRepositoryMock.findOne(eq(fakeUserId))).willReturn(Optional.empty());

		//When:
		try {
			this.sut.deleteById(fakeUserId);
			fail("UserNotFoundException expected because we try to delete the user which does not exist");

			//Then:
		} catch (UserNotFoundException e) {
			verify(this.userRepositoryMock, times(1)).findOne(fakeUserId);
			verifyNoMoreInteractions(this.userRepositoryMock);
		}
	}

	@Test
	public void shouldDeleteUser() throws Exception {
		//Given:
		User deletedUser = TestUserBuilder.aUser().build();

		//When:
		this.sut.delete(deletedUser);

		//Then:
		verify(this.userRepositoryMock, times(1)).delete(deletedUser);
		verifyNoMoreInteractions(this.userRepositoryMock);
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
		given(this.userRepositoryMock.findOne(eq(target.getId()))).willReturn(Optional.of(target));

		//When:
		User actual = this.sut.update(target.getId(), source);

		//Then:
		assertThat(actual.getId()).isEqualTo(target.getId());
		assertThat(actual.getUsername()).isEqualTo(source.getUsername());
		assertThat(actual.getEmail()).isEqualTo(source.getEmail());
		assertThat(actual.getPassword()).isEqualTo(PasswordHasher.md5(source.getPassword()));
		verify(this.userRepositoryMock, times(1)).findOne(target.getId());
		verifyNoMoreInteractions(this.userRepositoryMock);
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
		given(this.userRepositoryMock.findOne(eq(fakeUserId))).willReturn(Optional.empty());

		//When
		try {
			this.sut.update(fakeUserId, newUser);
			fail("UserNotFoundException expected because we try to update the user which does not exist");

			//Then:
		} catch (UserNotFoundException ex) {
			verify(this.userRepositoryMock, times(1)).findOne(fakeUserId);
			verifyNoMoreInteractions(this.userRepositoryMock);
		}
	}

	@Test
	public void shouldReturnListOfAllUsers() throws Exception {
		//Given:
		List<User> users = Lists.newArrayList(
				TestUserBuilder.aUser().build(),
				TestUserBuilder.anAdmin().build()
		);
		given(this.userRepositoryMock.findAll()).willReturn(users);

		//When:
		List<User> actual = this.sut.findAll();

		//Then:
		assertThat(actual).isEqualTo(users);
		verify(this.userRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(this.userRepositoryMock);
	}

	@Test
	public void shouldReturnFoundUserByEmailAndPassword() throws Exception {
		//Given:
		User found = TestUserBuilder.aUser().build();
		given(this.userRepositoryMock.findUserByEmailAndPassword(eq(found.getEmail()),eq(found.getPassword()))).willReturn(Optional.of(found));

		//When:
		User actual = this.sut.findUserByEmailAndPassword(found.getEmail(), found.getPassword());

		//Then:
		assertThat(actual).isEqualTo(found);
		verify(this.userRepositoryMock, times(1)).findUserByEmailAndPassword(found.getEmail(), found.getPassword());
		verifyNoMoreInteractions(this.userRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryFindUserByEmailAndPasswordWhichDoesNotExist() throws Exception {
		//Given:
		String fakeUserEmail = "fake@fake.fake";
		String fakeUserPassword = "fake";
		given(this.userRepositoryMock.findUserByEmailAndPassword(eq(fakeUserEmail), eq(fakeUserPassword))).willReturn(Optional.empty());

		//When:
		try {
			this.sut.findUserByEmailAndPassword(fakeUserEmail, fakeUserPassword);
			fail("UserNotFoundException expected because we try to find the user which does not exist");

			//Then:
		} catch (UserNotFoundException e) {
			verify(this.userRepositoryMock, times(1)).findUserByEmailAndPassword(fakeUserEmail, fakeUserPassword);
			verifyNoMoreInteractions(this.userRepositoryMock);
		}
	}

	@Test
	public void shouldReturnFoundUserByEmail() throws Exception {
		//Given:
		User found = TestUserBuilder.aUser().build();
		given(this.userRepositoryMock.findUserByEmail(eq(found.getEmail()))).willReturn(found);

		//When:
		User actual = this.sut.findUserByEmail(found.getEmail());

		//Then:
		assertThat(actual).isEqualTo(found);
		verify(this.userRepositoryMock, times(1)).findUserByEmail(found.getEmail());
		verifyNoMoreInteractions(this.userRepositoryMock);
	}

	@Test
	public void shouldReturnNullWhenTryFindUserByEmailWhichDoesNotExist() throws Exception {
		//Given:
		String fakeUserEmail = "fake@fake.fake";
		given(this.userRepositoryMock.findUserByEmail(eq(fakeUserEmail))).willReturn(null);

		//When:

		User actual =  this.sut.findUserByEmail(fakeUserEmail);

		//Then:
		assertThat(actual).isEqualTo(null);
		verify(this.userRepositoryMock, times(1)).findUserByEmail(fakeUserEmail);
		verifyNoMoreInteractions(this.userRepositoryMock);
	}

	@Test
	public void shouldSaveNewUserWithSocialRegistrationAndReturnIt() throws Exception {
		//Given:
		User found = TestUserBuilder.aUser().build();
		given(this.userRepositoryMock.findUserBySocialNetworkAndSocialId(eq(found.getSocialMediaService()), any(String.class))).willReturn(null);
		given(this.userRepositoryMock.save(found)).willReturn(found);

		//When:
		User actual = this.sut.findUserOrRegisterNewUserAccount(found);

		//Then:
		assertThat(actual).isEqualTo(found);
		verify(this.userRepositoryMock, times(1)).findUserBySocialNetworkAndSocialId(eq(found.getSocialMediaService()), any(String.class));
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(this.userRepositoryMock, times(1)).save(userCaptor.capture());
		verifyNoMoreInteractions(this.userRepositoryMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(found.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(found.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(found.getPassword());
	}

	@Test
	public void shouldFindUserWithSocialRegistrationAndReturnIt() throws Exception {
		//Given:
		User found = TestUserBuilder.aUser().build();
		given(this.userRepositoryMock.findUserBySocialNetworkAndSocialId(eq(found.getSocialMediaService()), any(String.class))).willReturn(found);

		//When:
		User actual = this.sut.findUserOrRegisterNewUserAccount(found);

		//Then:
		assertThat(actual).isEqualTo(found);
		assertThat(actual.getUsername()).isEqualTo(found.getUsername());
		assertThat(actual.getEmail()).isEqualTo(found.getEmail());
		assertThat(actual.getPassword()).isEqualTo(found.getPassword());
		verify(this.userRepositoryMock, times(1)).findUserBySocialNetworkAndSocialId(eq(found.getSocialMediaService()), any(String.class));
		verifyNoMoreInteractions(this.userRepositoryMock);
	}

	@Test
	public void shouldCreateNewUserAndReturnIt() throws Exception {
		//Given:
		User found = TestUserBuilder.aUser().build();
		given(this.userRepositoryMock.findUserByEmailWithNotEmptyPasswordOptional(any(String.class))).willReturn(Optional.empty());
		given(this.userRepositoryMock.save(found)).willReturn(found);

		//When:
		User actual = this.sut.createUserAccountAndReturnIt(found);

		//Then:
		assertThat(actual).isEqualTo(found);
		verify(this.userRepositoryMock, times(1)).findUserByEmailWithNotEmptyPasswordOptional(any(String.class));
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(this.userRepositoryMock, times(1)).save(userCaptor.capture());
		verifyNoMoreInteractions(this.userRepositoryMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(found.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(found.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(found.getPassword());
	}

	@Test
	public void shouldThrowExceptionWhenFindUserWithDuplicateEmail() throws Exception {
		//Given:
		User found = TestUserBuilder.aUser().build();
		given(this.userRepositoryMock.findUserByEmailWithNotEmptyPasswordOptional(any(String.class))).willReturn(Optional.of(found));

		//When:
		try {
			this.sut.createUserAccountAndReturnIt(found);
			fail("DuplicateUserException expected because we try create the user which has email existing in database");

		//Then:
		} catch (DuplicateUserException e) {
			verify(this.userRepositoryMock, times(1)).findUserByEmailWithNotEmptyPasswordOptional(any(String.class));
			verifyNoMoreInteractions(this.userRepositoryMock);
		}
	}


}
