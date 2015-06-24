package com.epam.idea.core.model;

import java.util.Optional;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthorityTest {

	@Test
	public void shouldReturnAdmin() throws Exception {
		// Given:
		int adminId = Authority.ADMIN.getId();

		//When:
		Optional<Authority> actual = Authority.getById(adminId);

		//Then:
		assertThat(actual.get()).isEqualTo(Authority.ADMIN);
	}

	@Test
	public void shouldReturnEmptyOptionalWhenThereAreNoAuthorityWithTheGivenId() throws Exception {
		//Given:
		int invalidId = Authority.values()[0].getId() - 1;

		//When:
		Optional<Authority> actual = Authority.getById(invalidId);
		
		//Then:
		assertThat(actual.isPresent()).isFalse();
	}
}
