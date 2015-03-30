package com.epam.idea.core.service.impl;

import java.util.List;
import java.util.Optional;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.builders.TestIdeaBuilder;
import com.epam.idea.core.repository.IdeaRepository;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.exception.IdeaNotFoundException;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IdeaServiceImplTest {

	@Mock
	private IdeaRepository ideaRepositoryMock;

	@InjectMocks
	private IdeaService sut = new IdeaServiceImpl();

	@Before
	public void setUp() throws Exception {
		Mockito.reset(ideaRepositoryMock);
	}

	@Test
	public void shouldSaveNewIdea() throws Exception {
		//Given:
		Idea ideaToSave = TestIdeaBuilder.anIdea().build();

		//When:
		sut.save(ideaToSave);

		//Then:
		ArgumentCaptor<Idea> ideaCaptor = ArgumentCaptor.forClass(Idea.class);
		verify(ideaRepositoryMock, times(1)).save(ideaCaptor.capture());
		verifyNoMoreInteractions(ideaRepositoryMock);

		Idea ideaArgument = ideaCaptor.getValue();
		assertThat(ideaArgument.getTitle()).isEqualTo(ideaToSave.getTitle());
		assertThat(ideaArgument.getDescription()).isEqualTo(ideaToSave.getDescription());
	}

	@Test
	public void shouldReturnFoundIdea() throws Exception {
		//Given:
		Idea foundIdea = TestIdeaBuilder.anIdea().build();
		when(ideaRepositoryMock.findOne(eq(foundIdea.getId()))).thenReturn(Optional.of(foundIdea));

		//When:
		Idea actual = sut.findOne(foundIdea.getId());

		//Then:
		assertThat(actual).isEqualTo(foundIdea);
		verify(ideaRepositoryMock, times(1)).findOne(foundIdea.getId());
		verifyNoMoreInteractions(ideaRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryFindIdeaWhichDoesNotExist() throws Exception {
		//Given:
		long ideaId = 3L;
		when(ideaRepositoryMock.findOne(eq(ideaId))).thenReturn(Optional.empty());

		//When:
		try {
			sut.findOne(ideaId);
			fail("IdeaNotFoundException expected because we try to find the idea which does not exist");
		} catch (IdeaNotFoundException e) {

			//Then:
			verify(ideaRepositoryMock, times(1)).findOne(ideaId);
			verifyNoMoreInteractions(ideaRepositoryMock);
		}
	}

	@Test
	public void shouldDeleteIdeaAndReturnIt() throws Exception {
		//Given:
		Idea deletedIdea = TestIdeaBuilder.anIdea().build();
		when(ideaRepositoryMock.findOne(eq(deletedIdea.getId()))).thenReturn(Optional.of(deletedIdea));

		//When:
		Idea actual = sut.deleteById(deletedIdea.getId());

		//Then:
		assertThat(actual).isEqualTo(deletedIdea);

		verify(ideaRepositoryMock, times(1)).findOne(deletedIdea.getId());
		verify(ideaRepositoryMock, times(1)).delete(deletedIdea);
		verifyNoMoreInteractions(ideaRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryDeleteIdeaWhichDoesNotExist() throws Exception {
		//Given:
		long fakeIdeaId = 2L;
		when(ideaRepositoryMock.findOne(eq(fakeIdeaId))).thenReturn(Optional.empty());

		//When:
		try {
			sut.deleteById(fakeIdeaId);
			fail("IdeaNotFoundException expected because we try to delete the idea which does not exist");
		} catch (IdeaNotFoundException e) {

			//Then:
			verify(ideaRepositoryMock, times(1)).findOne(fakeIdeaId);
			verifyNoMoreInteractions(ideaRepositoryMock);
		}
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
		when(ideaRepositoryMock.findOne(eq(target.getId()))).thenReturn(Optional.of(target));

		//When:
		Idea actual = sut.update(target.getId(), source);

		//Then:
		assertThat(actual.getId()).isEqualTo(target.getId());
		assertThat(actual.getTitle()).isEqualTo(source.getTitle());
		assertThat(actual.getDescription()).isEqualTo(source.getDescription());
		verify(ideaRepositoryMock, times(1)).findOne(target.getId());
		verifyNoMoreInteractions(ideaRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryUpdateIdeaWhichDoesNotExist() throws Exception {
		//Given:
		long fakeIdeaId = 3L;
		Idea source = new TestIdeaBuilder()
				.withTitle("New title")
				.withDescription("New description")
				.build();
		when(ideaRepositoryMock.findOne(eq(fakeIdeaId))).thenReturn(Optional.empty());

		//When
		try {
			sut.update(fakeIdeaId, source);
			fail("IdeaNotFoundException expected because we try to update the idea which does not exist");
		} catch (IdeaNotFoundException ex) {

			//Then:
			verify(ideaRepositoryMock, times(1)).findOne(fakeIdeaId);
			verifyNoMoreInteractions(ideaRepositoryMock);
		}
	}

	@Test
	public void shouldReturnListOfAllIdeas() throws Exception {
		//Given:
		List<Idea> ideas = asList(TestIdeaBuilder.anIdea().build(), TestIdeaBuilder.anIdea().build());
		when(ideaRepositoryMock.findAll()).thenReturn(ideas);

		//When:
		List<Idea> actual = sut.findAll();

		//Then:
		assertThat(actual).isEqualTo(ideas);
		verify(ideaRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(ideaRepositoryMock);
	}
}
