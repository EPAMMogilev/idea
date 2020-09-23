package com.epam.idea.core.service.impl;

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
import org.junit.Ignore;
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
import org.springframework.security.core.GrantedAuthority;
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
    private final IdeaService sut = new IdeaServiceImpl();

    private final Pageable defaultPageRequest = PageRequest.of(0, 500);

    @Before
    public void setUp() throws Exception {
        Mockito.reset(this.ideaRepositoryMock);
    }

    @Test
    public void shouldSaveNewIdea() throws Exception {
        // Given:
        final Long id = 1L;
        final Idea ideaToSave = TestIdeaBuilder.anIdea().build();
        final CommonUserDetails principal = new CommonUserDetails("username", "password",
                Collections.<GrantedAuthority> emptyList());
        principal.setId(id);
        final Optional<User> user = Optional.of(new User());

        given(securityContextMock.getAuthentication()).willReturn(authenticationMock);
        //given(authenticationMock.getPrincipal()).willReturn(principal);
        //given(userRepositoryMock.findById(id)).willReturn(user);

        SecurityContextHolder.setContext(securityContextMock);

        // When:
        this.sut.save(ideaToSave);

        // Then:
        final ArgumentCaptor<Idea> ideaCaptor = ArgumentCaptor.forClass(Idea.class);
        verify(this.ideaRepositoryMock, times(1)).save(ideaCaptor.capture());
        verifyNoMoreInteractions(this.ideaRepositoryMock);

        final Idea ideaArgument = ideaCaptor.getValue();
        assertThat(ideaArgument.getTitle()).isEqualTo(ideaToSave.getTitle());
        assertThat(ideaArgument.getDescription()).isEqualTo(ideaToSave.getDescription());
    }

    @Test
    public void shouldReturnFoundIdea() throws Exception {
        // Given:
        final Idea foundIdea = TestIdeaBuilder.anIdea().build();
        given(this.ideaRepositoryMock.findById(eq(foundIdea.getId()))).willReturn(Optional.of(foundIdea));

        // When:
        final Idea actual = this.sut.findOne(foundIdea.getId());

        // Then:
        assertThat(actual).isEqualTo(foundIdea);
        verify(this.ideaRepositoryMock, times(1)).findById(foundIdea.getId());
    }

    @Test
    public void shouldThrowExceptionWhenTryFindIdeaWhichDoesNotExist() throws Exception {
        // Given:
        final long ideaId = 3L;
        given(this.ideaRepositoryMock.findById(eq(ideaId))).willReturn(Optional.empty());

        // When:
        try {
            this.sut.findOne(ideaId);
            fail("IdeaNotFoundException expected because we try to find the idea which does not exist");

            // Then:
        } catch (final IdeaNotFoundException e) {
            verify(this.ideaRepositoryMock, times(1)).findById(ideaId);
            verifyNoMoreInteractions(this.ideaRepositoryMock);
        }
    }

    @Test
    public void shouldDeleteIdeaAndReturnIt() throws Exception {
        // Given:
        final Idea deletedIdea = TestIdeaBuilder.anIdea().build();
        given(this.ideaRepositoryMock.findById(eq(deletedIdea.getId()))).willReturn(Optional.of(deletedIdea));

        // When:
        final Idea actual = this.sut.deleteById(deletedIdea.getId());

        // Then:
        assertThat(actual).isEqualTo(deletedIdea);
        verify(this.ideaRepositoryMock, times(1)).findById(deletedIdea.getId());
        verify(this.ideaRepositoryMock, times(1)).delete(deletedIdea);
    }

    @Test
    public void shouldThrowExceptionWhenTryDeleteIdeaWhichDoesNotExist() throws Exception {
        // Given:
        final long fakeIdeaId = 2L;
        given(this.ideaRepositoryMock.findById(eq(fakeIdeaId))).willReturn(Optional.empty());

        // When:
        try {
            this.sut.deleteById(fakeIdeaId);
            fail("IdeaNotFoundException expected because we try to delete the idea which does not exist");

            // Then:
        } catch (final IdeaNotFoundException e) {
            verify(this.ideaRepositoryMock, times(1)).findById(fakeIdeaId);
            verifyNoMoreInteractions(this.ideaRepositoryMock);
        }
    }

    @Test
    public void shouldDeleteIdea() throws Exception {
        // Given:
        final Idea deletedIdea = TestIdeaBuilder.anIdea().build();

        // When:
        this.sut.delete(deletedIdea);

        // Then:
        verify(this.ideaRepositoryMock, times(1)).delete(deletedIdea);
        verifyNoMoreInteractions(this.ideaRepositoryMock);
    }

    @Test
    public void shouldUpdateIdeaAndReturnIt() throws Exception {
        // Given:
        final Idea source = new TestIdeaBuilder().withTitle("New title").withDescription("New description").build();
        final Idea target = new TestIdeaBuilder().withId(1L).withTitle("Title").withDescription("Description").build();
        given(this.ideaRepositoryMock.findById(eq(target.getId()))).willReturn(Optional.of(target));

        // When:
        final Idea actual = this.sut.update(target.getId(), source);

        // Then:
        assertThat(actual.getId()).isEqualTo(target.getId());
        assertThat(actual.getTitle()).isEqualTo(source.getTitle());
        assertThat(actual.getDescription()).isEqualTo(source.getDescription());
        assertThat(actual.getRating()).isEqualTo(source.getRating());
        assertThat(actual.getLikedUsers()).isEqualTo(source.getLikedUsers());
        assertThat(actual.getLiked()).isEqualTo(source.getLiked());
        verify(this.ideaRepositoryMock, times(1)).findById(target.getId());
    }

    @Test
    public void shouldThrowExceptionWhenTryUpdateIdeaWhichDoesNotExist() throws Exception {
        // Given:
        final long fakeIdeaId = 3L;
        final Idea source = new TestIdeaBuilder().withTitle("New title").withDescription("New description").build();
        given(this.ideaRepositoryMock.findById(eq(fakeIdeaId))).willReturn(Optional.empty());

        // When
        try {
            this.sut.update(fakeIdeaId, source);
            fail("IdeaNotFoundException expected because we try to update the idea which does not exist");

            // Then:
        } catch (final IdeaNotFoundException ex) {
            verify(this.ideaRepositoryMock, times(1)).findById(fakeIdeaId);
            verifyNoMoreInteractions(this.ideaRepositoryMock);
        }
    }

    @Test
    public void shouldReturnListOfAllIdeas() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAll()).willReturn(ideas);
        //given(this.securityContextMock.getAuthentication()).willReturn(authenticationMock);

        // When:
        final List<Idea> actual = this.sut.findAll();

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(this.ideaRepositoryMock);
    }

    @Test
    public void shouldReturnListOfIdeasByUserId() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAllByUserId(any(PageRequest.class), any(Long.class))).willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAllByUserId(defaultPageRequest, 1L);

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAllByUserId(any(PageRequest.class), any(Long.class));
    }

    @Test
    public void shouldReturnListOfIdeasByTagId() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAllVisibleByTagId(any(PageRequest.class), any(Long.class))).willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAllByUserIdQueryAndTagId(defaultPageRequest, null, null, 1L);

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAllVisibleByTagId(any(PageRequest.class), any(Long.class));
    }

    @Test
    public void shouldReturnListOfIdeasByTagIdAndQuery_whenQueryIsNull() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAllVisibleByTagId(any(PageRequest.class), any(Long.class))).willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAllByUserIdQueryAndTagId(defaultPageRequest, null, null, 1L);

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAllVisibleByTagId(any(PageRequest.class), any(Long.class));
    }

    @Test
    public void shouldReturnListOfIdeasByTagIdAndQuery_whenTagIdIsNull() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAllVisibleByQuery(any(PageRequest.class), any(String.class)))
                .willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAllByUserIdQueryAndTagId(defaultPageRequest, null, "Bar", null);

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAllVisibleByQuery(any(PageRequest.class), any(String.class));
    }

    @Test
    public void shouldReturnListOfIdeasByTagIdAndQuery() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAllVisibleByTagIdAndByQuery(any(PageRequest.class), any(Long.class),
                any(String.class))).willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAllByUserIdQueryAndTagId(defaultPageRequest, null, "Bar", 1L);

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAllVisibleByTagIdAndByQuery(any(PageRequest.class),
                any(Long.class), any(String.class));
    }

    @Test
    public void shouldReturnListOfIdeasByUserIdTagIdAndQuery() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAllByUserIdByTagIdAndByQuery(any(PageRequest.class), any(Long.class),
                any(Long.class), any(String.class))).willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAllByUserIdQueryAndTagId(defaultPageRequest, 1L, "Query", 1L);

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAllByUserIdByTagIdAndByQuery(any(PageRequest.class),
                any(Long.class), any(Long.class), any(String.class));
    }

    @Test
    public void shouldReturnListOfIdeasByUserIdTagIdAndQuery_whenTagIdIsNull() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAllByUserIdAndByQuery(any(PageRequest.class), any(Long.class),
                any(String.class))).willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAllByUserIdQueryAndTagId(defaultPageRequest, 1L, "Query", null);

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAllByUserIdAndByQuery(any(PageRequest.class), any(Long.class),
                any(String.class));
    }

    @Test
    public void shouldReturnListOfIdeasByUserIdTagIdAndQuery_whenQueryIsNull() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAllByUserIdAndByTagId(any(PageRequest.class), any(Long.class),
                any(Long.class))).willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAllByUserIdQueryAndTagId(defaultPageRequest, 1L, null, 1L);

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAllByUserIdAndByTagId(any(PageRequest.class), any(Long.class),
                any(Long.class));
    }

    @Test
    public void shouldReturnListOfIdeasByUserIdTagIdAndQuery_whenQueryAndTagIdIsNull() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAllByUserId(any(PageRequest.class), any(Long.class))).willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAllByUserIdQueryAndTagId(defaultPageRequest, 1L, null, null);

        // Then:
        assertThat(actual).isEqualTo(ideas);
        verify(this.ideaRepositoryMock, times(1)).findAllByUserId(any(PageRequest.class), any(Long.class));
    }

    @Test
    public void shouldReturnPageWithListOfIdeas() throws Exception {
        // Given:
        final List<Idea> ideas = Lists.newArrayList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
        given(this.ideaRepositoryMock.findAll()).willReturn(ideas);

        // When:
        final List<Idea> actual = this.sut.findAll();

        // Then:
        assertThat(actual).isEqualTo(ideas);
    }

    @Test
    public void shouldSaveForUser() throws Exception {
        // getting idea
        final Idea foundIdea = TestIdeaBuilder.anIdea().build();
        final Optional<User> user = Optional.of(new User());

        given(this.ideaRepositoryMock.save(foundIdea)).willReturn(foundIdea);
        given(userRepositoryMock.findById(1l)).willReturn(user);

        // When:
        final Idea actual = this.sut.saveForUser(1, foundIdea);
        // Then:
        assertThat(actual).isEqualTo(foundIdea);
    }

    @Test
    public void shouldChangeLikedForUserAndIdea_whenLikedIsFalse() {
        final long ideaId = 1L;
        final User user = new User();
        final Idea idea = TestIdeaBuilder.anIdea().withId(ideaId).build();

        final int ratingBefore = idea.getRating();
        final int likedUsersBefore = idea.getLikedUsers().size();

        given(ideaRepositoryMock.findByIdAndLikedByCurrentUser(ideaId)).willReturn(null);
        given(userRepositoryMock.findCurrentUser()).willReturn(user);
        given(ideaRepositoryMock.findById(ideaId)).willReturn(Optional.of(idea));
        given(ideaRepositoryMock.save(idea)).willReturn(idea);

        final Idea changedLikeIdea = this.sut.changeIdeaLike(ideaId);

        assertThat(changedLikeIdea.getLikedUsers().size()).isEqualTo(likedUsersBefore + 1);
        assertThat(changedLikeIdea.getRating()).isEqualTo(ratingBefore + 1);
    }

    @Test
    public void shouldChangeLikedForUserAndIdea_whenLikedIsTrue() {
        final long ideaId = 1L;
        final User user = new User();
        final Idea likedIdea = TestIdeaBuilder.anIdea().withId(ideaId).withLikedUser(user).withLiked(true).build();

        final int ratingBefore = likedIdea.getRating();
        final int likedUsersBefore = likedIdea.getLikedUsers().size();

        given(ideaRepositoryMock.findByIdAndLikedByCurrentUser(ideaId)).willReturn(likedIdea);
        given(userRepositoryMock.findCurrentUser()).willReturn(user);
        given(ideaRepositoryMock.save(likedIdea)).willReturn(likedIdea);

        final Idea changedLikeIdea = this.sut.changeIdeaLike(ideaId);

        assertThat(changedLikeIdea.getLikedUsers().size()).isEqualTo(likedUsersBefore - 1);
        assertThat(changedLikeIdea.getRating()).isEqualTo(ratingBefore - 1);
    }
}