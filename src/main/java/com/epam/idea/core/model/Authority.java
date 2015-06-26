package com.epam.idea.core.model;

import java.util.Arrays;
import java.util.Optional;

public enum Authority {

	ADMIN(1), USER(2);

	private final int id;

	Authority(final int i) {
		id = i;
	}

	public int getId() {
		return id;
	}

	public static Optional<Authority> getById(final int id) {
		return Arrays.stream(values())
				.filter(v -> v.getId() == id)
				.findAny();
	}
}
