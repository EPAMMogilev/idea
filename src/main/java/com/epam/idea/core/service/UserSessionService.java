package com.epam.idea.core.service;

import com.epam.idea.core.model.User;
import com.epam.idea.core.model.UserSession;
import com.epam.idea.core.security.LoginRequest;


public interface UserSessionService extends BaseService<UserSession, String> {

	/**
	 * Deletes a user.
	 *
	 * @param sessionId The id of the deleted session.
	 * @return The deleted session.
	 * @throws com.epam.idea.core.service.exception.UserSessionNotFoundException if no session was found with the given id.
	 */
	UserSession deleteById(String sessionId);

	UserSession save(LoginRequest loginRequest);

	UserSession save(User user);
}
