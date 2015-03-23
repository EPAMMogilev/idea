package com.epam.idea.core.service;

import com.epam.idea.core.model.User;

public interface UserService extends BaseService<User, Long> {

	/**
	 * Deletes a user.
	 *
	 * @param userId The id of the deleted user.
	 * @return The deleted user.
	 * @throws com.epam.idea.core.service.exception.UserNotFoundException if no user was found with the given id.
	 */
	User deleteById(long userId);

	/**
	 * Updates the information of a user.
	 *
	 * @param userId The id of the user to updateUser.
	 * @param source The information of the updated user.
	 * @return The updated user.
	 * @throws com.epam.idea.core.service.exception.UserNotFoundException If no user was found with the given id.
	 */
	User update(long userId, User source);
}
