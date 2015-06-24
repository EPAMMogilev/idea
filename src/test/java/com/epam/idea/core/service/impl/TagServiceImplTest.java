package com.epam.idea.core.service.impl;

import java.util.List;
import java.util.Optional;

import com.epam.idea.builder.model.TestTagBuilder;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.repository.TagRepository;
import com.epam.idea.core.service.TagService;
import com.epam.idea.core.service.exception.TagDoesNotExistException;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceImplTest {

	@Mock
	private TagRepository tagRepositoryMock;

	@InjectMocks
	private TagService sut = new TagServiceImpl();

	@Before
	public void setUp() throws Exception {
		Mockito.reset(this.tagRepositoryMock);
	}

	@Test
	public void shouldSaveNewTag() throws Exception {
		//Given:
		Tag tagToSave = TestTagBuilder.aTag().build();

		//When:
		this.sut.save(tagToSave);

		//Then:
		ArgumentCaptor<Tag> tagCaptor = ArgumentCaptor.forClass(Tag.class);
		verify(this.tagRepositoryMock, times(1)).save(tagCaptor.capture());
		verifyNoMoreInteractions(this.tagRepositoryMock);

		Tag tagArgument = tagCaptor.getValue();
		assertThat(tagArgument.getName()).isEqualTo(tagToSave.getName());
	}

	@Test
	public void shouldReturnFoundTag() throws Exception {
		//Given:
		Tag foundTag = TestTagBuilder.aTag().build();
		given(this.tagRepositoryMock.findOne(eq(foundTag.getId()))).willReturn(Optional.of(foundTag));

		//When:
		Tag actualTag = this.sut.findOne(foundTag.getId());

		//Then:
		assertThat(actualTag).isEqualTo(foundTag);
		verify(this.tagRepositoryMock, times(1)).findOne(foundTag.getId());
		verifyNoMoreInteractions(this.tagRepositoryMock);
	}

	@Test
	public void shouldThrowExceptionWhenTryFindTagWhichDoesNotExist() throws Exception {
		//Given:
		long tagId = 3L;
		given(this.tagRepositoryMock.findOne(eq(tagId))).willReturn(Optional.empty());

		//When:
		try {
			this.sut.findOne(tagId);
			fail("TagDoesNotExistException expected because we try to find the tag which does not exist");

			//Then:
		} catch (TagDoesNotExistException e) {
			verify(this.tagRepositoryMock, times(1)).findOne(tagId);
			verifyNoMoreInteractions(this.tagRepositoryMock);
		}
	}

	@Test
	public void shouldReturnAllFoundTags() throws Exception {
		//Given:
		List<Tag> foundTags = Lists.newArrayList(TestTagBuilder.aTag().build());
		given(this.tagRepositoryMock.findAll()).willReturn(foundTags);

		//When:
		List<Tag> actualTags = this.sut.findAll();

		//Then:
		assertThat(actualTags).isEqualTo(foundTags);
		verify(this.tagRepositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(this.tagRepositoryMock);
	}

	@Test
	public void shouldDeleteNewIdea() throws Exception {
		//Given:
		Tag deletedTag = TestTagBuilder.aTag().build();
		given(this.tagRepositoryMock.findOne(eq(deletedTag.getId()))).willReturn(Optional.of(deletedTag));

		//When:
		this.sut.delete(deletedTag);

		//Then:
		ArgumentCaptor<Tag> tagCaptor = ArgumentCaptor.forClass(Tag.class);
		verify(this.tagRepositoryMock, times(1)).delete(tagCaptor.capture());
		verifyNoMoreInteractions(this.tagRepositoryMock);

		Tag tagArgument = tagCaptor.getValue();
		assertThat(tagArgument.getName()).isEqualTo(deletedTag.getName());
	}
}
