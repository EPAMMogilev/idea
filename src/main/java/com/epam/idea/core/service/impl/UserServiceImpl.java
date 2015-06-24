package com.epam.idea.core.service.impl;

import java.util.Arrays;
import java.util.List;

import com.epam.idea.core.model.Authority;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.SocialMediaService;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.UserNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void delete(final User deleted) {
		userRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		final List<User> userList = userRepository.findAll();
		//userList.forEach(user -> Hibernate.initialize(user.getIdeas()));
		userList.forEach(Hibernate::initialize);
		return userList;
	}

	@Override
	@Transactional(readOnly = true)
	public User findOne(final Long id) {
		return userRepository.findOne(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@Override
	public User save(final User persisted) {
		return userRepository.save(persisted);
	}

	@Override
	public User deleteById(final long userId) {
		final User deleted = findOne(userId);
		userRepository.delete(deleted);
		return deleted;
	}

	@Override
	public User update(final long userId, final User source) {
		final User target = findOne(userId);
		target.updateWith(source);
		return target;
	}

	@Override
	public User findUserByEmailAndPassword(String email, String password) {
		return userRepository.findUserByEmailAndPassword(email, password).orElseThrow(() -> new UserNotFoundException("This user does not exist"));
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User findRegisteredUserByEmail(String email) {
		return userRepository.findUserByEmailWithNotEmptyPasswordOptional(email).orElseThrow(() -> new UserNotFoundException("This user does not exist"));
	}

	@Override
	public User findUserOrRegisterNewUserAccount(User newUser) {

		User registered = findUserAccount(newUser);
		System.out.println(registered);
		if (registered == null) {
			Role roleUser = new Role();
			roleUser.setName(Authority.USER);
			newUser.setRoles(Arrays.asList(roleUser));
			
			registered = save(newUser);
		}

		return registered;
	}

	public User findUserBySocialNetworkAndSocialId(SocialMediaService socialMediaService, String socialId) {
		return userRepository.findUserBySocialNetworkAndSocialId(socialMediaService, socialId);
	}

	private User findUserAccount(User userAccountData) {
		User registered = null;

		registered = findUserBySocialNetworkAndSocialId(userAccountData.getSocialMediaService(), userAccountData.getSocialId());
		return registered;
	}

}
