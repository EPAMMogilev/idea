package com.epam.idea.core.model;

import com.epam.idea.core.model.builders.TestIdeaBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IdeaTest {

	@Test
	public void shouldAddCreationAndModificationTime() {
		//Given:
		Idea idea = new Idea();

		//When:
		idea.prePersist();

		//Then:
		assertThat(idea.getCreationTime()).isNotNull();
		assertThat(idea.getModificationTime()).isNotNull();
		assertThat(idea.getCreationTime()).isEqualTo(idea.getModificationTime());
		assertThat(idea.getAuthor()).isNull();
		assertThat(idea.getTitle()).isNull();
		assertThat(idea.getDescription()).isNull();
		assertThat(idea.getRating()).isEqualTo(0);
		assertThat(idea.getComments()).hasSize(0);
		assertThat(idea.getRelatedTags()).hasSize(0);
	}

	@Test
	public void modificationTimeShouldBeAfterCreation() {
		//Given:
		Idea idea = new Idea();

		//When:
		idea.prePersist();
		pause(1000);
		idea.preUpdate();

		//Then:
		assertThat(idea.getCreationTime()).isNotNull();
		assertThat(idea.getModificationTime()).isNotNull();
		assertThat(idea.getModificationTime().isAfter(idea.getCreationTime())).isTrue();
		assertThat(idea.getAuthor()).isNull();
		assertThat(idea.getTitle()).isNull();
		assertThat(idea.getDescription()).isNull();
		assertThat(idea.getRating()).isEqualTo(0);
		assertThat(idea.getComments()).hasSize(0);
		assertThat(idea.getRelatedTags()).hasSize(0);
	}

	private void pause(long timeInMillis) {
		try {
			Thread.currentThread().sleep(timeInMillis);
		} catch (InterruptedException e) {
			//Do Nothing
		}
	}

	@Test
	public void shouldUpdateUser() throws Exception {
		// Given:
		Idea target = new TestIdeaBuilder()
				.withTitle("title")
				.withDescription("description")
				.build();
		Idea source = new TestIdeaBuilder()
				.withTitle("New title")
				.withDescription("New description")
				.build();

		// When:
		target.updateWith(source);

		// Then:
		assertThat(target.getTitle()).isEqualTo(source.getTitle());
		assertThat(target.getDescription()).isEqualTo(source.getDescription());
	}
}
