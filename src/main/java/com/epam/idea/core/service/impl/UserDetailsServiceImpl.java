package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.CommonUserDetails;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository repository) {
		this.userRepository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("No user found with username: " + username);
		}
		System.out.println(user.getUsername());

		CommonUserDetails principal = CommonUserDetails.getBuilder()
//				.firstName(user.getFirstName())
//				.lastName(user.getUsername())
//				.role(user.getRole())
				.fullName(user.getUsername())
				.id(user.getId())
				.password(user.getPassword())
				.socialSignInProvider(user.getSocialMediaService())
				.username(user.getEmail())
				.build();

		return principal;
	}
}
