package com.epam.idea.core.service.impl;

import java.util.List;

import com.epam.idea.core.model.User;
import com.epam.idea.core.model.UserSession;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.repository.UserSessionRepository;
import com.epam.idea.core.security.LoginRequest;
import com.epam.idea.core.security.PasswordHasher;
import com.epam.idea.core.service.UserSessionService;
import com.epam.idea.core.service.exception.UserNotFoundException;
import com.epam.idea.core.service.exception.UserSessionNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserSessionServiceImpl implements UserSessionService {

	@Autowired
	UserSessionRepository userSessionRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public void delete(UserSession deleted) {
		userSessionRepository.delete(deleted);
	}

	@Override
	public List<UserSession> findAll() {
		final List<UserSession> userSessionList = userSessionRepository.findAll();
		userSessionList.forEach(Hibernate::initialize);
		return userSessionList;
	}

	@Override
	@Transactional(readOnly = true)
	public UserSession findOne(String id) {
		return userSessionRepository.findOne(id).orElseThrow(() -> new UserSessionNotFoundException(id));
	}

	@Override
	public UserSession save(UserSession persisted) {
		return userSessionRepository.save(persisted);
	}

	@Override
	public UserSession deleteById(String sessionId) {
		final UserSession deleted = findOne(sessionId);
		userSessionRepository.delete(deleted);
		return deleted;
	}

	@Override
	public UserSession save(LoginRequest loginRequest) {
		User user = userRepository.findUserByEmailAndPassword(loginRequest.getEmail(), PasswordHasher.md5(loginRequest.getPassword())).orElseThrow(() -> new UserNotFoundException("This user does not exist"));
		UserSession userSession = new UserSession();
		userSession.setUser(user);
		UserSession savedUserSession = save(userSession);
		return savedUserSession;
	}
}
