package com.epam.idea.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.epam.idea.builder.model.TestIdeaBuilder;
import com.epam.idea.core.model.CommonUserDetails;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.IdeaRepository;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.exception.IdeaNotFoundException;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class IdeaServiceImplTest {

	@Mock
	private IdeaRepository ideaRepositoryMock;

	@Mock
	private AnonymousAuthenticationToken authenticationMock;

	@Mock
	private SecurityContext securityContextMock;

	@Mock
	private UserRepository userRepositoryMock;

	@InjectMocks
	private IdeaService sut = new IdeaServiceImpl();

    private final Pageable defaultPageRequest = new PageRequest(0, 500, null);

	@Before
	public void setUp() throws Exception {
		Mockito.reset(this.ideaRepositoryMock);
	}

	@Test
	public void shouldSaveNewIdea() throws Exception {
		//Given:
		Long id = 1L;
		Idea ideaToSave = TestIdeaBuilder.anIdea().build();
		CommonUserDetails principal = new CommonUserDetails("username", "password", Collections.<GrantedAuthority>emptyList());
		principal.setId(id);
		Optional<User> user = Optional.of(new User());

		given(securityContextMock.getAuthentication()).willReturn(authenticationMock);
		given(authenticationMock.getPrincipal()).willReturn(principal);
		given(userRepositoryMock.findOne(id)).willReturn(user);

		SecurityContextHolder.setContext(securityContextMock);

		//When:
		this.sut.save(ideaToSave);

		//Then:
		ArgumentCaptor<Idea> ideaCaptor = ArgumentCaptor.forClass(Idea.class);
		verify(this.ideaRepositoryMock, times(1)).save(ideaCaptor.capture());
		verifyNoMoreInteractions(this.ideaRepositoryMock);

		Idea ideaArgument = ideaCaptor.getValue();
		assertThat(ideaArgument.getTitle()).isEqualTo(ideaToSave.getTitle());
		assertThat(ideaArgument.getDescription()).isEqualTo(ideaToSave.getDescription());
	}

	@Test
	public void shouldReturnFoundIdea() throws Exception {
		//Given:
		Idea foundIdea = TestIdeaBuilder.anIdea().build();
		given(this.ideaRepositoryMock.findOne(eq(foundIdea.getId()))).willReturn(Optional.of(foundIdea));

		//When:
		Idea actual = this.sut.findOne(foundIdea.getId());

		//Then:
		assertThat(actual).isEqualTo(foundIdea);
		verify(this.ideaRepositoryMock, times(1)).findOne(foundIdea.getId());
	}

	@Test
	public void shouldThrowExceptionWhenTryFindIdeaWhichDoesNotExist() throws Exception {
		//Given:
		long ideaId = 3L;
		given(this.ideaRepositoryMock.findOne(eq(ideaId))).willReturn(Optional.empty());

		//When:
		try {
			this.sut.findOne(ideaId);
			fail("IdeaNotFoundException expected because we try to find the idea which does not exist");

			//Then:
		} catch (IdeaNotFoundException e) {
			verify(this.ideaRepositoryMock, times(1)).findOne(ideaId);
			verifyNoMoreInteractions(this.ideaRepositoryMock);
		}
	}

	@Test
	public void shouldDeleteIdeaAndReturnIt() throws Exception {
		//Given:
		Idea deletedIdea = TestIdeaBuilder.anIdea().build();
		given(this.ideaRepositoryMock.findOne(eq(deletedIdea.getId()))).willReturn(Optional.of(deletedIdea));

		//When:
		Idea actual = this.sut.deleteById(deletedIdea.getId());

		//Then:
		assertThat(actual).isEqualTo(deletedIdea);
		verify(this.ideaRepositoryMock, times(1)).findOne(deletedIdea.getId());
		verify(this.ideaRepositoryMock, times(1)).delete(deletedIdea);
	}

	@Test
	public void shouldThrowExceptionWhenTryDeleteIdeaWhichDoesNotExist() throws Exception {
		//Given:
		long fakeIdeaId = 2L;
		given(this.ideaRepositoryMock.findOne(eq(fakeIdeaId))).willReturn(Optional.empty());

		//When:
		try {
			this.sut.deleteById(fakeIdeaId);
			fail("IdeaNotFoundException expected because we try to delete the idea which does not exist");

			//Then:
		} catch (IdeaNotFoundException e) {
			verify(this.ideaRepositoryMock, times(1)).findOne(fakeIdeaId);
			verifyNoMoreInteractions(this.ideaRepositoryMock);
		}
	}

	@Test
	public void shouldDeleteIdea() throws Exception {
		//Given:
		Idea deletedIdea = TestIdeaBuilder.anIdea().build();

		//When:
		this.sut.delete(deletedIdea);

		//Then:
		verify(this.ideaRepositoryMock, times(1)).delete(deletedIdea);
		verifyNoMoreInteractions(this.ideaRepositoryMock);
	}

	@Test
	public void shouldUpdateIdeaAndReturnIt() throws Exception {
		//Given:
		Idea source = new TestIdeaBuilder()
				.withTitle("New title")
				.withDescription("New description")
				.build();
		Idea target = new TestIdeaBuilder()
				.withId(1L)
				.withTitle("Title")
				.withDescription("Description")
				.build();
		given(this.ideaRepositoryMock.findOne(eq(target.getId()))).willReturn(Optional.of(target));

		//When:
		Idea actual = this.sut.update(target.getId(), source);

		//Then:
		assertThat(actual.getId()).isEqualTo(target.getId());
		assertThat(actual.getTitle()).isEqualTo(source.getTitle());
		assertThat(actual.getDescription()).isEqualTo(source.getDescription());
		verify(this.ideaRepositoryMock, times(1)).findOne(target.getId());
	}

	@Test
	public void shouldThrowExceptionWhenTryUpdateIdeaWhichDoesNotExist() throws Exception {
		//Given:
		long fakeIdeaId = 3L;
		Idea source = new TestIdeaBuilder()
				.withTitle("New title")
				.withDescription("New description")
				.build();
		given(this.ideaRepositoryMock.findOne(eq(fakeIdeaId))).willReturn(Optional.empty());

		//When
		try {
			this.sut.update(fakeIdeaId, source);
			fail("IdeaNotFoundException expected because we try to update the idea which does not exist");

			//Then:
		} catch (IdeaNotFoundException ex) {
			verify(this.ideaRepositoryMock, times(1)).findOne(fakeIdeaId);
			verifyNoMoreInteractions(this.ideaRepositoryMock);
		}
	}

	@Test
	public void shouldReturnListOfAllIdeas() throws Exception {
		//Given:
		List<Idea> ideas = Lists.newArrayList(
				TestIdeaBuilder.anIdea().build(),
				TestIdeaBuilder.anIdea().build()
		);
		given(this.ideaRepositoryMock.findAll()).willReturn(ideas);
		given(this.securityContextMock.getAuthentication()).willReturn(authenticationMock);

		//When:
		List<Idea> actual = this.sut.findAll();

		//Then:
		assertThat(actual).isEqualTo(ideas);
		verify(this.ideaRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(this.ideaRepositoryMock);
	}

	@Test
	public void shouldReturnListOfIdeasByUserId() throws Exception {
		//Given:
		List<Idea> ideas = Lists.newArrayList(
				TestIdeaBuilder.anIdea().build(),
				TestIdeaBuilder.anIdea().build()
		);
		given(this.ideaRepositoryMock.findByUserId(any(Long.class))).willReturn(ideas);

		//When:
		List<Idea> actual = this.sut.findIdeasByUserId(1);

		//Then:
		assertThat(actual).isEqualTo(ideas);
		verify(this.ideaRepositoryMock, times(1)).findByUserId(any(Long.class));
	}

	@Test
	public void shouldReturnListOfIdeasByTagId() throws Exception {
		//Given:
		List<Idea> ideas = Lists.newArrayList(
				TestIdeaBuilder.anIdea().build(),
				TestIdeaBuilder.anIdea().build()
		);
		given(this.ideaRepositoryMock.findByTagId(any(Long.class))).willReturn(ideas);

		//When:
		List<Idea> actual = this.sut.findIdeasByTagId(1);

		//Then:
		assertThat(actual).isEqualTo(ideas);
		verify(this.ideaRepositoryMock, times(1)).findByTagId(any(Long.class));
	}


    @Test
    public void shouldReturnPageWithListOfIdeas() throws Exception {
        //Given:
        List<Idea> ideas = Lists.newArrayList(
                TestIdeaBuilder.anIdea().build(),
                TestIdeaBuilder.anIdea().build()
        );
        given(this.ideaRepositoryMock.findAll(defaultPageRequest)).willReturn(ideas);

        //When:
        List<Idea> actual = this.sut.findAll(defaultPageRequest);

        //Then:
        assertThat(actual).isEqualTo(ideas);
    }


    @Test
    public void shouldSaveForUser() throws Exception {
        //getting idea
        Idea foundIdea = TestIdeaBuilder.anIdea().build();
        Optional<User> user = Optional.of(new User());

        given(this.ideaRepositoryMock.save(foundIdea)).willReturn(foundIdea);
        given(userRepositoryMock.findOne(1l)).willReturn(user);

        //When:
        Idea actual = this.sut.saveForUser(1, foundIdea);
        //Then:
        assertThat(actual).isEqualTo(foundIdea);
    }

	@Test
	public void shouldChangeLikedForUserAndIdea_whenLikedIsFalse() {
		long ideaId = 1L;
		User user = new User();
		Idea idea = TestIdeaBuilder.anIdea().withId(ideaId).build();

		final int ratingBefore = idea.getRating();
		final int likedUsersBefore = idea.getLikedUsers().size();

		given(ideaRepositoryMock.findIdeaByIdThatLikedCurrentUser(ideaId)).willReturn(null);
		given(userRepositoryMock.findCurrentUser()).willReturn(user);
		given(ideaRepositoryMock.findOne(ideaId)).willReturn(Optional.of(idea));
		given(ideaRepositoryMock.save(idea)).willReturn(idea);

		Idea changedLikeIdea = this.sut.changeIdeaLike(ideaId);

		assertThat(changedLikeIdea.getLikedUsers().size()).isEqualTo(likedUsersBefore + 1);
		assertThat(changedLikeIdea.getRating()).isEqualTo(ratingBefore + 1);
	}

	@Test
	public void shouldChangeLikedForUserAndIdea_whenLikedIsTrue() {
		long ideaId = 1L;
		User user = new User();
		Idea likedIdea = TestIdeaBuilder.anIdea().withId(ideaId).withLikedUser(user).withLiked(true).build();

		final int ratingBefore = likedIdea.getRating();
		final int likedUsersBefore = likedIdea.getLikedUsers().size();

		given(ideaRepositoryMock.findIdeaByIdThatLikedCurrentUser(ideaId)).willReturn(likedIdea);
		given(userRepositoryMock.findCurrentUser()).willReturn(user);
		given(ideaRepositoryMock.save(likedIdea)).willReturn(likedIdea);

		Idea changedLikeIdea = this.sut.changeIdeaLike(ideaId);

		assertThat(changedLikeIdea.getLikedUsers().size()).isEqualTo(likedUsersBefore - 1);
		assertThat(changedLikeIdea.getRating()).isEqualTo(ratingBefore - 1);
	}
}
