package com.epam.idea.core.repository;

import com.epam.idea.core.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
