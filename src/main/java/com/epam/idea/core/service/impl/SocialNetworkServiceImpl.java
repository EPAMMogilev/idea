package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.SocialNetwork;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.SocialNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SocialNetworkServiceImpl implements SocialNetworkService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User findOrCreateUserBySocialIdGoogle(Person person) {
		return userRepository.findUserBySocialNetworkAndSocialId(SocialNetwork.GOOGLE, person.getId())
				.orElseGet(() -> {
					User user = new User();
					user.setSocialId(person.getId());
					user.setUsername(person.getDisplayName());
					user.setEmail(person.getAccountEmail());
					user.setAuthSocial(SocialNetwork.GOOGLE);
					return userRepository.save(user);
				});
	}
}
