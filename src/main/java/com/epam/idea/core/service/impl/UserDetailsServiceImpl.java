package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.CommonUserDetails;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository repository) {
		this.userRepository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmailWithNotEmptyPassword(username);

		if (user == null) {
			user = userRepository.findUserBySocialId(username);
		}
		if (user == null) {
			throw new UsernameNotFoundException("No user found with username: " + username);
		}

		CommonUserDetails principal = CommonUserDetails.getBuilder()
				.id(user.getId())
				.password(user.getPassword())
				.roles(user.getRoles())
				.socialSignInProvider(user.getSocialMediaService())
				.username(user.getUsername())
				.email(user.getEmail())
				.build();

		return principal;
	}
}
