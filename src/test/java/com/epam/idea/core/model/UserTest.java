package com.epam.idea.core.model;

import com.epam.idea.builder.model.TestUserBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

	@Test
	public void shouldAddCreationTime() {
		//Given:
		User user = new User();
		
		//When:
		user.prePersist();

		//Then:
		assertThat(user.getEmail()).isNull();
		assertThat(user.getPassword()).isNull();
		assertThat(user.getCreationTime()).isNotNull();
		assertThat(user.getIdeas()).hasSize(0);
		assertThat(user.getComments()).hasSize(0);
		assertThat(user.getRoles()).hasSize(0);
	}

	@Test
	public void shouldUpdateUser() throws Exception {
		// Given:
		User target = new TestUserBuilder()
				.withEmail("email")
				.withPassword("password")
				.build();
		User source = new TestUserBuilder()
				.withEmail("New email")
				.withPassword("New password")
				.build();

		// When:
		target.updateWith(source);

		// Then:
		assertThat(target.getEmail()).isEqualTo(source.getEmail());
		assertThat(target.getPassword()).isEqualTo(source.getPassword());
	}
}
