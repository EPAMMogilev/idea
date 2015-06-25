package com.epam.idea.core.repository;

import java.util.Optional;

import com.epam.idea.core.model.SocialMediaService;
import com.epam.idea.core.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends BaseRepository<User, Long> {

	@Query("select u from User u where u.email = ?1 and u.password =?2")
	Optional<User> findUserByEmailAndPassword(String userEmail, String userPassword);

	@Query("select u from User u where u.email = ?1")
	User findUserByEmail(String userEmail);

	@Query("select u from User u where u.email = ?1 and u.password is not null")
	Optional<User> findUserByEmailWithNotEmptyPasswordOptional(String userEmail);

	@Query("select u from User u where u.email = ?1 and u.password is not null")
	User findUserByEmailWithNotEmptyPassword(String userEmail);

	@Query("select u from User u where u.socialId = ?1")
	User findUserBySocialId(String socialId);

	@Query("select u from User u where u.socialMediaService = ?1 and u.socialId = ?2")
	User findUserBySocialNetworkAndSocialId(SocialMediaService socialMediaService, String socialId);
}
