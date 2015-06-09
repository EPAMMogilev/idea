package com.epam.idea.core.repository;

import java.util.Optional;

import com.epam.idea.core.model.SocialNetwork;
import com.epam.idea.core.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends BaseRepository<User, Long> {

	@Query("select u from User u where u.email = ?1 and u.password =?2")
	Optional<User> findUserByEmailAndPassword(String userEmail, String userPassword);

	@Query("select u from User u where u.authSocial = ?1 and u.socialId =?2")
	Optional<User> findUserBySocialNetworkAndSocialId(SocialNetwork authSocial, String socialId);
}
